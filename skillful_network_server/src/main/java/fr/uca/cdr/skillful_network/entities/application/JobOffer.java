package fr.uca.cdr.skillful_network.entities.application;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.uca.cdr.skillful_network.entities.Keyword;

@Entity
public class JobOffer {

	public enum Risk {
		SIMPLE, MODERATE, CRITICAL;
	}

	public enum Complexity {
		SIMPLE, MODERATE, COMPLEX;
	}

	@Transient
	@JsonSerialize
	@JsonDeserialize
	private final double[][] score = { { 0.4, 0.6, 0.8 }, { 0.6, 0.8, 1 }, { 0.8, 1, 1.2 } };

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String company;

	private String description;

	private String type;

	private Date dateBeg;

	private Date dateEnd;

	private Date dateUpload;

	@ManyToMany(fetch = FetchType.EAGER, cascade =  CascadeType.PERSIST)
	private Set<Keyword> keywords = new HashSet<>();

	@Enumerated(EnumType.ORDINAL)
	@Column(length = 50)
	private Risk risk;

	@Enumerated(EnumType.ORDINAL)
	@Column(length = 50)
	private Complexity complexity;

	@OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<JobApplication> jobApplicationSet = new HashSet<>();

	public JobOffer() {

	}

	public JobOffer(String name,
					String company,
					String description,
					String type,
					Date dateBeg,
					Date dateEnd,
					Date dateUpload,
					Set<Keyword> keywords,
					Risk risk,
					Complexity complexity,
					Set<JobApplication> jobApplicationSet) {
		this.name = name;
		this.company = company;
		this.description = description;
		this.type = type;
		this.dateBeg = dateBeg;
		this.dateEnd = dateEnd;
		this.dateUpload = dateUpload;
		this.keywords = keywords;
		this.risk = risk;
		this.complexity = complexity;
		this.jobApplicationSet = jobApplicationSet;
	}

	public double getScore() {
		return score[this.complexity.ordinal()][this.risk.ordinal()];
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public Risk getRisk() {
		return risk;
	}

	public void setRisk(Risk risk) {
		this.risk = risk;
	}

	public Complexity getComplexity() {
		return complexity;
	}

	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	public Set<JobApplication> getJobApplicationSet() {
		return jobApplicationSet;
	}

	public void setJobApplicationSet(Set<JobApplication> jobApplicationSet) {
		this.jobApplicationSet = jobApplicationSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		JobOffer jobOffer = (JobOffer) o;
		return id == jobOffer.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
