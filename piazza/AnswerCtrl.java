package piazza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;  



 // brukes til å kalle usecase3-controller-objekt
public class AnswerCtrl extends ConnectorClass{

    //initi param
    public int postID;
    public String content;

    public AnswerCtrl(){
        connect();
    }

   
    //Oppretter ny rad i instructoranswer-tabellen

    public void createInstructorAnswer(int postID, String content){

        final String insertion = "INSERT INTO instructorsanswer VALUES(?,?)";
        this.postID = postID;
        this.content = content;

        try{
            PreparedStatement regStmt = conn.prepareStatement(insertion);
            regStmt.setInt(1,this.postID);   //regstmt2 setter inn i IA
            regStmt.setString(2,this.content);
            regStmt.execute();
        }
        catch(Exception e){
            System.out.println("***DB error during preparation of insert" +
                               " into instructoranswer***");
        }   
    }
    //oppretter ny rad i answeredbyinstructor-tabellen

    public void createAnsweredByInstructor(int postID){
        final String answeredByI = "INSERT INTO answeredbyinstructor VALUES(?,?,?)";
        String username = "stein@ntnu.no";  //"instruktør"
        LocalDate date = LocalDate.now();
        
        try {
            PreparedStatement regStmt1 = conn.prepareStatement(answeredByI);
            regStmt1.setInt(1,this.postID);  
            regStmt1.setString(2,username);
            regStmt1.setObject(3,date);
            regStmt1.execute(); 
        } 
        catch (Exception e) {
            System.out.println("***DB error during preparation of insert" +
                               " into answeredbyinstructor***");
        }
    }

    //setter colorCode med postID = ? til yellow -> answeredByInstructor

    public void postAnsweredByInstructor(int postID){
        String updateColor = "UPDATE post SET post.colorCode = 'yellow'" +
                            " WHERE post.postID = ?";
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
    
    //returnerer false hvis postID ikke finnes. ellers true

    public boolean checkPostExist(int postID){
        final String postIDCheck = "SELECT count(*) from post where postID = ?";
        boolean exist = false;

        try{
        PreparedStatement stmt = conn.prepareStatement(postIDCheck);
        stmt.setInt(1,postID);
        stmt.executeQuery();
        ResultSet rs = stmt.getResultSet();           
        System.out.println(postID);          
        while(rs.next()){                    
            exist = rs.getBoolean(1);         
            }
        }
    
    catch(Exception e){
        e.printStackTrace();
    }
    return exist;
    }





    public static void main(String[] args) {
        AnswerCtrl test = new AnswerCtrl();
        /*test.createInstructorAnswer(4,"Dette er et svar");
        test.createAnsweredByInstructor(4);
        test.postAnsweredByInstructor(4);*/
        System.out.println(test.checkPostExist(5));
    }
}
