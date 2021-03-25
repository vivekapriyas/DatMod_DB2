package piazza.src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//usecase 5: kontroller for å sjekke brukerstatistikk i emnet
public class StatisticCtrl extends ConnectorClass{
    public String courseID;

    public StatisticCtrl(String courseID){
        connect();
        this.courseID = courseID;
    }
    
    //sql spørring mot post, hasread, enrolled  og user tabellene
    //inkluderer kun brukere som tar emnet. inkluderer ikke statistikk om instruktører i emnet
    //inkluderer også ikkea-aktive brukere
    String one = "select email,count(postID) as NumberRead, NumberPosted ";
    String two = "from (hasread right outer join (select * from (select * from enrolled right outer join _user using(email) where role = 'Student') as students where courseid = ?) as courseusers  using(email)) "; 
    String three = "inner join (select email, count(postID) as NumberPosted ";
    String four = "from post right outer join (select * from enrolled where courseid = ?) as courseusers on creatoremail=email group by email) ";
    String five = "as Posted using(email) group by email order by NumberRead desc,NumberPosted desc";

    
    //utfører spørringen
    public void showStatistics(){
        final String query = one+two+three+four+five; 

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, courseID);
            stmt.setString(2, courseID);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            
            String column1 = "Username";
            String column2 = "NumberRead";
            String column3 = "NumberPosted";
            System.out.println("\n\n------------------------------------------------");
            System.out.format("|%-20s|%-12s|%-12s|%n",column1,column2,column3);
            System.out.println("------------------------------------------------");
        
            while (rs.next()){                

                String username = rs.getString("email");
                int read = rs.getInt("NumberRead");
                int posted = rs.getInt("NumberPosted");

                System.out.format("|%-20s|%-12d|%-12d|%n", username, read,posted);
                //System.out.println(username+" "+read+" "+posted);
            }
            System.out.println("------------------------------------------------");

        }
        catch (SQLException e){
            System.out.println("DB error while trying to add post: " + e);
        }
    }

}