package app;

import java.util.Optional;

public class Router {

	private RouteInterface[] interfaces;
	
	private ArpTable arpTable = new ArpTable();
	private RouteTable routeTable = new RouteTable();
	
	public Router(int interfaces) {
		this.interfaces = new RouteInterface[interfaces];
		
		for (int i = 0; i < interfaces; i++) {
			this.interfaces[i] = new RouteInterface(this);
		}
	}
	
	public void registryRouteEntry(RouteTableEntry entry) {
		this.routeTable.addEntry(entry);
	}
	
	public RouteInterface getInterface(int n) {
		return this.interfaces[n];
	}
	
	public String getMacByIp(String ip, RouteInterface routeInterface) {
		String mac = arpTable.getMacByIp(ip);
		if (mac != null) return mac;
		
		return arpTable.executeArpRequest(routeInterface.getIp(), routeInterface.getMac(), ip, packet -> routeInterface.receive(packet));
	}
	
	public void send(Packet packet, RouteInterface routeInterface) {
		
		if (packet.getPayload().startsWith("ARP:")) {
			arpTable.learnIpMac(packet);
			
			if (packet.getPayload().startsWith("ARP:REQ")) {
				Packet arpResponsePacket = arpTable.getResponseArpRequestPacket(
					packet, routeInterface.getIp(), routeInterface.getMac(), packet.getIpFrom(), routeInterface
				);
				
				routeInterface.receive(arpResponsePacket);
			}
			
			return;
		}
		
		Optional<RouteTableEntry> routeTableEntryOpt = routeTable.getEntryByIp(packet.getIpTo());
		if (routeTableEntryOpt.isEmpty()) return;
		
		RouteTableEntry routeTableEntry = routeTableEntryOpt.get();
		if (routeTableEntry.isDirectlyConnected()) {
			packet.setMacFrom(routeTableEntry.getOutInterface().getMac());
			packet.setMacTo(this.getMacByIp(packet.getIpTo(), routeTableEntry.getOutInterface()));
			
			routeTableEntry.getOutInterface().receive(packet);
		} else {
			String nextHopIp = routeTableEntry.getNextHop();
			RouteTableEntry nextHopRouteTableEntry = routeTable.getEntryByIp(nextHopIp).get();
			
			packet.setMacFrom(nextHopRouteTableEntry.getOutInterface().getMac());
			packet.setMacTo(this.getMacByIp(nextHopIp, nextHopRouteTableEntry.getOutInterface()));
			
			nextHopRouteTableEntry.getOutInterface().receive(packet);
		}
		
	}
	
}
