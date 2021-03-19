package piazza;

import java.sql.*;
//arver fra DBConn abstraktklasse
public class AnswerCtrl extends ConnectorClass{
    private String postID;
    private String content;
    private PreparedStatement regStmt;

    public AnswerCtrl(String postID, String content){
        this.postID = postID;
        this.content = content;
        try{
            regStmt = myConn.prepareStatement("INSERT INTO instructorsanswer VALUES((?),(?))");
        }catch(Exception e){
            System.out.println("***DB error during preparation of insert into instructoranswer***");
        }    
    }
    public void createPost(String postID, String content){
        //as long as post exists
        if(postID != ""){
            try{
                regStmt.setString(1,postID);
                regStmt.setString(2,content);
                regStmt.execute();
            } catch(Exception e){
                System.out.println("***DB error during insert of InstructorAnswer postID= " +postID );            }
        }
    }
}


