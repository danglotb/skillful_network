package fr.uca.cdr.skillful_network.controller.user;


import fr.uca.cdr.skillful_network.entities.user.Qualification;
import fr.uca.cdr.skillful_network.services.user.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/qualifications")
public class QualificationController {

	@Autowired
	private QualificationService qualificationservice;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "")
    public ResponseEntity<Qualification> createQualification(@RequestParam(name="qualificationName") String qualificationName) {
        return new ResponseEntity<>(this.qualificationservice.createOrUpdate(qualificationName), HttpStatus.OK);
    }

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "")
	public ResponseEntity<List<Qualification>> getAllQualifications() {
		return new ResponseEntity<>(this.qualificationservice.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/candidates")
	public ResponseEntity<List<Qualification>> getCandidates(@RequestParam(required=false , name="keyword") String keyword) {
		return new ResponseEntity<>(this.qualificationservice.getCandidates(keyword), HttpStatus.OK);
	}

}
