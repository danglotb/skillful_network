package fr.uca.cdr.skillful_network.entities.application;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.uca.cdr.skillful_network.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class Application {

    public enum ApplicationStatus {INIT, SUBMITTED, INVESTIGATING, WAITING, PAUSED, POSTPONED, ACCEPTED, REJECTED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonManagedReference
    protected User user;

    protected ApplicationStatus status;

    protected Date submitDate;

    public Application() {

    }

    public Application(User user, ApplicationStatus status, Date submitDate) {
        this.user = user;
        this.status = status;
        this.submitDate = submitDate;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
