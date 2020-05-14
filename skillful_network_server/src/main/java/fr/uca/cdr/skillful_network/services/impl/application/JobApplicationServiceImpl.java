package fr.uca.cdr.skillful_network.services.impl.application;

import fr.uca.cdr.skillful_network.entities.application.Application;
import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.application.JobApplicationRepository;
import fr.uca.cdr.skillful_network.repositories.application.JobOfferRepository;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.application.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service(value = "JobApplicationService")
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Override
    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public Optional<JobApplication> getJobApplicationById(Long id) {
        return jobApplicationRepository.findById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return jobApplicationRepository.findById(id)
                .map(Application::getUser);
    }

    @Override
    public Optional<JobOffer> getJobOfferById(Long id) {
        return jobApplicationRepository.findById(id)
                .map(JobApplication::getJobOffer);
    }

    @Override
    public Optional<List<JobApplication>> getJobApplicationsByUserId(Long userId) {
        return jobApplicationRepository.findByUserId(userId);
    }

    @Override
    public Optional<List<JobApplication>> getJobApplicationsByJobOfferId(Long jobOfferId) {
        return jobApplicationRepository.findByJobOfferId(jobOfferId);
    }

    @Override
    public JobApplication saveOrUpdateJobApplication(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public JobApplication saveJobApplicationById(Long userId, Long jobOfferId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé avec l'id : " + userId));
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune offre d'emploi trouvée avec l'id : " + jobOfferId));
        return jobApplicationRepository.save(new JobApplication(user, jobOffer));
    }

    @Override
    public Optional<User> setUserById(Long id, Long userId) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune candidature trouvé avec l'id : " + id));
        return userRepository.findById(userId)
                .map( user -> {
                    application.setUser(user);
                    return jobApplicationRepository.save(application).getUser();
                });
    }

    @Override
    public Optional<JobOffer> setJobOfferById(Long id, Long jobOfferId) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune candidature trouvé avec l'id : " + id));
        return jobOfferRepository.findById(jobOfferId)
                .map( jobOffer -> {
                    application.setJobOffer(jobOffer);
                    return jobApplicationRepository.save(application).getJobOffer();
                });
    }

    @Override
    public void deleteJobApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }
}
