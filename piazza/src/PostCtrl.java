package piazza;
import java.sql. *;
import java.time.LocalDate;

//controller for å lage post
public class PostCtrl extends ConnectorClass {

    private String email;
    private String courseID;


    public PostCtrl(String email, String courseID) {
    connect();
    this.email = email;
    this.courseID = courseID;
    }

    //queries 
    protected static final String INSERT_POST_SQL = "INSERT INTO post(title, author, content, courseID, _type, colorCode, creatoremail, _date) values(?, ?, ?, ?, ?, ?, ?, ?) ";
    protected static final String ANON_QUERY = "SELECT anonymous FROM Course WHERE CourseID = (?)"; 
    protected static final String SELECT_EXAM_FOLDER_QUERY = "SELECT folderID FROM Folder WHERE _name = (?)"; //spør ikke til courseID.... 
    protected static final String SELECT_LASTPOSTID_QUERY = "SELECT postID FROM Post WHERE postID = (SELECT max(postID) FROM Post)";
    protected static final String INSERT_POSTINFOLDER_SQL = "INSERT INTO PostInFolder(postID,folderID) values (?,?) ";

    
    //kalles på PostCtrl objekter
    public void addPost(String title, String anonWanted, boolean anonAllowed, String content, String folderName, String _type) {

        //init param
        LocalDate date = LocalDate.now();
        int folderID;
        int postID;
        String colorCode = "red";
        String author = "";
        String anonymousWanted = anonWanted; //bruker spørres om å poste anonymt dersom faget tillater det
        boolean anonymousAllowed = anonAllowed; //sjekkes utenfor, i piazza-klassen

        //setter forfatter basert på om anonymous er tillat og ønsket
        setAuthor(email, anonymousAllowed, anonymousWanted);

        // vi skal sette inn i post-tabellen: 1title,2author,3content,4courseID,5_type,6colorCode,7creatorEmail,8_Date
        insertToPost(title, author, content, courseID, _type, colorCode, email, date);

        //må spørre etter FolderID'en til exam folder
        folderID = findFolderID(folderName);

        //må spørre etter PostID'en til posten vi nettopp laga (den ble autoinkrementert)
        postID = findPostID();

        //må legge til post i riktig folder
        addPostToFolder(postID, folderID);
    }
    

    //helpers
    //public: akkurat denne skal brukes utenfor: kan bruker velge å være anonym
    public boolean isAnonAllowed(String courseID) {
        boolean loc = false;
        try{
        PreparedStatement anonStatement = conn.prepareStatement(ANON_QUERY);
        anonStatement.setString(1, courseID);
        ResultSet anonRs = anonStatement.executeQuery();
            while (anonRs.next()) {
                loc = anonRs.getBoolean("anonymous");
            }
        } 
        catch (SQLException e){
        System.out.println("DB error when checking for anon variable.");
        }
        return loc;
    }


    //setter authour basert på hva som er tillat og ønsket
    private String setAuthor(String email,boolean anonymousAllowed,String anonymousWanted) {
        String author = "";
        if (anonymousAllowed && ( anonymousWanted.equals("Y") || anonymousWanted.equals("y"))){
            author = "Anonymous";
        } 
        else{
            author = this.email;
        }
        return author;
    }
    
    //utfører innsetting i post-tabellen
    private void insertToPost(String title, String author, String content, String courseID, String _type, String colorCode, String email, LocalDate date) {
       try {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_POST_SQL);
            preparedStatement.setString(1,title);        
            preparedStatement.setString(2,author); 
            preparedStatement.setString(3,content);
            preparedStatement.setString(4,courseID);
            preparedStatement.setString(5,_type);
            preparedStatement.setString(6, colorCode);
            preparedStatement.setString(7, email); 
            preparedStatement.setObject(8, date); 
            preparedStatement.executeUpdate();
            System.out.println("Post has successfully been created");
        } 
       catch (SQLException e) {
            System.out.println("DB error when inserting values into post.");
        }
    }
    
    //utfører innsetting i postinfolder-tabellen 
    private void addPostToFolder(int postID, int folderID) { 
        try {
            PreparedStatement PostInFolderPreparedStatement = conn.prepareStatement(INSERT_POSTINFOLDER_SQL);
            PostInFolderPreparedStatement.setInt(1, postID);
            PostInFolderPreparedStatement.setInt(2, folderID);   
            PostInFolderPreparedStatement.executeUpdate();
            System.out.println("Post has successfully been added to Folder");            

        }
        catch (SQLException e) {
            System.out.println("DB error while trying to add Post to Folder");
        }        
    }

    //finner folderID til angitt mappe
    private int findFolderID(String folderName) {
    int folderID = 0;
        try {
        PreparedStatement findExamFolderID = conn.prepareStatement(SELECT_EXAM_FOLDER_QUERY);
        findExamFolderID.setString(1,folderName);
        ResultSet examFolderIDrs = findExamFolderID.executeQuery();
        while (examFolderIDrs.next()) {
            folderID = examFolderIDrs.getInt("folderID");
            System.out.println("FolderID has successfully been retrieved");
        }
    } catch (SQLException e) {
        System.out.println("DB error while trying to find FolderID.");
    }
     return folderID;
    }

    //finner postID til posten som nettopp er blitt laget
    //må hentes vha spøøring fordi postID autoinkrementeres
    private int findPostID() {
        int localpostID = 0;
        try {
        PreparedStatement findPostIDPreparedStatement = conn.prepareStatement(SELECT_LASTPOSTID_QUERY);
        ResultSet lastpostIDrs = findPostIDPreparedStatement.executeQuery();
        while (lastpostIDrs.next()) {
            localpostID = lastpostIDrs.getInt("postID");
            System.out.println("PostID has successfully been retrieved");
            }
        } catch (SQLException e) {
            System.out.println("DB error while trying to find postID");
        }
        return localpostID;
    }
    

}