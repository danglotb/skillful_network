package fr.uca.cdr.skillful_network.services.application;

import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import fr.uca.cdr.skillful_network.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface TrainingApplicationService {

    List<TrainingApplication> getAllTrainingApplications();

    Optional<TrainingApplication> getTrainingApplicationById(Long id);

    Optional<User> getUserById(Long id);

    Optional<Training> getTrainingById(Long id);

    Optional<List<TrainingApplication>> getTrainingApplicationsByUserId(Long userId);

    Optional<List<TrainingApplication>> getTrainingApplicationsByTrainingId(Long trainingId);

    TrainingApplication saveOrUpdateTrainingApplication(TrainingApplication trainingApplication);

    TrainingApplication saveTrainingApplicationById(Long userId, Long trainingId);

    Optional<User> setUserById(Long id, Long userId);

    Optional<Training> setTrainingById(Long id, Long trainingId);

    void deleteTrainingApplication(Long id);
}
