package fr.uca.cdr.skillful_network.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.application.Training;

@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull
    private Set<JobOffer> jobOffers = new HashSet<JobOffer>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull
    private Set<Training> trainings = new HashSet<Training>();

    public Keyword() {

	}

    public Keyword(String name) {
    	this.name = name;
	}

	public Keyword(String name, Set<JobOffer> jobOffers, Set<Training> trainings) {
		this.name = name;
		this.jobOffers = jobOffers;
		this.trainings = trainings;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(Set<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
