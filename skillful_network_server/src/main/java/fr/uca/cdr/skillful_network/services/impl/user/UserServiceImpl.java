package fr.uca.cdr.skillful_network.services.impl.user;

import java.util.List;

import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean exists(String mail) {
		return this.userRepository.findByEmail(mail).isPresent();
	}

	@Override
	public boolean isValidated(String mail) {
		return this.getByEmail(mail).isValidated();
	}

	@Override
	public User getById(long id) {
		return this.userRepository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None user could be found with the id %d", id))
		);
	}

	@Override
	public User getByEmail(String email) {
		return this.userRepository.findByEmail(email).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None user could be found with the email %s", email)));
	}

	@Override
	public Page<User> getByKeyword(Pageable pageable, String keyword) {
		return this.userRepository.findByLastNameContainsOrFirstNameContainsAllIgnoreCase(pageable, keyword, keyword);
	}

	@Override
	public List<User> getAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User createOrUpdate(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public void delete(Long id) {
		this.userRepository.deleteById(id);
	}

}
