package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.user.Followable;
import fr.uca.cdr.skillful_network.entities.user.Follower;
import fr.uca.cdr.skillful_network.entities.user.Notification;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.FollowStateTrackerRepository;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowStateTrackerServiceImpl implements FollowStateTrackerService {

    @Autowired
    private FollowStateTrackerRepository fstRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Override
    public Optional<List<User>> getAllFollowedByFollower(Long followerID) {
        return Optional.empty();
    }

    @Override
    public boolean follow(Long followerID, Long followableID) {
        return false;
    }

    @Override
    public boolean unfollow(Long followerID, Long followableID) {
        return false;
    }

    @Override
    public void setFollowerStatusByFollowable(Long followerID, Long followableID, Follower.FollowerStatus status) {

    }

    @Override
    public void setFollowerNotifiableStatusByFollowable(Long followerID, Long followableID, Follower.FollowerNotification notifiable) {

    }

    @Override
    public Optional<List<User>> getAllFollowersByFollowable(Long followableID) {
        return Optional.empty();
    }

    @Override
    public void banFollower(Long followableID, Long followerID) {

    }

    @Override
    public void setFollowableStatus(Long followableID, Followable.FollowableStatus status) {

    }

    @Override
    public void setFollowableNotifiableStatus(Long followableID, Followable.FollowableNotification notifiable) {

    }

    @Override
    public Optional<List<Notification>> getAllNotifications() {
        return Optional.empty();
    }

    @Override
    public Optional<List<Notification>> getAllNotificationsByFollower(Long followerID) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Notification>> getAllNotificationsByFollowable(Long followableID) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Notification>> getAllNotificationsByFollowerAndByFollowable(Long followerID, Long followableID) {
        return Optional.empty();
    }

    @Override
    public void setNotifcationsReadStatus(List<Notification> notifications, Boolean isRead) {

    }

    @Override
    public void popNotifications(List<Notification> notifications) {

    }
}
