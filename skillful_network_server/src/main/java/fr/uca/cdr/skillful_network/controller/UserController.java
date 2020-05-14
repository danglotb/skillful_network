package fr.uca.cdr.skillful_network.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.io.Files;


import fr.uca.cdr.skillful_network.model.entities.Qualification;
import fr.uca.cdr.skillful_network.model.entities.Skill;
import fr.uca.cdr.skillful_network.model.entities.Subscription;
import fr.uca.cdr.skillful_network.model.entities.User;
import fr.uca.cdr.skillful_network.model.repositories.UserRepository;
import fr.uca.cdr.skillful_network.model.services.SkillService;
import fr.uca.cdr.skillful_network.model.services.UserService;
import fr.uca.cdr.skillful_network.request.UserForm;
import fr.uca.cdr.skillful_network.request.UserPwdUpdateForm;
import fr.uca.cdr.skillful_network.tools.PageTool;

/**
 * Cette classe est responsable du traitement des requêtes liées aux
 * utilisateurs comme /users.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "")
    public List<User> getUsers() {
        return (List<User>) this.repository.findAll();
    }

//	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
//	@GetMapping(value = "/users/")
//	public ResponseEntity<Page<User>> getUsersPerPage(@Valid PageTool pageTool) {
//		if (pageTool != null) {
//			Page<User> listUserByPage = userService.getPageOfEntities(pageTool);
//			return new ResponseEntity<Page<User>>(listUserByPage, HttpStatus.OK);
//		} else {
//			System.out.println(pageTool);
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Données en paramètre non valide");
//		}
//	}

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<User>> getUsersBySearch(@Valid PageTool pageTool,
                                                       @RequestParam(name = "keyword", required = false) String keyword) {
        if (pageTool != null && keyword != null) {
            Page<User> listUsersSeachByPage = userService.searchUsersByKeyword(pageTool.requestPage(), keyword);
            return new ResponseEntity<Page<User>>(listUsersSeachByPage, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Données en paramètre non valide");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@AuthenticationPrincipal User userLogged,

                                           @Valid @RequestBody UserForm userRequest) {
        Long id = userLogged.getId();
        System.out.println(id);
        System.out.println(userRequest);
        if (userService.getUserById(id).isPresent()) {
            User userToUpdate = userService.getUserById(id).get();
            if (userRequest != null) {
                userToUpdate.setLastName(userRequest.get_lastName());
                userToUpdate.setFirstName(userRequest.get_firstName());
                userToUpdate.setBirthDate(userRequest.get_birthDate());
                userToUpdate.setEmail(userRequest.get_email());
                userToUpdate.setMobileNumber(userRequest.get_mobileNumber());
                userToUpdate.setSkillSet(userRequest.get_skillSet());
                userToUpdate.setQualificationSet(userRequest.get_qualificationSet());
                userToUpdate.setSubscriptionSet(userRequest.get_subscriptionSet());
                userToUpdate.setCareerGoal(userRequest.get_careerGoal());
                User userUpdated = userService.saveOrUpdateUser(userToUpdate);
                return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucune données en paramètre");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé");

        }
    }

    //	@PreAuthorize("hasRole('USER')")
//	@Transactional
//	@PutMapping(value = "/usersModifPassword/{id}")
//	public ResponseEntity<User> updateUserPassword(@PathVariable(value = "id") long id,
//			@Valid @RequestBody UserPwdUpdateForm userModifPwd) {
//
//		User userToUpdate = userService.getUserById(id).orElseThrow(
//				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé avec l'id " + id));
//		
//		String userNewPwd = passwordEncoder.encode(userModifPwd.getPassword());
//		userToUpdate.setPassword(userNewPwd);
//		User userUpdated = userService.saveOrUpdateUser(userToUpdate);
//		return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
//	}
    // Utilisation du current User pour la modification
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PutMapping("/user")
    public ResponseEntity<User> updatePasswordCurrentUser(@AuthenticationPrincipal User user,
                                                          @Valid @RequestBody UserPwdUpdateForm userModifPwd) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        String userNewPwd = passwordEncoder.encode(userModifPwd.getPassword());
        currentUser.setPassword(userNewPwd);
        userService.saveOrUpdateUser(currentUser);

        return new ResponseEntity<User>(currentUser, HttpStatus.OK);

    }

    @GetMapping(value = "/testCreationRepo")
    public ResponseEntity<Boolean> testCreationRepo() {
        this.userService.createRepoImage();

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('USER')")
//	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = { MediaType.IMAGE_JPEG_VALUE,
//			MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE })
//	public String fileUpload(@RequestParam("image") MultipartFile image) throws IOException {
//
//		File convertFile = new File("WebContent/images/" + image.getOriginalFilename());
//		convertFile.createNewFile();
//		FileOutputStream fout = new FileOutputStream(convertFile);
//		fout.write(image.getBytes());
//		fout.close();
//
//		return "File is upload successfully" + image.getOriginalFilename();
//	}

    private static final ArrayList<String> SUPPORTED_EXTENSIONS_PROFILE_PICTURE = new ArrayList<>();

    static {
        SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("jpg");
        SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("jpeg");
        SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("png");
    }

    @SuppressWarnings("resource")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    @RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.POST)
    public ResponseEntity<Boolean> profilePictureUpload(@RequestParam("image") MultipartFile image,
                                              RedirectAttributes redirectAttributes) {
        if (image.getOriginalFilename() == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Veuillez selectionner une photo profil");
        }
        final String imageName = image.getOriginalFilename().toLowerCase();
        if (!SUPPORTED_EXTENSIONS_PROFILE_PICTURE.contains(Files.getFileExtension(imageName))) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        try {
            if (image.getBytes().length > 1000 * 500) {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        "The provided image is too large. we accept only 1000 x 500 images for profile picture.");
            }
        } catch (IOException e) {
            throw  new ResponseStatusException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Something went wrong when checking the size of the provided images", e
            );
        }

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final long id = ((User) authentication.getPrincipal()).getId();
        final User currentUser = this.userService.getUserById(id).get();
        final String pathImageName = "WebContent/images/" + id + ".png"; // TODO we set to png here, but might be a jpg ?

        try (FileOutputStream writer = new FileOutputStream(pathImageName)) {
            writer.write(image.getBytes());
            currentUser.setPhoto(true);
            userService.saveOrUpdateUser(currentUser);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while writing the profile picture file.", e);
        }
    }

    // TODO could not find where it used
    @GetMapping(value = "/profilePicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable("id") Long id) throws IOException {
        final Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé avec l'id :" + id);
        }
        final String pathname = "WebContent/images/" + id + ".png";
        final File profilePictureFile = new File(pathname);
        if (!profilePictureFile.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found the profile picture for user with id " + id + " at " + pathname);
        }
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage buffer = ImageIO.read(profilePictureFile);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(buffer, "png", outputStream);
            outputStream.flush();
            byte[] img = outputStream.toByteArray();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while reading the picture", e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    // @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME')")
    @GetMapping(value = "/usersbyId/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {

        Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id : " + id);

        }
        return ResponseEntity.ok().body(user.get());
    }

    //	@GetMapping(value = "/users/{userId}/skills/{skillId}")
//	public ResponseEntity<Skill> getOneSkillByUser(@PathVariable(value = "userId")Long userId, @PathVariable(value = "skillId")Long skillId) {
//		
////		On vérifie que l'utilisateur existe bien
//		User userFromDb = userService.getUserById(userId)
//						  .orElseThrow(
//								  () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun utilisateur trouvé avec l'id " + userId)
//								  );
////		On vérifie que la compétence existe bien
//		Skill SkillFromDb = skillService.getSkillById(skillId)
//				  			.orElseThrow(
//				  					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune compétence trouvée avec l'id " + skillId)
//				  				);
////		On récupère le liste de compétences de l'utilisateur
//		Set<Skill> userSkills = userFromDb.getSkillSet();
////		Si la compétence de la bdd est contenue dans la liste de l'utilisateur, on la renvoie
//		if (userSkills.contains(SkillFromDb)) {
//			return new ResponseEntity<Skill> ( SkillFromDb,HttpStatus.OK);
//		}
////		Dans le cas contraire on renvoie une exception
//		else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La compétence demandée avec l'id : "+skillId+" n'est pas dans la liste de compétences de l'utilisateur avec l'id : "+userId);
//		}
//	}
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user/skills/{skillName}")
    public ResponseEntity<Skill> getOneSkillByNameByUser(@AuthenticationPrincipal User user,

                                                         @PathVariable(value = "skillName") String skillName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

//		On vérifie que l'utilisateur existe bien
//		User userFromDb = userService.getUserById(userId)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//						"Aucun utilisateur trouvé avec l'id " + userId));

//		On vérifie que la compétence existe bien
        Skill SkillFromDb = skillService.getSkillByName(skillName);

//		On récupère la liste de compétences de l'utilisateur
        Set<Skill> userSkills = currentUser.getSkillSet();

//		Si la compétence de la bdd est contenue dans la liste de l'utilisateur, on la renvoie
        if (userSkills.contains(SkillFromDb)) {
            return new ResponseEntity<Skill>(SkillFromDb, HttpStatus.OK);
        }
//		Dans le cas contraire on renvoie une exception
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La compétence demandée avec le nom : " + skillName
                    + " n'est pas dans la liste de compétences de l'utilisateur avec l'id : " + currentUser.getId());
        }
    }
//	@PreAuthorize("hasRole('USER')")
//	@Transactional
//	@DeleteMapping("/users/{userId}/skills/{skillId}")
//	public ResponseEntity<Skill> deleteSkillById(@PathVariable(value = "userId") Long id,
//			@PathVariable(value = "skillId") Long skillId) {
//
////		On vérifie que l'utilisateur existe bien
//		User userToUpdate = this.userService.getUserById(id).orElseThrow(
//				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune user trouvee avec l'id : " + id));
//
////		On vérifie que la compétence existe bien
//		Skill skillToDelete = this.skillService.getSkillById(skillId)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//						"Aucune competence trouvee avec l'id : " + skillId));
//
////		On récupère le liste de compétences de l'utilisateur
//		Set<Skill> listSkill = userToUpdate.getSkillSet();
//
////      Si la compétence à enlever est bien dans la liste de compétences de l'utilisateur alors on le mets à jours 
//		if (listSkill.contains(skillToDelete)) {
//			listSkill.remove(skillToDelete);
//			userToUpdate.setSkillSet(listSkill);
//			this.userService.saveOrUpdateUser(userToUpdate);
//			return new ResponseEntity<Skill>(skillToDelete, HttpStatus.OK);
//		}
////		Dans le cas contraire on renvoie une exception
//		else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La compétence demandée avec l'id : " + skillId
//					+ " n'est pas dans la liste de compétences de l'utilisateur avec l'id : " + id);
//		}
//	}

    // Utilisation du current User pour la suppression des skills
    @PreAuthorize("hasRole('USER')")
    @Transactional
    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<Skill> deleteSkillById(@AuthenticationPrincipal User user,
                                                 @PathVariable(value = "skillId") Long skillId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

//		On vérifie que la compétence existe bien
        Skill skillToDelete = this.skillService.getSkillById(skillId);

//		On récupère le liste de compétences de l'utilisateur
        Set<Skill> listSkill = currentUser.getSkillSet();

//      Si la compétence à enlever est bien dans la liste de compétences de l'utilisateur alors on la supprime 
        if (listSkill.contains(skillToDelete)) {
            listSkill.remove(skillToDelete);
            currentUser.setSkillSet(listSkill);
            this.userService.saveOrUpdateUser(currentUser);
            return new ResponseEntity<Skill>(skillToDelete, HttpStatus.OK);
        }
//		Dans le cas contraire on renvoie une exception
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La compétence demandée avec l'id : " + skillId
                    + " n'est pas dans la liste de compétences de l'utilisateur avec l'id : " + currentUser.getId());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PostMapping("/{userId}/skills/{skillId}")
    public ResponseEntity<Skill> setSkillbyId(@AuthenticationPrincipal User user,
                                              @PathVariable(value = "skillId") Long skillId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

//        //On vérifie que l'utilisateur existe bien
//		User userToUpdate = this.userService.getUserById(id).orElseThrow(
//				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune user trouvee avec l'id : " + id));

        // On vérifie que la compétence existe bien
        Skill skillToAdd = this.skillService.getSkillById(skillId);

        // On récupère le liste de compétences de l'utilisateur
        Set<Skill> listSkill = currentUser.getSkillSet();

        // Si la compétence à ajouter n'est pas dans la liste de compétences de
        // l'utilisateur, alors on le mets à jours
        if (!(listSkill.contains(skillToAdd))) {
            listSkill.add(skillToAdd);
            currentUser.setSkillSet(listSkill);
            this.userService.saveOrUpdateUser(currentUser);
            return new ResponseEntity<Skill>(skillToAdd, HttpStatus.OK);
        }
        // Dans le cas contraire on renvoie une exception
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La compétence demandée avec l'id : " + skillId
                    + " est déjà dans la liste de compétences de l'utilisateur avec l'id : " + currentUser.getId());
        }
    }

    @GetMapping(value = "/{id}/Qualifications")
    public ResponseEntity<Set<Qualification>> getAllQualificationByUser(@PathVariable(value = "id") Long id) {
        Set<Qualification> listQualifications = this.userService.getUserById(id)
                .map((user) -> {
                    return user.getQualificationSet();
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune compétence trouvée avec l'id : " + id));
        return new ResponseEntity<Set<Qualification>>(listQualifications, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/Subscription")
    public ResponseEntity<Set<Subscription>> getAllSubscriptionByUser(@PathVariable(value = "id") Long id) {
        Set<Subscription> listSubscription = this.userService.getUserById(id)
                .map((user) -> {
                    return user.getSubscriptionSet();
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune compétence trouvée avec l'id : " + id));
        return new ResponseEntity<Set<Subscription>>(listSubscription, HttpStatus.OK);
    }

//	@GetMapping(value = "users/{id}/skills")
//	public ResponseEntity<Set<Skill>> getAllSkillByUser1(@PathVariable(value = "id") Long id) {
//		Set<Skill> listSkills = this.userService.getUserById(id).map((user) -> {
//			return user.getSkillSet();
//		}).orElseThrow(
//				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune compétence trouvée avec l'id : " + id));
//		return new ResponseEntity<Set<Skill>>(listSkills, HttpStatus.OK);
//	}


    // @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{id}/skills")
    public ResponseEntity<Set<Skill>> getAllSkillByUserSkills(@PathVariable(value = "id") Long id) {
        Set<Skill> listSkills = this.userService.getUserById(id).map((user) -> {
            return user.getSkillSet();
        }).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune compétence trouvée avec l'id : " + id));
        return new ResponseEntity<Set<Skill>>(listSkills, HttpStatus.OK);
    }

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final SkillService skillService;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, UserService userService, SkillService skillService) {
        this.repository = repository;
        this.userService = userService;
        this.skillService = skillService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

}
