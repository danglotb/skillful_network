package fr.uca.cdr.skillful_network;


import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import fr.uca.cdr.skillful_network.entities.post.Comment;
import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.entities.user.FollowStateTracker;
import fr.uca.cdr.skillful_network.repositories.application.JobApplicationRepository;
import fr.uca.cdr.skillful_network.repositories.application.TrainingApplicationRepository;
import fr.uca.cdr.skillful_network.repositories.post.CommentRepository;
import fr.uca.cdr.skillful_network.repositories.post.PostRepository;
import fr.uca.cdr.skillful_network.repositories.user.FollowStateTrackerRepository;
import fr.uca.cdr.skillful_network.tools.json.JSONLoader;
import fr.uca.cdr.skillful_network.tools.json.UserAdapter;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.entities.Keyword;
import fr.uca.cdr.skillful_network.repositories.application.JobOfferRepository;
import fr.uca.cdr.skillful_network.repositories.KeywordRepository;
import fr.uca.cdr.skillful_network.repositories.user.RoleRepository;
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
	ApplicationRunner initJobApplicationRepository(JobApplicationRepository jobApplicationRepository) {
		return args -> {
			if (jobApplicationRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/job-offer-applications.json", JobApplication[].class, jobApplicationRepository).load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initTrainingApplicationRepository(TrainingApplicationRepository trainingApplicationRepository) {
		return args -> {
			if (trainingApplicationRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/training-applications.json", TrainingApplication[].class, trainingApplicationRepository).load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initFollowStateTrackerRepository(FollowStateTrackerRepository fstRepository) {
		return args -> {
			if (fstRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/followers.json", FollowStateTracker[].class, fstRepository).load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initPostRepository(PostRepository postRepository) {
		return args -> {
			if (postRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/posts.json", Post[].class, postRepository).load();
			}
		};
	}

	@Bean
	@Profile({"dev"})
	ApplicationRunner initCommentRepository(CommentRepository commentRepository) {
		return args -> {
			if (commentRepository.findAll().isEmpty()) {
				new JSONLoader<>("src/main/resources/data/comments.json", Comment[].class, commentRepository).load();
			}
		};
	}

}

