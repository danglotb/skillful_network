package fr.uca.cdr.skillful_network.services.impl.user;

import java.util.List;

import fr.uca.cdr.skillful_network.entities.user.Skill;
import fr.uca.cdr.skillful_network.entities.user.Subscription;
import fr.uca.cdr.skillful_network.repositories.user.SkillRepository;
import fr.uca.cdr.skillful_network.services.user.SkillService;
import fr.uca.cdr.skillful_network.services.user.SubscriptionService;
import fr.uca.cdr.skillful_network.tools.AutoCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.repositories.user.SubscriptionRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SubscriptionServiceImpl  extends PerkServiceImpl<Subscription> implements SubscriptionService {

	public SubscriptionServiceImpl(SubscriptionRepository repository) {
		super(repository, "subscription");
	}

	@Override
	public List<Subscription> getCandidates(String keyword) {
		return ((SubscriptionRepository) this.repository).search(keyword).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None subscription could be found with the keyword %s", keyword)));
	}

	@Override
	public Subscription getByName(String name) {
		return ((SubscriptionRepository) this.repository).findByName(name).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None subscription could be found with the name %s", name)));
	}
}