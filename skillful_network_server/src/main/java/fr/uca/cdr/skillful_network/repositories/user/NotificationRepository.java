package fr.uca.cdr.skillful_network.repositories.user;

import fr.uca.cdr.skillful_network.entities.user.FollowStateTracker;
import fr.uca.cdr.skillful_network.entities.user.Notification;
import fr.uca.cdr.skillful_network.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByPostId(Long postId);
}