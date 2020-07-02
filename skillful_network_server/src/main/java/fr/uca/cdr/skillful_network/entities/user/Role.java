package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Entity
public class Role {

	public enum Name {
		ROLE_USER(" Un utilisateur"),
		ROLE_COMPANY("Une entreprise"),
		ROLE_TRAINING_ORGANIZATION("Un organisme de formation");

		private final String description;

		Name(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	private static final Logger logger = LoggerFactory.getLogger(Role.class);

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
		return "Role{" +
				"id=" + id +
				", name=" + name +
				", meaning=" + name.getDescription()+
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		return id == role.id &&
				name == role.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	public static Map<Name, String> getNamesAndDescriptions() {
		Map<Name, String> names = new HashMap<>();
		for (Name n : Name.values()) {
			names.put(n, n.getDescription());
		}
		logger.debug("Map names : {}", names);
		return names;
	}

	public static Set<String> getRoleDescriptions() {
		HashSet<String> descriptions = new HashSet<>();
		for (Name n : Name.values()) {
			descriptions.add(n.getDescription());
		}
		logger.debug("role's descriptions : {}", descriptions);
		return descriptions;
	}
}
