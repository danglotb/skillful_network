package fr.uca.cdr.skillful_network.services.application;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.application.Post;
import fr.uca.cdr.skillful_network.entities.user.User;

public interface PostService {
   Post createPost(String body);
   List<Post> getAll();
   List<Post> getByUserId(long id);
   List<Post> getPostsforCurrentUser();
   void deletePostById(long id);
   Post update(long id, String postbodyText, Date dateOfPost, Set<String> files);
}
