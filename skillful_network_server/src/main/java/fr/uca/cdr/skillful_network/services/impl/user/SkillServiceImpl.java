package fr.uca.cdr.skillful_network.services.impl.user;


import java.util.*;

import fr.uca.cdr.skillful_network.services.user.SkillService;
import fr.uca.cdr.skillful_network.tools.AutoCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.user.Skill;
import fr.uca.cdr.skillful_network.repositories.user.SkillRepository;
import org.springframework.web.server.ResponseStatusException;

@Service(value = "skillService")
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillRepository skillRepository;

	private AutoCompletion<Skill> autoCompletion;

	public SkillServiceImpl() {
		this.autoCompletion = new AutoCompletion<>(Skill.class, "name", "userList");
	}

	@Override
	public List<String> searchSkill(String keyword) {
		return this.skillRepository.search(keyword).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None skill could be found with the keyword %s", keyword)));
	}

	@Override
	public Skill getSkillByName(String name) {
		return this.skillRepository.findByName(name).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None skill could be found with the name %s", name)));
	}

	@Override
	public Skill getSkillById(Long id) {
		return this.skillRepository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None skill could be found with the id %d", id)));
	}

	@Override
	public List<Skill> getAllSkills() {
		return this.skillRepository.findAll();
	}

	@Override
	public List<Skill> getSkillsByMatch(String match) {
		return this.autoCompletion.findCandidates(skillRepository.findAll(), match);
	}
}
