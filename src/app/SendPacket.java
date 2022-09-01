package app;

public interface SendPacket {
	void send(Packet packet);
	void receive(Packet packet);
}
