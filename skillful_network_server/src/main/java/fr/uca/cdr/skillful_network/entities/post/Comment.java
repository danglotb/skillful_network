package fr.uca.cdr.skillful_network.entities.post;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import fr.uca.cdr.skillful_network.entities.user.User;

@Entity
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String commentBodyText;
	
	private Date dateOfComment ;
	
	@ElementCollection
	private Set<String> files = new HashSet<String>() ;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private User user;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="comment_id")
	private Comment comment ;
	
	
	@OneToMany(mappedBy="comment")
	@JsonIgnore
	private Set<Comment> comments = new HashSet<Comment>() ;

	@ManyToOne(cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private Post post;
	
	public Comment() {
		super();
	}

	public Comment(String commentBodyText, Date dateOfComment, Set<String> files, User user, Set<Comment> comments,
			Post post) {
		super();
		this.commentBodyText = commentBodyText;
		this.dateOfComment = dateOfComment;
		this.files = files;
		this.user = user;
		this.comments = comments;
		this.post = post;
	}


	public Comment(String commentBodyText, Date dateOfComment, User user) {
		super();
		this.commentBodyText = commentBodyText;
		this.dateOfComment = dateOfComment;
		this.user = user;
	}

	public Comment(Date dateOfComment, Set<String> files, User user) {
		super();
		this.dateOfComment = dateOfComment;
		this.files = files;
		this.user = user;
	}

	public Comment(Date dateOfComment, Set<String> files, User user, Set<Comment> comments) {
		super();
		this.dateOfComment = dateOfComment;
		this.files = files;
		this.user = user;
		this.comments = comments;
	}

	public Comment(String commentBodyText, Date dateOfComment, Set<String> files, User user, Post post) {
		super();
		this.commentBodyText = commentBodyText;
		this.dateOfComment = dateOfComment;
		this.files = files;
		this.user = user;
		this.post = post;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommentBodyText() {
		return commentBodyText;
	}

	public void setCommentBodyText(String commentBodyText) {
		this.commentBodyText = commentBodyText;
	}

	public Date getDateOfComment() {
		return dateOfComment;
	}

	public void setDateOfComment(Date dateOfComment) {
		this.dateOfComment = dateOfComment;
	}

	public Set<String> getFiles() {
		return files;
	}

	public void setFiles(Set<String> files) {
		this.files = files;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	

	@Override
	public String toString() {
		return "Comment [id=" + id + ", commentBodyText=" + commentBodyText + ", dateOfComment=" + dateOfComment
				+ ", user=" + user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return id == other.id;
	}
	
	
	
	
}
