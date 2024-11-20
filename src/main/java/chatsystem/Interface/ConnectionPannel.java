package chatsystem.Interface;
import chatsystem.Core.Exceptions.ContactAlreadySaved;
import chatsystem.Core.Network.UDPclient;
import chatsystem.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;

public class ConnectionPannel {

    public static final Logger LOGGER = LogManager.getLogger(ConnectionPannel.class);


    static ContactList contacts = ContactList.getInstance();
	public static void createConn() {
		// création de la fenêtre de connexion
      JFrame f = new JFrame();
      f.setTitle("Connexion");
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      // taille et position
      f.setPreferredSize(new Dimension(600, 200));
      f.setLocation(150,150); // la fenêtre est en 150, 150
      f.setLocationRelativeTo(null); 
      
      f.addWindowListener(new WindowAdapter() {
          public void windowClosed(WindowEvent evt){
              Main.setCount(Main.getCount()-1);
          }
          
      });
      
      
      JPanel pan = new JPanel();
      pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
      
      JPanel panT = new JPanel(new FlowLayout(), false);
      JPanel panM = new JPanel(new FlowLayout(), false);
      JPanel panB = new JPanel(new FlowLayout(), false);
      
      JPanel panL = new JPanel();
      panL.setLayout(new BoxLayout(panL, BoxLayout.PAGE_AXIS));

      JLabel logLabel = new JLabel("Username :");
      
      JLabel errLabel = new JLabel("Erreur, login déjà utilisé");
      errLabel.setForeground(Color.red);
      
      //Création des champs à remplir
      
      JTextField logField = new JTextField();
      logField.setColumns(20);
     
      JButton butConf = new JButton("Se connecter");
      butConf.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	String log = logField.getText();
        	if(log.equals("")) {
        		errLabel.setText("Erreur, veuillez remplir le champ");
        		panM.add(errLabel);
        		f.pack();
        	}
        	else if(log.equals("discover") || log.equals("close")){ // On interdit l'utilisation des usernames 'close' et 'discover' pour ne pas gêner la gestion des messages d'information corrsespondant
        		errLabel.setText("Erreur, username interdit");
        		panM.add(errLabel);
        		f.pack();
        	}
            /** Si tout est ok **/
        	else {
        		try {
          			contacts.addUser(log);
        			contacts.setSelf(log);
                    LOGGER.info(log+" vient de se connecter.");
                    try {
                        UDPclient.send(InetAddress.getByName("255.255.255.255"), 5001, log);
                    } catch (UnknownHostException ex) {
                        throw new RuntimeException(ex);
                    }
                    panM.remove(errLabel);
                    ChatWindow.createChatWindow();
                    f.dispose();
        		}catch(ContactAlreadySaved err) {
        			errLabel.setText("Erreur, username déjà utilisé");
                    panM.add(errLabel);	//On affiche un message d'erreur si le login entré est déjà utilisé
                    f.pack();
        		}
	        }
          }
        });
      
      
      
      panT.add(panL);
      
      panL.add(logLabel);
      panL.add(logField);
      
      panB.add(butConf);
      
      pan.add(panT);
      pan.add(panM);
      pan.add(panB);
      
      f.add(pan);
      f.pack();
      f.setVisible(true);
      //connFrame = f;
	}
}

