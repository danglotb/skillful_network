package fr.uca.cdr.skillful_network.repositories.user;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testController() {
        entityManager.persistAndFlush(createUser());
        assertThat(userRepository.findAll()).isNotEmpty();
    }

    private User createUser() {
        final User user = new User();
        user.setEmail("user.name@test.com");
        user.setTemporaryCodeExpirationDate(LocalDateTime.now().plus(24, ChronoUnit.HOURS));
        final String randomCodeEncrypt = "password";
        user.setPassword(randomCodeEncrypt);
        user.setRoles(Collections.emptySet());
        return user;
    }
}
