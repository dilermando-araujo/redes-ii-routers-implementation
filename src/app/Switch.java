package app;

public class Switch {
	
	SwitchPort[] ports;
	MacTable macTable = new MacTable();
	
	public Switch(int ports) {
		this.ports = new SwitchPort[ports];
		
		for (int i = 0; i < ports; i++) {
			this.ports[i] = new SwitchPort(i, this);
		}
	}
	
	public void send(Packet packet, SwitchPort port) {
		if (packet.getMacTo().equals("ff:ff:ff:ff:ff:ff")) {
			this.broadcast(packet, port);
			return;
		}
		
		macTable.learnMacFromPacket(packet, port);
		
		SwitchPort portDestination = macTable.getPacketDestinationPort(packet);
		if (portDestination != null)
			portDestination.receive(packet);
		else
			this.broadcast(packet, port);
	}
	
	public void broadcast(Packet packet, SwitchPort portSource) {
		for (SwitchPort switchPort : this.ports) {
			if (switchPort == portSource) continue;
			
			switchPort.receive(packet);
		}
	}
	
	public void connect(DeviceInterface node, int port) {
		this.ports[port].connect(node);
	}
	
	public SwitchPort getPortByPosition(int position) {
		return this.ports[position];
	}
}
