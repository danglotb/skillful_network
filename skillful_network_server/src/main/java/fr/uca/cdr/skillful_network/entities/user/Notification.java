package fr.uca.cdr.skillful_network.entities.user;

import fr.uca.cdr.skillful_network.entities.post.Post;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Notification {

    private static final int LABEL_MAXED_BODYTEXT = 20 ;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany( cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private Set<FollowStateTracker> followerSet = new HashSet<>();

    private String label;
    // notified object should be put here
    private Long postId;
    private Boolean isRead;

    public Notification() {}

    public Notification(String label) {
        this.label = label;
        this.isRead=false;
    }

    public Notification(Post post) {
        this.postId = post.getId();
        this.label = makeLabel(post);
        this.isRead=false;
    }

    public void updateNotification(Post post) {
        if ( this.postId != post.getId()) { return; }
        this.label = makeLabel(post);
        this.isRead=false;
    }

    public String makeLabel(Post post) {
        String result = post.getPostbodyText();
        if ( post.getPostbodyText().length() > LABEL_MAXED_BODYTEXT) {
            result = result.substring(0, LABEL_MAXED_BODYTEXT) + "...";
        }
        if ( ! post.getFiles().isEmpty() ) { result += " ##MEDIA##"; }
        return result;
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
