package fr.uca.cdr.skillful_network.repositories.user;

import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.entities.user.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long>{

	// TODO see if we must use an Optional here, the method search() might return an empty, that would be ok
	@Query(value="select * from qualification where name like %:keyword%", nativeQuery = true)
	Optional<List<Qualification>> search(@Param("keyword")String prefix);

	Optional<Qualification> findByName(String name);

	Optional<Qualification> findById(long id);
}
