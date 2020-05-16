package fr.uca.cdr.skillful_network.services.impl.user;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.services.impl.EmailServiceImpl;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.tools.PageTool;
import org.springframework.web.server.ResponseStatusException;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean alreadyExists(String mail) {
		return this.userRepository.findByEmail(mail).isPresent();
	}

	@Override
	public boolean existingMailIsValidated(String mail) {
		return this.findByEmail(mail).isValidated();
	}

	@Override
	public User getUserById(long id) {
		return this.userRepository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None skill could be found with the id %d", id))
		);
	}

	@Override
	public User saveOrUpdateUser(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

	@Deprecated
	@Override
	public String createRepoImage() {
		String dossier1 = "WebContent/images/";
		if (!new File(dossier1).exists()) {
			new File(dossier1).mkdirs();
		}
		return dossier1;
	}

	@Deprecated
	@Override
	public boolean updateImage() {
		String photoprofiljpg = "WebContent/images/iconeprofildefaut.jpg";
		String photoprofiljpeg = "WebContent/images/iconeprofildefaut.jpeg";
		if (!new File(photoprofiljpg).exists() || !new File(photoprofiljpg).exists()) {
			new File(photoprofiljpg).mkdirs();
			new File(photoprofiljpeg).mkdirs();
		}
		return true;
	}

	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None user could be found with the email %s", email)));
	}

	@Override
	public Page<User> getPageOfEntities(PageTool pageTool) {
		return this.userRepository.findAll(pageTool.requestPage());
	}

	@Override
	public Page<User> searchUsersByKeyword(Pageable pageable, String keyword) {
		return this.userRepository.findByLastNameContainsOrFirstNameContainsAllIgnoreCase(pageable, keyword, keyword);
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Deprecated
	@Override
	public boolean mdpExpired(LocalDateTime dateExpiration, LocalDateTime currentDate) {
		return currentDate.isAfter(dateExpiration);
	}

	@Deprecated
	@Override
	public void validationMdp(Boolean isExpired, Optional<User> userFromDB) {
		Long idFromDB = userFromDB.get().getId();
		if (isExpired == true && userFromDB.get().isValidated() == false) {
			// le mot de passe est supprimé de la table si isAfter est true
			userRepository.deleteById(idFromDB);
		} else {
			// cas du mot de passe en cours de validité
			userFromDB.get().setValidated(true);
			this.saveOrUpdateUser(userFromDB.get());
		}
	}

}
