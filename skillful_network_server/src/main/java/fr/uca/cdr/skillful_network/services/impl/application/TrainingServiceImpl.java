package fr.uca.cdr.skillful_network.services.impl.application;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.Keyword;
import fr.uca.cdr.skillful_network.services.application.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.repositories.application.TrainingRepository;
import fr.uca.cdr.skillful_network.tools.PageTool;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingRepository repository;

	@Override
	public Training create(Training training) {
		return this.repository.save(training);
	}

	@Override
	public List<Training> getAll() {
		return this.repository.findAll();
	}

	@Override
	public Page<Training> getByPage(PageTool pageTool) {
		return this.repository.findAll(pageTool.requestPage());
	}

	@Override
	public Training getById(long id) {
		return this.repository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("None Training could be found with the id %d", id))
		);
	}

	@Override
	public Page<Training> getCandidates(Pageable pageable, String keyword) {
		return this.repository.findByNameContainsOrOrganizationContainsAllIgnoreCase(pageable, keyword, keyword);
	}

	@Override
	public Training update(long id,
						   String name,
						   String organization,
						   String description,
						   Date dateBeg,
						   Date dateEnd,
						   Date dateUpload,
						   long durationHours,
						   Set<Keyword> keywords) {
		final Training trainingToUpdate = this.getById(id);
		trainingToUpdate.setName(name);
		trainingToUpdate.setOrganization(organization);
		trainingToUpdate.setDescription(description);
		trainingToUpdate.setDateBeg(dateBeg);
		trainingToUpdate.setDateEnd(dateEnd);
		trainingToUpdate.setDateUpload(dateUpload);
		trainingToUpdate.setDurationHours(durationHours);
		trainingToUpdate.setKeywords(keywords);
		return trainingToUpdate;
	}

	@Override
	public void delete(long id) {
		this.repository.deleteById(id);
	}

}

