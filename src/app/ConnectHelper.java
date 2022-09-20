package app;

public class ConnectHelper {

	public void connect(DeviceInterface in, DeviceInterface out) {
		in.connect(out);
		out.connect(in);
	}

}
