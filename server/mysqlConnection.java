package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// TODO: Auto-generated Javadoc
/**
 * The Class mysqlConnection.
 */
public class mysqlConnection {

	/** The connection. */
	public Connection connection = null;
	
	/** The data base. */
	private static mysqlConnection dataBase;


	/**
	 * Instantiates a new mysql connection.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public mysqlConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        	String databaseName = "";
			String url = "jdbc:mysql://localhost:3306/?useTimezone=true&serverTimezone=UTC" +databaseName;	
			String username ="root";
			String password = "Aa123456";
	
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			//com.mysql.cj.jdbc.Driver d = new com.mysql.cj.jdbc.Driver();
			connection =  DriverManager.getConnection(url, username, password);          
    }

	   /**
   	 * Gets the connection.
   	 *
   	 * @return the mysql connection
   	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
   	 */
   	public static mysqlConnection GetConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			if (dataBase == null) {
			    dataBase = new mysqlConnection();
			}
			return dataBase;
		    }
}


