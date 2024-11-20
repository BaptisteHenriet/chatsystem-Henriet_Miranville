package chatsystem.Core.Network;


import chatsystem.Core.Event.NetworkConnectionEvent;
import chatsystem.Core.Event.Events;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TcpServer {
	
	public interface Observer {
		void handle(TCPMessage received);
	}
	
	private final ServerSocket tcp_socket;
	private final int tcp_port;
	private Consumer<Events> callback;
    public final List<Observer> observers = new ArrayList<>();

	public TcpServer(int port) throws IOException {
		tcp_socket = new ServerSocket(port);
		tcp_port = port;
		callback = null;
	}
	public void addEventListener(Consumer<Events> cb) {
		callback = cb;
	}
	
	 public void addObserver(Observer obs){
	        this.observers.add(obs);
	    }
	
	public void listen() throws IOException {
		final TcpServerListenerThread listener = new TcpServerListenerThread(tcp_socket, tcp_port);
		final Thread thread = new Thread(listener);
		listener.onConnect(this::onConnect);
		thread.start();
	}
	private void onConnect(Socket socket) {
		if (callback != null) callback.accept(new NetworkConnectionEvent(socket));
	}
}
