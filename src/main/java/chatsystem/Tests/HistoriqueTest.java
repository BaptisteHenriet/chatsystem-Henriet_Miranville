package chatsystem.Tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import chatsystem.DATABASES.BddAccess;
import chatsystem.Interface.Historique;

public class HistoriqueTest {
	
	@Test 
	void addHistoTest(){
		BddAccess.Connect();
		Connection conn = BddAccess.getConn();

		
		String usernameTest = "BddAccessTest";
		String messageTest = "BddAccessTestMess";
		String query1 = "SELECT * FROM message WHERE username ='"+usernameTest+"';";
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(query1);
			
			assert(resultSet.getString("username") == null);
			assert(resultSet.getString("message") == null);
			
			Historique.addHisto(usernameTest, messageTest,1);
			
			resultSet = stmt.executeQuery(query1);
			
			assert(resultSet.getString("username").equals(usernameTest));
			assert(resultSet.getString("message").equals(messageTest));
			
			query1 =  "DELETE FROM message WHERE username = '"+usernameTest+"';";
			stmt.execute(query1);
			}
			catch(SQLException err) {
				err.printStackTrace();
			}
		BddAccess.close();
	}
}
