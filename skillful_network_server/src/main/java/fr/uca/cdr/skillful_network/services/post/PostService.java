package fr.uca.cdr.skillful_network.services.post;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.post.Comment;
import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.entities.user.User;

public interface PostService {

    Post createPost(String body);
    Post createPost(Long userId, String body);

    List<Post> getAll();
    Optional<List<Post>> getByUserId(Long id);
    Optional<List<Post>> getAllPostForCurrentUser();

    Optional<Post> getPostById(long id);
    Optional<User> getUserByPostId(long postId);
    Post update(long id, String body, Date dateOfPost);
    Post update(Long userId, long id, String body, Date dateOfPost);
    Post addComment(Long id, Comment comment);
    Post removeComment(Long id, Comment comment);

    void deletePostById(Long id);
    void deletePostById(Long userId, Long id);
}
