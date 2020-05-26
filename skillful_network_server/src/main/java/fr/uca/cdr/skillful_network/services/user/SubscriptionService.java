package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Perk.Subscription;

import java.util.List;

public interface SubscriptionService {

	List<Subscription> getAllSubscription();
	List<Subscription> getSubscriptionsByMatch(String match);
	Subscription getSubscriptionById(Long id);
	Subscription getSubscriptionByName(String name);
	Subscription saveOrUpdateSubscription(Subscription subscription);
	void deleteSubscription(Long id);
}
