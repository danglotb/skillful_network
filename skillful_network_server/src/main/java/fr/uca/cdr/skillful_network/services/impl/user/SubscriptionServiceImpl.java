package fr.uca.cdr.skillful_network.services.impl.user;

import java.util.List;

import fr.uca.cdr.skillful_network.entities.user.Perk.Subscription;
import fr.uca.cdr.skillful_network.services.user.SubscriptionService;
import fr.uca.cdr.skillful_network.tools.AutoCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.repositories.user.SubscriptionRepository;
import org.springframework.web.server.ResponseStatusException;

@Service(value = "subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	private AutoCompletion<Subscription> autoCompletion;

	public  SubscriptionServiceImpl() {
		this.autoCompletion = new AutoCompletion<>(Subscription.class, "name", "userList");;
	}

	@Override
	public List<Subscription> getAllSubscription() {
		return this.subscriptionRepository.findAll();
	}

	@Override
	public List<Subscription> getSubscriptionsByMatch(String match) {
		return this.autoCompletion.findCandidates(this.subscriptionRepository.findAll(), match);
	}

	@Override
	public Subscription getSubscriptionById(Long id) {
		return this.subscriptionRepository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None subscription could be found with the id %d", id)));
	}

	@Override
	public Subscription getSubscriptionByName(String name) {
		return this.subscriptionRepository.findByName(name).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None subscription could be found with the name %s", name)));
	}

	@Override
	public Subscription saveOrUpdateSubscription(Subscription subscription) {
		return this.subscriptionRepository.save(subscription);
	}

	@Override
	public void deleteSubscription(Long id) {
		this.subscriptionRepository.deleteById(id);
	}

}
