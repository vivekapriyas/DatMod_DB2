package piazza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


 //kontroller for å lage et svar fra en instruktør
public class AnswerCtrl extends ConnectorClass{

    public int postID;
    public String content;
    public String username;

    public AnswerCtrl(String username){
        connect();
        this.username = username;
    }

   
    //metode som skal kalles i piazza-klassen
    public void createInstructorAnswer(int postID, String content){

        //sjekker om posten er svart på fra før
        //lager enten helt ny rad i instructorsanswer eller updater eksisterende rad
        if(checkIfAnswered(postID)){
            editAnswer(postID, content);
        }
        else{
            createNewAnswer(postID,content);
        }
        
    }

    //helpers
    ////Oppretter ny rad i instructoranswer-tabellen hvis posten ikke er svart på fra før
    private void createNewAnswer(int postID, String content){
        final String insertion = "INSERT INTO instructorsanswer VALUES(?,?)";

        try{
            PreparedStatement regStmt = conn.prepareStatement(insertion);
            regStmt.setInt(1,postID);   
            regStmt.setString(2,content);
            regStmt.execute();
        }
        catch(Exception e){
            System.out.println("DB error during preparation of insert into instructoranswer: "+e);
        }   

        //endrer fargekode i post-tabellen
        postAnsweredByInstructor(postID);
        //innsetting i answeredbyinstructor-tabellen
        createAnsweredByInstructor(postID);
    }


    //updater svaret ved å legge til nytt innhold
    public void editAnswer(int postid, String content){
        String earlier = getAnswer(postid);
        String updated = earlier+" "+content;
        final String update = "update instructorsanswer set content=? where postid=?";

        try{
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, updated);
            stmt.setInt(2, postid);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Error when updating answer: "+e);
        }

        //sjekker om det er bruker som har svart på samme post fra før eller en annen instruktør
        //hhv oppdaterer snweredbyinstructor eller lager ny rad
        if(checkWhoAnswered(postid).equals(this.username)){
            updateAnsweredByInstructor(postID);
        }
        else{
            createAnsweredByInstructor(postID);
        }
    }


    //oppretter ny rad i answeredbyinstructor-tabellen
    private void createAnsweredByInstructor(int postID){
        final String answeredByI = "INSERT INTO answeredbyinstructor VALUES(?,?,?)";
        LocalDate date = LocalDate.now();
        
        try {
            PreparedStatement regStmt1 = conn.prepareStatement(answeredByI);
            regStmt1.setInt(1,this.postID);  
            regStmt1.setString(2,this.username);
            regStmt1.setObject(3,date);
            regStmt1.execute(); 
        } 
        catch (Exception e) {
            System.out.println("DB error during preparation of insert into answeredbyinstructor: "+e);
        }
    }

    //oppdaterer raden i AnsweredByInstructor
    private void updateAnsweredByInstructor(int postID){
        final String update = "update answeredbyinstructor set _Date=? where postid=? and email=?";
        LocalDate date = LocalDate.now();
        
        try {
            PreparedStatement regStmt1 = conn.prepareStatement(update);
            regStmt1.setObject(1,date);
            regStmt1.setInt(2,postID);  
            regStmt1.setString(3,this.username);
            regStmt1.execute(); 
        } 
        catch (Exception e) {
            System.out.println("DB error during update of answeredbyinstructor: "+e);
        }
    }


    //setter colorCode med postID = ? til yellow -> answeredByInstructor
    private void postAnsweredByInstructor(int postID){
        String updateColor = "UPDATE post SET post.colorCode = 'yellow'" +
                            " WHERE post.postID = ?";
        try {
            PreparedStatement regStmt2 = conn.prepareStatement(updateColor);
            regStmt2.setInt(1,this.postID);        
            regStmt2.executeUpdate();
        } 
        catch (Exception e) {
            System.out.println("**DB error during updating color: "+e);
            e.printStackTrace();
        }

    }

    //public: brukes i piazza-klassen
    //teller antall rader der postID = postID. returnerer 0/1 ettersom postID er primærnøkkel
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


    //sjekker om en post er blitt svart på fra før
    private boolean checkIfAnswered(int postID){
        final String answerCheck = "select count(*) from instructorsanswer where postID = ?";
        boolean answered = false;

        try{
            PreparedStatement stmt = conn.prepareStatement(answerCheck);
            stmt.setInt(1, postID);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                answered = rs.getBoolean(1);
            }
        }
        catch(SQLException e){
            System.out.println("Error when checking if post is answered already: "+e);
        }

        return answered;
    }


    //sjekker hvem som har svart på en post
    private String checkWhoAnswered(int postid){
        final String query = "select email from answeredbyinstructor where postid=?";
        String name = "";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, postid);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                name = rs.getString(1);
            }

        }
        catch(SQLException e){
            System.out.println("DB Error when checking who answered: "+e);
        }
        return name;
    }


    //henter allerede eksisterende svar på posten 
    private String getAnswer(int postid){
        final String query = "select content from instructorsanswer where postid=?";
        String answer = "";

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, postid);
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                answer = rs.getString(1);
            }

        }
        catch(SQLException e){
            System.out.println("Error when fetching earlier answer: "+e);
        }

        return answer;
    }

}
