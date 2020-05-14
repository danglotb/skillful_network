package fr.uca.cdr.skillful_network.services.impl.simulation.exercise;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.*;
import fr.uca.cdr.skillful_network.repositories.simulation.exercise.ResultRepository;
import fr.uca.cdr.skillful_network.services.simulation.exercise.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service(value = "ResultService")
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    @Override
    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }

    @Override
    public Result saveOrUpdateResult(Result result) {
        return resultRepository.save(result);
    }

}
