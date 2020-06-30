package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Followable;
import fr.uca.cdr.skillful_network.entities.user.Follower;
import fr.uca.cdr.skillful_network.entities.user.Notification;
import fr.uca.cdr.skillful_network.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface FollowStateTrackerService {


    // Follower methods
    Optional<List<User>> getAllFollowedByFollower(Long followerID);
    boolean follow(Long followerID, Long followableID);
    boolean unfollow(Long followerID, Long followableID);
    void setFollowerStatusByFollowable(Long followerID, Long followableID, Follower.FollowerStatus status);
    void setFollowerNotifiableStatusByFollowable(Long followerID, Long followableID, Follower.FollowerNotification notifiable);

    // Followable methods
    Optional<List<User>>  getAllFollowersByFollowable(Long followableID);
    void banFollower(Long followableID, Long followerID);
    void setFollowableStatus(Long followableID, Followable.FollowableStatus status);
    void setFollowableNotifiableStatus(Long followableID, Followable.FollowableNotification notifiable);

    // notification methods
    Optional<List<Notification>> getAllNotifications();
    Optional<List<Notification>> getAllNotificationsByFollower(Long followerID);
    Optional<List<Notification>> getAllNotificationsByFollowable(Long followableID);
    Optional<List<Notification>> getAllNotificationsByFollowerAndByFollowable(Long followerID, Long followableID);
    void setNotifcationsReadStatus(List<Notification> notifications, Boolean isRead);
    void popNotifications(List<Notification> notifications);

}
