package fr.uca.cdr.skillful_network.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.user.Rolename;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role,Long> {
	Optional<Role> findByName(Rolename roleName);
}
