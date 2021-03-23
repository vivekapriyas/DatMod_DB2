package piazza;
import java.sql.*;
import java.util.Properties;
public class ConnectorClass {

    //private static final String DBName = "db";
    //private static final String DBURI = "jdbc:mysql://127.0.0.1/" + DBName + "?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false";
    private static final String DBUsername = "root";
    private static final String DBPassword = "RighteouSsql3211";
    private static final String DBName = "db";
    

    protected Connection conn;

    public void connect() {
    try {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Properties p = new Properties();
        p.put("user", DBUsername);
        p.put("password", DBPassword);

        conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DBName,p);
        //conn = DriverManager.getConnection(DBURI, p); dette fungerer tydeligvis ikke

        /* PreparedStatement stmt = conn.prepareStatement("Select  * from course");
        ResultSet rs = stmt.executeQuery();
                    while(rs.next())
                    {
                        System.out.println(rs.getString(1));
                    }
                conn.close();
        */
        
        System.out.println("Successfully connected to database");
        } 
    catch(Exception e)
        {
                    throw new RuntimeException("Not able to connect", e);
         }
        
    }


public static void main(String[] args) {
    ConnectorClass forsok = new ConnectorClass();
    forsok.connect();
}


}