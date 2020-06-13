package fr.uca.cdr.skillful_network.controller;

import javax.validation.Valid;

import fr.uca.cdr.skillful_network.request.LoginForm;
import fr.uca.cdr.skillful_network.request.JwtResponse;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.request.RegisterForm;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService service;

    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser() {
        return new ResponseEntity<>(this.service.getCurrentUser(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterForm registerForm) {
        logger.debug("register: {}", registerForm.getEmail());
        final User user = this.service.register(registerForm.getEmail(), registerForm.getRole());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    String.format("Initialization of user and email send to %s. Proceed.", registerForm.getEmail())
            );
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginForm credentials) {
        final Authentication authenticate;
        try {
            authenticate = this.service.authentication(credentials.getEmail(), credentials.getPassword());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Wrong credentials, please try again or contact an administrator.");
        }
        final User user = (User) authenticate.getPrincipal();
        final String token = this.service.login(user);
        return new ResponseEntity<>(new JwtResponse(user, token, authenticate.getAuthorities()), HttpStatus.OK);
    }

    @PostMapping(value = "/whoami")
    public ResponseEntity<User> whoAmI() {
        return new ResponseEntity<>(this.service.whoAmI(), HttpStatus.OK);
    }

    @PostMapping(value = "/passwordForgotten")
    public ResponseEntity<?> passwordForgotten() {
        final String email = this.service.resetPassword();
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
				String.format("Reinitialization of user and email send to %s. Proceed.", email)
		);
    }

}
