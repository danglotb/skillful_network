package fr.uca.cdr.skillful_network.services.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.tools.PageTool;

public interface JobOfferService {

	JobOffer createOrUpdate(JobOffer jobOffer);
	
	List<JobOffer> getAll();
	
	Optional<JobOffer> getById(long id);
	
	Page<JobOffer> getByPage(PageTool pageTool);

	Page<JobOffer> getCandidates(Pageable pageable, String keyword);
	
	void delete(long id);

}
