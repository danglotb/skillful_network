package fr.uca.cdr.skillful_network.services.impl.post;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public List<Post> getAllPostForCurrentUser() {
        return this.getByUserId(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Post update(long id, String body, Date dateOfPost) {
        return update(authenticationService.getCurrentUser(), id, body, dateOfPost);
    }

    @Override
    public Post update(Long userId, long id, String body, Date dateOfPost) {
        return update(userService.getById(userId), id, body, dateOfPost);
    }

    public Post update(User user, long id, String body, Date dateOfPost) {
        final Post post = this.repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None Post could be found with the id %d", id))
        );
        post.setPostbodyText(body);
        post.setDateOfPost(dateOfPost);
        this.fstService.updateNotifications(user.getId(), Collections.singleton(post));
        return post;
    }

    @Override
    public void deletePostById(long id) {
        deletePostById(authenticationService.getCurrentUser(), id);
    }

    @Override
    public void deletePostById(Long userId, long id) {
        deletePostById(userService.getById(userId), id);
    }

    public void deletePostById(User user, long id) {
        this.fstService.popNotificationsByPostIds(user.getId(), Collections.singleton(id));
        this.repository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> getByUserId(long id) {
        return (List<Post>) this.repository.findById(id).orElse((Post) Collections.emptyList());
    }

}
