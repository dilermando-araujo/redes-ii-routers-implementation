package app;

public class Host implements DeviceInterface {
	private String mac;
	private String ip;
	private Integer mask;
	private String gateway;
	
	private DeviceInterface node;
	
	private ArpTable arpTable = new ArpTable();
	
	private NetService netService = new NetService();
	
	@Override
	public void send(Packet packet) {
		
		if (packet.getPayload().startsWith("ARP:")) {
			arpTable.learnIpMac(packet);
			
			if (packet.getPayload().startsWith("ARP:REQ")) {
				arpTable.responseArpRequest(packet, this.ip, this.mac, packet.getIpFrom(), this.node);
			}
		}
		
		if (packet.getMacTo().equals(this.mac)) {
			this.receive(packet);
			
			return;
		}
	}
	
	public void receive(Packet packet) {
		System.out.println(String.format("[%s -> %s] %s", 
			packet.getIpFrom(), packet.getIpTo(), packet.getPayload()
		));
	}
	
	public void connect(DeviceInterface node) {
		this.node = node;
	}
	
	public Host setMac(String mac) {
		this.mac = mac;
		return this;
	}
	
	public Host setIp(String ip) {
		this.ip = ip;
		return this;
	}
	
	public Host setMask(Integer mask) {
		this.mask = mask;
		return this;
	}
	
	public Host setGateway(String gateway) {
		this.gateway = gateway;
		return this;
	}
	
	public String getMacByIp(String ip) {
		String mac = arpTable.getMacByIp(ip);
		if (mac != null) return mac;
		
		return arpTable.executeArpRequest(this.ip, this.mac, ip, this.node);
	}
	
	public void createAndSendPacket(String payload, String ipDestination) {
		String hostAddressNet = netService.getAddressNetByIpAsString(this.ip, this.mask);
		Packet packet = new Packet();
		
		packet.setIpFrom(this.ip);
		packet.setMacFrom(this.mac);
		packet.setIpTo(ipDestination);
		
		packet.setMacTo(
			netService.checkIpInNet(ipDestination, hostAddressNet, this.mask)
				? this.getMacByIp(ipDestination)
				: this.getMacByIp(this.gateway)
		);
		
		packet.setPayload(payload);
		
		DeviceInterface node = this.node;
		
		new Thread() {
			public void run() {
				node.send(packet);
			}
		}.start();
	}
}
