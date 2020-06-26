package fr.uca.cdr.skillful_network.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.uca.cdr.skillful_network.entities.user.Followable.*;
import fr.uca.cdr.skillful_network.entities.user.Follower.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

@Entity
public class FollowStateTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Past
    private Date creationDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties( { "followableSet", "followerSet"})
//    @JsonManagedReference
    private User followed;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties( { "followableSet", "followerSet"})
//    @JsonManagedReference
    private User follower;

    private LinkedHashMap<Object, Boolean> notifications = new LinkedHashMap<>();

    // Followable states
    private FollowableStatus followedStatus;
    private FollowableNotification followedNotifiable;

    // Follower states
    private FollowerStatus followerStatus;
    private FollowerNotification followerNotifiable;

    public FollowStateTracker(User followed, User follower) {
        this.followed = followed;
        this.follower = follower;
        this.creationDate = new Date();
        this.followedStatus = FollowableStatus.on;
        this.followerStatus = FollowerStatus.on;
        this.followedNotifiable = FollowableNotification.all;
        this.followerNotifiable = FollowerNotification.all;
    }

    // User getters
    public User getFollowed() { return this.followed; }
    public User getFollower() { return this.follower; }

    // User status Management
    public FollowableStatus getFollowedStatus() { return this.followedStatus; }
    public void setFollowedStatus(FollowableStatus followedStatus) { this.followedStatus = followedStatus; }

    public FollowableNotification getFollowedNotifiable() { return this.followedNotifiable; }
    public void setFollowedNotifiable(FollowableNotification followedNotifiable) { this.followedNotifiable = followedNotifiable; }

    public FollowerStatus getFollowerStatus() { return this.followerStatus; }
    public void setFollowerStatus(FollowerStatus followerStatus) { this.followerStatus = followerStatus; }

    public FollowerNotification getFollowerNotifiable() { return this.followerNotifiable; }
    public void setFollowerNotifiable(FollowerNotification followerNotifiable) { this.followerNotifiable = followerNotifiable; }

    // Notification Management
    public LinkedHashMap<Object, Boolean> getNotifications() { return this.notifications; }
    public void pushNotifications(Set<Object> notifications) {
        notifications.forEach(notification -> this.notifications.putIfAbsent(notification, true));
    }
    public void popNotifications(Set<Object> notifications) {
        notifications.forEach( notification -> this.notifications.remove(notification) );
    }
    public void setNotificationStatus(Set<Object> notifications, Boolean read) {
        notifications.forEach( notification -> this.notifications.replace(notification, read) );
    }
    public void cleanNotifications() { this.notifications.clear();}
}
