package fr.uca.cdr.skillful_network.entities.application;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	@OneToMany(cascade=CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>() ;

	public Comment() {
		super();
	}

	public Comment(String commentBodyText, Date dateOfComment, Set<String> files, User user, Set<Comment> comments) {
		super();
		this.commentBodyText = commentBodyText;
		this.dateOfComment = dateOfComment;
		this.files = files;
		this.user = user;
		this.comments = comments;
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

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", commentBodyText=" + commentBodyText + ", dateOfComment=" + dateOfComment
				+ ", files=" + files + ", user=" + user + ", comments=" + comments + "]";
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
