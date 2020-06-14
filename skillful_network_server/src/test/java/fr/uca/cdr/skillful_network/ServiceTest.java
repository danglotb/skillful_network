package fr.uca.cdr.skillful_network;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.EmailService;
import fr.uca.cdr.skillful_network.services.impl.AuthenticationServiceImpl;
import fr.uca.cdr.skillful_network.services.impl.EmailServiceImpl;
import fr.uca.cdr.skillful_network.services.impl.user.QualificationServiceImpl;
import fr.uca.cdr.skillful_network.services.impl.user.RoleServiceImpl;
import fr.uca.cdr.skillful_network.services.impl.user.SkillServiceImpl;
import fr.uca.cdr.skillful_network.services.impl.user.SubscriptionServiceImpl;
import fr.uca.cdr.skillful_network.services.impl.user.UserServiceImpl;
import fr.uca.cdr.skillful_network.services.user.QualificationService;
import fr.uca.cdr.skillful_network.services.user.RoleService;
import fr.uca.cdr.skillful_network.services.user.SkillService;
import fr.uca.cdr.skillful_network.services.user.SubscriptionService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ServiceTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public EmailService emailService() {
            return null;
        }
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public RoleService roleService() {
            return null;
        }
        @Bean
        public AuthenticationManager authenticationManager() {
            return null;
        }
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public AuthenticationService authenticationService() {
            return new AuthenticationServiceImpl();
        }

        @Bean
        public SkillService skillService() {
            return new SkillServiceImpl(null);
        }

        @Bean
        public QualificationService qualificationService() {
            return new QualificationServiceImpl(null);
        }

        @Bean
        public SubscriptionService subscriptionService() {
            return new SubscriptionServiceImpl(null);
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
