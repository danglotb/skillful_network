package fr.uca.cdr.skillful_network.entities.application;

import javax.persistence.*;

import fr.uca.cdr.skillful_network.entities.Keyword;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class Training {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String organization;

	private String description;

	private Date dateBeg;

	private Date dateEnd;

	private Date dateUpload;

	private long durationHours;

	@ManyToMany(fetch = FetchType.EAGER, cascade =  CascadeType.PERSIST)
	private Set<Keyword> keywords;

	@OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TrainingApplication> trainingApplicationSet;

	public Training() {

	}

	public Training(String name,
					String organization,
					String description,
					Date dateBeg,
					Date dateEnd,
					Date dateUpload,
					Long durationHours,
					Set<Keyword> keywords,
					Set<TrainingApplication> trainingApplicationSet) {
		this.name = name;
		this.organization = organization;
		this.description = description;
		this.dateBeg = dateBeg;
		this.dateEnd = dateEnd;
		this.dateUpload = dateUpload;
		this.durationHours = durationHours;
		this.keywords = keywords;
		this.trainingApplicationSet = trainingApplicationSet;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateBeg() {
		return dateBeg;
	}

	public void setDateBeg(Date dateBeg) {
		this.dateBeg = dateBeg;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateUpload() {
		return dateUpload;
	}

	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}

	public long getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(long durationHours) {
		this.durationHours = durationHours;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public Set<TrainingApplication> getTrainingApplicationSet() {
		return trainingApplicationSet;
	}

	public void setTrainingApplicationSet(Set<TrainingApplication> trainingApplicationSet) {
		this.trainingApplicationSet = trainingApplicationSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Training training = (Training) o;
		return Objects.equals(id, training.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
