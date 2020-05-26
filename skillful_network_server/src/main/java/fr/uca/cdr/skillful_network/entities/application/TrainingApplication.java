package fr.uca.cdr.skillful_network.entities.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.uca.cdr.skillful_network.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "trainingApplications")
public class TrainingApplication extends Application {

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "training_id")
    @JsonIgnore
    private Training training;

    public TrainingApplication() {

    }

    public TrainingApplication(User user, Training training) {
        this.training = training;
        this.user = user;
    }

    public TrainingApplication(User user, ApplicationStatus status, Date submitDate, Training training) {
        super(user, status, submitDate);
        this.training = training;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
