package fr.uca.cdr.skillful_network.repositories.user;


import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.entities.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{

	@Query(value="select * from skill where name like %:keyword%", nativeQuery = true)
	Optional<List<Skill>> search(@Param("keyword")String keyword);

	Optional<Skill> findByName(String name);

	Optional<Skill> findById(long id);
}
