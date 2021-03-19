public class Post {

    protected int postID;
    protected String title;
    protected String author;
    protected String content;
    protected int courseId;
    protected String _type;
    protected boolean isAnonymous;

    protected int parentId;


    public int getPostId(){
        return postID;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setPostId(int postId) {
        this.postID = postId;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}