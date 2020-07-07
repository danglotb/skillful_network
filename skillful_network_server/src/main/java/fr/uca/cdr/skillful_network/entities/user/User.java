package fr.uca.cdr.skillful_network.entities.user;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class User implements UserDetails, Followable, Follower  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters")
	private String firstName;

	@Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters")
	private String lastName;

	@Deprecated
	@Size(min = 8, message = "password must be at least 8 characters")
	private String password;

	@Past
	private Date birthDate;

	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be valid")
	private String email;

	private String mobileNumber;

	@Deprecated
	private boolean validated = false;

	@JsonIgnore
	@Transient
	@Deprecated
	private LocalDateTime temporaryCodeExpirationDate;

	@Deprecated
	private String careerGoal;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Skill> skillSet = new HashSet<Skill>();

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Qualification> qualificationSet = new HashSet<Qualification>();

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Subscription> subscriptionSet = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("user")
	@JsonBackReference
	private Set<JobApplication> jobApplicationSet = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("user")
	@JsonBackReference
	private Set<TrainingApplication> trainingApplicationSet = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	private byte[] profilePicture;

	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	private FollowableStatus followableStatus = FollowableStatus.on;
	private FollowableNotifiable followableNotifiable = FollowableNotifiable.all;

	@OneToMany(mappedBy = "followed", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<FollowStateTracker> followableSet = new HashSet<>();

	@OneToMany(mappedBy = "follower", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<FollowStateTracker> followerSet = new HashSet<>();

	public User() {
		super();
	}

	private User(
			@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters") String firstName,
			@Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters") String lastName,
			@Size(min = 8, message = "password must be at least 8 characters") String password,
			@Past Date birthDate,
			@NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email,
			String mobileNumber,
			boolean validated,
			LocalDateTime temporaryCodeExpirationDate,
			String careerGoal,
			Set<Skill> skillSet,
			Set<Qualification> qualificationSet,
			Set<Subscription> subscriptionSet,
			Set<JobApplication> jobApplicationSet,
			Set<TrainingApplication> trainingApplicationSet,
			Set<Role> roles,
			Collection<? extends GrantedAuthority> authorities,
			byte[] profilePicture) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.birthDate = birthDate;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.validated = validated;
		this.temporaryCodeExpirationDate = temporaryCodeExpirationDate;
		this.careerGoal = careerGoal;
		this.skillSet = skillSet;
		this.qualificationSet = qualificationSet;
		this.subscriptionSet = subscriptionSet;
		this.jobApplicationSet = jobApplicationSet;
		this.trainingApplicationSet = trainingApplicationSet;
		this.roles = roles;
		this.authorities = authorities;
		this.profilePicture = profilePicture;
	}

	private User(
			long id,
			@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters") String firstName,
			@Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters") String lastName,
			@Size(min = 8, message = "password must be at least 8 characters") String password,
			@Past Date birthDate,
			@NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email,
			String mobileNumber,
			boolean validated,
			LocalDateTime temporaryCodeExpirationDate,
			String careerGoal,
			Set<Skill> skillSet,
			Set<Qualification> qualificationSet,
			Set<Subscription> subscriptionSet,
			Set<JobApplication> jobApplicationSet,
			Set<TrainingApplication> trainingApplicationSet,
			Set<Role> roles,
			Collection<? extends GrantedAuthority> authorities,
			byte[] profilePicture) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.birthDate = birthDate;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.validated = validated;
		this.temporaryCodeExpirationDate = temporaryCodeExpirationDate;
		this.careerGoal = careerGoal;
		this.skillSet = skillSet;
		this.qualificationSet = qualificationSet;
		this.subscriptionSet = subscriptionSet;
		this.jobApplicationSet = jobApplicationSet;
		this.trainingApplicationSet = trainingApplicationSet;
		this.roles = roles;
		this.authorities = authorities;
		this.profilePicture = profilePicture;
	}

	public static User copy(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(roles -> new SimpleGrantedAuthority(roles.getName().name())).collect(Collectors.toList());
		return new User(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getPassword(),
				user.getBirthDate(),
				user.getEmail(),
				user.getMobileNumber(),
				user.isValidated(),
				user.getTemporaryCodeExpirationDate(),
				user.getCareerGoal(),
				user.getSkillSet(),
				user.getQualificationSet(),
				user.getSubscriptionSet(),
				user.getJobApplicationSet(),
				user.getTrainingApplicationSet(),
				user.getRoles(),
				authorities,
				user.getProfilePicture()
		);
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public LocalDateTime getTemporaryCodeExpirationDate() {
		return temporaryCodeExpirationDate;
	}

	public void setTemporaryCodeExpirationDate(LocalDateTime temporaryCodeExpirationDate) {
		this.temporaryCodeExpirationDate = temporaryCodeExpirationDate;
	}

	public String getCareerGoal() {
		return careerGoal;
	}

	public void setCareerGoal(String careerGoal) {
		this.careerGoal = careerGoal;
	}

	public Set<Skill> getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(Set<Skill> skillSet) {
		this.skillSet = skillSet;
	}

	public Set<Qualification> getQualificationSet() {
		return qualificationSet;
	}

	public void setQualificationSet(Set<Qualification> qualificationSet) {
		this.qualificationSet = qualificationSet;
	}

	public Set<Subscription> getSubscriptionSet() {
		return subscriptionSet;
	}

	public void setSubscriptionSet(Set<Subscription> subscriptionSet) {
		this.subscriptionSet = subscriptionSet;
	}

	public Set<JobApplication> getJobApplicationSet() {
		return jobApplicationSet;
	}

	public void setJobApplicationSet(Set<JobApplication> jobApplicationSet) {
		this.jobApplicationSet = jobApplicationSet;
	}

	public Set<TrainingApplication> getTrainingApplicationSet() {
		return trainingApplicationSet;
	}

	public void setTrainingApplicationSet(Set<TrainingApplication> trainingApplicationSet) {
		this.trainingApplicationSet = trainingApplicationSet;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id &&
				email.equals(user.email) &&
				mobileNumber.equals(user.mobileNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, mobileNumber);
	}

	////////////////////////////////////////////////////////////////////////////////////
	// FOLLOWABLE Methods
	////////////////////////////////////////////////////////////////////////////////////

	public Set<FollowStateTracker> getFollowableSet() { return this.followableSet; }
	public void setFollowableSet(Set<FollowStateTracker> followableSet) { this.followableSet = followableSet; }

	@Override
	public FollowableStatus getFollowableStatus() { return this.followableStatus; }
	@Override
	public void setFollowableStatus(FollowableStatus status) { this.followableStatus = status; }

	@Override
	public FollowableNotifiable getFollowableNotifiable() { return this.followableNotifiable; }
	@Override
	public void setFollowableNotifiable(FollowableNotifiable followableNotifiable) { this.followableNotifiable = followableNotifiable; }

	@Override
	public Set<User> findFollowers() {
		return this.followableSet.stream()
			.map(FollowStateTracker::getFollower)
			.collect(Collectors.toSet());
	}

	@Override
	public void banFollower(User follower) {
		this.followableSet.stream()
				.filter( item -> follower.getId() == item.getFollower().getId())
				.forEach( item -> item.setFollowerStatus(FollowerStatus.banned));
	}

	@Override
	public void notify(Set<Notification> notifications) {
		this.followableSet.forEach(item -> item.pushNotifications(notifications) );
	}

	////////////////////////////////////////////////////////////////////////////////////
	// FOLLOWER Methods
	////////////////////////////////////////////////////////////////////////////////////

	public Set<FollowStateTracker> getFollowerSet() { return this.followerSet; }
	public void setFollowerSet(Set<FollowStateTracker> followerSet) { this.followerSet = followerSet; }

	@Override
	public void follow(User followable) {
		this.followerSet.add(new FollowStateTracker(followable, this));
	}

	@Override
	public void unfollow(User followable) {
		Optional<FollowStateTracker> fst = this.followerSet.stream()
				.filter( item -> followable.getId() == item.getFollowed().getId() )
				.findFirst();
		fst.ifPresent( this.followerSet::remove );
	}

	@Override
	public Set<User> findAllFollowed() {
		return this.followerSet.stream()
				.map( FollowStateTracker::getFollowed )
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Notification> findAllNotifications() {

		// for Set<Notification> we can do this :
		 return this.followerSet.stream()
		 	.flatMap(follower -> follower.getNotifications().stream())
		 	.collect(Collectors.toSet());

		// if we have LinkedHashSet<V>, we could use this :
		// LinkedHashSet<Notification> notificationSet = new LinkedHashSet<>();
		// this.followerSet.forEach( fst -> notificationSet.addAll(fst.getNotifications()) );
		// if we have LinkedHashMap<K, V>, we could use this :
		// fst.getNotifications().entrySet().forEach( notif -> { notificationSet.put(notif.getKey(), notif.getValue()); });
		// return notificationSet;
	}

	@Override
	public void setNotificationsReadStatus(Set<Notification> notifications, Boolean isRead) {
		this.followerSet.forEach( fst -> fst.setNotificationReadStatus(notifications, isRead) );
	}

	@Override
	public void popNotifications(Set<Notification> notifications) {
		this.followerSet.forEach( fst -> fst.popNotifications(notifications) );
	}
}
