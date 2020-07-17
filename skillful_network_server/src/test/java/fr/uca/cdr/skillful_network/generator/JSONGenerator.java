package fr.uca.cdr.skillful_network.generator;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fr.uca.cdr.skillful_network.entities.Keyword;
import fr.uca.cdr.skillful_network.entities.application.Application;
import fr.uca.cdr.skillful_network.entities.application.JobApplication;
import fr.uca.cdr.skillful_network.entities.application.JobOffer;
import fr.uca.cdr.skillful_network.entities.post.Comment;
import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.entities.application.Training;
import fr.uca.cdr.skillful_network.entities.application.TrainingApplication;
import fr.uca.cdr.skillful_network.entities.user.*;
import fr.uca.cdr.skillful_network.repositories.KeywordRepository;
import fr.uca.cdr.skillful_network.repositories.application.JobApplicationRepository;
import fr.uca.cdr.skillful_network.repositories.application.JobOfferRepository;
import fr.uca.cdr.skillful_network.repositories.post.CommentRepository;
import fr.uca.cdr.skillful_network.repositories.post.PostRepository;
import fr.uca.cdr.skillful_network.repositories.application.TrainingApplicationRepository;
import fr.uca.cdr.skillful_network.repositories.application.TrainingRepository;
import fr.uca.cdr.skillful_network.repositories.user.*;
import fr.uca.cdr.skillful_network.tools.json.JSONLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_COMPANY;
import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_TRAINING_ORGANIZATION;
import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_USER;
import static org.junit.Assert.fail;

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
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowStateTrackerRepository fstRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private static final long SEED_RANDOM = 23L;

    private final Random RANDOM = new Random(SEED_RANDOM);

    private final Faker FAKER = new Faker(Locale.FRANCE, RANDOM);

    private static final String PREFIX_PATH = "src/main/resources/data/";

    private static final String EXTENSION_JSON = ".json";

    private static final String NEW_LINE = System.getProperty("line.properties");

    @SuppressWarnings("all")
    private void saveTo(String name, Class<?> clazz, JpaRepository<?, Long> repository) {
        boolean jsonFilesHasBeenUpdated = true;
        final JSONLoader jsonLoader = new JSONLoader(PREFIX_PATH + name + EXTENSION_JSON, clazz, repository);
        if (new File(PREFIX_PATH + name + EXTENSION_JSON).exists()) {
            try (final JsonReader reader = new JsonReader(new FileReader(
                    new File(PREFIX_PATH + name + EXTENSION_JSON)
            ))) {
                final Gson gson = jsonLoader.getGson();
                List elements =  Arrays.asList(gson.fromJson(reader, clazz));
                jsonFilesHasBeenUpdated = repository.findAll().stream().allMatch(elements::contains);
            } catch (Exception e) {
//                throw new RuntimeException(e);
            }
        }
        jsonLoader.save(repository.findAll());
        if (!jsonFilesHasBeenUpdated) {
            fail(
                    "[CI] This assertion is meant to be run in in ci." + NEW_LINE +
                    "When modifiying the generation of json files, you must also update the json files" + NEW_LINE +
                            "Please, push the modification of the json files, or remove the changes in the generator."
            );
        }
    }

    private Object getRandomElement(JpaRepository<?, Long> repository) {
        final List<?> elements = repository.findAll();
        if (elements.isEmpty()) {
            fail("Should have at least one element in " + repository.toString());
        }
        return elements.get(RANDOM.nextInt(elements.size()));
    }

    private static final int NB_PERKS = 10;

    private <T extends Perk> boolean add(Set<T> perks, T perk) {
        if (perks.stream().anyMatch(p -> p.getName().equals(perk.getName()))) {
            return false;
        } else {
            return perks.add(perk);
        }
    }

    private <T extends Perk> void  generatePerks(Set<T> perks,
                               String name,
                               Class<?> perkClassArray,
                               int nbPerk,
                               JpaRepository<T, Long> perkRepository,
                               Function<Faker, T> create) {
        while (perks.size() < nbPerk) {
            final T perk = create.apply(this.FAKER);
            if (this.add(perks, perk)) {
                this.entityManager.persistAndFlush(perk);
            }
        }
        this.saveTo(name, perkClassArray, perkRepository);
    }

    private Function<Faker, Skill> createSkill = (faker) -> new Skill(faker.job().keySkills());

    private Function<Faker, Qualification> createQualification = (faker) -> new Qualification(faker.educator().course());

    private Function<Faker, Subscription> createSubscription = (faker) -> new Subscription(faker.programmingLanguage().name());

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

    private Function<Faker, Keyword> createKeyword = (faker) -> new Keyword(
            RANDOM.nextBoolean() ?
                    faker.job().keySkills() : faker.programmingLanguage().name()
    );

    private void generateKeywords() {
        final Set<Keyword> keywords = new HashSet<>();
        while (keywords.size() < NB_KEYWORDS) {
            final Keyword keyword = this.createKeyword.apply(this.FAKER);
            if (keywords.stream().noneMatch(k -> k.getName().equals(keyword.getName()))) {
                keywords.add(keyword);
                this.entityManager.persistAndFlush(keyword);
            }
        }
        this.saveTo("keywords", Keyword[].class, this.keywordRepository);
    }

    private void generateJobOffers() {
        final JobOffer jobOffer1 = new JobOffer(
                this.FAKER.job().title(),
                this.FAKER.company().name(),
                this.FAKER.company().catchPhrase(),
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
                this.FAKER.educator().course(),
                this.FAKER.educator().university(),
                this.FAKER.educator().campus(),
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
                this.FAKER.name().firstName(),
                this.FAKER.name().lastName(),
                "Qwerty123",
                this.FAKER.date().birthday(),
                "user@uca.fr",
                this.FAKER.phoneNumber().cellPhone(),
                this.FAKER.job().title(),
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
                this.FAKER.name().firstName(),
                this.FAKER.name().lastName(),
                this.FAKER.internet().password(8, 10, true, true, true),
                this.FAKER.date().birthday(),
                this.FAKER.internet().emailAddress(),
                this.FAKER.phoneNumber().cellPhone(),
                this.FAKER.job().title(),
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
  
    private void generatePosts() {
        this.entityManager.persistAndFlush(new Post(this.FAKER.chuckNorris().fact(), this.FAKER.date().birthday(0, 1), (User) this.getRandomElement(userRepository)));
        this.entityManager.persistAndFlush(new Post(this.FAKER.chuckNorris().fact(), this.FAKER.date().birthday(0, 1), (User) this.getRandomElement(userRepository)));
        this.entityManager.persistAndFlush(new Post(this.FAKER.chuckNorris().fact(), this.FAKER.date().birthday(0, 1), (User) this.getRandomElement(userRepository)));
        this.saveTo("posts", Post.class, this.postRepository);
    }

    private void generateComments() {
        final Date today = new Date();
        for (Post post : this.postRepository.findAll()) {
            final int nbCommentForPost = 1 + this.RANDOM.nextInt(5);
            for (int i = 0; i < nbCommentForPost; i++) {
                final Comment comment = new Comment(
                        this.FAKER.gameOfThrones().quote(),
                        this.FAKER.date().between(post.getDateOfPost(), today),
                        (User) this.getRandomElement(userRepository)
                );
                comment.setPost(post);
                this.entityManager.persistAndFlush(comment);
            }
        }
        this.saveTo("comments", Comment.class, this.commentRepository);
    }

    private void generateFollowers() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) return;
        User first = users.get(0);
        User last = users.get(users.size() - 1);
        users.forEach( user -> {
            if ( user.getId() != first.getId()) {
                FollowStateTracker fst = new FollowStateTracker(first, user);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                entityManager.persistAndFlush(fst);
            }
            if ( user.getId() != last.getId()) {
                FollowStateTracker fst = new FollowStateTracker(last, user);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                entityManager.persistAndFlush(fst);
            }
        });
//        }
//        FollowStateTracker fst = new FollowStateTracker( userRepository.getOne((long) NB_USERS_TO_GENERATE), userRepository.getOne((long) 1));
//        entityManager.persistAndFlush(fst);
        this.saveTo("followers", FollowStateTracker[].class, this.fstRepository);
    }

    // TODO add assertions
    // TODO fix coma in the date
    // TODO try to export the value from h2 db to json
    @Test
    public void generateJSON() {
        this.generatePerks(new HashSet<>(),  "skills", Skill[].class, NB_PERKS, this.skillRepository, this.createSkill);
        this.generatePerks(new HashSet<>(), "qualifications", Qualification[].class, NB_PERKS, this.qualificationRepository, this.createQualification);
        this.generatePerks(new HashSet<>(), "subscriptions", Subscription[].class, NB_PERKS, this.subscriptionRepository, this.createSubscription);
        this.generateRoles();
        this.generateKeywords();
        this.generateJobOffers();
        this.generateTrainings();
        this.generateUsers();
        this.generatePosts();
        this.generateComments();
        this.generateFollowers();
    }
}
