package piazza;
import java.util.*; 

public class Main {

    public static void main(String[] args) {
        Piazza pz = new Piazza();
        Scanner sc = new Scanner(System.in);

        pz.logIn();

        while (pz.getLoggedIN()){

            System.out.println("You are now logged in as "+pz.getUsername()
            +"\n Press 0 to change user"
            +"\n Press 1 to make a post"
            +"\n Press 2 to answer a post as an instructor"
            +"\n Press 3 to search the posts for a keyword"
            +"\n Press 4 to view user-statistics"
            +"\n Press 5 to exit");

            int choice = sc.nextInt();
            switch(choice){
                case 0:
                System.out.println("You are now logging into a different user.");
                pz.logIn();
                break;

                case 1:
                pz.makePost();
                break;

                case 2:
                pz.answerPostInstructor();
                break;

                case 3:
                pz.searchForKeyword();
                break;

                case 4:
                pz.checkStatistics();
                break;

                case 5:
                System.out.println("You are now logging out. \nGoodbye!");
                sc.close();
                return;
            }

        }

        sc.close();
        return;
    }
    
}
