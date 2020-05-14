package fr.uca.cdr.skillful_network.repositories.simulation.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.Exam;
@Repository
public interface ExamRepository extends JpaRepository<Exam,Long>{

}
