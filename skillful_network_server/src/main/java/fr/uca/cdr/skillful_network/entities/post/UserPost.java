package fr.uca.cdr.skillful_network.entities.post;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.uca.cdr.skillful_network.entities.user.User;

@Entity
@Table(name = "T_USER_POST")
public class UserPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private boolean hasLiked = false;

	public UserPost() {
		super();
	}

	public UserPost(User user, Post post, boolean hasLiked) {
		super();
		this.user = user;
		this.post = post;
		this.hasLiked = hasLiked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public boolean isHasLiked() {
		return hasLiked;
	}

	public void setHasLiked(boolean hasLiked) {
		this.hasLiked = hasLiked;
	}

	@Override
	public String toString() {
		return "UserPost [id=" + id + ", user=" + user + ", post=" + post + ", hasLiked=" + hasLiked + "]";
	}

}