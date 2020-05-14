
package fr.uca.cdr.skillful_network.services.user;

import java.util.List;

import fr.uca.cdr.skillful_network.entities.user.Qualification;

public interface QualificationService {

	List<Qualification> getAllQualifications();
	List<Qualification> getQualificationByPrefix(String prefix);
	List<Qualification> getQualificationsByMatch(String match);

}
