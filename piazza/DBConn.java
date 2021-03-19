package piazza;

/**
 *
 * @author Svein Erik
 */

import java.sql.*;
import java.util.Properties;

public abstract class DBConn {
    protected Connection conn;
    public DBConn () {
    }
    public void connect() {
    	try {
            // Class.forName("com.mysql.jdbc.Driver").newInstance(); when you are using MySQL 5.7
	    Class.forName("com.mysql.cj.jdbc.Driver"); 
	    // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "myuser");
            p.put("password", "mypassword");           
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/avtalebok?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
    	{
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}
