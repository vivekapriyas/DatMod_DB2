package piazza;
import java.sql.*;



public class ConnectorClass {
    public static void main(String[] args) {

        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2", "vivekaps","Piki3lapskaus");
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
                System.out.println(e);
            }
    }



}