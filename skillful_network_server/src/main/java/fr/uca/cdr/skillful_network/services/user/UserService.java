package fr.uca.cdr.skillful_network.services.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	boolean exists(String mail);

	boolean isValidated(String mail);

	User getById(long id);

	User getByEmail(String mail);

	Page<User> getByKeyword(Pageable pageable, String keyword);

	List<User> getAll();

	User createOrUpdate(User user);
	
	void delete(Long id);

	User update(String firstName, String lastName, Date birthDate, String careerGoal, Set<Skill> skillSet, Set<Qualification> qualificationSet, Set<Subscription> subscriptionSet);

	User updateConfirmationRegister(String firstName, String lastName, Set<Role> roleSet);

	User updatePassword(String password);

	User updateEmail(String email);

	byte[] getCurrentProfilePicture() throws IOException;

	byte[] getProfilePictureById(long id) throws IOException;

	boolean isCorrectSize(int size);

	boolean supportProvidedPicture(MultipartFile image);

	boolean uploadProfilePicture(MultipartFile image);
}
