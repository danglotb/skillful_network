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

    Optional<List<User>>  getAllFollowersByFollowable();
    Optional<List<User>>  getAllFollowersByFollowableID(Long followableID);

    boolean follow(Long followableID);
    boolean follow(Long followerID, Long followableID);
    void unfollowByFollowedID(Long followedID);
    void unfollowByFSTId(Long fstId);

    void setFollowerStatus(Follower.FollowerStatus status);
    void setFollowerStatusByFollowerID(Long followerID, Follower.FollowerStatus status);
    void setFollowerStatusByFollowedID(Long followedID, Follower.FollowerStatus status);
    void setFollowerStatusByFSTID(Long fstId, Follower.FollowerStatus status);

    void setFollowerNotifiableStatus(Follower.FollowerNotifiable notifiable);
    void setFollowerNotifiableStatusByFollowerID(Long followerID, Follower.FollowerNotifiable notifiable);
    void setFollowerNotifiableStatusByFollowedID(Long followedID, Follower.FollowerNotifiable notifiable);
    void setFollowerNotifiableStatusByFSTID(Long fstId, Follower.FollowerNotifiable notifiable);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<FollowStateTracker>> getAllFSTByFollowable();
    Optional<List<FollowStateTracker>> getAllFSTByFollowableID(Long followableID);

    Optional<List<User>> getAllFollowedByFollower();
    Optional<List<User>> getAllFollowedByFollowerID(Long followerID);

    void banFollower(Long followerID);
    void banFollower(Long followedID, Long followerID);

    void setFollowableStatus(Followable.FollowableStatus status);
    void setFollowableStatusByFollowedID(Long followedID, Followable.FollowableStatus status);
    void setFollowableStatusByFollowerID(Long followerID, Followable.FollowableStatus status);
    void setFollowableStatusByFSTID(Long fstId, Followable.FollowableStatus status);

    void setFollowableNotifiableStatus(Followable.FollowableNotifiable notifiable);
    void setFollowableNotifiableStatusByFollowedID(Long followedID, Followable.FollowableNotifiable notifiable);
    void setFollowableNotifiableStatusByFollowerID(Long followerID, Followable.FollowableNotifiable notifiable);
    void setFollowableNotifiableStatusByFSTID(Long fstId, Followable.FollowableNotifiable notifiable);

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
