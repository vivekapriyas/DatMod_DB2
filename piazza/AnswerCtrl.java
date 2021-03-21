package piazza;

import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;  


//Se gjennom koden for evt. feil
//Mangler logikk for å kontrollere eksistensen av courseID


//arver fra ConnectorClass 
public class AnswerCtrl extends ConnectorClass{

    public int postID;
    public String content;
    public String username;

    public AnswerCtrl(int postid, String content, String username){
        connect();
        this.postID = postid;
        this.content = content;
        this.username = username;  //usikker på om dette er riktig måte å hente username på
    }
//balbablalba
    public void createPost(){
        try{
            LocalDate date = LocalDate.now();
            //String date_string = date;
            String colorCode = "UPDATE post SET colorCode = 'yellow' WHERE postID = ?"; 
            String insertion = "INSERT INTO instructorsanswer VALUES(?,?)";
            String answeredByI = "INSERT INTO answeredbyinstructor VALUES(?,?,?)";
            PreparedStatement regStmt = conn.prepareStatement(insertion);
            PreparedStatement regStmt1 = conn.prepareStatement(answeredByI);
            PreparedStatement regStmt2 = conn.prepareStatement(colorCode);
            regStmt.setInt(1,this.postID);   //regstmt2 setter inn i IA
            regStmt.setString(2,this.content);
            regStmt.execute();
            regStmt1.setInt(1,this.postID);  //regStmt1 setter inn i ABI
            regStmt1.setString(2,this.username);
            regStmt1.setObject(3,date);
            regStmt1.execute();         
            regStmt2.executeUpdate(colorCode); //oppdaterer colorCode i post

        }
        catch(SQLException e){
            System.out.println("***DB error during preparation of insert into instructoranswer***");
        }   
    }
    
    public static void main(String[] args) {
        AnswerCtrl test = new AnswerCtrl(1,"Dette er et svar","abc@ntnu.no");
        test.createPost();
    }
}
