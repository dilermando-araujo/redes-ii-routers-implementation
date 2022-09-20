package app;

public class SwitchPort implements Device {

	private Integer port;
	private Device node;
	private Switch manager;
	
	public Integer getPort() {
		return this.port;
	}
	
	public SwitchPort(int port, Switch switchNode) {
		this.port = port;
		this.manager = switchNode;
	}
	
	@Override
	public void send(Packet packet) {
		this.manager.send(packet, this);
	}

	public void connect(Device node) {
		this.node = node;
	}

	@Override
	public void receive(Packet packet) {
		Device node = this.node;
		
		new Thread() {
			public void run() {
				if (node != null) node.send(packet);
			}
		}.start();
	}
	
}
