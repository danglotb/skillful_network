package fr.uca.cdr.skillful_network.request;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import fr.uca.cdr.skillful_network.entities.user.Skill;
import fr.uca.cdr.skillful_network.entities.user.Qualification;
import fr.uca.cdr.skillful_network.entities.user.Subscription;


public class UserForm {

	@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters")
	private String firstName;

	@Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters")
	private String lastName;

	@Past
	private Date birthDate;

	@Deprecated
	private String careerGoal;

	private Set<Skill> skillSet;

	private Set<Qualification> qualificationSet;

	private Set<Subscription> subscriptionSet;

	private UserForm() {
	}

	public UserForm(@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters") String firstName, @Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters") String lastName, @Past Date birthDate, String careerGoal, Set<Skill> skillSet, Set<Qualification> qualificationSet, Set<Subscription> subscriptionSet) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.careerGoal = careerGoal;
		this.skillSet = skillSet;
		this.qualificationSet = qualificationSet;
		this.subscriptionSet = subscriptionSet;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getCareerGoal() {
		return careerGoal;
	}

	public Set<Skill> getSkillSet() {
		return skillSet;
	}

	public Set<Qualification> getQualificationSet() {
		return qualificationSet;
	}

	public Set<Subscription> getSubscriptionSet() {
		return subscriptionSet;
	}
}
