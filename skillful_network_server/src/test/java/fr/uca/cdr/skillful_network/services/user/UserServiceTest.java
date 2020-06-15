package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.impl.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class TestConfigurationServiceTest {
        @MockBean
        private AuthenticationService authenticationService;
        @MockBean
        private QualificationService qualificationService;
        @MockBean
        private SkillService skillService;
        @MockBean
        private SubscriptionService subscriptionService;
        @MockBean
        private BCryptPasswordEncoder passwordEncoder;

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        final User user = new User();
        user.setEmail("user.email@test.com");
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(java.util.Optional.of(user));
    }

    @Test
    public void testGetUserById() {
        final long id = 0L;
        final User user = userService.getById(id);
        assertThat(user.getEmail())
                .isEqualTo("user.email@test.com");
    }

}
