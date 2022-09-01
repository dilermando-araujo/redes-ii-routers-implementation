package entities;

public class Packet {
	private String ipFrom;
	private String ipTo;
	private String macFrom;
	private String macTo;
	private String payload;
	
	public String getIpFrom() {
		return ipFrom;
	}
	public void setIpFrom(String ipFrom) {
		this.ipFrom = ipFrom;
	}
	public String getIpTo() {
		return ipTo;
	}
	public void setIpTo(String ipTo) {
		this.ipTo = ipTo;
	}
	public String getMacFrom() {
		return macFrom;
	}
	public void setMacFrom(String macFrom) {
		this.macFrom = macFrom;
	}
	public String getMacTo() {
		return macTo;
	}
	public void setMacTo(String macTo) {
		this.macTo = macTo;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
}
