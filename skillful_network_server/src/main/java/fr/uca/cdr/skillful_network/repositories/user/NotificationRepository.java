package fr.uca.cdr.skillful_network.repositories.user;

import fr.uca.cdr.skillful_network.entities.user.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {}