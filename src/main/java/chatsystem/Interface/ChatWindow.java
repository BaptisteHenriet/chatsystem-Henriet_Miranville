package chatsystem.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import chatsystem.Main;
import chatsystem.Core.Network.TCPclient;
import chatsystem.Core.Network.UDPclient;
import chatsystem.DATABASES.BddAccess;

import javax.swing.*;


public class ChatWindow {
	private static ArrayList<JButton> listUserB = new ArrayList<JButton>();
	private static ArrayList<Message> listMess = new ArrayList<Message>();
	private static ArrayList<JLabel> lisLabL = new ArrayList<JLabel>();
	private static ArrayList<JLabel> lisLabR = new ArrayList<JLabel>();
	
	private static JFrame f = null;

	ContactList contacts = ContactList.getInstance();
	private static JPanel chatPanR = null;
	private static JPanel chatPanL = null;
	private static JPanel userList = null;
	
	private static Socket sock = null;
	
	private static User user;

	public static JPanel getUserList(){
		return userList;
	}
	
	public static void createChatWindow() {
		Main.iterateCount();
		f = new JFrame();
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setPreferredSize(new Dimension(2000, 2000));
	    f.setLocation(150,150); // la fenêtre est en 150, 150
	    f.setLocationRelativeTo(null); 
	    f.setTitle("Chat Window");
	    f.setLayout(new BorderLayout());
	    f.addWindowListener(new WindowAdapter() {
	          public void windowClosed(WindowEvent evt){
	              Main.setCount(Main.getCount()-1);
	              
	              try {
	      			UDPclient.send(InetAddress.getByName("255.255.255.255"), 5001, "close");
	      		  } catch (UnknownHostException e) {
	      			e.printStackTrace();
	      		}
	          }
	          
	      });
	    	
	    JPanel mainPan = new JPanel();
	    mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.PAGE_AXIS));
	    
	    JPanel pan = new JPanel(new FlowLayout(), false);
	    JPanel textPan = new JPanel(new FlowLayout(), false); 
	    JPanel errPan = new JPanel(new FlowLayout(), false); 
	    
	    JPanel chatPan = new JPanel();
	    chatPan.setLayout(new BoxLayout(chatPan, BoxLayout.PAGE_AXIS));
	    chatPan.setPreferredSize(new Dimension(700,2000));
	    
	    chatPanL = new JPanel();
	    chatPanL.setLayout(new BoxLayout(chatPanL, BoxLayout.PAGE_AXIS));
	    chatPanL.setPreferredSize(new Dimension(700,2000));
	    chatPanR = new JPanel();
	    chatPanR.setLayout(new BoxLayout(chatPanR, BoxLayout.PAGE_AXIS));
	    chatPanR.setPreferredSize(new Dimension(700,2000));
	    
	    JPanel chatPanM = new JPanel(new FlowLayout(), false);
	    
	    
	    userList = new JPanel();
	    userList.setLayout(new BoxLayout(userList, BoxLayout.PAGE_AXIS));
	    userList.setPreferredSize(new Dimension(100,2000));
		JScrollPane chatScrollPan = new JScrollPane(chatPan);
		chatPan.setAutoscrolls(true);
		chatScrollPan.setPreferredSize(new Dimension(1500,900));
		JScrollPane userScrollList = new JScrollPane(userList);
		userList.setAutoscrolls(true);
		userScrollList.setPreferredSize(new Dimension(200,900));
		
		
		JLabel errLabel = new JLabel();
		errLabel.setForeground(Color.red);
		JTextField textBox = new JTextField();
		textBox.setColumns(20);
		JButton sendB = new JButton("Envoyer");
		sendB.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	errPan.remove(errLabel);
	        	if (user == null) {
	        		
	        		errLabel.setText("User est null");
	        		errPan.add(errLabel);
	        		f.pack();
	        	}
	        	else if (textBox.getText().equals("")) {
	        		errLabel.setText("Veuillez remplir le champ message");
	        		errPan.add(errLabel);
	        		f.pack();
	        	}
	        	else {
	        		String mes = textBox.getText();
					TCPclient.tcpSend(sock, mes);
					Historique.addHisto(user.getLogin(),mes,1);
					repack();
	        		}

	        }});
		
		pan.add(chatScrollPan, BorderLayout.WEST);
		chatPan.add(chatPanM);
		chatPanM.add(chatPanL);
		chatPanM.add(chatPanR);
		
		pan.add(userScrollList, BorderLayout.EAST);
		
		textPan.add(textBox);
		textPan.add(sendB);
		
		mainPan.add(pan);
		mainPan.add(textPan);
		mainPan.add(errPan);
		
		f.add(mainPan,BorderLayout.CENTER);
		f.add(textPan,BorderLayout.SOUTH);
		
		repack();

		f.setVisible(true);
	}
	
	public static void repack() {
		if (user != null ) {
			
			
			listMess.clear();
			for(JLabel l : lisLabL) {
				chatPanL.remove(l);
			}
			lisLabL.clear();
			for(JLabel l : lisLabR) {
				chatPanR.remove(l);
			}
			lisLabR.clear();
			BddAccess.Connect();
			Connection conn = BddAccess.getConn();
			String query = "SELECT * FROM message WHERE username ='"+user.getLogin()+"' ORDER BY date, id;";
			try {
				Statement stmt = (Statement) conn.createStatement();
				java.sql.ResultSet resultSet = stmt.executeQuery(query);
				while(resultSet.next()) {
					Message m = new Message(resultSet.getString("message"),resultSet.getInt("sent"),user,resultSet.getString("date"));
					listMess.add(m);
				}
			}catch(SQLException err) {
				err.printStackTrace();
			}
			for(Message m : listMess) {
				JLabel j = new JLabel(m.getCont());	
				JLabel jUser = new JLabel();
				JLabel j2 = new JLabel(" ");
				j2.setPreferredSize(new Dimension(100,20));
				if(m.getStat() == 0) {
					jUser.setText("["+m.getDate()+"] "+m.getUser().getLogin() +" :");
					j.setForeground(Color.red);
					jUser.setForeground(Color.red);
					chatPanL.add(jUser);
					chatPanL.add(j);
					chatPanR.add(j2);
					lisLabL.add(jUser);
					lisLabL.add(j);
					lisLabR.add(j2);
				}
				else {
					jUser.setText("["+m.getDate()+"] Vous :");
					j.setForeground(Color.blue);
					jUser.setForeground(Color.blue);
					chatPanR.add(jUser);
					chatPanR.add(j);
					chatPanL.add(j2);
					lisLabR.add(jUser);
					lisLabR.add(j);
					lisLabL.add(j2);
				}
			}
		}
		
		for (JButton j : listUserB) {
				userList.remove(j);	
			}
		listUserB.clear();
		for(User u : UserTable.getTable()) {
			
			JButton userButton = new JButton(u.getContact().username());
			userButton.addActionListener(new ActionListener() {
		          public void actionPerformed(ActionEvent e) {
		        	user = u;
					  try {
						  TCPclient.startConnection(u.getIp_address(),5001);
					  } catch (IOException ex) {
						  throw new RuntimeException(ex);
					  }
					  sock = TCPclient.clientSocket;
					  repack();
		        }});
			listUserB.add(userButton);
			userList.add(userButton);
		}
		BddAccess.close();
		f.pack();
	}
	
}
