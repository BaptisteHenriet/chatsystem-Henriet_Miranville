package chatsystem.Interface;

public class Message {
	private String contenu;
	private int status;
	private User user;
	private String date;
	
	public Message (String cont, int stat, User user, String date) {
		this.contenu = cont;
		this.status = stat;
		this.user = user;
		this.date = date;
	}
	
	public String getCont() {
		return this.contenu;
	}
	public int getStat() {
		return this.status;
	}

	public User getUser() {
		return user;
	}

	public String getDate() {
		return date;
	}
	
}