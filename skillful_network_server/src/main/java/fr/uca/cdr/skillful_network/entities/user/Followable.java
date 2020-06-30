package fr.uca.cdr.skillful_network.entities.user;

import java.util.Set;

public interface Followable {

    public enum FollowableStatus { on, off, banned, warned, prohibited };
    public enum FollowableNotification { all, custom, none };



    public Set<User> getFollowers();
    public void banFollower(User follower);
    public void notify(Set<Notification> notifications);

    public FollowableStatus getFollowableStatus();
    public void setFollowableStatus(FollowableStatus status);
    public FollowableNotification getFollowableNotifiable();
    public void setFollowableNotifiable(FollowableNotification followableNotifiable);
}
