package app;

public class RouteInterface implements DeviceInterface {

	private String ip;
	private String mac;
	private Integer mask;
	private Router manager;
	private DeviceInterface node;

	public RouteInterface(Router manager) {
		this.manager = manager;
	}
	
	@Override
	public void send(Packet packet) {
		this.manager.send(packet, this);
	}
	
	@Override
	public void receive(Packet packet) {
		DeviceInterface node = this.node;
		
		new Thread() {
			public void run() {
				if (node != null) node.send(packet);
			}
		}.start();
	}
	
	@Override
	public void connect(DeviceInterface node) {
		this.node = node;
	}

	public String getIp() {
		return ip;
	}

	public RouteInterface setIp(String ip) {
		this.ip = ip;
		return this;
	}

	public RouteInterface setMask(Integer mask) {
		this.mask = mask;
		return this;
	}
	
	public String getMac() {
		return mac;
	}

	public RouteInterface setMac(String mac) {
		this.mac = mac;
		return this;
	}
	
}
