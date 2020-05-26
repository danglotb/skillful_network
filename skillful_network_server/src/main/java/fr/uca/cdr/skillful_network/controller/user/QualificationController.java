package fr.uca.cdr.skillful_network.controller.user;

import java.util.List;

import fr.uca.cdr.skillful_network.entities.user.Perk.Qualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.uca.cdr.skillful_network.services.user.QualificationService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/qualifications")
public class QualificationController {

	@Autowired
	private final QualificationService qualificationservice;

	public QualificationController(QualificationService qualificationservice) {
		this.qualificationservice = qualificationservice;
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/")
	public List<Qualification> getQualifications(@RequestParam(name = "prefix", required = false) String prefix) {
		return prefix == null ? this.qualificationservice.getAllQualifications() : qualificationservice.getQualificationByPrefix(prefix);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/candidates")
	public ResponseEntity<List<Qualification>> getCandidatesByMatch(@RequestParam(required=false , name="contain") String match) {
		return new ResponseEntity<>(this.qualificationservice.getQualificationsByMatch(match), HttpStatus.OK);
	}
}
