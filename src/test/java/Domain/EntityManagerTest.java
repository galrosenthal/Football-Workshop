package Domain;

import Domain.Users.Referee;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.SystemUserStub;
import org.junit.After;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void getRefereesUTest() {
        assertTrue(EntityManager.getInstance().getReferees().isEmpty());
        EntityManager.getInstance().addUser(new SystemUserStub("stubUsername","name",93121));
        assertFalse(EntityManager.getInstance().getReferees().isEmpty());
    }
    @Test
    public void getRefereesITest() {
        assertTrue(EntityManager.getInstance().getReferees().isEmpty());
        SystemUser systemUser = new SystemUser("stubUsername","name");
        new Referee(systemUser, "Training");
        List<SystemUser> referees = EntityManager.getInstance().getReferees();
        assertFalse(referees.isEmpty());
        assertTrue(referees.size()==1);
        assertTrue(referees.get(0).isType(RoleTypes.REFEREE));
    }

    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
    }
}