package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.*;

import java.util.List;
import java.util.Optional;

public interface FollowStateTrackerService {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Global
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<FollowStateTracker>> getAllFST();
    Optional<FollowStateTracker> getFSTById(Long ID);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Follower methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<FollowStateTracker>> getAllFSTByFollower();
    Optional<List<FollowStateTracker>> getFSTByFollowerID(Long followerID);
    Optional<List<User>> getAllFollowedByFollower();
    Optional<List<User>> getAllFollowedByFollowerID(Long followerID);
    boolean follow(Long followableID);
    boolean follow(Long followerID, Long followableID);
    void unfollowByFollowedID(Long followedID);
    void unfollowByFSTId(Long fstId);
    void setFollowerStatusByFollowerID(Long followerID, Follower.FollowerStatus status);
    void setFollowerStatusByFSTID(Long fstId, Follower.FollowerStatus status);
    void setFollowerNotifiableStatusByFollowedID(Long followedID, Follower.FollowerNotifiable notifiable);
    void setFollowerNotifiableStatusByFSTID(Long fstId, Follower.FollowerNotifiable notifiable);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<FollowStateTracker>> getAllFSTByFollowable();
    Optional<List<FollowStateTracker>> getAllFSTByFollowableID(Long followableID);
    Optional<List<User>>  getAllFollowersByFollowable();
    Optional<List<User>>  getAllFollowersByFollowableID(Long followableID);
    void banFollower(Long followableID, Long followerID);
    void setFollowableStatus(Long followableID, Followable.FollowableStatus status);
    void setFollowableNotifiableStatus(Long followableID, Followable.FollowableNotifiable notifiable);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // notification methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<Notification>> getAllNotifications();
    Optional<List<Notification>> getAllNotificationsByFollower(Long followerID);
    Optional<List<Notification>> getAllNotificationsByFollowable(Long followableID);
    Optional<List<Notification>> getAllNotificationsByFollowerAndByFollowable(Long followerID, Long followableID);
    void setNotifcationsReadStatus(Long followerID, List<Notification> notifications, Boolean isRead);
    void popNotifications(Long followerID, List<Notification> notifications);

}
