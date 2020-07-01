package fr.uca.cdr.skillful_network.controllers.user;

import fr.uca.cdr.skillful_network.controller.user.RoleController;
import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.services.user.RoleService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        final HashMap<Role.Name,String> roles = new HashMap<>();
        roles.put(Role.Name.ROLE_USER, "User");
        Mockito.when(roleService.getInfoRoles()).thenReturn(roles);
    }

    @Test
    public void testGetRoles() throws Exception {
        mvc.perform(get("/roles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//                .andExpect(jsonPath("$[0].value").value("User"));

    }
}
