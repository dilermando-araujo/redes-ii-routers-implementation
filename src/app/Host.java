package app;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Host implements DeviceInterface {
	private String mac;
	private String ip;
	
	private DeviceInterface node;
	
	private Map<String, String> arpTable = new HashMap<>();
	
	public void showArpTable() {
		for (Map.Entry<String, String> entry : this.arpTable.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	@Override
	public void send(Packet packet) {
		
		if (packet.getPayload().startsWith("ARP:REQ")) {
			if (packet.getIpTo().equals(this.ip)) {
				Packet arpPacket = new Packet();
				
				arpPacket.setIpFrom(this.ip);
				arpPacket.setMacFrom(this.mac);
				arpPacket.setIpTo(ip);
				arpPacket.setMacTo("ff:ff:ff:ff:ff:ff");
				arpPacket.setPayload("ARP:REP");
				
				new Thread() {
					public void run() {
						node.send(arpPacket);
					}
				}.start();
			}
		}
		
		if (packet.getPayload().startsWith("ARP:")) {
			arpTable.put(packet.getIpFrom(), packet.getMacFrom());
		}
		
		if (packet.getMacTo().equals(this.mac)) {
			this.receive(packet);
			
			return;
		}
	}
	
	public void receive(Packet packet) {			
		System.out.println(this.mac + ": " + packet.getPayload());
	}
	
	public void connect(DeviceInterface node) {
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
	
	public String getMacByIp(String ip) {
		String mac = this.arpTable.get(ip);
		if (mac != null) return mac;
		
		Packet arpPacket = new Packet();
		
		arpPacket.setIpFrom(this.ip);
		arpPacket.setMacFrom(this.mac);
		arpPacket.setIpTo(ip);
		arpPacket.setMacTo("ff:ff:ff:ff:ff:ff");
		arpPacket.setPayload("ARP:REQ");
		
		DeviceInterface node = this.node;
		new Thread() {
			public void run() {
				node.send(arpPacket);
			}
		}.start();
		
		LocalDateTime startAt = LocalDateTime.now();
		while (mac == null && ChronoUnit.SECONDS.between(startAt, LocalDateTime.now()) < 5) {
			mac = this.arpTable.get(ip);
		}
		
		if (mac == null) throw new RuntimeException();
		return mac;
	}
	
	public void createAndSendPacket(String payload, String ipDestination) {
		Packet packet = new Packet();
		
		packet.setIpFrom(this.ip);
		packet.setMacFrom(this.mac);
		packet.setIpTo(ipDestination);
		packet.setMacTo(this.getMacByIp(ipDestination));
		packet.setPayload(payload);
		
		DeviceInterface node = this.node;
		
		new Thread() {
			public void run() {
				node.send(packet);
			}
		}.start();
	}
}
