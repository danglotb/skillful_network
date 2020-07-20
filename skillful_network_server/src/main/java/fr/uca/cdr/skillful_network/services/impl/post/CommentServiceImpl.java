package fr.uca.cdr.skillful_network.services.impl.post;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.entities.post.Comment;
import fr.uca.cdr.skillful_network.repositories.post.CommentRepository;
import fr.uca.cdr.skillful_network.services.post.CommentService;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PostService postService;

    @Override
    public Comment createComment(String body, Long idPost) {
        final Comment comment = new Comment(body, new Date(), authenticationService.getCurrentUser());
        this.postService.addComment(idPost, comment);
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
    public Comment updateComment(long id, String newBody) {
        final Comment comment = this.repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None Comment could be found with the id %d", id))
        );
        comment.setCommentBodyText(newBody);
        comment.setDateOfComment(new Date());
        repository.saveAndFlush(comment);
        return comment;
    }

    @Override
    public void deleteCommentById(Long id) {
        this.repository.deleteById(id);
    }

}
