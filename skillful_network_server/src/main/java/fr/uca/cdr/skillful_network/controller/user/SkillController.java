package fr.uca.cdr.skillful_network.controller.user;

import java.util.*;

import fr.uca.cdr.skillful_network.services.user.SkillService;
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

import fr.uca.cdr.skillful_network.entities.user.Skill;

@CrossOrigin("*")
@RestController
@RequestMapping("/skills")
public class SkillController {

	@Autowired
	private SkillService skillService;

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "")
	public ResponseEntity<Skill> createQualification(@RequestParam(name="qualificationName") String qualificationName) {
		return new ResponseEntity<>(this.skillService.createOrUpdate(qualificationName), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "")
	public ResponseEntity<List<Skill>> getAllQualifications() {
		return new ResponseEntity<>(this.skillService.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/candidates")
	public ResponseEntity<List<Skill>> getCandidates(@RequestParam(required=false , name="keyword") String keyword) {
		return new ResponseEntity<>(this.skillService.getCandidates(keyword), HttpStatus.OK);
	}
}
