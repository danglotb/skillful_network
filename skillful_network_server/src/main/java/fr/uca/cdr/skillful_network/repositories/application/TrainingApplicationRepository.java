package fr.uca.cdr.skillful_network.repositories.application;

import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingApplicationRepository extends ApplicationRepository<TrainingApplication>, JpaRepository<TrainingApplication, Long> {

}
