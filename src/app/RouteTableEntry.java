package app;

public class RouteTableEntry {

	private NetAddress addressNet;
	private boolean directlyConnected;
	private RouteInterface outInterface;
	private String nextHop;
	
	public NetAddress getAddressNet() {
		return addressNet;
	}

	public RouteTableEntry setAddressNet(NetAddress addressNet) {
		this.addressNet = addressNet;
		return this;
	}

	public boolean isDirectlyConnected() {
		return directlyConnected;
	}
	
	public RouteTableEntry setDirectlyConnected(boolean directlyConnected) {
		this.directlyConnected = directlyConnected;
		return this;
	}
	
	public RouteInterface getOutInterface() {
		return outInterface;
	}
	
	public RouteTableEntry setOutInterface(RouteInterface outInterface) {
		this.outInterface = outInterface;
		return this;
	}
	
	public String getNextHop() {
		return nextHop;
	}
	
	public RouteTableEntry setNextHop(String nextHop) {
		this.nextHop = nextHop;
		return this;
	}
	
	
}
