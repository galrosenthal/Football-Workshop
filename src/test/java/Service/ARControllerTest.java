package Service;

import Domain.EntityManager;
import Domain.Game.League;
import Domain.Users.AssociationRepresentative;
import Domain.Users.AssociationRepresentativeStub;
import Domain.Users.SystemUser;
import Domain.Users.SystemUserStub;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ARControllerTest {
    @BeforeClass
    public static void beforeClass() throws Exception {
        EntityManager.getInstance().clearAll();
    }

    @Before
    public void setUp() throws Exception {
        UIController.setIsTest(true);
    }

    @Test
    public void addLeagueUTest() {
        assertFalse(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 4)));
    }

    @Test
    public void addLeague2UTest() {
        AssociationRepresentativeStub.setSelector(0);
        assertFalse(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }

    @Test
    public void addLeague3UTest() {
        AssociationRepresentativeStub.setSelector(1);
        assertTrue(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }

    @Test
    public void addLeagueITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(5);
        //First league creation
        assertTrue(ARController.addLeague(systemUser));
        //Duplicated league creation
        assertFalse(ARController.addLeague(systemUser));
        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }


    /**
     * Creates a new system user with AssociationRepresentative role.
     *
     * @return - SystemUser - a new system user with AssociationRepresentative role
     **/
    private SystemUser getSystemUserAR() {
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new AssociationRepresentative(systemUser));
        return systemUser;
    }

    @Test
    public void addSeasonToLeagueUTest() {
        UIController.setSelector(0);
        assertFalse(ARController.addSeasonToLeague(new SystemUserStub("stubUsername", "stub", 4)));
    }

    @Test
    public void addSeasonToLeague2UTest() {
        UIController.setSelector(0);
        assertFalse(ARController.addSeasonToLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }

    @Test
    public void addSeasonToLeagueITest() {
        //Adding SystemUser for integration
        SystemUser systemUser = getSystemUserAR();
        assertFalse(ARController.addSeasonToLeague(systemUser));
    }

    @Test
    public void addSeasonToLeague2ITest() {
        //success no re-tries test

        System.out.println("addSeasonToLeague2ITest");

        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague("newLeagueName");

        UIController.setSelector(921); //0 , "2020/21","2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));

        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2020/21"));

        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }

    @Test
    public void addSeasonToLeague3ITest() {
        //duplicated creation attempt failure and then change input and success..

        System.out.println("addSeasonToLeague3ITest");

        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague("newLeagueName");
        UIController.setSelector(921); //0 , "2020/21", "2020/21", "2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));
        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2020/21"));

        assertTrue(ARController.addSeasonToLeague(systemUser));
        assertTrue(league.doesSeasonExists("2021/22"));

        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }

    @Test
    public void addSeasonToLeague4ITest() {
        //success after wrong format

        System.out.println("addSeasonToLeague4ITest");

        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague("newLeagueName");
        UIController.setSelector(924); //0 , "wrong Format","2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));

        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2021/22"));

        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }

    @After
    public void tearDown() throws Exception {
        //UIController.setSelector(0);
    }
}