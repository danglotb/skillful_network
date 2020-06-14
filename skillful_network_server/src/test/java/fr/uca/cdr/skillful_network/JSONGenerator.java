package fr.uca.cdr.skillful_network;

import fr.uca.cdr.skillful_network.entities.Keyword;
import fr.uca.cdr.skillful_network.entities.application.Application;
import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import fr.uca.cdr.skillful_network.entities.user.Qualification;
import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.user.Skill;
import fr.uca.cdr.skillful_network.entities.user.Subscription;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.KeywordRepository;
import fr.uca.cdr.skillful_network.repositories.application.JobApplicationRepository;
import fr.uca.cdr.skillful_network.repositories.application.JobOfferRepository;
import fr.uca.cdr.skillful_network.repositories.application.TrainingApplicationRepository;
import fr.uca.cdr.skillful_network.repositories.application.TrainingRepository;
import fr.uca.cdr.skillful_network.repositories.user.QualificationRepository;
import fr.uca.cdr.skillful_network.repositories.user.RoleRepository;
import fr.uca.cdr.skillful_network.repositories.user.SkillRepository;
import fr.uca.cdr.skillful_network.repositories.user.SubscriptionRepository;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.tools.json.JSONLoader;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;

import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_COMPANY;
import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_TRAINING_ORGANIZATION;
import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_USER;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class JSONGenerator {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private TrainingApplicationRepository trainingApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String PREFIX_PATH = "src/main/resources/data/";

    private static final String EXTENSION_JSON = ".json";

    @SuppressWarnings("all")
    private void saveTo(String name, Class<?> clazz,  JpaRepository<?, Long> repository) {
        new JSONLoader(PREFIX_PATH + name + EXTENSION_JSON, clazz, repository).save(repository.findAll());
    }

    private void generateSkills() {
        final Skill skillName1 = new Skill("skillName1");
        this.entityManager.persistAndFlush(skillName1);
        this.saveTo("skills", Skill[].class, this.skillRepository);
    }

    private void generateSubscriptions() {
        final Subscription subscriptionName1 = new Subscription("subscriptionName1");
        this.entityManager.persistAndFlush(subscriptionName1);
        this.saveTo("subscriptions", Subscription[].class, this.subscriptionRepository);
    }

    private void generateQualifications() {
        final Qualification qualificationName1 = new Qualification("qualificationName1");
        this.entityManager.persistAndFlush(qualificationName1);
        this.saveTo("qualifications", Qualification[].class, this.qualificationRepository);
    }

    private void generateRoles() {
        final Role roleUser = new Role(ROLE_USER);
        final Role roleCompany = new Role(ROLE_COMPANY);
        final Role roleTrainingOrganization = new Role(ROLE_TRAINING_ORGANIZATION);
        this.entityManager.persistAndFlush(roleUser);
        this.entityManager.persistAndFlush(roleCompany);
        this.entityManager.persistAndFlush(roleTrainingOrganization);
        this.saveTo("roles", Role[].class, this.roleRepository);
    }

    private void generateKeywords() {
        final Keyword keyword1 = new Keyword("keyword1");
        final Keyword keyword2 = new Keyword("keyword2");
        final Keyword keyword3 = new Keyword("keyword3");
        this.entityManager.persistAndFlush(keyword1);
        this.entityManager.persistAndFlush(keyword2);
        this.entityManager.persistAndFlush(keyword3);
        this.saveTo("keywords", Keyword[].class, this.keywordRepository);
    }

    private void generateJobOffers() {
        final JobOffer jobOffer1 = new JobOffer(
                "jobOffer1",
                "company1",
                "descriptions1",
                "type1",
                new Date(),
                new Date(),
                new Date(),
                Collections.singleton(this.keywordRepository.getOne(1L)),
                JobOffer.Risk.MODERATE,
                JobOffer.Complexity.MODERATE,
                Collections.emptySet()
        );
        this.entityManager.persistAndFlush(jobOffer1);
        this.saveTo("job-offers", JobOffer[].class, this.jobOfferRepository);
    }

    private void generateTrainings() {
        final Training training1 = new Training(
                "trainingName1",
                "trainingOrganization1",
                "trainingDescription1",
                new Date(),
                new Date(),
                new Date(),
                10L,
                Collections.singleton(this.keywordRepository.getOne(1L)),
                Collections.emptySet()
        );
        this.entityManager.persistAndFlush(training1);
        this.saveTo("trainings", Training[].class, this.trainingRepository);
    }

    private void generateJobApplications(User user, final long idJob){
        final JobApplication application = new JobApplication(
                user,
                Application.ApplicationStatus.ACCEPTED,
                new Date(),
                this.jobOfferRepository.getOne(idJob)
        );
        this.entityManager.persistAndFlush(application);
        this.saveTo("job-offer-applications", JobApplication[].class, this.jobApplicationRepository);
    }

    private void generateTrainingApplications(User user, final long idTraining) {
        final TrainingApplication application = new TrainingApplication(
                user,
                Application.ApplicationStatus.ACCEPTED,
                new Date(),
                this.trainingRepository.getOne(idTraining)
        );
        this.entityManager.persistAndFlush(application);
        this.saveTo("training-applications", TrainingApplication[].class, this.trainingApplicationRepository);
    }

    private void generateUsers() {
        final User user = new User();
        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setPassword("Qwerty123");
        user.setBirthDate(new Date());
        user.setEmail("user@uca.fr");
        user.setMobileNumber("0712345678");
        user.setCareerGoal("userCareerGoal");
        user.setValidated(true);
        user.setSkillSet(Collections.singleton(this.skillRepository.getOne(1L)));
        user.setSubscriptionSet(Collections.singleton(this.subscriptionRepository.getOne(1L)));
        user.setQualificationSet(Collections.singleton(this.qualificationRepository.getOne(1L)));
        user.setRoles(Collections.singleton(this.roleRepository.findByName(ROLE_USER).get()));
        this.entityManager.persistAndFlush(user);
        this.generateJobApplications(user, 1L);
        this.generateTrainingApplications(user, 1L);
        this.saveTo("users", User[].class, this.userRepository);
    }

    @Ignore
    @Test
    public void generateJSON() {
        this.generateSkills();
        this.generateSubscriptions();
        this.generateQualifications();
        this.generateRoles();
        this.generateKeywords();
        this.generateJobOffers();
        this.generateTrainings();
        this.generateUsers();
    }
}
