package fr.uca.cdr.skillful_network;

import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.impl.user.UserServiceImpl;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ServiceTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
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
        final User user = new User(1L, "name", "lastName");
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(java.util.Optional.of(user));
    }

    @Test
    public void testGetUserById() {
        final long id = 1L;
        final User user = userService.getUserById(id);
        assertThat(user.getLastName())
                .isEqualTo("lastName");
        assertThat(user.getFirstName())
                .isEqualTo("name");
        assertThat(user.getId())
                .isEqualTo(id);
    }

}
