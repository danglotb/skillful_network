package fr.uca.cdr.skillful_network.controller.post;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import fr.uca.cdr.skillful_network.services.AuthenticationService;
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

import fr.uca.cdr.skillful_network.entities.application.Post;
import fr.uca.cdr.skillful_network.services.application.PostService;

@CrossOrigin("*")
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired 
	private PostService postService;
	
	@GetMapping(value="")
	public List<Post> getAll(){
		return this.postService.getAll();
	}
	
	@PostMapping(value="")
	public Post create(@Valid @RequestBody String body) {
		return this.postService.createPost(body);
	}
	
	 @PutMapping(value = "/{id}")
	    @Transactional
	    public ResponseEntity<Post> update(@PathVariable(value = "id") long id,
	                                                   @Valid @RequestBody Post postToUpdate) {

	        return new ResponseEntity<>(
	                this.postService.update(
	                        id,
	                        postToUpdate.getPostbodyText(),
	                        postToUpdate.getDateOfPost(),
	                        postToUpdate.getFiles()
	                ), HttpStatus.OK);
	    }
	 
	@DeleteMapping(value = "/{id}")
    @Transactional
    public void delete(@PathVariable(value = "id") Long id) {
        postService.deletePostById(id);
    }
}
