package Domain;

import Domain.Users.SystemUserStub;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityManagerTest {


    @Test
    public void doesLeagueExistsUTest() {
        EntityManager.getInstance().removeLeagueByName("League Name");
        assertFalse(EntityManager.getInstance().doesLeagueExists("League Name"));
    }

    @Test
    public void addLeagueUTestUTest() {
        assertFalse(EntityManager.getInstance().doesLeagueExists("League Name"));
        EntityManager.getInstance().addLeague("League Name");
        assertTrue(EntityManager.getInstance().doesLeagueExists("League Name"));
        EntityManager.getInstance().removeLeagueByName("League Name");
    }

    @Test
    public void removeLeagueByNameUTest() {
        EntityManager.getInstance().addLeague("League Name1");
        EntityManager.getInstance().addLeague("League Name2");
        assertTrue(EntityManager.getInstance().removeLeagueByName("League Name2"));
        assertFalse(EntityManager.getInstance().doesLeagueExists("League Name2"));
        assertFalse(EntityManager.getInstance().removeLeagueByName("League Name2"));
        assertFalse(EntityManager.getInstance().removeLeagueByName("League Name2"));
    }

    /*@Test
    public void addUserUTest() {
        assertTrue(EntityManager.getInstance().addUser(new SystemUserStub("this username definitely unique","ss",0)));
    }*/
}