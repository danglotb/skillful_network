package fr.uca.cdr.skillful_network.services.impl.application;

import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import fr.uca.cdr.skillful_network.repositories.application.TrainingApplicationRepository;
import fr.uca.cdr.skillful_network.services.application.TrainingApplicationService;
import org.springframework.stereotype.Service;

@Service
public class TrainingApplicationServiceImpl  extends ApplicationServiceImpl<TrainingApplication> implements TrainingApplicationService {

    public TrainingApplicationServiceImpl(TrainingApplicationRepository repository) {
        super(repository);
    }

}
