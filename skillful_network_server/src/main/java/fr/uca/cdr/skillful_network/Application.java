package fr.uca.cdr.skillful_network;


import fr.uca.cdr.skillful_network.tools.json.ExerciseAdapter;
import fr.uca.cdr.skillful_network.tools.json.JSONLoader;
import fr.uca.cdr.skillful_network.tools.json.UserAdapter;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.simulation.Simulation;
import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.entities.simulation.exercise.Exercise;
import fr.uca.cdr.skillful_network.entities.simulation.exercise.Keyword;
import fr.uca.cdr.skillful_network.entities.simulation.exercise.Result;
import fr.uca.cdr.skillful_network.entities.simulation.exercise.Choice;
import fr.uca.cdr.skillful_network.repositories.simulation.exercise.ChoiceRepository;
import fr.uca.cdr.skillful_network.repositories.simulation.exercise.ExerciseRepository;
import fr.uca.cdr.skillful_network.repositories.application.JobOfferRepository;
import fr.uca.cdr.skillful_network.repositories.KeywordRepository;
import fr.uca.cdr.skillful_network.repositories.simulation.exercise.ResultRepository;
import fr.uca.cdr.skillful_network.repositories.user.RoleRepository;
import fr.uca.cdr.skillful_network.repositories.simulation.SimulationRepository;
import fr.uca.cdr.skillful_network.repositories.application.TrainingRepository;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
public class Application {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
	@Bean
	@Profile({"dev"})
	ApplicationRunner initChoiceRepository(ChoiceRepository choiceRepository) {
		return args -> {
			if (choiceRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/choices.json", Choice[].class, choiceRepository).load();
			}
		};
	}
	@Bean
	@Profile({"dev"})
	ApplicationRunner initKeywordRepository(KeywordRepository keywordRepository) {
		return args -> {
			if (keywordRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/keywords.json", Keyword[].class, keywordRepository).load();
			}
		};
	}
	
	@Bean
	@Profile({"dev"})
	ApplicationRunner initJobOfferRepository(JobOfferRepository jobOfferRepository) {
		return args -> {
			if (jobOfferRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/job-offers.json", JobOffer[].class, jobOfferRepository)
						.load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initRoleRepository(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/roles.json", Role[].class, roleRepository).load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initUserRepository(UserRepository userRepository) {
		return args -> {
			if (userRepository.findAll().isEmpty()) {
				new JSONLoader<>(
						"src/main/resources/data/users.json", 
						User[].class,
						User.class,
						userRepository,
						new UserAdapter()
						).load();
				
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initTrainingRepository(TrainingRepository trainingRepository) {
		return args -> {
			if (trainingRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/trainings.json", Training[].class, trainingRepository).load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initExercises(ExerciseRepository exerciseRepository) {
		return args -> {
			if (exerciseRepository.findAll().isEmpty()) {
				new JSONLoader<>(
						"src/main/resources/data/exercises.json",
						Exercise[].class,
						Exercise.class,
						exerciseRepository,
						new ExerciseAdapter()
						).load();
			}
		};
	}
	
	@Bean
	@Profile({"dev"})
	ApplicationRunner initSimulationRepository(SimulationRepository simulationRepository) {
		return args -> {
			if (simulationRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/simulation.json", Simulation[].class, simulationRepository).load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initResultRepository(ResultRepository resultRepository) {
		return args -> {
			if (resultRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/results.json", Result[].class, resultRepository).load();
			}
		};
	}
}

