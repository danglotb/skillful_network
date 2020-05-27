package fr.uca.cdr.skillful_network.services.impl.simulation.exercise;

import java.util.Collections;
import java.util.List;

import fr.uca.cdr.skillful_network.entities.user.Qualification;
import fr.uca.cdr.skillful_network.services.user.QualificationService;
import fr.uca.cdr.skillful_network.tools.AutoCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.uca.cdr.skillful_network.repositories.user.QualificationRepository;

@Service(value = "QualificationService")
public class QualificationServiceImpl implements QualificationService {

	@Autowired
	private QualificationRepository qualificationrepository;

	private AutoCompletion<Qualification> autoCompletion;

	public QualificationServiceImpl() {
		this.autoCompletion = new AutoCompletion<>(Qualification.class, "name", "userSet");
	}

	@Override
	public List<Qualification> getAllQualifications() {
		return qualificationrepository.findAll();
	}

	@Override
	public List<Qualification> getQualificationByPrefix(String prefix) {
		return qualificationrepository.search(prefix).orElse(Collections.emptyList());
	}

	@Override
	public List<Qualification> getQualificationsByMatch(String match) {
		return autoCompletion.findCandidates(qualificationrepository.findAll(), match);
	}

}
