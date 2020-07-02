package fr.uca.cdr.skillful_network.entities.user;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public interface Follower {

    public enum FollowerStatus { on, off, banned };
    public enum FollowerNotifiable { all, custom, none };

    public void follow(User followable);
    public void unfollow(User followable);
    public Set<User> findAllFollowed();
    public Set<Notification> findAllNotifications();
    public void setNotificationsReadStatus(Set<Notification> notifications, Boolean isRead);
    public void popNotifications(Set<Notification> notifications);
}
