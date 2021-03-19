package piazza;
import java.sql. *;

public class useCase2Controller extends ConnectorClass {

public useCase2Controller() {
    connect();
}



public int addPost(String title, String author, String content, String courseID, String _type, String colorCode, String email) {

    //if loggedIn

    int postID = 0;

    try {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Post (title,author,content,type,colorCode,creator.email) " + "VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(2,title);
        preparedStatement.setString(3,author);
        preparedStatement.setString(4,content);
        preparedStatement.setString(5,courseID); //user.course
        preparedStatement.setString(6,_type); //question
        preparedStatement.setString(7, colorCode);
        preparedStatement.executeUpdate();
    }
    catch (SQLException e) {
        System.out.println("DB error while trying to add post: " + e);
    }

//studenten velger om det skal være en anonym post, må sjekkes mot emnet om dette er lovlig(spør mot course)
//if lovlig, author = anon,
//else author = anon,

//createPost(PostID, Title, Author, Content, Type, ColorCode, creator.email)

return postID;
    }

}