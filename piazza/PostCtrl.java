package piazza;
import java.sql. *;
import java.time.LocalDate;

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

    
//the method to call on a useCase2ControllerObject
    public void addPost(String title, String displayName, String content, String folderName, String _type) {

        //init param
        LocalDate date = LocalDate.now();
        int folderID;
        int postID;
        boolean anonymousAllowed;
        String colorCode = "red";
        String author = displayName;

        //sjekker om faget vi skal poste i tillater anonyme posts
        anonymousAllowed = isAnonAllowed(courseID);

        //dersom anonymous lovlig, sett forfatter til det hen har skrevet inn
        setAuthor(displayName, email, author, anonymousAllowed);

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
    private boolean isAnonAllowed(String courseID) {
    boolean loc = false;
    try{
    PreparedStatement anonStatement = conn.prepareStatement(ANON_QUERY);
    anonStatement.setString(1, courseID);
    ResultSet anonRs = anonStatement.executeQuery();
    while (anonRs.next()) {
        loc = anonRs.getBoolean("anonymous");
        }
    } catch (SQLException e){
        System.out.println("DB error when checking for anon variable.");
    }
    return loc;
}
    
    private void setAuthor(String displayName, String email, String author, boolean anonymousAllowed) {
        if (displayName.equals(email) || !(anonymousAllowed)) {
            author = email;
            System.out.println("You have either entered your email, or anonymous name is not allowed in this course, so author has been set to email: " + email);
            return;
        } 
        if (anonymousAllowed){
            author = displayName;
            System.out.println("Anonymous name is allowed, so author has been set to the inserted display name: " + displayName);
        }
    }
    
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
       } catch (SQLException e) {
            System.out.println("DB error when inserting values into post.");
       }
}
    
    private void addPostToFolder(int postID, int folderID) { 
        try {
        PreparedStatement PostInFolderPreparedStatement = conn.prepareStatement(INSERT_POSTINFOLDER_SQL);
        PostInFolderPreparedStatement.setInt(1, postID);
        PostInFolderPreparedStatement.setInt(2, folderID);   
        PostInFolderPreparedStatement.executeUpdate();
        System.out.println("Post has successfully been added to Folder");            

        }catch (SQLException e) {
            System.out.println("DB error while trying to add Post to Folder");
        }        
    }

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
    


    public static void main(String[] args) {
       PostCtrl test = new PostCtrl("anders@ntnu.no","TDT4145");
       test.addPost("ny ffffffff", "Anders", "ny spm", "Exam", "Question");
    }

}