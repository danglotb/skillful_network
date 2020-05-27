package fr.uca.cdr.skillful_network.services.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.uca.cdr.skillful_network.entities.user.User;

public interface UserService {

	boolean exists(String mail);

	boolean isValidated(String mail);

	User getUserById(long id);

	User getByEmail(String mail);

	Page<User> getUsersByKeyword(Pageable pageable, String keyword);

	List<User> getAll();

	User createOrUpdateUser(User user);
	
	void deleteUser(Long id);

}
