package fr.uca.cdr.skillful_network.controller.application;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.services.application.TrainingService;
import fr.uca.cdr.skillful_network.tools.PageTool;

@CrossOrigin("*")
@RestController
@RequestMapping("/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "")
    public List<Training> getAll() {
        return this.trainingService.getAll();
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/")
    public ResponseEntity<Page<Training>> getPerPage(@Valid PageTool pageTool) {
        if (pageTool != null) {
            Page<Training> listTrainingsByPage = this.trainingService.getByPage(pageTool);
            return new ResponseEntity<>(listTrainingsByPage, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Données en paramètre non valides");
        }
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Training> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.trainingService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<Training>> getBySearch(@Valid PageTool pageTool,
                                                               @RequestParam(name = "keyword", required = false) String keyword) {
        if (pageTool != null && keyword != null) {
            Page<Training> listBySearch = this.trainingService.getCandidates(pageTool.requestPage(), keyword);
            return new ResponseEntity<>(listBySearch, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Données en paramètre non valides");
        }
    }

    @PreAuthorize("hasRole('ORGANISME')")
    @PutMapping(value = "{id}")
    @Transactional
    public ResponseEntity<Training> update(@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody Training training) {
        return new ResponseEntity<>(this.trainingService.update(
                id,
                training.getName(),
                training.getOrganization(),
                training.getDescription(),
                training.getDateBeg(),
                training.getDateEnd(),
                training.getDateUpload(),
                training.getDurationHours(),
                training.getKeywords()
        ), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ORGANISME')")
    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        this.trainingService.delete(id);
    }
}
