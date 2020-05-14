package fr.uca.cdr.skillful_network.controller;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.model.entities.Skill;
import fr.uca.cdr.skillful_network.model.entities.User;
import fr.uca.cdr.skillful_network.model.services.SkillService;
import fr.uca.cdr.skillful_network.tools.AutoCompletion;

@CrossOrigin("*")
@RestController
@RequestMapping("/skills")
public class SkillController {

	@Autowired
	private SkillService skillService;
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "")
	public ResponseEntity<List<Skill>> getAllSkills() {
		List<Skill> listSkill = this.skillService.getAllSkills();
		return new ResponseEntity<>(listSkill, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/{name}")
	public ResponseEntity<Skill> getSkillByName(@PathVariable(value = "name") String name) {
		Skill skillFromDb = this.skillService.getSkillByName(name);
		return new ResponseEntity<>(skillFromDb, HttpStatus.OK);
	}
  
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME')")
	@GetMapping(value = "/{id}/users")
	public ResponseEntity<Set<User>> getAllUserBySkill(@PathVariable(value = "id") Long id) {
		Set<User> listUser = this.skillService.getSkillById(id).getUserList();
		return new ResponseEntity<>(listUser, HttpStatus.OK);

	}
  
	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/search/{keyword}", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<String>> search(@PathVariable("keyword") String keyword) {
			List<String> listSkill = this.skillService.searchSkill(keyword);
			return new ResponseEntity<>(listSkill, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value = "/candidates")
	public ResponseEntity<List<Skill>>  getCandidatesByMatch(@RequestParam(required=false , name="contain") String match) {
		return new ResponseEntity<>(skillService.getSkillsByMatch(match), HttpStatus.OK);
	}
}
