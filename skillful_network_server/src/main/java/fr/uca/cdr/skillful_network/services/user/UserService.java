package fr.uca.cdr.skillful_network.services.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.uca.cdr.skillful_network.entities.user.User;

public interface UserService {

	boolean exists(String mail);

	boolean isValidated(String mail);

	User getById(long id);

	User getByEmail(String mail);

	Page<User> getByKeyword(Pageable pageable, String keyword);

	List<User> getAll();

	User createOrUpdate(User user);
	
	void delete(Long id);

}
