package fr.uca.cdr.skillful_network.controller.application;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public Post create(@Valid @RequestBody Post post) {
		return this.postService.createPost(post);
	}
	
	@DeleteMapping(value = "/{id}")
    @Transactional
    public void delete(@PathVariable(value = "id") Long id) {
        postService.deletePostById(id);
    }
}
