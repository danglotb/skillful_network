package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.FollowStateTracker;
import fr.uca.cdr.skillful_network.entities.user.Notification;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.FollowStateTrackerRepository;
import fr.uca.cdr.skillful_network.repositories.user.NotificationRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.impl.user.FollowStateTrackerServiceImpl;
import fr.uca.cdr.skillful_network.services.impl.user.UserServiceImpl;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class FollowStateTrackerServiceTest {

    @TestConfiguration
    static class TestConfigurationServiceTest {
        @Bean
        public FollowStateTrackerService followStateTrackerService() {
            return new FollowStateTrackerServiceImpl();
        }
    }

    @Autowired
    private FollowStateTrackerService followStateTrackerService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserService userService;

    @MockBean
    private FollowStateTrackerRepository followStateTrackerRepository;

    @MockBean
    private NotificationRepository notificationRepository;


    @Before
    public void setUp(){

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Follower methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*public Optional<List<FollowStateTracker>> getFSTByFollowerID(Long followerID) {
        return Optional.of( fstRepository.findAllByFollower(userService.getById(followerID)) );
    }*/

    @Test
    public void testGetFSTByFollowerID(){
        final Boolean[] passingMethods = {false, false};
        final int PASSING_FIND_ALL= 0;
        final int  GET_BY_ID = 1;
        final User userFollowed = new User();
        final User userFollower = new User();
        final List<FollowStateTracker> fstList = new ArrayList<>();
        final Set<FollowStateTracker> fstSet = new HashSet<>();

        FollowStateTracker fst = new FollowStateTracker(userFollowed,userFollower);
        fstList.add(fst);
        fstSet.add(fst);

        userFollower.setFollowerSet(fstSet);
        userFollowed.setFollowableSet(fstSet);

        /*Mockito.when(followStateTrackerRepository.findAllByFollower(userFollower))
                .thenReturn(fstList);
        Mockito.when(userService.getById(userFollower.getId())).thenReturn(userFollower);*/

        // Check if the followStateTrackerRepository.findAllByFollower(User user) method is used in followStateTrackerService.getFSTByFollowerID(Long followerID) method

        Mockito.doAnswer(new Answer<List<FollowStateTracker>>() {
            @Override
            public List<FollowStateTracker> answer(InvocationOnMock invocationOnMock) throws Throwable {
                passingMethods[PASSING_FIND_ALL] = true;
                return fstList;
            }
        }).when(followStateTrackerRepository).findAllByFollower(userFollower);
        //Should be false
        assertFalse(passingMethods[PASSING_FIND_ALL]);

        // Check if the userService.getById(User user) method is used in followStateTrackerService.getFSTByFollowerID(Long followerID) method
        Mockito.doAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                passingMethods[GET_BY_ID] = true;
                return userFollower;
            }
        }).when(userService).getById(userFollower.getId());
        //Should be false
        assertFalse(passingMethods[GET_BY_ID]);

        Long id = 0L;
        final List<FollowStateTracker> fstResponse = followStateTrackerService.getFSTByFollowerID(id).get();

        // Should be true, normally we go through into followStateRepository.findAllByFollower() & userService.getById() methods
        assertTrue(passingMethods[PASSING_FIND_ALL]);
        assertTrue(passingMethods[GET_BY_ID]);

        assertThat(userFollower.getFollowerSet().toArray()[0]).isEqualTo(fstResponse.get(0));
        assertThat(fstResponse.get(0).getFollowed()).isEqualTo(userFollowed);
    }

    /* public Optional<List<User>> getAllFollowersByFollowableID(Long followableID) {
        return Optional.of(
                fstRepository.findAllByFollowed(userService.getById(followableID)).stream()
                        .map(FollowStateTracker::getFollower)
                        .collect(Collectors.toList())
        );
    }*/
    @Test
    public void testGetAllFollowersByFollowableID() {

         final Boolean[] passingMethods = {false, false};
         final int PASSING_FIND_ALL = 0;
         final int  GET_BY_ID = 1;

         final User userFollowable = new User();
         userFollowable.setLastName("Pierre");
         final User userWhoFollowUserFollowable = new User();
         userWhoFollowUserFollowable.setLastName("Afeu");

         final List<FollowStateTracker> fstList = new ArrayList<>();
         final Set<FollowStateTracker> fstSet = new HashSet<>();

        final FollowStateTracker fst = new FollowStateTracker(userFollowable, userWhoFollowUserFollowable);
         fstList.add(fst);
         fstSet.add(fst);

         userFollowable.setFollowableSet(fstSet);
         userWhoFollowUserFollowable.setFollowerSet(fstSet);


         // Check if the followStateTrackerRepository.findAllByFollowed(User user) method is used in followStateTrackerService.getAllFollowersByFollowableID(Long followableID) method

         Mockito.doAnswer(new Answer<List<FollowStateTracker>>() {
             @Override
             public List<FollowStateTracker> answer(InvocationOnMock invocationOnMock) throws Throwable {
                 passingMethods[PASSING_FIND_ALL] = true;
                 return fstList;
             }
         }).when(followStateTrackerRepository).findAllByFollowed(userFollowable);
         //Should be false
         assertFalse(passingMethods[PASSING_FIND_ALL]);

         // Check if the userService.getById(User user) method is used in followStateTrackerService.getAllFollowersByFollowableID(Long followableID) method
         Mockito.doAnswer(new Answer<User>() {
             @Override
             public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                 passingMethods[GET_BY_ID] = true;
                 return userFollowable;
             }
         }).when(userService).getById(userFollowable.getId());
         //Should be false
         assertFalse(passingMethods[GET_BY_ID]);

         Long id = 0L;
         final List<User> userListWhoFollowFollowable = followStateTrackerService.getAllFollowersByFollowableID(id).get();
         final FollowStateTracker fstResponse = (FollowStateTracker)(userListWhoFollowFollowable.get(0).getFollowerSet().toArray()[0]);

         // Should be true, normally we go through into followStateRepository.findAllByFollowed() & userService.getById() methods
         assertTrue(passingMethods[PASSING_FIND_ALL]);
         assertTrue(passingMethods[GET_BY_ID]);

         assertThat(userListWhoFollowFollowable.get(0).getLastName()).isEqualTo("Afeu");
         assertThat(userListWhoFollowFollowable.size()).isEqualTo(1);
         assertThat(userListWhoFollowFollowable.get(0).getFollowerSet().toArray().length).isEqualTo(1);
         assertThat(fstResponse.getFollowed().getLastName()).isEqualTo("Pierre");
     }

   /* @Override
    public boolean follow(Long followerID, Long followableID) {
        //  System.out.println("FollowStateTrackerServiceImpl.follow("+followerID+","+followableID+")");
        if ( followableID == followerID ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de se suivre soi même!");
        }

        // check if FST link already exists
        User follower = userService.getById(followerID);
        User followable = userService.getById(followableID);
        if ( fstRepository.findByFollowerAndFollowed(follower, followable) != null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi est déjà activé.");
        }

        // persist
        return ( fstRepository.save(new FollowStateTracker(followable, follower)) != null) ;
    }
    }*/

    /*public void unfollowByFollowedID(Long followedID) {
        //  System.out.println("FollowStateTrackerServiceImpl.unfollowByFollowedID("+followedID+")");
        // get followed user
        User followed = userService.getById(followedID);
        if ( followed == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun User avec l'id: " + followedID);
        }

        // get FST
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                this.authenticationService.getCurrentUser(), followed);
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }

        // delete process
        this.unfollow(fst);
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

   /* public Optional<List<FollowStateTracker>> getAllFSTByFollowable() {
        return this.getAllFSTByFollowableID(authenticationService.getCurrentUser().getId());
    }*/

   /* public Optional<List<User>> getAllFollowedByFollower() {
        return this.getAllFollowedByFollowerID(authenticationService.getCurrentUser().getId());
    }*/

   /* public void banFollower(Long followerID) {
        this.banFollower(authenticationService.getCurrentUser().getId(), followerID);
    }*/
}
