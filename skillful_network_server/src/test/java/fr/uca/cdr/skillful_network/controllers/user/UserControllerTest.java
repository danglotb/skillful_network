package fr.uca.cdr.skillful_network.controllers.user;

import com.google.gson.Gson;
import fr.uca.cdr.skillful_network.controller.user.UserController;
import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.request.ConfirmationRegisterForm;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static fr.uca.cdr.skillful_network.entities.user.Role.Name.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        final User user = new User();
        user.setEmail("user.email@test.com");
        Mockito.when(userService.getAll())
                .thenReturn(Collections.singletonList(user));
    }

    @Test
    public void testGetUsers() throws Exception {
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value("0"));
    }

    @Test
    public void testUpdateConfirmationRegister() throws Exception {

        final Set<Role> roleSet =new HashSet<Role>();
        roleSet.add(new Role(ROLE_USER));
        final User userUpdate = new User();
        userUpdate.setFirstName("Pierre");
        userUpdate.setLastName("Afeu");
        userUpdate.setRoles(roleSet);

        final Set<String> roleSetUpdated =new HashSet<String>();
        roleSetUpdated.add("ROLE_USER");
        Mockito.when(userService.updateConfirmationRegister("Pierre","Afeu",roleSetUpdated)).thenReturn(userUpdate);

        mvc.perform(put("/users/confirmation")
                .content((new Gson().toJson(new ConfirmationRegisterForm(
                "Pierre",
                "Afeu",
                roleSetUpdated)
                )))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.roles.[0].name").value("ROLE_USER"))
                .andExpect(jsonPath("$.firstName").value("Pierre"))
                .andExpect(jsonPath("$.lastName").value("Afeu"));





     }


}