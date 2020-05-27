package fr.uca.cdr.skillful_network.entities.application;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.uca.cdr.skillful_network.entities.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class JobApplication extends Application {

    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "job_offer_id")
    @JsonManagedReference
    private JobOffer offer;

    public JobApplication() {

    }

    public JobApplication(User user, JobOffer offer) {
        this.user = user;
        this.offer = offer;
    }

    public JobApplication(User user, ApplicationStatus status, Date submitDate, JobOffer offer) {
        super(user, status, submitDate);
        this.offer = offer;
    }

    public JobOffer getOffer() {
        return offer;
    }

    public void setOffer(JobOffer offer) {
        this.offer = offer;
    }
}
