package fr.uca.cdr.skillful_network.services.post;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.post.Comment;
import fr.uca.cdr.skillful_network.entities.post.Post;

public interface PostService {

    Post createPost(String body);
    Post createPost(Long userId, String body);

    List<Post> getAll();
    Optional<List<Post>> getByUserId(Long id);
    Optional<List<Post>> getAllPostForCurrentUser();
    Optional<Post> getPostById(Long id);
    Post update(Long id, String body, Date dateOfPost);
    Post update(Long userId, Long id, String body, Date dateOfPost);
    void addComment(Long id, Comment comment);

    void deletePostById(Long id);
    void deletePostById(Long userId, Long id);
}
