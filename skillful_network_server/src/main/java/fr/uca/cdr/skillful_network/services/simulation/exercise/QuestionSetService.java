package fr.uca.cdr.skillful_network.services.simulation.exercise;

import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.QuestionSet;
import fr.uca.cdr.skillful_network.request.ExerciseForm;

public interface QuestionSetService {
	
	double calculateGrade(ExerciseForm exerciseForm, double weightByExercice);
	List<QuestionSet> getAllQuestionSets();
    Optional<QuestionSet> getQuestionSetById(Long id);
    QuestionSet saveOrUpdateQuestionSet(QuestionSet questionSet);
			
}
