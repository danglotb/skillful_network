package fr.uca.cdr.skillful_network.services.impl.application;

import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.services.application.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.repositories.application.JobOfferRepository;
import fr.uca.cdr.skillful_network.tools.PageTool;

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
	public Optional<JobOffer> getById(long id) {
		return this.repository.findById(id);
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
	public void delete(long id) {
		this.repository.deleteById(id);
	}
}
