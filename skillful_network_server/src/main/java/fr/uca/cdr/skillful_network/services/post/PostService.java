package fr.uca.cdr.skillful_network.services.post;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.post.Post;

public interface PostService {
    Post createPost(String body);
    Post createPost(Long userId, String body);

    List<Post> getAll();
    List<Post> getByUserId(long id);
    List<Post> getAllPostForCurrentUser();

    Post update(long id, String body, Date dateOfPost);
    Post update(Long userId, long id, String body, Date dateOfPost);

    void deletePostById(long id);
    void deletePostById(Long userId, long id);
}
