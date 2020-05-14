package fr.uca.cdr.skillful_network.services.simulation.exercise;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.*;
import java.util.List;
import java.util.Optional;

public interface ResultService {

    List<Result> getAllResults();
    Optional<Result> getResultById(Long id);
    Result saveOrUpdateResult(Result result);

}
