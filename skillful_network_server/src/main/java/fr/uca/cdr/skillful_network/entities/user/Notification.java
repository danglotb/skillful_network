package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany( cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private Set<FollowStateTracker> followerSet = new HashSet<>();

    private String label;
    // notified object should be put here
//    private Post post;
    private Boolean isRead;

    public Notification() {}

//    public Notification(Post post) {
//        this.post = post;
//        this.label = post.getLabel();
//        this.isRead=false;
//    }

    public Notification(String label) {
        this.label = label;
        this.isRead=false;
    }


    public long getId() { return id;  }
    public void setId(long id) { this.id = id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Boolean getRead() { return isRead; }
    public void setRead(Boolean read) { isRead = read; }

//    public Set<FollowStateTracker> getFollowerSet() { return followerSet; }
//    public void setFollowerSet(Set<FollowStateTracker> followerSet) { followerSet = followerSet; }
    public void addFST(FollowStateTracker fst) { followerSet.add(fst); }
    public void removeFST(FollowStateTracker fst) { followerSet.remove(fst); }
    public int followerSetSize() { return followerSet.size(); }

    @Override
    public String toString() {
        return "Notification[" + id +
                "] label='" + label + '\'' +
                ", isRead=" + isRead +
                ", followerSet.size= " + followerSet.size();
    }
}
