package fr.uca.cdr.skillful_network.entities.application;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.uca.cdr.skillful_network.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "jobApplications")
public class JobApplication extends Application {

    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "job_offer_id")
    @JsonManagedReference
    private JobOffer jobOffer;

    public JobApplication() {

    }

    public JobApplication(User user, JobOffer jobOffer) {
        this.user = user;
        this.jobOffer = jobOffer;
    }

    public JobApplication(User user, ApplicationStatus status, Date submitDate, JobOffer jobOffer) {
        super(user, status, submitDate);
        this.jobOffer = jobOffer;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
}
