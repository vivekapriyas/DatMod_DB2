package piazza.src;
import java.util.*;  


//samler usecasene og implementerer logikk
public class Piazza {

    private String username;
    private String role;
    private boolean loggedIn;
    private String activeCourse = "TMA4180"; //alle usecasene kan foregå innenfor ett emne


    //konstruktør.
    public Piazza(){
        this.loggedIn = false;
    }


    //Usecase 1: logge seg inn
    public void logIn(){

        //kun lagt inn for å gjøre sensur lettere
        System.out.println("Username=anders@ntnu.no, password=anderspassword to log in as a student"
                        +"\nUsername=stein@ntnu.no, password=steinspassword to log in as an instructor");  

        //input fra bruker
        Scanner sc= new Scanner(System.in);
        System.out.println("Username: ");
        String name = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        LogInCtrl log = new LogInCtrl(name,password); //LogInCTRL håndterer sql


        //dersom login feiler kan man prøve på nytt
        while (! log.executeLogIn()){ 
            System.out.println("Wrong username or password");
            System.out.println("Press 1 to try again or 2 to break");

            int check = sc.nextInt();
            switch(check){
                case 1:
                System.out.println("Username: ");
                name = sc.nextLine();
                System.out.println("Password: ");
                password = sc.nextLine();
    
                log.setInput(name, password);
                log.executeLogIn();

                case 2:
                //velger å ikke prøve på nytt
                //loggedIn fortsetter å være false
                return;

            }
        }

        setLoggedIN(true);
        setUsername(name);
        setRole(log.getRole());
    
        //sc.close();
        return;
    }

    //Usecase 2: lage Post
    public void makePost(){
        PostCtrl post = new PostCtrl(getUsername(),getActiveCourse()); //PostCTrl håndetere sql
        Scanner sc = new Scanner(System.in);
        
        //input fra bruker
        System.out.println("Title: ");
        String title = sc.nextLine();
        System.out.println("Content: ");
        String content = sc.nextLine();
        System.out.println("Folder: ");
        String foldername = sc.nextLine();
        System.out.println("Type: ");
        String type = sc.nextLine();
        String anonWanted = "";
        boolean anonAllowed = post.isAnonAllowed(getActiveCourse());

        //hvis emnet tillater å poste anonymt blir bruker spurt om han/hun ønsker det
        if (anonAllowed){
            System.out.println("Do you want to post anonymously? Y/N");
            anonWanted = sc.nextLine();
        }
        else{
            anonWanted = "N";
        }

        //sc.close();
        
        post.addPost(title, anonWanted, anonAllowed, content, foldername, type);
        return;
    }

    //Usecase 3: en instruktør svarer på en post
    public void answerPostInstructor(){
        //kun instruktører kan svare i answeredByInstructor
        if (! getRole().equals("Instructor")){
            System.out.println("You are not authorized to answer as an instructor.");
            return;
        }

        AnswerCtrl answer = new AnswerCtrl(getUsername()); //AnswerCtrl håndterer sql

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter postID: ");
        int postid = sc.nextInt();

        //dersom posten instruktør ønsker å svare på ikke finnes kan han/hun prøve på nytt
        while(! answer.checkPostExist(postid)){
            System.out.println("PostID does not exist.");
            System.out.println("Press 1 to enter existing PostID or press 2 to break");

            int check = sc.nextInt();
            switch(check){
                case 1:
                postid = sc.nextInt();
                break;

                case 2:
                //velger å ikke svare på en post likevel
                return;
            }
        }

        System.out.println("Post found");
        System.out.println("Enter answer: ");
        String content = sc.nextLine();
        content = sc.nextLine();

        answer.createInstructorAnswer(postid, content);
      
        //sc.close();
        return;
    }   


    //Usecase 4: lete gjennom poster etter et keyword
    public void searchForKeyword(){
        SearchCtrl search = new SearchCtrl(); //håndterer sql

        //input fra bruker
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter keyword: ");
        String keyword = sc.nextLine();
        //sc.close();

        search.executeSearch(keyword);
        return;
    }


    //Usecase 5: en intruktør ser på statistikk over brukere i faget
    public void checkStatistics(){
        //kun instruktører kan sjekke brukerstatistikk i et emne
        if (getRole().equals(("Instructor"))){
            Statistic stats = new Statistic(getActiveCourse()); //håndterer sql
            stats.showStatistics();
        }
        else{
            System.out.println("you are not authorized to check statistics");
        }
        return;
    }


    //Setters and getters
    public boolean getLoggedIN(){
        return loggedIn;
    }

    private void setLoggedIN(boolean loggedIN){
        this.loggedIn = loggedIN;
        return;
    }
    
    public String getUsername(){
        return username;
    }

    private void setUsername(String username){
        this.username = username;
        return;
    }


    public String getRole(){
        return role;
    }

    private void setRole(String role){
        this.role = role;
        return;
    }


    public String getActiveCourse(){
        return activeCourse;
    }



    
}
