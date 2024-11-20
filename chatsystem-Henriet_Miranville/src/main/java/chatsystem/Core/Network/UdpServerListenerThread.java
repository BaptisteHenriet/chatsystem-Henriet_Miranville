package chatsystem.Core.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.function.Consumer;

public class UdpServerListenerThread implements Runnable {
	private final DatagramSocket my_socket;
	private Consumer<DatagramPacket> callback;


    public UdpServerListenerThread(DatagramSocket socket, int port) {
    	my_socket = socket;
		callback = null;
    }
	public void onMessage(Consumer<DatagramPacket> cb){
		callback = cb;
	}
	@Override
	public void run() {
		try {
			while (true) {
				byte[] buf = new byte[1024];
				DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
				my_socket.receive(inPacket);
				if (callback != null) callback.accept(inPacket);
			}
		}catch(IOException e){
			System.out.println("IO error"+e.getMessage());
		}
	}
}
