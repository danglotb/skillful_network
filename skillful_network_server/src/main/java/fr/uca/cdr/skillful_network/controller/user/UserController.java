package fr.uca.cdr.skillful_network.controller.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.io.Files;


import fr.uca.cdr.skillful_network.entities.user.Qualification;
import fr.uca.cdr.skillful_network.entities.user.Subscription;
import fr.uca.cdr.skillful_network.entities.user.Skill;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.UserService;
import fr.uca.cdr.skillful_network.request.UserForm;
import fr.uca.cdr.skillful_network.request.UserPwdUpdateForm;
import fr.uca.cdr.skillful_network.tools.PageTool;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "")
    public List<User> getUsers() {
        return this.userService.getAll();
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<User>> getUsersBySearch(@Valid PageTool pageTool,
                                                       @RequestParam(name = "keyword", required = false) String keyword) {
        if (pageTool != null && keyword != null) {
            Page<User> listUsersSearchByPage = userService.getByKeyword(pageTool.requestPage(), keyword);
            return new ResponseEntity<>(listUsersSearchByPage, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Parameters should not be both null: (%s, %s)", pageTool, keyword));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PutMapping(value = "")
    public ResponseEntity<User> update(@Valid @RequestBody UserForm userRequest) {
        final User currentUser = getCurrentUser();
        if (userRequest != null) {
            currentUser.setLastName(userRequest.get_lastName());
            currentUser.setFirstName(userRequest.get_firstName());
            currentUser.setBirthDate(userRequest.get_birthDate());
            currentUser.setEmail(userRequest.get_email());
            currentUser.setMobileNumber(userRequest.get_mobileNumber());
            currentUser.setSkillSet(userRequest.get_skillSet());
            currentUser.setQualificationSet(userRequest.get_qualificationSet());
            currentUser.setSubscriptionSet(userRequest.get_subscriptionSet());
            currentUser.setCareerGoal(userRequest.get_careerGoal());
            final User userUpdated = this.userService.createOrUpdate(currentUser);
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters are not valid");
        }
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PutMapping("/password")
    public ResponseEntity<User> updatePassword(@Valid @RequestBody UserPwdUpdateForm passwordUpdate) {
        final User currentUser = getCurrentUser();
        final String userNewPwd = this.passwordEncoder.encode(passwordUpdate.getPassword());
        currentUser.setPassword(userNewPwd);
        this.userService.createOrUpdate(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    private static final ArrayList<String> SUPPORTED_EXTENSIONS_PROFILE_PICTURE = new ArrayList<>();

    static {
        SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("jpg");
        SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("jpeg");
        SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("png");
    }

    @SuppressWarnings("resource")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PostMapping(value = "/profilePicture")
    public ResponseEntity<Boolean> updateProfilePicture(@RequestParam("image") MultipartFile image) {
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
            throw new ResponseStatusException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Something went wrong when checking the size of the provided images", e
            );
        }

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final long id = ((User) authentication.getPrincipal()).getId();
        final User currentUser = this.userService.getById(id);
        final String pathImageName = "WebContent/images/" + id + ".png"; // TODO we set to png here, but might be a jpg ?

        try (FileOutputStream writer = new FileOutputStream(pathImageName)) {
            writer.write(image.getBytes());
            userService.createOrUpdate(currentUser);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while writing the profile picture file.", e);
        }
    }

    @GetMapping(value = "/profilePicture", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePicture() throws IOException {
        final User currentUser = getCurrentUser();
        return this.getProfilePictureById(currentUser.getId());
    }

    @GetMapping(value = "/profilePicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePictureById(@PathVariable("id") Long id) throws IOException {
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(this.userService.getById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @DeleteMapping("/skills")
    public ResponseEntity<Set<Skill>> getAllCurrentSkills() {
        final User currentUser = getCurrentUser();
        return new ResponseEntity<>(currentUser.getSkillSet(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/skills")
    public ResponseEntity<Set<Skill>> getAllSkillByUserId(@PathVariable(value = "id") Long id) {
        Set<Skill> listSkills = this.userService.getById(id).getSkillSet();
        return new ResponseEntity<>(listSkills, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/qualifications")
    public ResponseEntity<Set<Qualification>> getAllQualificationByUserId(@PathVariable(value = "id") Long id) {
        Set<Qualification> listQualifications = this.userService.getById(id).getQualificationSet();
        return new ResponseEntity<>(listQualifications, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/subscriptions")
    public ResponseEntity<Set<Subscription>> getAllSubscriptionByUserId(@PathVariable(value = "id") Long id) {
        Set<Subscription> listSubscription = this.userService.getById(id).getSubscriptionSet();
        return new ResponseEntity<>(listSubscription, HttpStatus.OK);
    }

}
