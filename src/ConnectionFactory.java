//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: Handles the connection to the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
	private static ConnectionFactory instance = new ConnectionFactory();
	public static final String DATABASE_URL= "jdbc:mysql://localhost:3306/cami";
	private static  String user = "root";
	 private static  String pass = "";
	 
	  private ConnectionFactory(){

	  }
	  private Connection createConnection() throws SQLException{
		  Connection connection = null;
		  connection = DriverManager.getConnection(DATABASE_URL, user, pass);
		  return connection;
	  }
	  public static Connection getConnection(String username, String password) throws SQLException{
		  user = username;
		  pass  = password;
		  return instance.createConnection();
	  }
}
