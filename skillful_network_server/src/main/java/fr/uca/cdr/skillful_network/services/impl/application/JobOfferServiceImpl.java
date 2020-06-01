package fr.uca.cdr.skillful_network.services.impl.application;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.Keyword;
import fr.uca.cdr.skillful_network.services.application.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.repositories.application.JobOfferRepository;
import fr.uca.cdr.skillful_network.tools.PageTool;
import org.springframework.web.server.ResponseStatusException;

@Service(value = "JobOfferService")
public class JobOfferServiceImpl implements JobOfferService {

	@Autowired
	private JobOfferRepository repository;

	@Override
	public JobOffer createOrUpdate(JobOffer jobOffer) {
		return this.repository.save(jobOffer);
	}

	@Override
	public List<JobOffer> getAll() {
		return this.repository.findAll();
	}

	@Override
	public JobOffer getById(long id) {
		return this.repository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None JobOffer could be found with the id %d", id))
		);
	}

	@Override
	public Page<JobOffer> getByPage(PageTool pageTool) {
		return this.repository.findAll(pageTool.requestPage());
	}

	@Override
	public Page<JobOffer> getCandidates(Pageable pageable, String keyword) {
		return this.repository.findByNameContainsOrCompanyContainsAllIgnoreCase(pageable, keyword, keyword);
	}

	@Override
	public JobOffer update(long id,
						   String name,
						   String company,
						   String description,
						   String type,
						   Date dateBeg,
						   Date dateEnd,
						   Date dateUpload,
						   Set<Keyword> keywords,
						   JobOffer.Risk risk,
						   JobOffer.Complexity complexity) {
		final JobOffer jobOffer = this.repository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None JobOffer could be found with the id %d", id))
		);
		jobOffer.setName(name);
		jobOffer.setCompany(company);
		jobOffer.setDescription(description);
		jobOffer.setType(type);
		jobOffer.setDateBeg(dateBeg);
		jobOffer.setDateEnd(dateEnd);
		jobOffer.setDateUpload(dateUpload);
		jobOffer.setKeywords(keywords);
		jobOffer.setRisk(risk);
		jobOffer.setComplexity(complexity);
		return jobOffer;
	}

	@Override
	public void delete(long id) {
		this.repository.deleteById(id);
	}
}
