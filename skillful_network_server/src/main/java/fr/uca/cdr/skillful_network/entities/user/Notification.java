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
    //private Post notifiedPost;
    private Boolean isRead;

    public Notification() {}

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
}
