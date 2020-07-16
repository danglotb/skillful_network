package fr.uca.cdr.skillful_network.controller.user;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import fr.uca.cdr.skillful_network.request.ConfirmationRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.UserService;
import fr.uca.cdr.skillful_network.request.UserForm;
import fr.uca.cdr.skillful_network.request.PasswordForm;
import fr.uca.cdr.skillful_network.tools.PageTool;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "")
    public List<User> getAll() {
        return this.userService.getAll();
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<User>> getBySearch(@Valid PageTool pageTool,
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
    public ResponseEntity<User> update(@Valid @RequestBody UserForm userForm) {
        return new ResponseEntity<>(this.userService.update(
                userForm.getFirstName(),
                userForm.getLastName(),
                userForm.getBirthDate(),
                userForm.getCareerGoal(),
                userForm.getSkillSet(),
                userForm.getQualificationSet(),
                userForm.getSubscriptionSet()
        ), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PutMapping(value = "/confirmation")
    public ResponseEntity<User> updateConfirmationRegister(@Valid @RequestBody ConfirmationRegisterForm confirmationRegisterForm) {
        return new ResponseEntity<>(this.userService.updateConfirmationRegister(
                confirmationRegisterForm.getFirstName(),
                confirmationRegisterForm.getLastName(),
                confirmationRegisterForm.getRoleSet()
        ), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PutMapping("/password")
    public ResponseEntity<User> updatePassword(@Valid @RequestBody PasswordForm passwordUpdate) {
        return new ResponseEntity<>(this.userService.updatePassword(passwordUpdate.getPassword()), HttpStatus.OK);
    }

    @SuppressWarnings("resource")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PostMapping(value = "/profilePicture")
    public ResponseEntity<Boolean> updateProfilePicture(@RequestParam("image") MultipartFile image) {
        if (image.getOriginalFilename() == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Veuillez selectionner une photo profil");
        }
        try {
            if (!this.userService.supportProvidedPicture(image)) {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            if (!this.userService.isCorrectSize(image.getBytes().length)) {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        "The provided image is too large. we accept only 1000 x 500 images for profile picture.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Something went wrong when checking the size of the provided images", e
            );
        }
        if (this.userService.uploadProfilePicture(image)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while writing the profile picture file.");
        }
    }

    @GetMapping(value = "/profilePicture", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePicture() {
        final byte[] profilePictureAsByteArray;
        try {
            profilePictureAsByteArray = this.userService.getCurrentProfilePicture();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while reading the picture", e);
        }
        if (profilePictureAsByteArray.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found the profile picture for the current user");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profilePictureAsByteArray);
    }

    @GetMapping(value = "/profilePicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePictureById(@PathVariable("id") long id) throws IOException {
        final byte[] profilePictureAsByteArray;
        try {
            profilePictureAsByteArray = this.userService.getProfilePictureById(id);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while reading the picture", e);
        }
        if (profilePictureAsByteArray.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found the profile picture for the user with the id " + id);
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profilePictureAsByteArray);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(this.userService.getById(id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Follower methods :
    //
    // /followers                                       getAllFollowersByFollowable()           (currentUser -> followable)
    // /followers/{followableId}                        getAllFollowersByFollowableID(followableId)
    //
    // /followers/count                                 getFollowerCount()                      (currentUser -> followable)
    // /followers/count/{followableId}                  getFollowerCount(Long followableID)
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers")
    public ResponseEntity<List<User>> getAllFollowersByFollowable() {
        List<User> followedList = userService.getAllFollowersByFollowable()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>( followedList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers/{followableId}")
    public ResponseEntity<List<User>> getAllFollowersByFollowableID(
            @PathVariable(value = "followableId") Long followableId) {
        List<User> followedList = userService.getAllFollowersByFollowableID(followableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>( followedList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers/count")
    public ResponseEntity<Long> getFollowerCount(){
        return new ResponseEntity<>(userService.getFollowerCount(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers/count/{followableId}")
    public ResponseEntity<Long> getFollowerCount(
            @PathVariable(value = "followableId") Long followableID) {
        return new ResponseEntity<>(userService.getFollowerCount(followableID), HttpStatus.OK);
    }
}
