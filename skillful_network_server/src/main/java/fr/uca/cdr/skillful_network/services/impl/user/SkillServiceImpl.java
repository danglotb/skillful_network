package fr.uca.cdr.skillful_network.services.impl.user;


import fr.uca.cdr.skillful_network.entities.user.Skill;
import fr.uca.cdr.skillful_network.services.user.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.repositories.user.SkillRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SkillServiceImpl extends PerkServiceImpl<Skill> implements SkillService {

    public SkillServiceImpl(SkillRepository repository) {
        super(repository, "skill");
    }

    @Override
    public List<Skill> getCandidates(String keyword) {
        return ((SkillRepository) this.repository).search(keyword).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None skill could be found with the keyword %s", keyword)));
    }

    @Override
    public Skill getByName(String name) {
        return ((SkillRepository) this.repository).findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None skill could be found with the name %s", name)));
    }
}