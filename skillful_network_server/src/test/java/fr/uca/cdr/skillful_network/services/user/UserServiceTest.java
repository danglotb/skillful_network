package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.impl.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class TestConfigurationServiceTest {

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
    public AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {

        final Set<Role> roleSet = new HashSet<Role>();
        roleSet.add(new Role(ROLE_USER));
        final User user = new User();
        user.setEmail("user.email@test.com");
        user.setRoles(roleSet);
        user.setFirstName("Pierre");
        user.setLastName("Afeu");
        user.setValidated(Boolean.FALSE);
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(java.util.Optional.of(user));
        Mockito.when(authenticationService.getCurrentUser()).thenReturn(user);


    }

    @Test
    public void testGetUserById() {
        final long id = 0L;
        final User user = userService.getById(id);
        assertThat(user.getEmail())
                .isEqualTo("user.email@test.com");
    }

    @Test
    public void testUpdateConfirmationRegister(){
        final Set<String> roleSet =new HashSet<String>();
        roleSet.add("ROLE_COMPANY");
        User userInitial = authenticationService.getCurrentUser();
        assertThat(userInitial.getLastName())
                .isEqualTo("Afeu");
        assertThat(userInitial.getFirstName())
                .isEqualTo("Pierre");
        assertThat(!userInitial.isValidated());

        // Check if the userRepository.save(User user) method is used in userService.updateConfirmationRegister method
        final Boolean[] passingSave = {false};
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                passingSave[0] = true;
                return null;
            }
        }).when(userRepository).save(userInitial);
        //Should be false
        assertFalse(passingSave[0]);

        // Check if the authenticationService.manageRoles(Set<String> roles, User user) method is used in userService.updateConfirmationRegister method
        final Boolean[] passingManageRole = {false};
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                passingManageRole[0] = true;
                return null;
            }
        }).when(authenticationService).manageRoles(roleSet,userInitial);
        // Should be false
        assertFalse(passingManageRole[0]);

        final User userUpdated = userService.updateConfirmationRegister("Jacques","Jean",roleSet);

        // Should be true, normally we go through into userRepository.save() & authenticationService.manageRole() methods
        assertTrue(passingManageRole[0]);
        assertTrue(passingSave[0]);

        assertThat(userUpdated.getFirstName())
                .isEqualTo("Jacques");
        assertThat(userUpdated.getLastName())
                .isEqualTo("Jean");
        assertThat(userUpdated.isValidated());
    }

}
