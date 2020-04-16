package Domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class EntityManagerTest {


    @Test
    public void doesLeagueExistsUTest() {
        assertFalse(EntityManager.getInstance().doesLeagueExists("League Name"));
    }

    @Test
    public void addLeagueUTestUTest() {
        assertFalse(EntityManager.getInstance().doesLeagueExists("League Name"));
        EntityManager.getInstance().addLeague("League Name");
        assertTrue(EntityManager.getInstance().doesLeagueExists("League Name"));
    }
}