package fr.uca.cdr.skillful_network.services.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.tools.PageTool;

public interface UserService {

	boolean alreadyExists(String mail);

	boolean existingMailIsValidated(String mail);

	User getUserById(long id);

	User saveOrUpdateUser(User user);
	
	void deleteUser(Long id);

	void sendMail(String mail, String code);

	@Deprecated
	String createRepoImage();

	@Deprecated
	boolean updateImage();

	User findByEmail(String mail);

	Page<User> getPageOfEntities(PageTool pageTool);

	Page<User> searchUsersByKeyword(Pageable pageable, String keyword);

	@Deprecated
	boolean mdpExpired(LocalDateTime dateExpiration,LocalDateTime currentDate );

	@Deprecated
	void validationMdp(Boolean isExpired, Optional<User> userFromDB);

	List<User> findAll();

}
