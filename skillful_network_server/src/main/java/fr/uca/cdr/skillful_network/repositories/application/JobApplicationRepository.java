package fr.uca.cdr.skillful_network.repositories.application;

import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Optional<List<JobApplication>> findByUserId(Long userId);

    Optional<List<JobApplication>> findByJobOfferId(Long jobOfferId);
}
