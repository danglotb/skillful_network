package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
public class Role {

	public enum Name {
		ROLE_USER,
		ROLE_COMPANY,
		ROLE_TRAINING_ORGANIZATION;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
	private Name name;

	public Role() {

	}

	public Role(Name name) {
		super();
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		return id == role.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
