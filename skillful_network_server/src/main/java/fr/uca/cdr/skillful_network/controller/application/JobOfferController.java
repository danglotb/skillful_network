package fr.uca.cdr.skillful_network.controller.application;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.services.application.JobOfferService;
import fr.uca.cdr.skillful_network.tools.PageTool;

@CrossOrigin("*")
@RestController
@RequestMapping("/jobs")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "")
    public List<JobOffer> getAll() {
        return this.jobOfferService.getAll();
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "")
    public ResponseEntity<Page<JobOffer>> getPerPage(@Valid PageTool pageTool) {
        if (pageTool != null) {
            return new ResponseEntity<>(this.jobOfferService.getByPage(pageTool), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Données en paramètre non valides");
        }
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<JobOffer> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.jobOfferService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<JobOffer>> getBySearch(@Valid PageTool pageTool,
                                                            @RequestParam(name = "keyword", required = false) String keyword) {
        if (pageTool != null && keyword != null) {
            Page<JobOffer> candidates = jobOfferService.getCandidates(pageTool.requestPage(),
                    keyword);
            return new ResponseEntity<>(candidates, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Données en paramètre non valides");
        }
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @PostMapping(value = "")
    public JobOffer create(@Valid @RequestBody JobOffer jobOffer) {
        return jobOfferService.createOrUpdate(jobOffer);
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<JobOffer> update(@PathVariable(value = "id") long id,
                                                   @Valid @RequestBody JobOffer jobOfferToUpdate) {

        return new ResponseEntity<>(
                this.jobOfferService.update(
                        id,
                        jobOfferToUpdate.getName(),
                        jobOfferToUpdate.getCompany(),
                        jobOfferToUpdate.getDescription(),
                        jobOfferToUpdate.getType(),
                        jobOfferToUpdate.getDateBeg(),
                        jobOfferToUpdate.getDateEnd(),
                        jobOfferToUpdate.getDateUpload(),
                        jobOfferToUpdate.getKeywords(),
                        jobOfferToUpdate.getRisk(),
                        jobOfferToUpdate.getComplexity()
                ), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @DeleteMapping(value = "/{id}")
    @Transactional
    public void delete(@PathVariable(value = "id") Long id) {
        jobOfferService.delete(id);
    }
}
