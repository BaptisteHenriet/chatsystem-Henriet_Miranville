package chatsystem.Core.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

import chatsystem.Core.Controller.Controller;

public class TcpServerListenerThread implements Runnable {
	private final ServerSocket m_socket;
	private final int m_port;
	private Consumer<Socket> callback;

	
	public TcpServerListenerThread(ServerSocket socket, int port) throws IOException {
		m_socket = socket;
		m_port = port;
		callback = null;
	}
	
	public void onConnect(Consumer<Socket> cb) {
		callback = cb;
	}
	@Override 
	public void run() {
		try {
			while (true) {
				Socket socket = m_socket.accept();
				ClientHandler handle = new ClientHandler(socket);
				handle.start();
				System.out.println("Connection OK from "+ socket.getInetAddress());
				try {
					handle.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				}
	
		} catch (IOException e) { e.printStackTrace(); }
	}
	private class ClientHandler extends Thread{
		private final Socket sock;
		public ClientHandler(Socket sock) {
			this.sock = sock;
		}
		
		public void run() {
			try{
				String inputLine;
				BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				PrintWriter writer  = new PrintWriter(sock.getOutputStream(), true);
				while((inputLine=reader.readLine())!=null) {
					Controller.handleTCPMessage(new TCPMessage(inputLine,sock.getInetAddress()));
					writer.println("inputLine");
				}
			}catch(IOException io) {
				io.printStackTrace();
			}

			}
		
	}
}

	
