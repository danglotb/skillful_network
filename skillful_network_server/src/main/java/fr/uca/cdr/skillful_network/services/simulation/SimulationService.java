package fr.uca.cdr.skillful_network.services.simulation;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.simulation.Simulation;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.entities.simulation.exercise.Exam;
import fr.uca.cdr.skillful_network.entities.Keyword;
import fr.uca.cdr.skillful_network.request.SimulationForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SimulationService {

	List<Simulation> getAllSimulations();
	Optional<Simulation> getSimulationById(Long id);
	Optional<User> getUserById(Long id);
	Optional<List<Simulation>> getAllSimulationsByUserId(Long userId);
	Optional<Simulation> saveOrUpdateSimulation(Simulation simulation);
	Optional<Exam> startSimulation(Long userId);
	Optional<Simulation> getSimulationByExamId(Long idExam);
	void deleteSimulation(Long id);

	ArrayList<String> MatcherJobOfferJobGoal(String careerGoal, ArrayList<JobOffer> jobOffer);
	ArrayList<JobOffer> ListJobOfferByJobGoal(String careerGoal, ArrayList<JobOffer> jobOffer);
	Optional<Keyword> getKeyWordExoById(Long id);
	List<Keyword> findAllKeyWordExo();
	ArrayList<Keyword> exerciceMachJoboffer(ArrayList<Keyword> keyExo, ArrayList<String> keyJob);
    Optional<Simulation> evaluateSimulation(SimulationForm simulationForm, Long examId);


}
