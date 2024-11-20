package chatsystem.DATABASES;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BddAccess {
	private static Connection conn = null;
	public static Connection getConn() {
		if(conn == null) {
			System.out.println("Erreur connection non établie");
		}
		return conn;
	}
	public static void Connect() {
        try {
            String url = "jdbc:sqlite:src/main/java/chatsystem/DATABASES/chat.db";
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public static void close() {
	   try {
           if (conn != null) {
               conn.close();
           }
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
       }
   }	
}

