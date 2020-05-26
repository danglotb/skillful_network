package fr.uca.cdr.skillful_network.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.auth0.jwt.JWT;
import fr.uca.cdr.skillful_network.request.LoginForm;
import fr.uca.cdr.skillful_network.request.JwtResponse;
import fr.uca.cdr.skillful_network.services.EmailService;
import fr.uca.cdr.skillful_network.services.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.UserService;
import fr.uca.cdr.skillful_network.request.RegisterForm;
import fr.uca.cdr.skillful_network.security.CodeGeneration;
import org.springframework.web.server.ResponseStatusException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static fr.uca.cdr.skillful_network.security.SecurityConstants.EXPIRATION_TIME;
import static fr.uca.cdr.skillful_network.security.SecurityConstants.SECRET;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    public static final int SIZE_TMP_CODE = 10;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/user")
    public User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.userService.findByEmail((String)authentication.getPrincipal());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterForm registerForm) {
        final String email = registerForm.getEmail();
        if (this.userService.alreadyExists(email)) {
            if (this.userService.existingMailIsValidated(email)) {
                return new ResponseEntity<>(true, HttpStatus.OK);//TODO I guess that the true is handled by the front
            } else {
                User oldUser = this.userService.findByEmail(email);
                this.userService.deleteUser(oldUser.getId());
            }
        }
        final String randomCode = CodeGeneration.generate.apply(10);
        if (this.activeProfile.contains("prod")) {
            this.emailService.sendEmail(registerForm.getEmail(), randomCode);
        }
        final User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setTemporaryCodeExpirationDate(LocalDateTime.now().plus(24, ChronoUnit.HOURS));

        final String randomCodeEncrypt = this.encoder.encode(randomCode);
        user.setPassword(randomCodeEncrypt);

        this.manageRoles(registerForm, user);

        this.userService.saveOrUpdateUser(user);
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
				String.format("Initialization of user and email send to %s. Proceed.", email)
		);
    }

    private void manageRoles(@RequestBody @Valid RegisterForm registerForm, User user) {
        final Set<String> rolesAsString = registerForm.getRole();
        final Set<Role> roles = new HashSet<>();
        rolesAsString.forEach(roleAsString -> roles.add(
                this.roleService.findByName(Role.Name.valueOf(roleAsString))
                )
        );
        user.setRoles(roles);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginForm credentials) {
        final Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword(),
                        new ArrayList<>())
        );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Wrong credentials, please try again or contact an administrator.");
        }
        String token = JWT.create()
                .withSubject(((User) authenticate.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        return new ResponseEntity<>(new JwtResponse(token, authenticate.getAuthorities()), HttpStatus.OK);
    }

    @PostMapping(value = "/whoami")
    public ResponseEntity<User> whoAmI() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = (String) authentication.getPrincipal();
        final User currentUser = this.userService.findByEmail(email);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PostMapping(value = "/passwordForgotten")
    public ResponseEntity<?> passwordForgotten() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = (User) authentication.getPrincipal();
        final String email = currentUser.getEmail();
		currentUser.setValidated(false);
        final String randomCode = CodeGeneration.generate.apply(SIZE_TMP_CODE);
        if (this.activeProfile.contains("prod")) {
			this.emailService.sendEmail(email, randomCode);
        }
		currentUser.setPassword(randomCode);
		this.userService.saveOrUpdateUser(currentUser);
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
				String.format("Reinitialization of user and email send to %s. Proceed.", email)
		);
    }


}
