package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.entities.user.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    Long getFollowerCount();
    Long getFollowerCount(Long followableID);

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

    Long getFollowedCount();
    Long getFollowedCount(Long followerID);

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
    void pushNotifications(Set<Post> posts);
    void pushNotifications(Long followedID, Set<Post> posts);
    void pushNotificationLabels(Set<String> labels);
    void pushNotificationLabels(Long followedID, Set<String> labels);

    Optional<Set<Notification>> getAllNotifications();
    Optional<Set<Notification>> getAllNotificationsByFollowerId(Long followerID);
    Optional<Set<Notification>> getAllNotificationsByFollowedId(Long followedID);
    Optional<Set<Notification>> getAllNotificationsByFollowerIdAndByFollowedId(Long followerID, Long followedID);

    Optional<Map<Long, String>> getAllLabels();
    Optional<Map<Long, String>> getAllLabelsByFollowerId(Long followerID);

    Boolean isNotificationsEmpty();
    Boolean isNotificationsEmpty(Long followerID);

    Long notificationsSize();
    Long notificationsSize(Long followerID);

    Long unreadNotificationsCount();
    Long unreadNotificationsCount(Long followerID);

    void setNotificationReadStatus(Long notificationId, Boolean isRead);
    void setNotificationReadStatus(Long followerID, Long notificationId, Boolean isRead);
    void setNotificationsReadStatus(Set<Notification> notifications, Boolean isRead);
    void setNotificationsReadStatus(Long followerID, Set<Notification> notifications, Boolean isRead);

    void updateNotifications(Long followedID, Set<Post>  posts);

    void popNotification(Long notificationId);
    void popNotification(Long followerID, Long notificationId);
    void popNotifications(Set<Notification> notifications);
    void popNotifications(Long followerID, Set<Notification> notifications);

}