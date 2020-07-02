package fr.uca.cdr.skillful_network.entities.user;

import java.util.Set;

public interface Followable {

    public enum FollowableStatus { on, off, banned, warned, prohibited };
    public enum FollowableNotifiable { all, custom, none };



    public Set<User> findFollowers();
    public void banFollower(User follower);
    public void notify(Set<Notification> notifications);

    public FollowableStatus getFollowableStatus();
    public void setFollowableStatus(FollowableStatus status);
    public FollowableNotifiable getFollowableNotifiable();
    public void setFollowableNotifiable(FollowableNotifiable followableNotifiable);
}
