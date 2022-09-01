package app;

public class Application {
	
	public static void main(String[] args) {
		ConnectHelper connectHelper = new ConnectHelper();
		
		Host h1 = new Host();
		h1.setMac("H1").setIp("192.168.0.1");

		Host h2 = new Host();
		h2.setMac("H2").setIp("192.168.0.2");
		
		Switch s1 = new Switch(5);
		
		connectHelper.connectHostToSwitch(h1, s1, 0);
		connectHelper.connectHostToSwitch(h2, s1, 1);
		
		new Thread() {
			public void run() {
				h1.createAndSendPacket("OI", "192.168.0.2");			
			}
		}.start();
		
		new Thread() {
			public void run() {
				h1.createAndSendPacket("OI", "192.168.0.2");			
			}
		}.start();
	}
	
}
