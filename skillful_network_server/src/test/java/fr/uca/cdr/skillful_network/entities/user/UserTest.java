package fr.uca.cdr.skillful_network.entities.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserTest {

    @Test
    public void testEquals() {

        /*
                - equals null -> false
                - equals not user -> false
                - equals itself -> true
                - equals user with same id, email AND mobile -> true
                - equals user with different id, email OR mobile -> false
         */

        final String EMAIL = "user@uca.fr";
        final String MOBILE = "0000000000";

        final User user = new User();
        user.setEmail(EMAIL);
        user.setMobileNumber(MOBILE);

        assertNotEquals(user, null);
        assertNotEquals(user, new Object());
        assertEquals(user, user);

        final User userEquals = new User();
        userEquals.setEmail(EMAIL);
        userEquals.setMobileNumber(MOBILE);

        assertEquals(user, userEquals);

        final User userNotEquals = new User();
        userNotEquals.setEmail(EMAIL);
        userNotEquals.setMobileNumber("");

        assertNotEquals(user, userNotEquals);

        userNotEquals.setEmail("");
        userNotEquals.setMobileNumber(MOBILE);

        assertNotEquals(user, userNotEquals);
    }

    @Test
    public void testHashcode() {

        /*
            We compare hashcode with others objects to verify the uniqueness
                - hashcode itself -> true
                - hashcode user with same id, email AND mobile -> true
                - hashcode user with different id, email OR mobile -> false
         */

        final String EMAIL = "user@uca.fr";
        final String MOBILE = "0000000000";

        final User user = new User();
        user.setEmail(EMAIL);
        user.setMobileNumber(MOBILE);

        assertEquals(user.hashCode(), user.hashCode());

        final User userEquals = new User();
        userEquals.setEmail(EMAIL);
        userEquals.setMobileNumber(MOBILE);

        assertEquals(user.hashCode(), userEquals.hashCode());

        final User userNotEquals = new User();
        userNotEquals.setEmail(EMAIL);
        userNotEquals.setMobileNumber("");

        assertNotEquals(user.hashCode(), userNotEquals.hashCode());
        assertNotEquals(userEquals.hashCode(), userNotEquals.hashCode());
    }
}
