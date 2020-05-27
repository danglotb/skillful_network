package fr.uca.cdr.skillful_network.repositories.user;


import fr.uca.cdr.skillful_network.entities.user.Subscription;
import fr.uca.cdr.skillful_network.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	@Query(value="select name from skills where name like %:keyword%", nativeQuery = true)
	Optional<List<Subscription>> search(@Param("keyword")String keyword);

	Optional<Subscription> findByName(String name);

	Optional<Subscription> findById(long id);

}
