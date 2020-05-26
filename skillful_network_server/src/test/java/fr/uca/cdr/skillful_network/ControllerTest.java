package fr.uca.cdr.skillful_network;

import fr.uca.cdr.skillful_network.controller.user.UserController;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.SkillService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    // TODO should not have to do that...
    @MockBean
    private SkillService skillService;

    @Before
    public void setUp() {
        final User user = new User(1L, "name", "lastName");
        Mockito.when(userService.findAll())
                .thenReturn(Collections.singletonList(user));
    }

    @Test
    public void testGetUsers() throws Exception {
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value("1"));;
    }

}