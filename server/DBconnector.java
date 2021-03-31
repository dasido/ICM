package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//dor
public class DBconnector {

		//The connection
		public static Connection myConnection=null;
		//The data base.
		//private static DBconnector dataBase;
		
		/* Instantiates a new DBconnector connection.
	    *@throws SQLException the SQL exception
	    */
		public static Connection connectToDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	
	        	String databaseName = "";
				String url = "jdbc:mysql://localhost:3306/?useTimezone=true&serverTimezone=UTC" +databaseName;	
				String username ="root";
				String password = "Aa123456";
		
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				//com.mysql.cj.jdbc.Driver d = new com.mysql.cj.jdbc.Driver();
				myConnection =  DriverManager.getConnection(url, username, password);
				return myConnection;
	          }


}

		
