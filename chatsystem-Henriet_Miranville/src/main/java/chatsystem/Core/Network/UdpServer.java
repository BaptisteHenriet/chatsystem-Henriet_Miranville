package chatsystem.Core.Network;

import chatsystem.Core.Event.NetworkUDPMessageEvent;
import chatsystem.Core.Event.Events;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UdpServer {

   public interface Observer {
       void handle(UDPMessage received);
   }
    private final DatagramSocket udp_socket;
    private final int udp_port;
    public final List<Observer> observers = new ArrayList<>();

    public final Logger LOGGER = LogManager.getLogger(UdpServer.class);
    private Consumer<Events> callback;

    public UdpServer(int port) throws SocketException {
        udp_socket = new DatagramSocket(port);
        udp_port = port;
        callback = null;
    }

    public void addObserver(Observer obs){
        this.observers.add(obs);
    }
    public void addEventListener(Consumer<Events> cb) {
        callback = cb;
    }

    public DatagramSocket getUdp_socket(){
        return udp_socket;
    }
    public void listen() throws IOException {
        final UdpServerListenerThread listener = new UdpServerListenerThread(udp_socket, udp_port);
        final Thread thread = new Thread(listener);

        listener.onMessage(this::onMessage);
        thread.start();
    }

    private void onMessage(DatagramPacket packet) {
        if (callback != null) callback.accept(new NetworkUDPMessageEvent(packet,packet.getAddress()));

    }
}





