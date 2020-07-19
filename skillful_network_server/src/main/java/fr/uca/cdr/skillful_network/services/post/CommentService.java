package fr.uca.cdr.skillful_network.services.post;

import java.util.List;

import fr.uca.cdr.skillful_network.entities.post.Comment;

public interface CommentService {

      Comment createComment(String body);
      List<Comment> getAllComments();
      List<Comment> getAllCommentsByCommentId(Long id);
      List<Comment> getAllCommentsByPostId(Long id);
      Comment updateComment(long id, String newBody);
      void deleteCommentById(Long id);

}
