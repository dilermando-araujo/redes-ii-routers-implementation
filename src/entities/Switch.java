package entities;

import java.util.Map;

public class Switch implements SendPacket {
	private SendPacket[] ports;
	private Map<String, Integer> macTable;
	
	public Switch(int portsNumber) {
		this.ports = new SendPacket[portsNumber];
	}
	
	public void send(Packet packet) {
		if (packet.getMacTo().equals("ff:ff:ff:ff:ff:ff")) this.broadcast(packet);
		
		Integer portDestination = this.macTable.get(packet.getMacTo());
		if (portDestination == null) 
			this.broadcast(packet);
		else
			this.ports[portDestination].send(packet);
	}
	
	public void broadcast(Packet packet) {
		for (SendPacket host : ports) {
			if (host != null) host.send(packet);
		}
	}
	
	public void connect(SendPacket host, int port) {
		this.ports[port] = host;
	}
}
