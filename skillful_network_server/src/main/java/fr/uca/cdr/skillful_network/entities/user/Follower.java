package fr.uca.cdr.skillful_network.entities.user;

import java.util.LinkedHashMap;
import java.util.Set;

public interface Follower {

    public enum FollowerStatus { on, off, banned };
    public enum FollowerNotification { all, custom, none };

    public void follow(User followable);
    public void unfollow(User followable);
    public void getAllFollowed();
    public LinkedHashMap<Object, Boolean> getNotifications();
    public void setNotifications(Set<String> notifications, Boolean read);
    public void  popNotifications(Set<String> notifications);
}
