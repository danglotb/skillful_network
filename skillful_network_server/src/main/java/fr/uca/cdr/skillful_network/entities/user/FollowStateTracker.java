package fr.uca.cdr.skillful_network.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.uca.cdr.skillful_network.entities.user.Followable.*;
import fr.uca.cdr.skillful_network.entities.user.Follower.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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

//    private LinkedHashMap<Object, Boolean> notifications = new LinkedHashMap<>();
    private LinkedHashSet<Notification> notifications = new LinkedHashSet<>();

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
    public LinkedHashSet<Notification> getNotifications() { return this.notifications; }

    public void pushNotifications(Set<Notification> notifications) {
        notifications.forEach(notification -> {
            notification.setRead(false);
            this.notifications.add(notification);
        });
    }

    public void popNotifications(Set<Notification> notifications) {
        notifications.forEach( notification -> this.notifications.remove(notification) );
    }

    public void setNotificationStatus(Set<Notification> notifications, Boolean read) {
        notifications.forEach(notification -> {
            this.notifications.forEach(notif -> {
                if (notification.getId() == notif.getId()) {
                    notif.setRead(read);
                }
            });

        });
    }

     public void cleanNotifications() { this.notifications.clear();}
}
