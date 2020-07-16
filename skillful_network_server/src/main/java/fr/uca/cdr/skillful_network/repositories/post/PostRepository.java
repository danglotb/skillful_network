package fr.uca.cdr.skillful_network.repositories.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post , Long>{

}
