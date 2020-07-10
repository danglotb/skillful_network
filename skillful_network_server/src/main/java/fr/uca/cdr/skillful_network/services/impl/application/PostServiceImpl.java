package fr.uca.cdr.skillful_network.services.impl.application;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.application.Post;
import fr.uca.cdr.skillful_network.repositories.application.PostRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.application.PostService;

@Service(value="postService")
public class PostServiceImpl implements PostService{
	
    @Autowired
	private PostRepository repository;
    
    @Autowired
    private AuthenticationService authenticationService;
	
	@Override
	public Post createPost(Post post) {
		return this.repository.save(post);
	}

	@Override
	public List<Post> getAll() {
		return this.repository.findAll();
	}

	@Override
	public List<Post> getPostsforCurrentUser() {
		return this.getByUserId(authenticationService.getCurrentUser().getId());
	}

	@Override
	public void deletePostById(long id) {
		 this.repository.deleteById(id);
	}

	@Override
	public Post update(long id, String postbodyText, Date dateOfPost, Set<String> files) {
		final Post post = this.repository.findById(id).get();
post.setPostbodyText(postbodyText);
post.setDateOfPost(dateOfPost);
post.setFiles(files);
return post;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getByUserId(long id) {
		return (List<Post>) this.repository.findById(id).orElse((Post) Collections.emptyList());
	}

}
