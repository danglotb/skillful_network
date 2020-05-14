package fr.uca.cdr.skillful_network.services.impl.simulation.exercise;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.*;
import fr.uca.cdr.skillful_network.repositories.simulation.exercise.ExamRepository;
import fr.uca.cdr.skillful_network.services.simulation.exercise.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service(value = "ExamService")
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public Exam saveOrUpdateExam(Exam exam) {
        return examRepository.save(exam);
    }

}
