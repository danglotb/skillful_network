package fr.uca.cdr.skillful_network.services;

import fr.uca.cdr.skillful_network.entities.user.User;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface AuthenticationService {

    User getCurrentUser();

    User register(String email, Set<String> roles);

    Authentication authentication(String email, String password);

    String login(User user);

    User whoAmI();

    String resetPassword();

    void manageRoles(Set<String> rolesAsString, User user);

}
