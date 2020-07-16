package fr.uca.cdr.skillful_network.controller.post;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.uca.cdr.skillful_network.entities.application.Comment;
import fr.uca.cdr.skillful_network.services.application.CommentService;


@CrossOrigin("*")
@RestController
@RequestMapping("/comments")
public class CommentController {
     @Autowired
     private CommentService commentService ;
     
     @GetMapping(value="")
     public List<Comment> getAll(){
 		return this.commentService.getAllComments();
 	}
     
    @PostMapping(value="")
    public Comment createComment(@Valid @RequestBody Comment comment) {
    	return this.commentService.createComment(comment);
    }
    
    @DeleteMapping(value="/{id}")
    @Transactional
    public void delete(@PathVariable(value = "id") Long id) {
         commentService.deleteCommentById(id);      
    }
    
    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Comment> update(@PathVariable(value = "id") long id,
                                                   @Valid @RequestBody Comment commentToUpdate) {

        return new ResponseEntity<>(
                this.commentService.updateComment(
                        id,
                        commentToUpdate.getCommentBodyText(),
                        commentToUpdate.getDateOfComment(),
                        commentToUpdate.getFiles()
                ), HttpStatus.OK);
    }
    
    @GetMapping(value="/post/{id}")
    public List<Comment> getCommentsByPostId(@PathVariable(value = "id") Long id){
		return this.commentService.getAllCommentsByPostId(id);
	}
    
    @GetMapping(value="/comment/{id}")
    public List<Comment> getCommentsByCommentId(@PathVariable(value="id") Long id){
    	return this.commentService.getAllCommentsByCommentId(id);
    }
    
}
