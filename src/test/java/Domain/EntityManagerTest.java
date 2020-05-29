package Domain;

import Domain.Game.League;
import Domain.Game.PointsPolicy;
import Domain.Game.SchedulingPolicy;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EntityManagerTest {

    @BeforeClass
    public static void setUpBeforeAll() { //Will be called only once
        SystemLoggerManager.disableLoggers(); // disable loggers in tests
    }


    @Test
    public void doesLeagueExistsUTest() {
        EntityManager.getInstance().removeLeagueByName("League Name");
        assertFalse(EntityManager.getInstance().doesLeagueExists("League Name"));
    }

    @Test
    public void addLeagueUTestUTest() {
        assertFalse(EntityManager.getInstance().doesLeagueExists("League Name"));
        EntityManager.getInstance().addLeague(new League("League Name"));
        assertTrue(EntityManager.getInstance().doesLeagueExists("League Name"));
        EntityManager.getInstance().removeLeagueByName("League Name");
    }

    @Test
    public void removeLeagueByNameUTest() {
        EntityManager.getInstance().addLeague(new League("League Name1"));
        EntityManager.getInstance().addLeague(new League("League Name2"));
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
        new Referee(systemUser, RefereeQualification.VAR_REFEREE);
        List<SystemUser> referees = EntityManager.getInstance().getReferees();
        assertFalse(referees.isEmpty());
        assertTrue(referees.size()==1);
        assertTrue(referees.get(0).isType(RoleTypes.REFEREE));
    }

    @Test
    public void doesPointsPolicyExistsITest() {
        assertFalse(EntityManager.getInstance().doesPointsPolicyExists(1,-1,0));
    }

    @Test
    public void addPointsPolicyITest() {
        assertFalse(EntityManager.getInstance().doesPointsPolicyExists(1,-1,0));
        EntityManager.getInstance().addPointsPolicy(new PointsPolicy(1,-1,0));
        assertTrue(EntityManager.getInstance().doesPointsPolicyExists(1,-1,0));
    }

    @Test
    public void addPointsPolicy2ITest() {
        assertFalse(EntityManager.getInstance().doesPointsPolicyExists(1,-1,0));
        PointsPolicy pointsPolicy = new PointsPolicy(1,-1,0);
        EntityManager.getInstance().addPointsPolicy(pointsPolicy);
        assertTrue(EntityManager.getInstance().getPointsPolicy(1,-1,0).equals(pointsPolicy));
        assertNull(EntityManager.getInstance().getPointsPolicy(1,-1,1));
    }
    @Test
    public void doesSchedulingPolicyExistsITest() {
        assertFalse(EntityManager.getInstance().doesSchedulingPolicyExists(2,2,2));
    }

    @Test
    public void addSchedulingPolicyITest() {
        assertFalse(EntityManager.getInstance().doesSchedulingPolicyExists(2,2,2));
        EntityManager.getInstance().addSchedulingPolicy(new SchedulingPolicy(2,2,2));
        assertTrue(EntityManager.getInstance().doesSchedulingPolicyExists(2,2,2));
    }

    @Test
    public void addSchedulingPolicy2ITest() {
        assertFalse(EntityManager.getInstance().doesSchedulingPolicyExists(2,2,2));
        SchedulingPolicy schedulingPolicy= new SchedulingPolicy(2,2,2);
        EntityManager.getInstance().addSchedulingPolicy(schedulingPolicy);
        assertTrue(EntityManager.getInstance().getSchedulingPolicy(2,2,2).equals(schedulingPolicy));
        assertNull(EntityManager.getInstance().getSchedulingPolicy(3,2,2));
    }


    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
    }
}