package chatsystem.Interface;

import java.net.InetAddress;

public class User{
	private String login;
	private Contact cont;
	private InetAddress ip_address;
	
	public User(Contact user, InetAddress ip){
		this.cont = user;
		this.ip_address = ip;
	}

	public Contact getContact() {
		return this.cont;
	}
	
	public String getLogin() {
		return this.cont.username();
	}
	
	public InetAddress getIp_address(){ return this.ip_address;}
 
	public String toString() {
		return this.login + "," + this.ip_address;
	}

}
