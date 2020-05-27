package fr.uca.cdr.skillful_network.services.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.tools.PageTool;

public interface TrainingService {

	Training createOrUpdate(Training training);

	List<Training> getAll();

	Optional<Training> getById(long id);

	Page<Training> getByPage(PageTool pageTool);

	Page<Training> getCandidates(Pageable pageable, String keyword);

	void delete(long id);

}
