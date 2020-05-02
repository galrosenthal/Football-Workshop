package Service;

import Domain.EntityManager;
import Domain.Game.League;
import Domain.Users.*;
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
        //duplicated creation attempt failure and then change input and success.
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
        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague("newLeagueName");
        UIController.setSelector(924); //0 , "wrong Format","2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));

        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2021/22"));

        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }

    @Test
    public void addRefereeUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee2UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",9311));
        AssociationRepresentativeStub.setSelector(0);
        UIController.setSelector(9311);

        assertFalse(ARController.addReferee(systemUser));
    }
    @Test
    public void addReferee3UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",9311));
        AssociationRepresentativeStub.setSelector(0);
        UIController.setSelector(9312);

        assertFalse(ARController.addReferee(systemUser));
    }
    @Test
    public void addReferee4UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",9311));
        AssociationRepresentativeStub.setSelector(1);
        UIController.setSelector(9311);

        assertTrue(ARController.addReferee(systemUser));
    }
    @Test
    public void addReferee5UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",9311));
        AssociationRepresentativeStub.setSelector(1);
        UIController.setSelector(9312);

        assertTrue(ARController.addReferee(systemUser));
    }
    @Test
    public void addRefereeITest() {
        SystemUser systemUser = getSystemUserAR();

        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",93131));
        UIController.setSelector(9311);

        assertTrue(ARController.addReferee(systemUser));
    }
    @Test
    public void addReferee2ITest() {
        SystemUser systemUser = getSystemUserAR();

        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",93132));
        UIController.setSelector(9311);

        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee3ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        UIController.setSelector(9311);

        assertTrue(ARController.addReferee(systemUser));

        assertNotNull(refereeUser);
        assertTrue(refereeUser.isType(RoleTypes.REFEREE));
        Referee refRole = (Referee)refereeUser.getRole(RoleTypes.REFEREE);
        assertTrue(refRole.getTraining().equals("VAR"));
    }
    @Test
    public void addReferee4ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        UIController.setSelector(9312);

        assertTrue(ARController.addReferee(systemUser));

        assertNotNull(refereeUser);
        assertTrue(refereeUser.isType(RoleTypes.REFEREE));
        Referee refRole = (Referee)refereeUser.getRole(RoleTypes.REFEREE);
        assertTrue(refRole.getTraining().equals("VAR"));
    }
    @Test
    public void addReferee5ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser,"training");

        UIController.setSelector(9311);
        //The user is already a referee
        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void removeRefereeUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee2UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);

        assertFalse(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee3UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        AssociationRepresentativeStub.setSelector(0);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",9311));
        UIController.setSelector(9321);

        assertFalse(ARController.removeReferee(systemUser));
    }
    @Test
    public void removeReferee4UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        AssociationRepresentativeStub.setSelector(1);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",9311));
        UIController.setSelector(9321);

        assertTrue(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeRefereeITest() {
        SystemUser systemUser = getSystemUserAR();

        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName",9311));
        UIController.setSelector(9321);

        assertTrue(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee2ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");

        UIController.setSelector(9321);
        //There are no referees
        assertFalse(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee3ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser, "VAR");
        UIController.setSelector(9321);
        //There are no referees
        assertTrue(ARController.removeReferee(systemUser));
        assertFalse(refereeUser.isType(RoleTypes.REFEREE));
    }




    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
    }
}