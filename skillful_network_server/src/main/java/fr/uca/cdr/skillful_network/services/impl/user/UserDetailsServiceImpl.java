package fr.uca.cdr.skillful_network.services.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
		return User.build(user);
	}
}
