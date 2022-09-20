package app;

public class Application {
	
	public static void main(String[] args) {
		ConnectHelper connectHelper = new ConnectHelper();
		
		Host h1 = new Host();
		h1.setMac("H1").setIp("192.168.0.1");

		Host h2 = new Host();
		h2.setMac("H2").setIp("192.168.0.2");
		
		Host h3 = new Host();
		h3.setMac("H3").setIp("192.168.0.3");
		
		Switch s1 = new Switch(5); // Switch with 5 ports.
		
		connectHelper.connect(h1, s1.getPortByPosition(0));
		connectHelper.connect(h2, s1.getPortByPosition(1));
		connectHelper.connect(h3, s1.getPortByPosition(2));
		
		h1.createAndSendPacket("OI", "192.168.0.2");
		h2.createAndSendPacket("OI", "192.168.0.1");
	}
	
}
