package fr.uca.cdr.skillful_network.services.impl.application;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.entities.application.Comment;
import fr.uca.cdr.skillful_network.entities.application.Post;
import fr.uca.cdr.skillful_network.repositories.application.CommentRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.application.CommentService;

@Service(value="commentService")
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository repository;
	
	@Override
	public Comment createComment(Comment comment) {
		return this.repository.save(comment);
	}

	@Override
	public List<Comment> getAllComments() {
		return this.repository.findAll();
	}

	@Override
	public List<Comment> getAllCommentsByCommentId(Long id) {
		return this.repository.findAllByCommentId(id);
	}

	@Override
	public List<Comment> getAllCommentsByPostId(Long id) {
		return this.repository.findAllByPostId(id);
	}

	@Override
	public Comment updateComment(long id, String commentBodyText, Date dateOfComment, Set<String> files) {
		final Comment comment = this.repository.findById(id).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("None Comment could be found with the id %d", id))
		);
		comment.setCommentBodyText(commentBodyText);
		comment.setDateOfComment(dateOfComment);
		comment.setFiles(files);
		return comment;
	}

	@Override
	public void deleteCommentById(Long id) {
		 this.repository.deleteById(id);
		
	}

}
