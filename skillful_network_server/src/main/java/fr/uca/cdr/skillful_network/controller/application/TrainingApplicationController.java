package fr.uca.cdr.skillful_network.controller.application;

import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.application.TrainingApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/applications/trainings")
public class TrainingApplicationController {

    @Autowired
    private TrainingApplicationService trainingApplicationService;

    // #########################################################################
    // Get methods
    // #########################################################################

    // Provide all applications
    @PreAuthorize("hasRole('ORGANISME')")
    @GetMapping(value = "")
    public ResponseEntity<List<TrainingApplication>> getAllTrainingApplications() {
        List<TrainingApplication> applications = trainingApplicationService.getAll();
        return new ResponseEntity<List<TrainingApplication>>(applications, HttpStatus.OK);
    }

    // Provide application by its id
    @PreAuthorize("hasAnyRole('ORGANISME','USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TrainingApplication> getTrainingApplicationById(@PathVariable(value = "id") Long id) {
        TrainingApplication application = trainingApplicationService.getById(id);
        return new ResponseEntity<TrainingApplication>(application, HttpStatus.OK);
    }

    // Provide user of an application by its id
    @PreAuthorize("hasRole('ORGANISME')")
    @GetMapping(value = "/{id}/user")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        User user = trainingApplicationService.getById(id).getUser();
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // Provide training of an application by its id
    @PreAuthorize("hasAnyRole('ORGANISME','USER')")
    @GetMapping(value = "/{id}/training")
    public ResponseEntity<Training> getTrainingById(@PathVariable(value = "id") Long id) {
        Training training = trainingApplicationService.getById(id).getOffer();
        return new ResponseEntity<Training>(training, HttpStatus.OK);
    }

    // Provide all applications for a user by his id
    @PreAuthorize("hasRole('ORGANISME')")
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<TrainingApplication>> getTrainingApplicationByUser(@PathVariable(value = "userId") Long userId) {
        List<TrainingApplication> applications = trainingApplicationService.getByUserId(userId);
        return new ResponseEntity<List<TrainingApplication>>(applications, HttpStatus.OK);
    }

    // Provide all applications for a training by its id
    @PreAuthorize("hasRole('ORGANISME')")
    @GetMapping(value = "/training/{trainingId}")
    public ResponseEntity<List<TrainingApplication>> getTrainingApplicationByTraining(@PathVariable(value = "trainingId") Long trainingId) {
        List<TrainingApplication> applications = trainingApplicationService.getByOfferId(trainingId);
        return new ResponseEntity<List<TrainingApplication>>(applications, HttpStatus.OK);
    }

    // #########################################################################
    // Post methods
    // #########################################################################

    // Create a new application
    @PreAuthorize("hasRole('ORGANISME')")
    @PostMapping(value = "")
    public TrainingApplication saveTrainingApplication(@Valid @RequestBody TrainingApplication application) {
        return trainingApplicationService.createOrUpdate(application);
    }

    // Create a new application with a user and a training
    @PreAuthorize("hasAnyRole('ORGANISME','USER')")
    @PostMapping(value = "/user/{userId}/training/{trainingId}")
    public TrainingApplication saveTrainingApplication(@PathVariable(value = "userId") Long userId, @PathVariable(value = "trainingId") Long trainingId) {
//        new TrainingApplication(userId, trainingId);
//        return trainingApplicationService.createOrUpdate(userId, trainingId);
        return null;
    }

    // #########################################################################
    // Put methods
    // #########################################################################

    // Update an application
    @PreAuthorize("hasRole('ORGANISME')")
    @PutMapping(value = "")
    public TrainingApplication updateTrainingApplicationn(@Valid @RequestBody TrainingApplication application) {
        return trainingApplicationService.createOrUpdate(application);
    }

//    // Set application's associated user by their ids
//    @PreAuthorize("hasRole('ORGANISME')")
//    @PutMapping(value = "/{id}/user/{userId}")
//    public ResponseEntity<User> setUserById(@PathVariable(value = "id") Long id, @PathVariable(value = "userId") Long userId) {
//        User user = trainingApplicationService.setUserById(id, userId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé avec l'id : " + userId));
//        return new ResponseEntity<User>(user, HttpStatus.OK);
//    }
//
//    // Set application's associated training by their ids
//    @PreAuthorize("hasRole('ORGANISME')")
//    @PutMapping(value = "/{id}/training/{trainingId}")
//    public ResponseEntity<Training> setTrainingById(@PathVariable(value = "id") Long id, @PathVariable(value = "trainingId") Long trainingId) {
//        Training training = trainingApplicationService.setTrainingById(id, trainingId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune formation trouvée avec l'id : " + trainingId));
//        return new ResponseEntity<Training>(training, HttpStatus.OK);
//    }

    // #########################################################################
    // Delete methods
    // #########################################################################

    // Delete an application
    @PreAuthorize("hasRole('ORGANISME')")
    @DeleteMapping(value = "/{id}")
    public void deleteTrainingApplication(@PathVariable(value = "id") Long id) {
        this.trainingApplicationService.delete(id);
    }
}
