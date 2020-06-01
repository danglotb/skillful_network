package fr.uca.cdr.skillful_network.controller.application;

import fr.uca.cdr.skillful_network.entities.application.Application;
import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.services.application.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/applications/jobs")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PreAuthorize("hasRole('ENTREPRISE')")
    @GetMapping(value = "")
    public ResponseEntity<List<JobApplication>> getAll() {
        return new ResponseEntity<>(this.jobApplicationService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(this.jobApplicationService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<JobApplication>> getByUserId(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(this.jobApplicationService.getByUserId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @GetMapping(value = "/job/{id}")
    public ResponseEntity<List<JobApplication>> getByJobId(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(this.jobApplicationService.getByOfferId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @PostMapping(value = "")
    public JobApplication create(@Valid @RequestBody JobApplication application) {
        return this.jobApplicationService.create(application);
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @PutMapping(value = "/{id}")
    public JobApplication update(@PathVariable(value = "id") long id, @Valid @RequestBody Application.ApplicationStatus status) {
        return this.jobApplicationService.update(id, status);
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") long id) {
        this.jobApplicationService.delete(id);
    }
}
