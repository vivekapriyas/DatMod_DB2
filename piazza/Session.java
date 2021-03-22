package piazza;
import java.util.*;  

public class Session {
    private static boolean active;
    private User bruker;
    private String username;
    private String role;
    private String courseID = "TMA4180";


    public Session(){
        active = true;
        this.bruker = new User();
        this.username = bruker.getUsername();
        this.role = bruker.getRole();
        bruker.setActiveCourse(courseID);
    }



    public static void main(String[] args) {
        Session s = new Session();
        s.getUser().logIn();
        Scanner sc = new Scanner(System.in);

        while(s.getStatus()){

            

            System.out.println("You are now logged in as "+s.getUser().getUsername()
                                +"\n To make a post press 1"
                                +"\n To search for a keyword press 2"            
            );

            
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                s.getUser().makePost();
    
                case 2:
                s.getUser().searchForKeyword();
                
                case 0:
                break;
            }
            
          

        }
        sc.close();
    }

    private User getUser(){
        return bruker;
    }

    private boolean getStatus(){
        return active;
    }

 
    
}
