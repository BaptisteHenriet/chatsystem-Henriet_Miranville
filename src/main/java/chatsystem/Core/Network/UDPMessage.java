package chatsystem.Core.Network;

import java.net.InetAddress;

public record UDPMessage(String content, InetAddress origin) {

}
