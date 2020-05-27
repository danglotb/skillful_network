package fr.uca.cdr.skillful_network.repositories.application;

import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingApplicationRepository extends ApplicationRepository<TrainingApplication>, JpaRepository<TrainingApplication, Long> {

}
