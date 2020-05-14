package fr.uca.cdr.skillful_network.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.user.Qualification;


@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long>{
	
//	Méthode pour chercher une qualification en utilisant le prefix(auto-complete)
	
	@Query(value="select * from qualification where name like %:prefix%", nativeQuery = true)
	List<Qualification> search(@Param("prefix")String prefix);
}
