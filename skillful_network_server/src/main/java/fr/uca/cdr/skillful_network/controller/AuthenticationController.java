package fr.uca.cdr.skillful_network.controller;



import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.jwt.JwtProvider;
import fr.uca.cdr.skillful_network.jwt.response.JwtResponse;
import fr.uca.cdr.skillful_network.model.entities.Role;
import fr.uca.cdr.skillful_network.model.entities.Rolename;
import fr.uca.cdr.skillful_network.model.entities.User;
import fr.uca.cdr.skillful_network.model.repositories.RoleRepository;
import fr.uca.cdr.skillful_network.model.repositories.UserRepository;
import fr.uca.cdr.skillful_network.model.services.UserService;
import fr.uca.cdr.skillful_network.request.LoginForm;
import fr.uca.cdr.skillful_network.security.CodeGeneration;

/**
 * Cette classe a pour rôle d'identifié les utilisateurs. L'authentification des
 * utilisateurs se fait grâce à l'email ou au numéro de mobile (en tant que nom
 * d'utilisateur) ainsi qu'avec un code temporaire envoyé par le serveur à
 * l'email ou au numéro de mobile. Elle est responsable de notamment du
 * traitement des requêtes /login et /token.
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

	
     @Autowired
     private UserRepository userRepository;
     
     @Autowired
     private UserService userService;
     
     @Value("${spring.profiles.active}")
     private String activeProfil;
     
     @Autowired
 	 private JwtProvider jwtProv;
     
     @Autowired
     private RoleRepository roleRepository;
     
     
	@PostMapping(value = "/login")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
		if (loginRequest != null) {

			Optional<User> userFromDB = userService.findByEmail(loginRequest.getEmail());

			if (!userFromDB.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé");
			} else {
				Long idFromDB = userFromDB.get().getId();
 				String emailFromDB= userFromDB.get().getEmail();
				String passwordFromDB = userFromDB.get().getPassword();
				String passwordRequest = loginRequest.getPassword();
				if (passwordRequest != null && !passwordRequest.equals(passwordFromDB)) {
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
							"Les 2 mots de passe ne correspondent pas");
				} else {
					// On génère un token en fonction de l'id, l'email et le password de l'utilisateur
 					String jwt = jwtProv.generateJwtToken(idFromDB, emailFromDB, passwordFromDB);
 					System.out.println("jwt dans AuthController : "+jwt);
 					// Pour enlever le b' contenu devant le token et le ' de la fin
 					jwt = jwt.substring(2,jwt.length()-1);
 					System.out.println("jwt substring : "+jwt);
 					
 					// On retourne une jwt response qui contient le token et l'utilisateur
 	 				return ResponseEntity.ok(new JwtResponse(jwt, userFromDB.get()));
				}
			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé");
	}

	@RequestMapping(value = "/register", method = POST)
	public ResponseEntity<?> ifFirstConnection(@Valid @RequestBody User user) {
		if (userService.alreadyExists(user.getEmail())) {
			if (userService.existingMailIsValidated(user.getEmail()) == true) {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} else {

				Optional<User> oOldUser = userRepository.findByEmail(user.getEmail());
				userRepository.delete(oOldUser.get());
			}
		}
		String randomCode = CodeGeneration.generateCode(10);
        if (activeProfil.contains("prod")) {
			// Send Message!
			userService.sendMail(user.getEmail(), randomCode);
		}
		user.setPassword(randomCode);
		
		Set<String> strRoles = user.getRole();
		Set<Role> roles= new HashSet<>();
		
		strRoles.forEach(selectedRole->{
			switch (selectedRole) {
//				case "user":
//					Role userRole = roleRepository.findByName(Rolename.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Fail! -> Cause : User role not found"));
//					roles.add(userRole);
				case "organisme":
					Role organismeRole = roleRepository.findByName(Rolename.ROLE_ORGANISME)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause : Organisme role not found"));
					roles.add(organismeRole);
				case "entreprise":
					Role entrepriseRole = roleRepository.findByName(Rolename.ROLE_ENTREPRISE)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause : Entreprise role not found"));
					roles.add(entrepriseRole);
				default:
					Role defaultRole = roleRepository.findByName(Rolename.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause : default role not found"));
					roles.add(defaultRole);
			}
		});
		
		user.setRoles(roles);
		userRepository.save(user);
		return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/passwordForgotten", method = POST)
	public ResponseEntity<?> ifForgotPassword(@Valid @RequestBody User user) {
		if (userService.alreadyExists(user.getEmail())) {
			if (userService.existingMailIsValidated(user.getEmail())) {
				Optional<User> userFDb = userRepository.findByEmail(user.getEmail());
				userFDb.get().setValidated(false);
				String randomCode = CodeGeneration.generateCode(10);
				if (activeProfil.contains("prod")) {
					// Send Message!
					userService.sendMail(user.getEmail(), randomCode);
				}
				userFDb.get().setPassword(randomCode);
				userService.saveOrUpdateUser(userFDb.get());
				return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
			} else {
				Optional<User> oOldUser = userRepository.findByEmail(user.getEmail());
				userRepository.delete(oOldUser.get());
			}
		}
		String randomCode = CodeGeneration.generateCode(10);
		if (activeProfil.contains("prod")) {
			// Send Message!
			userService.sendMail(user.getEmail(), randomCode);
		}
		user.setPassword(randomCode);
		userRepository.save(user);
		return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
	}
}
