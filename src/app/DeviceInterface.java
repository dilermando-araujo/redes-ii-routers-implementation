package app;

public interface DeviceInterface {
	void send(Packet packet);
	void receive(Packet packet);
	void connect(DeviceInterface connect);
}
