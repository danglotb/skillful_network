package fr.uca.cdr.skillful_network.services.application;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.uca.cdr.skillful_network.entities.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.tools.PageTool;

public interface TrainingService {

	Training create(Training training);

	List<Training> getAll();

	Training getById(long id);

	Page<Training> getByPage(PageTool pageTool);

	Page<Training> getCandidates(Pageable pageable, String keyword);

	Training update(long id,
					String name,
					String organization,
					String description,
					Date dateBeg,
					Date dateEnd,
					Date dateUpload,
					long durationHours,
					Set<Keyword> keywords);

	void delete(long id);

}
