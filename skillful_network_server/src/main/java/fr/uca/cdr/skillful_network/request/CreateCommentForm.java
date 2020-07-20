package fr.uca.cdr.skillful_network.request;

public class CreateCommentForm {

    private String body;

    private long id;// might be the id of a Post or a Comment

    public CreateCommentForm() {
    }

    public CreateCommentForm(String body, long id) {
        this.body = body;
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(long id) {
        this.id = id;
    }
}
