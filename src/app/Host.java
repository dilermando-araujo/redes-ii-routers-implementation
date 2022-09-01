package app;

public class Host implements SendPacket {
	private String mac;
	private String ip;
	
	private SendPacket node;

	@Override
	public void send(Packet packet) {
		if (packet.getMacTo().equals(this.mac)) {
			this.receive(packet);
			
			return;
		}
	}
	
	public void receive(Packet packet) {
		System.out.println("RECEBI");
	}
	
	public void connect(SendPacket node) {
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
	
	public void createAndSendPacket(String payload, String ipDestination) {
		Packet packet = new Packet();
		
		packet.setIpFrom(this.ip);
		packet.setMacFrom(this.mac);
		packet.setIpTo(ipDestination);
		packet.setMacTo("H2");
		packet.setPayload(payload);
		
		this.node.send(packet);
	}
}
