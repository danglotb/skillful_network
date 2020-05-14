package fr.uca.cdr.skillful_network.repositories.simulation.exercise;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.QuestionSet;

@Repository
public interface QuestionSetRepository extends JpaRepository<QuestionSet,Long>  {
	
	Optional<QuestionSet> findById(Long id);
}
