package piazza;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Statistic extends ConnectorClass{
    public String courseID;

    public Statistic(String courseID){
        connect();
        this.courseID = courseID;
    }

    public void showStatistics(){
        //String cu = "(select * from enrolled where courseid = ?) as courseusers";
        //String select = "select email,count(postID) as NumberRead, NumberPosted from (hasread right outer join"+cu+"using(email)) inner join "; 
        //String posted = "(select email, count(postID) as NumberPosted from post right outer join" + cu + "on creatoremail = email";
        //String grouping = "group by email) as Posted using(email) group by email order by NumberRead desc,NumberPosted desc";

        String query = "select email,count(postID) as NumberRead, NumberPosted from (hasread right outer join (select * from enrolled where courseid = ?) as courseusers  using(email)) inner join (select email, count(postID) as NumberPosted from post right outer join (select * from enrolled where courseid = ?) as courseusers on creatoremail = email group by email) as Posted using(email) group by email order by NumberRead desc,NumberPosted desc";

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, courseID);
            stmt.setString(2, courseID);

            stmt.executeQuery();
            System.out.println("yippi");
        }
        catch (SQLException e){
            System.out.println("DB error while trying to add post: " + e);
        }
    }

    public static void main(String[] args) {
        Statistic test = new Statistic("TMA4180");
        test.showStatistics();
    }

}