package piazza;
import java.util.*;  

public class User {
    private String username;
    private String role;
    private boolean loggedIn;
    private String activeCourse;

    public User(){
        loggedIn = false;
    }

    public void logIn(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Username: ");
        String name = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        LogInCtrl thisUser = new LogInCtrl(name,password);
        if(thisUser.executeLogIn()){
            this.username = name;
            this.role = thisUser.getRole();
            this.loggedIn = true;
        }
        else{
            System.out.println("Wrong username or password");
        }
        //sc.close();
    }

    public void checkStatistics(){
        if (this.getLoggedIN() && role.equals("Instructor")){
            Statistic stats = new Statistic(this.activeCourse);
            stats.showStatistics();
        }
        else{
            System.out.println("you are not authorized to check statistics");
        }
    }

    public void makePost(){
        useCase2Controller post = new useCase2Controller(this.username,this.activeCourse);
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

    }

    public void answerPostInstructor(){
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
    }

    public void searchForKeyword(){
        Search search = new Search();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter keyword: ");
        String keyword = sc.nextLine();
        //sc.close();

        search.executeSearch(keyword);

    }

    public void setActiveCourse(String activeCourse){
        this.activeCourse = activeCourse;
        return;
    }

    public boolean getLoggedIN(){
        return loggedIn;
    }

    public String getUsername(){
        return username;
    }

    public String getRole(){
        return role;
    }

    public String getActiveCourse(){
        return activeCourse;
    }


}
