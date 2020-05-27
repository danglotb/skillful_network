package fr.uca.cdr.skillful_network.entities.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.uca.cdr.skillful_network.entities.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trainingApplications")
public class TrainingApplication extends Application {

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "training_id")
    @JsonIgnore
    private Training offer;

    public TrainingApplication() {

    }

    public TrainingApplication(User user, Training offer) {
        this.offer = offer;
        this.user = user;
    }

    public TrainingApplication(User user, ApplicationStatus status, Date submitDate, Training offer) {
        super(user, status, submitDate);
        this.offer = offer;
    }

    public Training getOffer() {
        return offer;
    }

    public void setOffer(Training offer) {
        this.offer = offer;
    }
}
