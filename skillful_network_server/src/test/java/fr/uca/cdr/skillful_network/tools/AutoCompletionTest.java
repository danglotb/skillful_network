package fr.uca.cdr.skillful_network.tools;

import fr.uca.cdr.skillful_network.entities.user.Perk;
import fr.uca.cdr.skillful_network.entities.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AutoCompletionTest {

    private static class PerkImpl extends Perk {
        public PerkImpl(final String name, final int nbUser) {
            this.name = name;
            for (int i = 0; i < nbUser; i++) {
                final User user = new User();
                user.setEmail(i + "@skillful.com");
                this.userList.add(user);
            }
        }
    }

    private AutoCompletion sut;

    @Before
    public void setUp() throws Exception {
        this.sut = new AutoCompletion();
    }

    @Test
    public void testFindCandidatesEmptyMatch() {

        /*
            When giving an empty match, the AutoCompletion must returns all the candidates sorted according to
            the number of time they appear, the most first.
         */

        final List<Perk> perks = new ArrayList<>();
        final String nameOne = "1";
        final String nameFive = "5";
        perks.add(new PerkImpl(nameOne, 1));
        perks.add(new PerkImpl(nameFive, 5));
        assertEquals(2, perks.size());
        final String match = "";

        final List<Perk> candidates = this.sut.findCandidates(perks, match);

        assertEquals(2, candidates.size());
        assertEquals(nameFive, candidates.get(0).getName());
        assertEquals(nameOne, candidates.get(1).getName());
    }

    @Test
    public void testFindCandidatesWithSpecifiedMatch() {

        /*
            When giving a specific match, the AutoCompletion should return only perks that match the given match.
            This list of perks is sorted according to the number of time they appear, the most first.
         */

        final List<Perk> perks = new ArrayList<>();
        final String nameOne = "x1";
        final String nameTen = "x10";
        final String nameThree = "y3";
        final String nameFive = "y5";
        perks.add(new PerkImpl(nameOne, 1));
        perks.add(new PerkImpl(nameThree, 3));
        perks.add(new PerkImpl(nameFive, 5));
        perks.add(new PerkImpl(nameTen, 10));
        assertEquals(4, perks.size());
        final String matchX = "x";

        List<Perk> candidates = this.sut.findCandidates(perks, matchX);
        assertEquals(2, candidates.size());
        assertEquals(nameTen, candidates.get(0).getName());
        assertEquals(nameOne, candidates.get(1).getName());

        final String matchY = "y";

        candidates = this.sut.findCandidates(perks, matchY);
        assertEquals(2, candidates.size());
        assertEquals(nameFive, candidates.get(0).getName());
        assertEquals(nameThree, candidates.get(1).getName());
    }

    @Test
    public void testFindCandidatesWhenNbOfPerkIsEquals() {

        /*
            When candidates have the same number of occurrences, the AutoCompletion sorts alphabetically.
         */

        final List<Perk> perks = new ArrayList<>();
        final String nameOneA = "xa1";
        final String nameOneB = "xb1";
        perks.add(new PerkImpl(nameOneB, 1));
        perks.add(new PerkImpl(nameOneA, 1));
        assertEquals(2, perks.size());
        final String matchX = "x";

        List<Perk> candidates = this.sut.findCandidates(perks, matchX);
        assertEquals(2, candidates.size());
        assertEquals(nameOneA, candidates.get(0).getName());
        assertEquals(nameOneB, candidates.get(1).getName());
    }

    @Test
    public void testFindCandidatesWithNullMatch() {

        /*
            When giving a null match, the AutoCompletion must returns all the candidates sorted according to
            the number of time they appear, the most first.
            Equivalent than giving an empty match
         */

        final List<Perk> perks = new ArrayList<>();
        final String nameOne = "1";
        final String nameFive = "5";
        perks.add(new PerkImpl(nameOne, 1));
        perks.add(new PerkImpl(nameFive, 5));
        assertEquals(2, perks.size());
        final String match = null;

        final List<Perk> candidates = this.sut.findCandidates(perks, match);

        assertEquals(2, candidates.size());
        assertEquals(nameFive, candidates.get(0).getName());
        assertEquals(nameOne, candidates.get(1).getName());
    }
}
