package piazza;
import java.sql. *;
import java.time.LocalDate;

public class useCase2Controller extends ConnectorClass {

public useCase2Controller() {
    connect();
}

protected static final String INSERT_POST_SQL = "INSERT INTO post(title, author, content, courseID, _type, colorCode, creatoremail) values(?, ?, ?, ?, ?, ?, ?) ";
protected static final String ANON_QUERY = "SELECT anonymous FROM Course WHERE CourseID = (?)"; 
protected static final String SELECT_EXAM_FOLDER_QUERY = "SELECT folderID FROM Folder WHERE _name = (?)"; //spør ikke til courseID.... 
protected static final String SELECT_LASTPOSTID_QUERY = "SELECT postID FROM Post WHERE postID = (SELECT max(postID) FROM Post)";
protected static final String INSERT_POSTINFOLDER_SQL = "INSERT INTO PostInFolder(postID,folderID) values (?,?) ";

public void addPost(String title, String displayName, String content, String folderName, String _type) {

    try {
        //henter dagens dato
        LocalDate date = LocalDate.now();
        
        String email = "augustlonningmeo@gmail.com";
        String colorCode = "red";
        String courseID = "TDT4145";
        String author = displayName;

        //sjekker om faget vi skal poste i tillater anonyme posts
        boolean anonymousAllowed;
        PreparedStatement anonStatement = conn.prepareStatement(ANON_QUERY);
        anonStatement.setString(1, courseID);
        ResultSet anonRs = anonStatement.executeQuery();
        anonymousAllowed = anonRs.getBoolean("anonymous");

        //dersom anonymous lovlig, sett forfatter til det hen har skrevet inn
        if (displayName.equals(email) || !(anonymousAllowed)) {
            author = email;
            System.out.println("Anonymous name is not allowed in this course, so author has been set to email");
        } else {
            author = displayName;
            System.out.println("Anonymous name is allowed, so author has been set to the inserted display name.");
        }
        // vi skal sette inn i post-tabellen: 1title,2author,3content,4courseID,5_type,6colorCode,7creatorEmail,8_Date
        
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
        //må jeg bruke result set her?

        //må spørre etter FolderID'en til exam folder
        PreparedStatement findExamFolderID = conn.prepareStatement(SELECT_EXAM_FOLDER_QUERY);
        findExamFolderID.setString(1,folderName);
        ResultSet examFolderIDrs = findExamFolderID.executeQuery();
        int folderID = examFolderIDrs.getInt("folderID");
        System.out.println("FolderID has successfully been retrieved");
        
        //må spørre etter PostID'en til posten vi nettopp laga (den ble autoinkrementert)
        PreparedStatement findPostIDPreparedStatement = conn.prepareStatement(SELECT_LASTPOSTID_QUERY);
        ResultSet lastpostIDrs = findPostIDPreparedStatement.executeQuery();
        int postID = lastpostIDrs.getInt("postID");
        System.out.println("PostID has successfully been retrieved");

        //må legge til post i riktig folder
        addPostToFolder(postID, folderID);
    }
    catch (SQLException e) {
        System.out.println("DB error while trying to add post: " + e);
    }
}

    public void addPostToFolder(int postID, int folderID) { 
        try {
        PreparedStatement PostInFolderPreparedStatement = conn.prepareStatement(INSERT_POSTINFOLDER_SQL);
        PostInFolderPreparedStatement.setInt(1, postID);
        PostInFolderPreparedStatement.setInt(2, folderID);   
        PostInFolderPreparedStatement.executeUpdate();
        System.out.println("Post has successfully been added to Folder.");            

        }catch (SQLException e) {
            System.out.println("DB error while trying to add Post to Folder");
        }        
    }

    public static void main() {
       
    }

} //pretty much done