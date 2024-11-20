package chatsystem.Tests;

import chatsystem.Core.Event.Events;
import chatsystem.Core.Event.NetworkUDPMessageEvent;
import chatsystem.Core.Network.UDPclient;
import chatsystem.Core.Network.UdpServer;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class NetworkTest {
    /** MOCKING DATE **/

    /******************/
    public final static int TEST_PORT = 1888;
    private static final Object MUTEX = new Object();
    static int COUNTER_UDPMESSAGE;
    static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    List<String> received_msg = new ArrayList<>();
    public void onEvent(Events e) {
        switch (e.name) {
            //case NetworkEvent.CONNECT_EVENT -> onNetworkConnectionEvent((NetworkConnectionEvent) e);
            case Events.CONNECT_UDP_EVENT -> onNetworkUDPMessageEvent((NetworkUDPMessageEvent) e);
            //case NetworkEvent.SAME_NICKNAME_EVENT -> onNetworkSameNickEvent((NetworkSameNickEvent e));
        }
    }
    public void onNetworkUDPMessageEvent(NetworkUDPMessageEvent e) {

        COUNTER_UDPMESSAGE += 1;
        System.out.println("Numéro du message reçu :" + COUNTER_UDPMESSAGE);
        String received = new String(e.d_packet.getData(), 0, e.d_packet.getLength());
        synchronized(MUTEX) {
            received_msg.add(received);
        }
        System.out.println("[" + ", IP: " + e.d_packet.getAddress() + ", Port: " + e.d_packet.getPort() + "]  " + received + " vient de se connecter.");

       ;
        //UDPMessage message = new UDPMessage(received, e.d_packet.getAddress());
        //System.out.println("Received" + message);
    }
    @Test
    void sendUDPTest() throws IOException, InterruptedException {
        UdpServer server = new UdpServer(TEST_PORT);
        server.addEventListener(this::onEvent);

        server.listen();
        sleep(1);

        UDPclient.sendLocalhost(TEST_PORT, "flo");

        UDPclient.sendLocalhost(TEST_PORT, "bap");

        UDPclient.sendLocalhost(TEST_PORT,"job");

    }
}
