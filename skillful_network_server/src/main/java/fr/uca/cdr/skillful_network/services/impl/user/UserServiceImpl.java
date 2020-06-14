package fr.uca.cdr.skillful_network.services.impl.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.common.io.Files;
import fr.uca.cdr.skillful_network.entities.user.Qualification;
import fr.uca.cdr.skillful_network.entities.user.Skill;
import fr.uca.cdr.skillful_network.entities.user.Subscription;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.user.QualificationService;
import fr.uca.cdr.skillful_network.services.user.SkillService;
import fr.uca.cdr.skillful_network.services.user.SubscriptionService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;

@Service
public class UserServiceImpl implements UserService {

	public static final int MAX_SIZE = 1000 * 500;
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	private static final ArrayList<String> SUPPORTED_EXTENSIONS_PROFILE_PICTURE = new ArrayList<>();

	static {
		SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("jpg");
		SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("jpeg");
		SUPPORTED_EXTENSIONS_PROFILE_PICTURE.add("png");
	}

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

    @Override
    public User update(String firstName,
                       String lastName,
                       Date birthDate,
                       String careerGoal,
                       Set<Skill> skillSet,
                       Set<Qualification> qualificationSet,
                       Set<Subscription> subscriptionSet) {
        final User currentUser = this.authenticationService.getCurrentUser();
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setBirthDate(birthDate);
        currentUser.setCareerGoal(careerGoal);
        currentUser.setQualificationSet(this.qualificationService.createNewAndUpdateGivenSet(qualificationSet));
        currentUser.setSkillSet(this.skillService.createNewAndUpdateGivenSet(skillSet));
        currentUser.setSubscriptionSet(this.subscriptionService.createNewAndUpdateGivenSet(subscriptionSet));
        this.userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public User updatePassword(String password) {
        final User currentUser = this.authenticationService.getCurrentUser();
        final String cipheredPassword = this.passwordEncoder.encode(password);
        currentUser.setPassword(cipheredPassword);
        this.userRepository.save(currentUser);
        return currentUser;
    }

    // TODO in the usage policy we might want to keep in memory the old email and
    // TODO validate using a temporary code the new email
    // TODO when the new email is validated, we can erase the old one.
    // TODO Otherwise, the user might enter a new and wrong email
    // TODO and potentially loose the access to its email.
    @Override
    public User updateEmail(String email) {
        final User currentUser = this.authenticationService.getCurrentUser();
        currentUser.setEmail(email);
        this.userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public byte[] getCurrentProfilePicture() throws IOException {
        final User currentUser = this.authenticationService.getCurrentUser();
        return this.getProfilePictureById(currentUser.getId());
    }

    @Override
    public byte[] getProfilePictureById(long id) throws IOException {
        final String pathname = "WebContent/images/" + id + ".png";
        final File profilePictureFile = new File(pathname);
        if (!profilePictureFile.exists()) {
            return new byte[0];
        }
        ByteArrayOutputStream outputStream = null;
        BufferedImage buffer = ImageIO.read(profilePictureFile);
        outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffer, "png", outputStream);
        outputStream.flush();
		return outputStream.toByteArray();
    }

	@Override
	public boolean isCorrectSize(int size) {
		return size > MAX_SIZE;
	}

	@Override
	public boolean supportProvidedPicture(MultipartFile image) {
		final String imageName = image.getOriginalFilename().toLowerCase();
		return !SUPPORTED_EXTENSIONS_PROFILE_PICTURE.contains(Files.getFileExtension(imageName));
	}

	@Override
	public boolean uploadProfilePicture(MultipartFile image) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final long id = ((User) authentication.getPrincipal()).getId();
		final User currentUser = this.authenticationService.getCurrentUser();
		final String pathImageName = "WebContent/images/" + id + ".png"; // TODO we set to png here, but might be a jpg ?
		try (FileOutputStream writer = new FileOutputStream(pathImageName)) {
			writer.write(image.getBytes());
			this.userRepository.save(currentUser);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
