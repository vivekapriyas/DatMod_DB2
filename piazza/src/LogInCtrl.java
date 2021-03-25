package piazza.src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//usecase 1: kontroller for innlogging 
public class LogInCtrl extends ConnectorClass{
    private String username;
    private String role;
    private String passord;
    private boolean success;

    public LogInCtrl(String username, String passord ){
        connect(); //tilkobling til databasen
        this.username = username;
        this.passord = passord;
        this.success = false;
    }

    public boolean executeLogIn(){
        //sjekker om bruker finnes i databasen
        if (!checkExistence(username)){ 
            System.out.println("Username not found");
            return success;
        }

        //sql query: henter passord og rolle (Student/Instructor) til bruker
        String query = "select password, role from _user where email=?"; 
        
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                String userpassword = rs.getString(1);
                String userrole = rs.getString(2);

                //sjekker om brukerinput passord stemmer
                if(this.passord.equals(userpassword) ){
                    success = true;
                    role = userrole;
                }
                else{
                }
            }
        }
        catch(SQLException e){
            System.out.println("Error in fetching password: "+e);
        }

        return success;
    }

    //helpers
    //sjekker ekstistens av bruker
    private boolean checkExistence(String email){
        //teller antall rader med email = input brukernavn
        //gir ut 0/1, tilsvarende false/true
        String query = "select count(*) from _user where email=?";
        boolean exists = false;

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                exists = rs.getBoolean(1);
            }
        }
        catch(SQLException e){
            System.out.println("DB error when checking for user-existence: "+e);
        }

        return exists;
    }


    //public: skal brukes i piazza-klassen, i tilfelle feil ved første innlogginsforsøk
    //endrer username og password attributtene
    public void setInput(String username, String password){ 
        this.username = username;
        this.passord = password;
        return;
    }

    //henter rolle-attributtet
    public String getRole(){
        return role;
    }
}
