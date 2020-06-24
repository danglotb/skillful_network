package fr.uca.cdr.skillful_network.generator;

import com.github.javafaker.Faker;
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
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_COMPANY;
import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_TRAINING_ORGANIZATION;
import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_USER;
import static org.junit.Assert.fail;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class JSONGenerator {

    private Faker faker = new Faker(Locale.FRANCE);

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

    private static final long SEED_RANDOM = 23L;

    private final Random RANDOM = new Random(SEED_RANDOM);

    private static final String PREFIX_PATH = "src/main/resources/data/";

    private static final String EXTENSION_JSON = ".json";

    @SuppressWarnings("all")
    private void saveTo(String name, Class<?> clazz, JpaRepository<?, Long> repository) {
        new JSONLoader(PREFIX_PATH + name + EXTENSION_JSON, clazz, repository).save(repository.findAll());
    }

    private Object getRandomElement(JpaRepository<?, Long> repository) {
        final List<?> elements = repository.findAll();
        if (elements.isEmpty()) {
            fail("Should have at least one element in " + repository.toString());
        }
        return elements.get(RANDOM.nextInt(elements.size()));
    }

    private static final int NB_PERKS = 10;

    private void generateSkills() {
        for (int i = 0; i < NB_PERKS; i++) {
            final Skill skillName1 = new Skill(this.faker.job().keySkills());
            this.entityManager.persistAndFlush(skillName1);
        }
        this.saveTo("skills", Skill[].class, this.skillRepository);
    }

    private void generateSubscriptions() {
        for (int i = 0; i < NB_PERKS; i++) {
            final Subscription subscriptionName1 = new Subscription(this.faker.programmingLanguage().name());
            this.entityManager.persistAndFlush(subscriptionName1);
        }
        this.saveTo("subscriptions", Subscription[].class, this.subscriptionRepository);
    }

    private void generateQualifications() {
        for (int i = 0; i < NB_PERKS; i++) {
            final Qualification qualificationName1 = new Qualification(this.faker.educator().course());
            this.entityManager.persistAndFlush(qualificationName1);
        }
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

    private final static int NB_KEYWORDS = 20;

    private void generateKeywords() {
        for (int i = 0; i < NB_KEYWORDS; i++) {
            final Keyword keyword1 = new Keyword(this.faker.job().keySkills());
            this.entityManager.persistAndFlush(keyword1);
        }
        this.saveTo("keywords", Keyword[].class, this.keywordRepository);
    }

    private void generateJobOffers() {
        final JobOffer jobOffer1 = new JobOffer(
                this.faker.job().title(),
                this.faker.company().name(),
                this.faker.company().catchPhrase(),
                "type1",
                new Date(),
                new Date(),
                new Date(),
                Collections.singleton((Keyword) this.getRandomElement(this.keywordRepository)),
                JobOffer.Risk.MODERATE,
                JobOffer.Complexity.MODERATE,
                Collections.emptySet()
        );
        this.entityManager.persistAndFlush(jobOffer1);
        this.saveTo("job-offers", JobOffer[].class, this.jobOfferRepository);
    }

    private void generateTrainings() {
        final Training training1 = new Training(
                this.faker.educator().course(),
                this.faker.educator().university(),
                this.faker.educator().campus(),
                new Date(),
                new Date(),
                new Date(),
                10L,
                Collections.singleton((Keyword) this.getRandomElement(this.keywordRepository)),
                Collections.emptySet()
        );
        this.entityManager.persistAndFlush(training1);
        this.saveTo("trainings", Training[].class, this.trainingRepository);
    }

    private void generateJobApplications(User user, final long idJob) {
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

    private static int NB_USERS_TO_GENERATE = 10;

    private void generateUsers() {
        final User user = this.generateUser(
                this.faker.name().firstName(),
                this.faker.name().lastName(),
                "Qwerty123",
                this.faker.date().birthday(),
                "user@uca.fr",
                this.faker.phoneNumber().cellPhone(),
                this.faker.job().title(),
                true,
                Collections.singleton((Skill) this.getRandomElement(this.skillRepository)),
                Collections.singleton((Subscription) this.getRandomElement(this.subscriptionRepository)),
                Collections.singleton((Qualification) this.getRandomElement(this.qualificationRepository)),
                Collections.singleton(this.roleRepository.findByName(ROLE_USER).get())
        );
        this.entityManager.persistAndFlush(user);
        for (int i = 0 ; i < NB_USERS_TO_GENERATE ; i++) {
            this.entityManager.persistAndFlush(this.generateUser());
        }
        this.generateJobApplications(user, 1L);
        this.generateTrainingApplications(user, 1L);
        this.saveTo("users", User[].class, this.userRepository);
    }

    private User generateUser() {
        return this.generateUser(
                this.faker.name().firstName(),
                this.faker.name().lastName(),
                this.faker.internet().password(8, 10, true, true, true),
                this.faker.date().birthday(),
                this.faker.internet().emailAddress(),
                this.faker.phoneNumber().cellPhone(),
                this.faker.job().title(),
                true,
                Collections.singleton((Skill) this.getRandomElement(this.skillRepository)),
                Collections.singleton((Subscription) this.getRandomElement(this.subscriptionRepository)),
                Collections.singleton((Qualification) this.getRandomElement(this.qualificationRepository)),
                Collections.singleton(this.roleRepository.findByName(ROLE_USER).get())
        );
    }

    private User generateUser(
            String firstName,
            String lastName,
            String password,
            Date birthday,
            String email,
            String mobile,
            String careerGoal,
            boolean validated,
            Set<Skill> skillSet,
            Set<Subscription> subscriptionSet,
            Set<Qualification> qualificationSet,
            Set<Role> roleSet) {
        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setBirthDate(birthday);
        user.setEmail(email);
        user.setMobileNumber(mobile);
        user.setCareerGoal(careerGoal);
        user.setValidated(validated);
        user.setSkillSet(skillSet);
        user.setSubscriptionSet(subscriptionSet);
        user.setQualificationSet(qualificationSet);
        user.setRoles(roleSet);
        return user;
    }

    // TODO add assertions
    // TODO fix coma in the date
    // TODO check to generate unique values with Faker
    // TODO try to export the value from h2 db to json
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
