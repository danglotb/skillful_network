package fr.uca.cdr.skillful_network.repositories.application;

import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingApplicationRepository extends JpaRepository<TrainingApplication, Long> {

    Optional<List<TrainingApplication>> findByUserId(Long userId);

    Optional<List<TrainingApplication>> findByTrainingId(Long trainingId);
}
