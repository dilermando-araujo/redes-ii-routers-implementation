package app;

public class SwitchPort implements SendPacket {

	private Integer port;
	private SendPacket node;
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

	public void connect(SendPacket node) {
		this.node = node;
	}

	@Override
	public void receive(Packet packet) {
		SendPacket node = this.node;
		
		new Thread() {
			public void run() {
				if (node != null) node.send(packet);
			}
		}.start();
	}
	
}
