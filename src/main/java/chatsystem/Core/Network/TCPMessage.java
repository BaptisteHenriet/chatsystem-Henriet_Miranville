package chatsystem.Core.Network;

import java.net.InetAddress;

public record TCPMessage(String content, InetAddress origin) {

}