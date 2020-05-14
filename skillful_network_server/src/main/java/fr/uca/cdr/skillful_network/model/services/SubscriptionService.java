package fr.uca.cdr.skillful_network.model.services;

import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.model.entities.Subscription;

public interface SubscriptionService {

	List<Subscription> getAllSubscription();
	List<Subscription> getSubscriptionsByMatch(String match);
	Subscription getSubscriptionById(Long id);
	Subscription getSubscriptionByName(String name);
	Subscription saveOrUpdateSubscription(Subscription subscription);
	void deleteSubscription(Long id);
}
