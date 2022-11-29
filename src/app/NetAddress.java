package app;

import java.util.Objects;

public class NetAddress {
	String address;
	Integer mask;
	
	public NetAddress() {}
	
	public NetAddress(String address, Integer mask) {
		this.address = address;
		this.mask = mask;
	}
	
	public String getAddress() {
		return address;
	}
	public NetAddress setAddress(String address) {
		this.address = address;
		return this;
	}
	public Integer getMask() {
		return mask;
	}
	public NetAddress setMask(Integer mask) {
		this.mask = mask;
		return this;
	}
	
	@Override
	public String toString() {
		return address + "/" + mask;
	}
	
	public static NetAddress parse(String ip) {
		String[] netAddressParts = ip.split("/");	
		return (new NetAddress()).setAddress(netAddressParts[0]).setMask(Integer.parseInt(netAddressParts[1]));
	}
	
}
