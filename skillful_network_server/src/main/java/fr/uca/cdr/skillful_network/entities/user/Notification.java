package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String label;
    // notified object should be put here
    //private Post notifiedPost;
    private Boolean isRead;

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
