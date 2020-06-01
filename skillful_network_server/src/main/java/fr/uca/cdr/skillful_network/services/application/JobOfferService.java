package fr.uca.cdr.skillful_network.services.application;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.tools.PageTool;

public interface JobOfferService {

	JobOffer createOrUpdate(JobOffer jobOffer);
	
	List<JobOffer> getAll();
	
	JobOffer getById(long id);
	
	Page<JobOffer> getByPage(PageTool pageTool);

	Page<JobOffer> getCandidates(Pageable pageable, String keyword);

	JobOffer update(
			long id,
			String name,
			String company,
			String description,
			String type,
			Date dateBeg,
			Date dateEnd,
			Date dateUpload,
			Set<Keyword> keywords,
			JobOffer.Risk risk,
			JobOffer.Complexity complexity
	);
	
	void delete(long id);

}
