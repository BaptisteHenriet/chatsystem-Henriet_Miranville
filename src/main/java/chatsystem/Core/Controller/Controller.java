package chatsystem.Core.Controller;

import chatsystem.Core.Exceptions.ContactAlreadySaved;
import chatsystem.Core.Network.TCPMessage;
import chatsystem.Core.Network.UDPMessage;
import chatsystem.Core.Network.UDPclient;
import chatsystem.Interface.Contact;
import chatsystem.Interface.ContactList;
import chatsystem.Interface.Historique;
import chatsystem.Interface.ChatWindow;
import chatsystem.Interface.User;
import chatsystem.Interface.UserTable;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Controller {

	private static InetAddress localAdd = null;
	
    private static Logger LOGGER = LogManager.getLogger(Controller.class);
	public synchronized static void handleContactDiscoveryMessage(UDPMessage message){
		if (localAdd == null) {
			try (final DatagramSocket datagramSocket = new DatagramSocket()) {
			    datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
			    localAdd = datagramSocket.getLocalAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
		}
        ContactList contacts = ContactList.getInstance();
        UserTable usertable = UserTable.getInstance();
        Contact contact = null;

        try {
        	if(message.content().equals("discover")) {
        		if (!(message.origin().equals(localAdd)) && contacts.getSelf() !=null) {
	            		UDPclient.send(message.origin(), 5001, contacts.getSelf().username());
	        		}
        	}
        	else if(message.content().equals("close")) {
        		if(!(message.origin().equals(localAdd))) {
        			System.out.println("Closing connection");
	        		User sender = UserTable.getByAddress(message.origin());
	        		UserTable.getTable().remove(sender);
	        		contacts.getContacts().remove(sender.getContact());
	        		ChatWindow.repack();
        		}
        	}
        	else {
        		if (!(message.origin().equals(localAdd))) {
        			
	        		contact = contacts.addUser(message.content());
	    			LOGGER.info("New contact added : "+ message.content());
	    			usertable.addUser(contact,message.origin());
	    			if(ChatWindow.getUserList()!=null) ChatWindow.repack();
        		}
        	}

        }catch (ContactAlreadySaved e){
            LOGGER.error("Received a contact already in the contact list: "+ message.content());
        } 
	}


    public static void handleTCPMessage(TCPMessage message){
    	LOGGER.info("New message received : "+ message.content() + " from " + message.origin());
        User sender = UserTable.getByAddress(message.origin());
        Historique.addHisto(sender.getLogin(), message.content(), 0);
        ChatWindow.repack();
    }

}

