package fr.uca.cdr.skillful_network.repositories.user;


import fr.uca.cdr.skillful_network.entities.user.Perk.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Optional<Subscription> findByName(String name);

}
