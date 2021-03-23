package piazza;
import java.util.*;  


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
        Scanner sc= new Scanner(System.in);
        System.out.println("Username: ");
        String name = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        LogInCtrl log = new LogInCtrl(name,password);

        while (! log.executeLogIn()){
            System.out.println("Wrong username or password");
            System.out.println("Press 1 to try again or 2 to break");

            int check = sc.nextInt();
            switch(check){
                case 1:
                System.out.println("USername: ");
                name = sc.nextLine();
                System.out.println("Password: ");
                password = sc.nextLine();
    
                log.setInput(name, password);
                log.executeLogIn();
                case 2:
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
        PostCtrl post = new PostCtrl(getUsername(),getActiveCourse());
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Title: ");
        String title = sc.nextLine();
        System.out.println("Content: ");
        String content = sc.nextLine();
        System.out.println("Folder: ");
        String foldername = sc.nextLine();
        System.out.println("Type: ");
        String type = sc.nextLine();
        System.out.println("Choose your display name: ");
        String dispname = sc.nextLine();
        //sc.close();
        
        post.addPost(title, dispname, content, foldername, type);
        return;
    }

    //Usecase 3: en instruktør svarer på en post
    public void answerPostInstructor(){
        if (! getRole().equals("Instructor")){
            System.out.println("You are not authorized to answer as an instructor.");
            return;
        }

        AnswerCtrl answer = new AnswerCtrl();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter postID: ");
        int postid = sc.nextInt();

        while(! answer.checkPostExist(postid)){
            System.out.println("PostID does not exist.");
            System.out.println("Press 1 to enter existing PostID or press 2 to break");

            int check = sc.nextInt();
            switch(check){
                case 1:
                postid = sc.nextInt();

                case 2:
                return;
            }
        }

        System.out.println("Post found");
        System.out.println("Enter answer: ");
        String content = sc.nextLine();

        answer.createInstructorAnswer(postid, content);
        answer.createAnsweredByInstructor(postid);
        answer.postAnsweredByInstructor(postid);
      
        //sc.close();
        return;
    }   


    //Usecase 4: lete gjennom poster etter et keyword
    public void searchForKeyword(){
        SearchCtrl search = new SearchCtrl();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter keyword: ");
        String keyword = sc.nextLine();
        //sc.close();

        search.executeSearch(keyword);
        return;
    }


    //Usecase 5: en intruktør ser på statistikk over brukere i faget
    public void checkStatistics(){
        if (getRole().equals(("Instructor"))){
            Statistic stats = new Statistic(getActiveCourse());
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
