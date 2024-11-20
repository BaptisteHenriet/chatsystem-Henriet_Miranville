package chatsystem.Tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import chatsystem.DATABASES.BddAccess;

public class BddAccessTest {
	
	@Test 
	void bddTest(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime localdate = LocalDateTime.now();
		BddAccess.Connect();
		Connection conn = BddAccess.getConn();
		int i = 0;
		int cont = 0;
		String query1 = "SELECT * FROM message;";
		String usernameTest = "BddAccessTest";
		String messageTest = "BddAccessTestMess";
		String query2 = "INSERT INTO message(id,username,date,message,sent) VALUES("+i+",'"+usernameTest+"','"+dtf.format(localdate)+"','"+messageTest+"','1');";
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			
			try {
				stmt.execute(query1);
			}
			catch(SQLException err) {
				i = 1;
			}
			assert(i != 1);
			ResultSet resultSet = stmt.executeQuery(query1);
			while(resultSet.next()) {
				cont++;
			}
		
			
			stmt.execute(query2);
			query1 = "SELECT * FROM message WHERE username = '"+usernameTest+"';";
			resultSet = stmt.executeQuery(query1);			

			assert(resultSet.getString("username").equals(usernameTest));
			assert(resultSet.getString("message").equals(messageTest));
			
			query2 =  "DELETE FROM message WHERE username = '"+usernameTest+"';";
			stmt.execute(query2);
			
			resultSet = stmt.executeQuery(query1);
			assert(resultSet.getString("username") == null);
			assert(resultSet.getString("message") == null);
			}
			catch(SQLException err) {
				err.printStackTrace();
			}
		BddAccess.close();
		try {
			stmt = (Statement) conn.createStatement();
			query2 = "INSERT INTO message(id,username,date,message,sent) VALUES("+cont+",'"+usernameTest+"','"+dtf.format(localdate)+"','"+messageTest+"','1');";
			stmt.execute(query2);
		}catch(SQLException err) {
			i = 0;
		}
		assert(i == 0);
		
	}
}
