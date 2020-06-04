package fr.uca.cdr.skillful_network.services.impl;

import com.auth0.jwt.JWT;
import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.security.CodeGeneration;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.EmailService;
import fr.uca.cdr.skillful_network.services.user.RoleService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static fr.uca.cdr.skillful_network.security.SecurityConstants.EXPIRATION_TIME;
import static fr.uca.cdr.skillful_network.security.SecurityConstants.SECRET;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final int SIZE_TMP_CODE = 10;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.userService.getByEmail((String) authentication.getPrincipal());
    }

    @Override
    public User register(String email, String password, Set<String> roles) {
        if (this.userService.exists(email)) {
            if (this.userService.isValidated(email)) {
                return this.getCurrentUser();
            } else {
                User oldUser = this.userService.getByEmail(email);
                this.userService.delete(oldUser.getId());
            }
        }
        final String randomCode = CodeGeneration.generate.apply(10);
        if (this.activeProfile.contains("prod")) {
            this.emailService.sendEmail(email, randomCode);
        }
        final User user = new User();
        user.setEmail(email);
        user.setTemporaryCodeExpirationDate(LocalDateTime.now().plus(24, ChronoUnit.HOURS));
        final String randomCodeEncrypt = this.encoder.encode(randomCode);
        user.setPassword(randomCodeEncrypt);
        this.manageRoles(roles, user);
        this.userService.createOrUpdate(user);
        return null;
    }

    private void manageRoles(Set<String> rolesAsString, User user) {
        final Set<Role> roles = new HashSet<>();
        rolesAsString.forEach(roleAsString -> roles.add(
                this.roleService.findByName(Role.Name.valueOf(roleAsString))
                )
        );
        user.setRoles(roles);
    }

    @Override
    public Authentication authentication(String email, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>())
        );
    }

    @Override
    public String login(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    @Override
    public User whoAmI() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = (String) authentication.getPrincipal();
        return this.userService.getByEmail(email);
    }

    @Override
    public String resetPassword() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = (User) authentication.getPrincipal();
        final String email = currentUser.getEmail();
        currentUser.setValidated(false);
        final String randomCode = CodeGeneration.generate.apply(SIZE_TMP_CODE);
        if (this.activeProfile.contains("prod")) {
            this.emailService.sendEmail(email, randomCode);
        }
        currentUser.setPassword(randomCode);
        this.userService.createOrUpdate(currentUser);
        return email;
    }
}
