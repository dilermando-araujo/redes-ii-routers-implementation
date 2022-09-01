package entities;

public class App {
	public static void main(String[] args) {	
		Host h1 = new Host("H1", "192.168.0.1");
		Host h2 = new Host("H2", "192.168.0.2");
		Switch sw1 = new Switch(5);
		Switch sw2 = new Switch(5);
		
		sw1.connect(sw2, 0);
		sw2.connect(sw1, 0);
		
		sw1.connect(h1, 1);
		h1.connect(sw1);
		
		sw2.connect(h2, 1);
		h2.connect(sw2);
		
		h1.send("ok", "192.168.0.2");
	}
}
