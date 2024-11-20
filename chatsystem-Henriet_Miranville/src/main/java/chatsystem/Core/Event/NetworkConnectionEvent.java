package chatsystem.Core.Event;

import java.net.Socket;

public class NetworkConnectionEvent extends Events {
	public Socket socket;
	public NetworkConnectionEvent(Socket socket) {
		super(CONNECT_EVENT);
		this.socket = socket;
	}
}
