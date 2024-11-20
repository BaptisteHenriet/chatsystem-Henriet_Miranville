package chatsystem.Interface;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import chatsystem.DATABASES.BddAccess;
import java.sql.Statement;

public class Historique {
	private User user1;
	private User user2;
	private static Connection conn = null;
	private static int i = 0;

	public Historique(User u1, User u2) {
		this.user1 = u1;
		this.user2 = u2;
	}

	/************ GETTERS *************************/
	public User getUser1(){return this.user1;}
	public User getUser2(){return this.user2;}

	public static Connection getConn() {
		return conn;
	}
	/**********************************************/
	public static void addHisto(String username,String message, int stat){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime localdate = LocalDateTime.now();  
	   	System.out.println(dtf.format(localdate));  

		try {	
			BddAccess.Connect();
			Connection conn = BddAccess.getConn();
			String query3 = "SELECT * FROM message";
			Statement stmt = (Statement) conn.createStatement();			
			java.sql.ResultSet resultSet = stmt.executeQuery(query3);
			while(resultSet.next()) {
				i++;
			}
			String query1 = "INSERT INTO message(id,username,date,message,sent) VALUES("+i+",'"+username+"','"+dtf.format(localdate)+"','"+message+"','"+stat+"');";
			stmt.execute(query1);
		}catch(SQLException err) {
			err.printStackTrace();
		}
		BddAccess.close();
	}
}
