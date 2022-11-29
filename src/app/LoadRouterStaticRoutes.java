package app;

public class LoadRouterStaticRoutes {

	public static void r1(Router r1) {
		r1.registryRouteEntry(
			(new RouteTableEntry()).setAddressNet(NetAddress.parse("192.168.0.0/24")).setDirectlyConnected(true).setOutInterface(r1.getInterface(0))
		);
		
		r1.registryRouteEntry(
			(new RouteTableEntry()).setAddressNet(NetAddress.parse("192.168.1.0/24")).setDirectlyConnected(true).setOutInterface(r1.getInterface(1))
		);
		
		r1.registryRouteEntry(
			(new RouteTableEntry()).setAddressNet(NetAddress.parse("192.168.2.0/24")).setDirectlyConnected(false).setNextHop("192.168.1.100")
		);
	}
	
	public static void r2(Router r2) {
		r2.registryRouteEntry(
			(new RouteTableEntry()).setAddressNet(NetAddress.parse("192.168.2.0/24")).setDirectlyConnected(true).setOutInterface(r2.getInterface(1))
		);
		
		r2.registryRouteEntry(
			(new RouteTableEntry()).setAddressNet(NetAddress.parse("192.168.1.0/24")).setDirectlyConnected(true).setOutInterface(r2.getInterface(0))
		);
		
		r2.registryRouteEntry(
			(new RouteTableEntry()).setAddressNet(NetAddress.parse("192.168.0.0/24")).setDirectlyConnected(false).setNextHop("192.168.1.1")
		);
	}
	
}
