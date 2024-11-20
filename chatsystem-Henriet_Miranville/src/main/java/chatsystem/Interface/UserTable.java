package chatsystem.Interface;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class UserTable {

	public interface Observer {
		void newUserAdded(User user);
	}

	private static final UserTable INSTANCEUSER = new UserTable();
	static List<User> userList = new ArrayList<>();

	List<Observer> observers = new ArrayList<>();
	public static UserTable getInstance(){
		return INSTANCEUSER;
	}
	public UserTable() {

	}
	public synchronized void addObserver(Observer obs){
		this.observers.add(obs);
	}
	public synchronized void addUser(Contact contact, InetAddress ip_addr) {
			User user= new User(contact,ip_addr);
			userList.add(user);
			for (UserTable.Observer obs : observers){
				obs.newUserAdded(user);
			}
			System.out.println("après for in addUser");
	}

	public static void setList(ArrayList<User> userTab) {
		userList = userTab;
	}
	public static List<User> getTable(){
		return userList;
	}
	public static User getByAddress(InetAddress ip) {
		for (User u : userList) {
			if (u.getIp_address().equals(ip)) return u;
		}
		return null;
	}

	public static void rmUser(User u) {
		while(userList.remove(u));
	}

}
