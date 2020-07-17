package fr.uca.cdr.skillful_network.services.post;

import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.post.PostRepository;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.impl.post.PostServiceImpl;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class PostServiceTest {

    public static final String THIS_A_BODY = "this a body";

    @TestConfiguration
    static class TestConfigurationServiceTest {
        @Bean
        public PostService postService() {
            return new PostServiceImpl();
        }
    }

    @Autowired
    private PostService postService;

    @MockBean
    public AuthenticationService authenticationService;

    @MockBean
    private FollowStateTrackerService fstService;

    @MockBean
    private UserService userService;

    @MockBean
    private PostRepository postRepository;

    @Before
    public void setUp() throws Exception {
        final User user = new User();
        Mockito.when(authenticationService.getCurrentUser()).thenReturn(user);
        final Post post = new Post(THIS_A_BODY, new Date(), user);
        Mockito.when(postRepository.findById(0L)).thenReturn(java.util.Optional.of(post));
    }

    @Test
    public void testCreatePost() {

        /*
            Test of the creation of a post
                This newly created post should have:
                    - 0 as id (it is the first one)
                    - the given string as body
                    - the date of creation should be the same as today
                    - the user should be the current user (mocked)
                    - at the creation, the comments should be empty
         */

        final Post post = this.postService.createPost(THIS_A_BODY);

        assertThat(post.getPostbodyText()).isEqualTo(THIS_A_BODY);
        assertThat(post.getId()).isEqualTo(0L);
        assertThat(post.getDateOfPost().getDate()).isEqualTo(new Date().getDate());
        assertThat(post.getComments()).isEmpty();
        assertThat(post.getUser()).isEqualTo(this.authenticationService.getCurrentUser());
    }

    @Test
    public void testUpdatePost() {

        /*
            Test the update of a post.
                The only modification are:
                    - the body change
                    - the date change
                Otherwise, the user, the id, the list of comments remain the same.
         */

        final Post post = this.postRepository.findById(0L).get();
        final Post update = this.postService.update(0L, "", new Date());

        assertThat(update.getPostbodyText()).isEqualTo("");
        assertThat(update.getDateOfPost().getTime()).isGreaterThanOrEqualTo(post.getDateOfPost().getTime());

        assertThat(update.getUser()).isEqualTo(post.getUser());
        assertThat(update.getId()).isEqualTo(post.getId());
        assertThat(update.getComments()).isEqualTo(post.getComments());
    }
}
