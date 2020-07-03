package fr.uca.cdr.skillful_network.entities.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoleTest {

    @Test
    public void testEquals() {

         /*
                - equals null -> false
                - equals not user -> false
                - equals itself -> true
                - equals user with same name -> true
                - equals user with different name -> false
         */

        final Role role = new Role(Role.Name.ROLE_USER);

        assertNotEquals(role, null);
        assertNotEquals(role, new Object());
        assertEquals(role, role);

        final Role roleEquals = new Role(Role.Name.ROLE_USER);

        assertEquals(role, roleEquals);

        final Role roleNotEquals = new Role(Role.Name.ROLE_COMPANY);
        final Role roleNotEquals1 = new Role(Role.Name.ROLE_TRAINING_ORGANIZATION);

        assertNotEquals(roleNotEquals, roleNotEquals1);
    }

    @Test
    public void testHashcode() {

        /*
            We compare hashcode with others objects to verify the uniqueness
                - hashcode null -> false
                - hashcode not role -> false
                - hashcode itself -> true
                - hashcode role with same id AND name -> true
                - hashcode user with different id OR name -> false
         */

        final Role role = new Role(Role.Name.ROLE_USER);

        assertNotEquals(role.hashCode(), null);
        assertNotEquals(role.hashCode(), new Object().hashCode());
        assertEquals(role.hashCode(), role.hashCode());

        final Role roleEquals = new Role(Role.Name.ROLE_USER);

        assertEquals(role.hashCode(), roleEquals.hashCode());

        final Role roleNotEquals = new Role(Role.Name.ROLE_COMPANY);

        assertNotEquals(role.hashCode(), roleNotEquals.hashCode());
    }

    @Test
    public void testGetNamesAndDescriptions() {

        final Role.Name[]roles = Role.Name.values();
        assertTrue(roles.length!=0);
        assertNotEquals(roles, null);
        for( Role.Name n : Role.Name.values()) {
            assertNotEquals(n, null);
            assertNotEquals(n.getDescription(), null);
        }
    }
}
