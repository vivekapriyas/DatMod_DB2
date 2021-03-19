import java.sql.*;
public abstract class ConnectorClass {

    Connection conn;

    public ConnectorClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prosjekt2", "root","RighteouSsql3211");
            PreparedStatement stmt = conn.prepareStatement("Select  * from course");
            ResultSet rs = stmt.executeQuery();
                     while(rs.next())
                     {
                         System.out.println(rs.getString(1));
                     }
                 conn.close();
             } 
             catch(Exception e)
                 {
                     System.out.println("Hvis dette vises kompilerer koden i alle fall");
                 }
        
    }





}