package fr.uca.cdr.skillful_network.services.impl.application;

import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.services.application.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.repositories.application.TrainingRepository;
import fr.uca.cdr.skillful_network.tools.PageTool;

@Service
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingRepository repository;

	@Override
	public Training createOrUpdate(Training training) {
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
	public Optional<Training> getById(long id) {
		return this.repository.findById(id);
	}

	@Override
	public Page<Training> getCandidates(Pageable pageable, String keyword) {
		return this.repository.findByNameContainsOrOrganizationContainsAllIgnoreCase(pageable, keyword, keyword);
	}

	@Override
	public void delete(long id) {
		this.repository.deleteById(id);
	}

}

