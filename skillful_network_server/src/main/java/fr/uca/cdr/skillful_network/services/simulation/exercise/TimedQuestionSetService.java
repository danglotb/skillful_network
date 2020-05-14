package fr.uca.cdr.skillful_network.services.simulation.exercise;

import fr.uca.cdr.skillful_network.entities.simulation.exercise.*;
import java.util.List;
import java.util.Optional;

public interface TimedQuestionSetService {

    List<TimedQuestionSet> getAllTimedQuestionSets();
    Optional<TimedQuestionSet> getTimedQuestionSetById(Long id);
    TimedQuestionSet saveOrUpdateTimedQuestionSet(TimedQuestionSet timedQuestionSet);

}
