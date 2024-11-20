package chatsystem.Core.Event;

public class Events {
	public static final String CONNECT_EVENT = "CONNECT_EVENT";

	public static final String CONNECT_TCP_EVENT = "CONNECT_TCP_EVENT";
	
	public static final String CONNECT_UDP_EVENT = "CONNECT_UDP_EVENT";

	public final String name;
	
	public Events(String n) {
		name = n;
	}

	
}
