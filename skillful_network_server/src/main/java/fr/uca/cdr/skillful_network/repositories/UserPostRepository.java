package fr.uca.cdr.skillful_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.post.UserPost;
@Repository
public interface UserPostRepository extends JpaRepository<UserPost , Long>{
   UserPost findByUserId(Long id);
}
