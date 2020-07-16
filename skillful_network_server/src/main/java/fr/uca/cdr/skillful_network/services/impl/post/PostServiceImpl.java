package fr.uca.cdr.skillful_network.services.impl.post;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private AuthenticationService authenticationService;

    @Override
    public Post createPost(String body) {
        Post post = new Post(
                body,
                new Date(),
                this.authenticationService.getCurrentUser()
        );
        this.repository.save(post);
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
    public void deletePostById(long id) {
        this.repository.deleteById(id);
    }

    @Override
    public Post update(long id, String body, Date dateOfPost) {
        final Post post = this.repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None JobOffer could be found with the id %d", id))
        );
        post.setPostbodyText(body);
        post.setDateOfPost(dateOfPost);
        return post;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> getByUserId(long id) {
        return (List<Post>) this.repository.findById(id).orElse((Post) Collections.emptyList());
    }

}
