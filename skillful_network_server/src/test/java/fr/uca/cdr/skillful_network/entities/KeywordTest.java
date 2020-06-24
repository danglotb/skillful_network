package fr.uca.cdr.skillful_network.entities;

import fr.uca.cdr.skillful_network.entities.user.Role;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class KeywordTest {

    public static final String NAME = "keyword";

    @Test
    public void testEquals() {

        /*
                - equals null -> false
                - equals not user -> false
                - equals itself -> true
                - equals user with same name -> true
                - equals user with different name -> false
         */

        final Keyword keyword = new Keyword(NAME);

        assertNotEquals(keyword, null);
        assertNotEquals(keyword, new Object());
        assertEquals(keyword, keyword);

        final Keyword keywordEquals = new Keyword(NAME);

        assertEquals(keyword, keywordEquals);

        final Keyword keywordNotEquals = new Keyword("");

        assertNotEquals(keyword, keywordNotEquals);
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

        final Keyword keyword = new Keyword(NAME);

        assertNotEquals(keyword.hashCode(), null);
        assertNotEquals(keyword.hashCode(), new Object().hashCode());
        assertEquals(keyword.hashCode(), keyword.hashCode());

        final Keyword keywordEquals = new Keyword(NAME);

        assertEquals(keyword.hashCode(), keywordEquals.hashCode());

        final Keyword keywordNotEquals = new Keyword("");

        assertNotEquals(keyword.hashCode(), keywordNotEquals.hashCode());
    }
}
