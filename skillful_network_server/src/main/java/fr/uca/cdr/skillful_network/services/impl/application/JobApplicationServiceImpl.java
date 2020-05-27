package fr.uca.cdr.skillful_network.services.impl.application;

import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.repositories.application.JobApplicationRepository;
import fr.uca.cdr.skillful_network.services.application.JobApplicationService;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationServiceImpl extends ApplicationServiceImpl<JobApplication> implements JobApplicationService {

    public JobApplicationServiceImpl(JobApplicationRepository repository) {
        super(repository);
    }
}
