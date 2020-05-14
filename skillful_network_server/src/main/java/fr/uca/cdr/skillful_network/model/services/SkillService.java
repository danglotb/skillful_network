package fr.uca.cdr.skillful_network.model.services;

import java.util.*;


import fr.uca.cdr.skillful_network.model.entities.Skill;

public interface SkillService {
	
	List<String> searchSkill(String keyword);
	Skill getSkillByName(String name);
	Skill getSkillById(Long id);
	List<Skill> getAllSkills();
    List<Skill> getSkillsByMatch(String match);
}
