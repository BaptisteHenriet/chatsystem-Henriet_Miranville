package chatsystem;

import chatsystem.Core.Controller.Controller;
import chatsystem.Core.Event.NetworkConnectionEvent;
import chatsystem.Core.Event.NetworkTCPMessageEvent;
import chatsystem.Core.Event.NetworkUDPMessageEvent;
import chatsystem.Core.Event.Events;
import chatsystem.Core.Network.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import chatsystem.Interface.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {
	
	public Main() throws IOException {
	}
	
	private volatile static int Count = 1;			// Compte le nombre de fenêtres ouvertes pour déterminer si le programme doit fermer ou non lors du dispose() de la fenêtre actuelle
	
	public static final Logger LOGGER = LogManager.getLogger(Main.class);
	public static final int PORT = 5001;
	final UdpServer udp_server = new UdpServer(PORT);
	final TcpServer tcp_server = new TcpServer(PORT);

	public static void main(String[] args) throws IOException  {

		new Main().run();
        
		UserTable.setList(new ArrayList<User>());

		UDPclient.send(InetAddress.getByName("255.255.255.255"), 5001, "discover");

        ContactList contacts = ContactList.getInstance();
		for(Contact c : contacts.getContacts()) {
			System.out.println("Nom : "+ c.username());
		}
		ConnectionPannel.createConn();
		while(Count>0);
		System.out.println("Fin");
		System.exit(0);
	}
	public void run() throws IOException {
		Configurator.setRootLevel(Level.INFO);

		/** Lancement TCP server **/
		tcp_server.addObserver(received -> Controller.handleTCPMessage(received));
		tcp_server.addEventListener(this::onEvent);
		tcp_server.listen();
		LOGGER.info("Serveur TCP lancé.");
		View.initialize();
		View2.initialize();

		/** Lancement UDP server **/
		udp_server.addObserver(received -> Controller.handleContactDiscoveryMessage(received));
		udp_server.addEventListener(this::onEvent);
		udp_server.listen();
		LOGGER.info("Serveur UDP lancé.");
		
	}
	
	public static void iterateCount() {
		Count++;
	}
	
	public static void setCount(int c) {
		Count = c;
	}
	
	public static int getCount() {
		return Count;
	}
	
	public synchronized void onEvent(Events e) {
		switch (e.name) {
			case Events.CONNECT_EVENT -> onNetworkConnectionEvent((NetworkConnectionEvent) e);
			case Events.CONNECT_UDP_EVENT -> onNetworkUDPMessageEvent((NetworkUDPMessageEvent) e);
			case Events.CONNECT_TCP_EVENT -> onNetworkTCPMessageEvent((NetworkTCPMessageEvent) e);

		}
	}
	public void onNetworkConnectionEvent(NetworkConnectionEvent e) {
		System.out.println(e.socket);
		System.out.println("["+"IP: " + e.socket.getInetAddress() + ", Port: " + e.socket.getPort() + "] ");

		 }
	
	public void onNetworkTCPMessageEvent(NetworkTCPMessageEvent e) {
		String received = new String(e.d_packet.getData(), 0, e.d_packet.getLength());
		TCPMessage message = new TCPMessage(received,e.d_packet.getAddress());
		synchronized(tcp_server.observers){
			for(TcpServer.Observer obs : tcp_server.observers) {
				obs.handle(message);
			}
		}
	}
	
	public void onNetworkUDPMessageEvent(NetworkUDPMessageEvent e) {
		String received = new String(e.d_packet.getData(), 0, e.d_packet.getLength());
		UDPMessage message = new UDPMessage(received,e.d_packet.getAddress());

		udp_server.LOGGER.trace("Received on port " + e.d_packet.getPort() + ": "+ message.content()+ " from "+ message.origin());
		synchronized(udp_server.observers) {
			for (UdpServer.Observer obs : udp_server.observers) {
				obs.handle(message);
			}
		}

	}
}
