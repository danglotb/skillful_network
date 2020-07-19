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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import fr.uca.cdr.skillful_network.entities.user.User;

@Entity
public class Post {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String postBodyText;
	
	private Date dateOfPost;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private User user;

	@OneToMany
	@JsonManagedReference
    private Set<Comment> comments = new HashSet<>() ;
	
	public Post() {
		super();
	}

	public Post( String postBodyText, Date dateOfPost, User user) {
		super();
		this.postBodyText = postBodyText;
		this.dateOfPost = dateOfPost;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPostBodyText() {
		return postBodyText;
	}

	public void setPostBodyText(String postBodyText) {
		this.postBodyText = postBodyText;
	}

	public Date getDateOfPost() {
		return dateOfPost;
	}

	public void setDateOfPost(Date dateOfPost) {
		this.dateOfPost = dateOfPost;
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

	public int getCommentsNumber() {
		return this.getComments().size();
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", postBodyText=" + postBodyText + ", dateOfPost=" + dateOfPost
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
		Post other = (Post) obj;
		return id == other.id;
	}

}
