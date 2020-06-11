package fr.uca.cdr.skillful_network.controller.application;

import fr.uca.cdr.skillful_network.entities.application.Application;
import fr.uca.cdr.skillful_network.entities.application.JobApplication;
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

    @PreAuthorize("hasRole('ORGANISME')")
    @GetMapping(value = "")
    public ResponseEntity<List<TrainingApplication>> getAll() {
        return new ResponseEntity<>(this.trainingApplicationService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ORGANISME','USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TrainingApplication> getById(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(this.trainingApplicationService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<TrainingApplication>> getForCurrentUser() {
        return new ResponseEntity<>(this.trainingApplicationService.getForCurrentUser(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ORGANISME')")
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<TrainingApplication>> getByUserId(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(this.trainingApplicationService.getByUserId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ORGANISME')")
    @GetMapping(value = "/training/{id}")
    public ResponseEntity<List<TrainingApplication>> getTrainingApplicationByTraining(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(this.trainingApplicationService.getByOfferId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ORGANISME')")
    @PostMapping(value = "")
    public TrainingApplication create(@Valid @RequestBody TrainingApplication application) {
        return trainingApplicationService.create(application);
    }

    @PreAuthorize("hasRole('ORGANISME')")
    @PutMapping(value = "/{id}")
    public TrainingApplication update(@PathVariable(value = "id") long id, @Valid @RequestBody Application.ApplicationStatus status) {
        return trainingApplicationService.update(id, status);
    }

    @PreAuthorize("hasRole('ORGANISME')")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        this.trainingApplicationService.delete(id);
    }
}
