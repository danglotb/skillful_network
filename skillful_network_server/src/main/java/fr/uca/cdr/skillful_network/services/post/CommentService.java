package fr.uca.cdr.skillful_network.services.application;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.application.Comment;

public interface CommentService {
      Comment createComment(Comment comment);
      List<Comment> getAllComments();
      List<Comment> getAllCommentsByCommentId(Long id);
      List<Comment> getAllCommentsByPostId(Long id);
      Comment updateComment(long id, String commentBodyText, Date dateOfComment, Set<String> files);
      void deleteCommentById(Long id);
}
