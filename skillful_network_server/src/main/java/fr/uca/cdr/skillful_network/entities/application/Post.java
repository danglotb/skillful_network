package fr.uca.cdr.skillful_network.entities.application;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import fr.uca.cdr.skillful_network.entities.user.User;

@Entity
public class Post {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String postbodyText;
	
	private Date dateOfPost ;
	
	private Set<String> files = new HashSet<String>() ;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private User user;

	public Post(long id, String postbodyText, Date dateOfPost, Set<String> files, User user) {
		super();
		this.id = id;
		this.postbodyText = postbodyText;
		this.dateOfPost = dateOfPost;
		this.files = files;
		this.user = user;
	}

	public Post(long id, String postbodyText, Date dateOfPost, User user) {
		super();
		this.id = id;
		this.postbodyText = postbodyText;
		this.dateOfPost = dateOfPost;
		this.user = user;
	}

	public Post(long id, Date dateOfPost, Set<String> files, User user) {
		super();
		this.id = id;
		this.dateOfPost = dateOfPost;
		this.files = files;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPostbodyText() {
		return postbodyText;
	}

	public void setPostbodyText(String postbodyText) {
		this.postbodyText = postbodyText;
	}

	public Date getDateOfPost() {
		return dateOfPost;
	}

	public void setDateOfPost(Date dateOfPost) {
		this.dateOfPost = dateOfPost;
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

	@Override
	public String toString() {
		return "Post [id=" + id + ", PostbodyText=" + postbodyText + ", dateOfPost=" + dateOfPost + ", files=" + files
				+ ", user=" + user + "]";
	}

	
	
	
	

}
