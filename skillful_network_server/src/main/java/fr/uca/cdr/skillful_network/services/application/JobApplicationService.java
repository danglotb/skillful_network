package fr.uca.cdr.skillful_network.services.application;

import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface JobApplicationService {

    List<JobApplication> getAllJobApplications();

    Optional<JobApplication> getJobApplicationById(Long id);

    Optional<User> getUserById(Long id);

    Optional<JobOffer> getJobOfferById(Long id);

    Optional<List<JobApplication>> getJobApplicationsByUserId(Long userId);

    Optional<List<JobApplication>> getJobApplicationsByJobOfferId(Long jobOfferId);

    JobApplication saveOrUpdateJobApplication(JobApplication jobApplication);

    JobApplication saveJobApplicationById(Long userId, Long jobOfferId);

    Optional<User> setUserById(Long id, Long userId);

    Optional<JobOffer> setJobOfferById(Long id, Long jobOfferId);

    void deleteJobApplication(Long id);
}
