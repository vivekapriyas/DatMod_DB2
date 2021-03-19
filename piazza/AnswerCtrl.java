package piazza;

import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

//arver fra ConnectorClass 
public class AnswerCtrl extends ConnectorClass{

    public int postID;
    public String content;

    public AnswerCtrl(int postid, String content){
        connect();
        this.postID = postid;
        this.content = content;
    }

    public void createPost(){
        try{
            String insertion = "INSERT INTO instructorsanswer VALUES(?,?)";
            PreparedStatement regStmt = conn.prepareStatement(insertion);
            regStmt.setInt(1,this.postID);
            regStmt.setString(2,this.content);
            regStmt.execute();

        }
        catch(SQLException e){
            System.out.println("***DB error during preparation of insert into instructoranswer***");
        }   
    }

    public static void main(String[] args) {
        AnswerCtrl test = new AnswerCtrl(1,"Dette er et svar");
        test.createPost();
    }
}

