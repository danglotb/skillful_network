package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Skill;

import java.util.List;

public interface SkillService {
	
	List<Skill> searchSkill(String keyword);
	Skill getSkillByName(String name);
	Skill getSkillById(Long id);
	List<Skill> getAllSkills();
    List<Skill> getSkillsByMatch(String match);
}
