package app;

import java.util.HashMap;
import java.util.Map;

public class MacTable {

	Map<String, SwitchPort> macTable = new HashMap<>();
	
	public void learnMacFromPacket(Packet packet, SwitchPort port) {
		macTable.put(packet.getMacFrom(), port);
	}
	
	public SwitchPort getPacketDestinationPort(Packet packet) {
		return macTable.get(packet.getMacTo());
	}
	
}
