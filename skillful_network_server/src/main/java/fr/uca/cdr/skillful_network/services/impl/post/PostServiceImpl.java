package fr.uca.cdr.skillful_network.services.impl.post;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.post.Comment;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.repositories.post.PostRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.post.PostService;

@Service(value = "postService")
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowStateTrackerService fstService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Post createPost(String body) {
        return createPost(authenticationService.getCurrentUser(), body);
    }

    @Override
    public Post createPost(Long userId, String body) {
        return createPost(userService.getById(userId), body);
    }

    public Post createPost(User user, String body) {
        Post post = new Post(body, new Date(), user);
        this.repository.save(post);
        this.fstService.pushNotifications(user.getId(), Collections.singleton(post));
        return post;
    }

    @Override
    public List<Post> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<List<Post>> getAllPostForCurrentUser() {
        return this.repository.getPostsByUserId(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Post update(Long id, String body, Date dateOfPost) {
        return update(authenticationService.getCurrentUser(), id, body, dateOfPost);
    }

    @Override
    public Post update(Long userId, Long id, String body, Date dateOfPost) {
        return update(userService.getById(userId), id, body, dateOfPost);
    }

    public Post update(User user, Long id, String body, Date dateOfPost) {
        final Post post = this.repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None Post could be found with the id %d", id))
        );
        post.setPostBodyText(body);
        post.setDateOfPost(dateOfPost);
        this.fstService.updateNotifications(user.getId(), Collections.singleton(post));
        return post;
    }

    @Override
    public void addComment(Long id, Comment comment) {
        this.getPostById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None Post could be found with the id %d", id))
        ).getComments().add(comment);
    }

    @Override
    public void deletePostById(Long id) {
        deletePostById(authenticationService.getCurrentUser(), id);
    }

    @Override
    public void deletePostById(Long userId, Long id) {
        deletePostById(userService.getById(userId), id);
    }

    public void deletePostById(User user, Long id) {
        this.fstService.popNotificationsByPostIds(user.getId(), Collections.singleton(id));
        this.repository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<List<Post>> getByUserId(Long userId) {
        return this.repository.getPostsByUserId(userId);
    }

	@Override
	public Optional<Post> getPostById(Long id) {
		
		return this.repository.findById(id);
	}
    
    

}
