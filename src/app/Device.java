package app;

public interface Device {
	void send(Packet packet);
	void receive(Packet packet);
}
