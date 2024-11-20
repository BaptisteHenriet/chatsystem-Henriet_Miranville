package chatsystem.Core.Event;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class NetworkUDPMessageEvent extends Events {
    public DatagramPacket d_packet;
    public InetAddress addr;
    public NetworkUDPMessageEvent(DatagramPacket packet, InetAddress source ){
        super(CONNECT_UDP_EVENT);
        this.d_packet = packet;
        this.addr = source;
    }
}
