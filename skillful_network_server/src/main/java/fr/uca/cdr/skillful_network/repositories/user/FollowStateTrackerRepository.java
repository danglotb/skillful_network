package fr.uca.cdr.skillful_network.repositories.user;


import fr.uca.cdr.skillful_network.entities.user.FollowStateTracker;
import fr.uca.cdr.skillful_network.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowStateTrackerRepository extends JpaRepository<FollowStateTracker, Long> {
    List<FollowStateTracker> findAllByFollower(User follower);
//    boolean existByFollowerAndFollowed(User follower, User followed);
    FollowStateTracker findAllByFollowerAndFollowed(User follower, User followed);
    List<FollowStateTracker> findAllByFollowed(User follower);
}