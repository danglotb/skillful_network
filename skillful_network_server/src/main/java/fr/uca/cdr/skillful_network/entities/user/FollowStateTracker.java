package fr.uca.cdr.skillful_network.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.uca.cdr.skillful_network.entities.user.Followable.FollowableNotifiable;
import fr.uca.cdr.skillful_network.entities.user.Followable.FollowableStatus;
import fr.uca.cdr.skillful_network.entities.user.Follower.FollowerNotifiable;
import fr.uca.cdr.skillful_network.entities.user.Follower.FollowerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FollowStateTracker {

    private static final Logger logger = LoggerFactory.getLogger(FollowStateTracker.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @PastOrPresent
    private Date creationDate;

    @ManyToOne
    @JsonIgnoreProperties( { "followableSet", "followerSet",
            "password", "birthDate", "mobileNumber", "validated", "roles", "careerGoal", "skillSet", "qualificationSet", "subscriptionSet",
            "profilePicture", "authorities", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired" } )
    private User followed;

    @ManyToOne
    @JsonIgnoreProperties( { "followableSet", "followerSet", "followableStatus", "followableNotifiable",
            "password", "birthDate", "mobileNumber", "validated", "roles", "careerGoal", "skillSet", "qualificationSet", "subscriptionSet",
            "profilePicture", "authorities", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired" } )
    private User follower;

    @ManyToMany(mappedBy = "followerSet", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @OrderBy
    @JsonIgnoreProperties( { "followerSet"})
    // Notifications List : ordered and unique items
    private Set<Notification> notifications = new HashSet<>();

    // Followable states
    private FollowableStatus followedStatus;
    private FollowableNotifiable followedNotifiable;

    // Follower states
    private FollowerStatus followerStatus;
    private FollowerNotifiable followerNotifiable;

    public FollowStateTracker() {}

    public FollowStateTracker(User followed, User follower) {
        this.followed = followed;
        this.follower = follower;
        this.creationDate = new Date();
        this.followedStatus = this.followed.getFollowableStatus();
        this.followedNotifiable = this.followed.getFollowableNotifiable();
        this.followerStatus = FollowerStatus.on;
        this.followerNotifiable = FollowerNotifiable.all;
    }

    // structural getters
    public long getId() { return id; }
    public Date getCreationDate() { return creationDate; }
    public User getFollowed() { return followed; }
    public User getFollower() { return follower; }

    // User status Management
    public FollowableStatus getFollowedStatus() { return followedStatus; }
    public void setFollowedStatus(FollowableStatus followedStatus) { this.followedStatus = followedStatus; }

    public FollowableNotifiable getFollowedNotifiable() { return followedNotifiable; }
    public void setFollowedNotifiable(FollowableNotifiable followedNotifiable) { this.followedNotifiable = followedNotifiable; }

    public FollowerStatus getFollowerStatus() { return followerStatus; }
    public void setFollowerStatus(FollowerStatus followerStatus) { this.followerStatus = followerStatus; }

    public FollowerNotifiable getFollowerNotifiable() { return followerNotifiable; }
    public void setFollowerNotifiable(FollowerNotifiable followerNotifiable) { this.followerNotifiable = followerNotifiable; }

    // Notification Management
    public Set<Notification> getNotifications() { return notifications; }

    public void pushNotifications(Set<Notification> notifications) {
        logger.debug("pushNotifications()");
        notifications.forEach(notification -> {
            notification.setRead(false);
            notification.addFST(this);
            logger.debug("-> notification: {})", notification);
            this.notifications.add(notification);
            // logger.debug("notifications: {})", this.notifications);
        });
    }

    public void popNotifications(Set<Notification> notifications) {
        logger.debug("popNotifications()");
        notifications.forEach(notification -> {
            if (this.notifications.contains(notification)) {
                logger.debug("-> notification: {})", notification);
                this.notifications.remove(notification);
                notification.removeFST(this);
            }
            // logger.debug("notifications: {})", this.notifications);
        });
    }

    public void setNotificationReadStatus(Set<Notification> notifications, Boolean isRead) {
        notifications.forEach(notification -> this.notifications.stream()
            .filter( notificationToUpdate -> notificationToUpdate.getId() == notification.getId() )
            .forEach( notificationToUpdate -> notificationToUpdate.setRead(isRead) ));
    }

     public void cleanNotifications() { this.notifications.clear();}


}
