package piazza.src;
import java.sql.*;
import java.util.Properties;

//håndterer tilkobling til databasen
public class ConnectorClass {

    private static final String DBUsername = "vivekaps"; //sett til brukernavn
    private static final String DBPassword = "Piki3lapskaus"; //sett til passord
    private static final String DBName = "db"; //set til databasenavn
    
    protected Connection conn;

    //connect benyttes for tilgang til databasen i barneklasser
    public void connect() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Properties p = new Properties();
            p.put("user", DBUsername);
            p.put("password", DBPassword);

            conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DBName,p);

        } 
        catch(Exception e){
            throw new RuntimeException("Not able to connect", e);
         }
        
    }

}