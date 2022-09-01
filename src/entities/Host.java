package entities;

import java.util.HashMap;
import java.util.Map;

public class Host implements SendPacket {
	private String ip;
	private String mac;
	
	private SendPacket node;
	private Map<String, String> arpTable = new HashMap<String, String>();
	
	public Host(String mac, String ip) {
		this.ip = ip;
		this.mac = mac;
	}
	
	public void send(String payload, String ipTo) {
		
		Packet packet = new Packet();
		packet.setIpFrom(this.ip);
		packet.setMacFrom(this.mac);
		packet.setMacTo(this.searchMacByIp(ipTo));
		packet.setIpTo(ipTo);
		packet.setPayload(payload);
		
		this.send(packet);
	}
	
	@Override
	public void send(Packet packet) {
		if (packet.getMacTo().equals(this.mac)) {
			System.out.println("RECEBIDO");
			return;
		}
		
		if (this.node != null)
			this.node.send(packet);
	}
	
	public String searchMacByIp(String ipTo) {
		String mac = this.arpTable.get(ipTo);
		if (mac != null) return mac;
		
		arp(ipTo);
		
		return mac;
	}
	
	public void arp(String ipTo) {
		
		Packet packet = new Packet();
		packet.setIpFrom(this.ip);
		packet.setMacFrom(this.mac);
		packet.setMacTo("ff:ff:ff:ff:ff:ff");
		packet.setIpTo(ipTo);
		packet.setPayload("REQ");
		
		this.send(packet);
	}
	
	public void connect(SendPacket node) {
		this.node = node;
	}

	
	public String getIp() {
		return ip;
	}
	

	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getMac() {
		return mac;
	}
	

	public void setMac(String mac) {
		this.mac = mac;
	}
	

	public SendPacket getNode() {
		return node;
	}
	

	public void setNode(SendPacket node) {
		this.node = node;
	}
	

	public Map<String, String> getArpTable() {
		return arpTable;
	}
	

	public void setArpTable(Map<String, String> arpTable) {
		this.arpTable = arpTable;
	}
}
