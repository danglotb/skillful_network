
package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Qualification;

import java.util.List;

public interface QualificationService {

	List<Qualification> getAllQualifications();
	List<Qualification> getQualificationByPrefix(String prefix);
	List<Qualification> getQualificationsByMatch(String match);

}
