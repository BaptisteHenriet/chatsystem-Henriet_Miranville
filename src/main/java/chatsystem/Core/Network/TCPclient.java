package chatsystem.Core.Network;
 
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPclient {
	public static Socket clientSocket;

	public static void startConnection(InetAddress ip ,int port) throws IOException{
		clientSocket = new Socket(ip, port);
		System.out.println("client Socket initialisé");
	}

	public static void tcpSend(Socket sock, String message) {
		try (Socket socket = new Socket(sock.getInetAddress(),sock.getPort());
			OutputStream output = socket.getOutputStream()){
			byte[] buf = message.getBytes(StandardCharsets.UTF_8);
			output.write(buf);
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void stopConnec() throws IOException{clientSocket.close();}

}

