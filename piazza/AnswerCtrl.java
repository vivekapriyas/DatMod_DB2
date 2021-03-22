package piazza;

import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;  

//Mangler logikk for å kontrollere eksistensen av courseID


//arver fra ConnectorClass 
public class AnswerCtrl extends ConnectorClass{

    public int postID;
    public String content;


    public AnswerCtrl(/*int postid, String content*/){
        connect();
        //this.postID = postid;
        //this.content = content;
    }
//balbablalba
    public void createAnswer(int postID, String content){
        this.postID = postID;
        this.content = content;
       final String insertion = "INSERT INTO instructorsanswer VALUES(?,?)";
        try{
            PreparedStatement regStmt = conn.prepareStatement(insertion);
            regStmt.setInt(1,this.postID);   //regstmt2 setter inn i IA
            regStmt.setString(2,this.content);
            regStmt.execute();
        }
        catch(SQLException e){
            System.out.println("***DB error during preparation of insert into instructoranswer***");
        }   


        LocalDate date = LocalDate.now();
        String username = "stein@ntnu.no";  //"instruktør"
       // String emne = "TDT4145";
        String answeredByI = "INSERT INTO answeredbyinstructor VALUES(?,?,?)";
        try {
            PreparedStatement regStmt1 = conn.prepareStatement(answeredByI);
            regStmt1.setInt(1,this.postID);  
            regStmt1.setString(2,username);
            regStmt1.setObject(3,date);
            regStmt1.execute(); 
            
        } 
        catch (Exception e) {
            System.out.println("***DB error during preparation of insert into answeredbyinstructor***");
        }

        String updateColor = "UPDATE post SET post.colorCode = 'yellow' WHERE post.postID = ?";
        try {
            PreparedStatement regStmt2 = conn.prepareStatement(updateColor);
            regStmt2.setInt(1,this.postID);        
            regStmt2.executeUpdate();
        } 
        catch (Exception e) {
            System.out.println("**DB error during updating color");
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args) {
        AnswerCtrl test = new AnswerCtrl();
        test.createAnswer(2,"Dette er et svar");
    }
}
