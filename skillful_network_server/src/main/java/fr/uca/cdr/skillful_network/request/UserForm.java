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
	public final String firstName;

	@Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters")
	public final String lastName;

	@Past
	public final Date birthDate;

	@Deprecated
	public final String careerGoal;

	public final Set<Skill> skillSet;

	public final Set<Qualification> qualificationSet;

	public final Set<Subscription> subscriptionSet;

	public UserForm(@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters") String firstName, @Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters") String lastName, @Past Date birthDate, String careerGoal, Set<Skill> skillSet, Set<Qualification> qualificationSet, Set<Subscription> subscriptionSet) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.careerGoal = careerGoal;
		this.skillSet = skillSet;
		this.qualificationSet = qualificationSet;
		this.subscriptionSet = subscriptionSet;
	}
}
