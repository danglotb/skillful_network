package fr.uca.cdr.skillful_network.model.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.model.entities.Subscription;

import java.util.Optional;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Optional<Subscription> findByName(String name);

}
