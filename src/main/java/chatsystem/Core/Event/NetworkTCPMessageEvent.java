package chatsystem.Core.Event;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class NetworkTCPMessageEvent extends Events {
    public DatagramPacket d_packet;
    public InetAddress addr;
    public NetworkTCPMessageEvent(DatagramPacket packet, InetAddress source ){
        super(CONNECT_EVENT);
        this.d_packet = packet;
        this.addr = source;
    }
}
