package chatsystem.Core.Network;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPclient {
	public static DatagramSocket socket;
	
	public static void send(InetAddress dest,int port, String message) {
		try {
			socket = new DatagramSocket();
			byte[] buf = message.getBytes(StandardCharsets.UTF_8);
	
			DatagramPacket broad = new DatagramPacket(buf, buf.length, dest, port);
			
			socket.send(broad);
		}catch(SocketException se){
			System.out.println("Socket error : "+se.getMessage());
		}catch(IOException e){
			System.out.println("IO error :"+e.getMessage());
		}

	}
	public static void sendLocalhost(int port, String message){
		try {
			send(InetAddress.getLocalHost(), port, message);
		}catch(UnknownHostException e){
			System.out.println("Unknown host error : "+e.getMessage());
		}
	}
}