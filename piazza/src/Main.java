package piazza.src;
import java.util.*; 

//selve tekst interfacet
public class Main {

    public static void main(String[] args) {
        Piazza pz = new Piazza(); //piazza instans
        Scanner sc = new Scanner(System.in);

        //må aller først logge inn
        pz.logIn();

        //å være innlogget er en forutsetning for tilgang til mer funksjonalitet
        while (pz.getLoggedIN()){

            System.out.println("\nYou are logged in as "+pz.getUsername()
            +"\n\n Press 0 to change user"
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
