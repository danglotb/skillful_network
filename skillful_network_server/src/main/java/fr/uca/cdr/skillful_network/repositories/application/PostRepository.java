package fr.uca.cdr.skillful_network.repositories.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.application.Post;

@Repository
public interface PostRepository extends JpaRepository<Post , Long>{

}
