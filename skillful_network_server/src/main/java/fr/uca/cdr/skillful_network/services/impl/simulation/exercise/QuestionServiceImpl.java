package fr.uca.cdr.skillful_network.services.impl.simulation.exercise;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.*;
import fr.uca.cdr.skillful_network.repositories.simulation.exercise.QuestionRepository;
import fr.uca.cdr.skillful_network.services.simulation.exercise.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service(value = "QuestionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question saveOrUpdateQuestion(Question question) {
        return questionRepository.save(question);
    }

}
