package app;

public class ConnectHelper {

	public void connectHostToSwitch(Host host, Switch switchNode, int switchPort) {
		host.connect(switchNode.getPortByPosition(switchPort));
		switchNode.connect(host, switchPort);
	}
	
}
