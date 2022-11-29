package app;

public class Application {
	
	public static void main(String[] args) {
		ConnectHelper connectHelper = new ConnectHelper();
		
		Host h1 = new Host();
		h1.setMac("H1").setIp("192.168.0.2").setMask(24).setGateway("192.168.0.1");
		
		Host h2 = new Host();
		h2.setMac("H2").setIp("192.168.0.3").setMask(24).setGateway("192.168.0.1");
		
		Host h3 = new Host();
		h3.setMac("H3").setIp("192.168.0.4").setMask(24).setGateway("192.168.0.1");
		
		Switch s1 = new Switch(5); // Switch with 5 ports.
		
		connectHelper.connect(h1, s1.getPortByPosition(0));
		connectHelper.connect(h2, s1.getPortByPosition(1));
		connectHelper.connect(h3, s1.getPortByPosition(2));
		
		Router r1 = new Router(2);
		LoadRouterStaticRoutes.r1(r1);
		
		r1.getInterface(0).setMac("R1-1").setIp("192.168.0.1").setMask(24);
		r1.getInterface(1).setMac("R1-2").setIp("192.168.1.1").setMask(24);
		
		connectHelper.connect(s1.getPortByPosition(3), r1.getInterface(0));
		
		Switch s2 = new Switch(5);
		
		connectHelper.connect(r1.getInterface(1), s2.getPortByPosition(0));
		
		Host h4 = new Host();
		h4.setMac("H4").setIp("192.168.1.2").setMask(24).setGateway("192.168.1.1");
		
		connectHelper.connect(h4, s2.getPortByPosition(1));
		
		Router r2 = new Router(2);
		LoadRouterStaticRoutes.r2(r2);
		
		r2.getInterface(0).setMac("R2-1").setIp("192.168.1.100").setMask(24);
		r2.getInterface(1).setMac("R2-2").setIp("192.168.2.1").setMask(24);
		
		connectHelper.connect(r2.getInterface(0), s2.getPortByPosition(2));
		
		Host h5 = new Host();
		h5.setMac("H5").setIp("192.168.2.2").setMask(24).setGateway("192.168.2.1");
		
		connectHelper.connect(h5, r2.getInterface(1));
		
		h1.createAndSendPacket("OI", "192.168.2.2");
		h5.createAndSendPacket("OI", "192.168.0.2");
		h4.createAndSendPacket("OI", "192.168.0.2");
		h2.createAndSendPacket("OI", "192.168.1.2");
	}
	
}
