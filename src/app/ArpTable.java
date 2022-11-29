package app;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ArpTable {

	private Map<String, String> arpTable = new HashMap<>();
	
	public void learnIpMac(Packet arpPacket) {
		arpTable.put(arpPacket.getIpFrom(), arpPacket.getMacFrom());
	}
	
	public String getMacByIp(String ip) {
		return this.arpTable.get(ip);
	}
	
	public void showArpTable() {
		for (Map.Entry<String, String> entry : this.arpTable.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public String executeArpRequest(String deviceIp, String deviceMac, String ipDestination, Consumer<Packet> send) {
		Packet arpPacket = new Packet();
		
		arpPacket.setIpFrom(deviceIp);
		arpPacket.setMacFrom(deviceMac);
		arpPacket.setIpTo(ipDestination);
		arpPacket.setMacTo("ff:ff:ff:ff:ff:ff");
		arpPacket.setPayload("ARP:REQ");
		
		new Thread() {
			public void run() {
				send.accept(arpPacket);
			}
		}.start();
		
		String mac = null;
		LocalDateTime startAt = LocalDateTime.now();
		while (mac == null && ChronoUnit.SECONDS.between(startAt, LocalDateTime.now()) < 5) {
			mac = arpTable.get(ipDestination);
		}
		
		if (mac == null) throw new RuntimeException();
		return mac;
	}
	
	public String executeArpRequest(String deviceIp, String deviceMac, String ipDestination, DeviceInterface deviceInterface) {
		return executeArpRequest(deviceIp, deviceMac, ipDestination, packet -> deviceInterface.send(packet));
	}
	
	public Packet getResponseArpRequestPacket(Packet arpRequestPacket, String deviceIp, String deviceMac, String ipDestination, DeviceInterface deviceInterface) {
		Packet arpPacket = new Packet();
		
		arpPacket.setIpFrom(deviceIp);
		arpPacket.setMacFrom(deviceMac);
		arpPacket.setIpTo(ipDestination);
		arpPacket.setMacTo("ff:ff:ff:ff:ff:ff");
		arpPacket.setPayload("ARP:REP");
		
		return arpPacket;
	}
	
	public void responseArpRequest(Packet arpRequestPacket, String deviceIp, String deviceMac, String ipDestination, DeviceInterface deviceInterface) {
		if (arpRequestPacket.getIpTo().equals(deviceIp)) {
			Packet arpPacket = this.getResponseArpRequestPacket(arpRequestPacket, deviceIp, deviceMac, ipDestination, deviceInterface);
			
			new Thread() {
				public void run() {
					deviceInterface.send(arpPacket);
				}
			}.start();
		}
	}
	
}
