package Domain.Users;

import Domain.EntityManager;
import Domain.Exceptions.RoleExistsAlreadyException;
import Domain.Game.League;
import Domain.Game.Season;
import org.junit.After;
import org.junit.Test;


import static org.junit.Assert.*;

public class AssociationRepresentativeTest {

    private AssociationRepresentative aR;

    @Test
    public void testAddLeagueITest() throws Exception {
        EntityManager.getInstance().removeLeagueByName("leagueName");
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        assertTrue(aR.addLeague("leagueName"));
    }


    @Test(expected = Exception.class)
    public void testAddLeague2ITest() throws Exception {
        EntityManager.getInstance().removeLeagueByName("leagueName");
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 0));
        aR.addLeague("leagueName");
        aR.addLeague("leagueName");
    }

    @Test(expected = RoleExistsAlreadyException.class)
    public void addRefereeUTest() throws RoleExistsAlreadyException {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        aR.addReferee(new SystemUserStub("stubUsername", "stub", 9311), "VAR");
    }

    @Test
    public void addReferee2UTest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 9312);
        try {
            aR.addReferee(newRefereeUser, "VAR");
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }

    @Test
    public void addRefereeITest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 93131);
        try {
            assertTrue(aR.addReferee(newRefereeUser, "VAR"));
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }


    @Test
    public void addReferee2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        try {
            assertTrue(aR.addReferee(newRefereeUser, "VAR"));
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }

    @Test(expected = RoleExistsAlreadyException.class)
    public void addReferee3ITest() throws RoleExistsAlreadyException {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        new Referee(newRefereeUser, "refTraining");

        aR.addReferee(newRefereeUser, "VAR");
    }

    @Test
    public void removeRefereeUTest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 9312);

        assertFalse(aR.removeReferee(newRefereeUser));

        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }

    @Test
    public void removeReferee2UTest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 9311);

        assertTrue(aR.removeReferee(newRefereeUser));
    }

    @Test
    public void removeRefereeITest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 93132);

        assertTrue(aR.removeReferee(newRefereeUser));
    }

    @Test
    public void removeReferee2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");

        assertFalse(aR.removeReferee(newRefereeUser));
    }

    @Test
    public void removeReferee3ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        new Referee(newRefereeUser, "refTraining");

        Referee referee = (Referee) newRefereeUser.getRole(RoleTypes.REFEREE);
        Season season = new Season(new League("noName"),"2020/21");
        season.assignReferee(referee);
        referee.assignToSeason(season);

        assertTrue(season.doesContainsReferee(referee));
        assertTrue(aR.removeReferee(newRefereeUser));
        assertFalse(season.doesContainsReferee(referee));
    }

    @Test
    public void assignRefereeToSeasonUTest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUserStub("refUsername", "refName", 93121);
        Referee refereeRole = (Referee) newRefereeUser.getRole(RoleTypes.REFEREE);
        //new RefereeStub(newRefereeUser, "refTraining");
        Season season = new Season(new League("noName"), "2020/21");

        boolean thrown = false;
        try {
            aR.assignRefereeToSeason(season, refereeRole);
        } catch (Exception e) {
            thrown = true;
        }
        assertFalse(thrown);

        assertTrue(season.doesContainsReferee(refereeRole));
        assertTrue(refereeRole.getSeasons().contains(season));
    }

    @Test
    public void assignRefereeToSeason2UTest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUserStub("refUsername", "refName", 93121);
        Referee refereeRole = (Referee) newRefereeUser.getRole(RoleTypes.REFEREE);
        //new RefereeStub(newRefereeUser, "refTraining");
        Season season = new Season(new League("noName"), "2020/21");

        boolean thrown = false;
        try {
            aR.assignRefereeToSeason(season, refereeRole);
        } catch (Exception e) {
            thrown = true;
        }
        assertFalse(thrown);
        assertTrue(season.doesContainsReferee(refereeRole));
        assertTrue(refereeRole.getSeasons().contains(season));
        //duplicated assign
        try {
            aR.assignRefereeToSeason(season, refereeRole);
        } catch (Exception e) {
            assertEquals("This referee is already assigned to the chosen season", e.getMessage());
        }
    }

    @Test
    public void assignRefereeToSeasonITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        Referee refereeRole = new Referee(newRefereeUser, "refTraining");
        Season season = new Season(new League("noName"), "2020/21");

        boolean thrown = false;
        try {
            aR.assignRefereeToSeason(season, refereeRole);
        } catch (Exception e) {
            thrown = true;
        }
        assertFalse(thrown);

        assertTrue(season.doesContainsReferee(refereeRole));
        assertTrue(refereeRole.getSeasons().contains(season));
    }

    @Test
    public void assignRefereeToSeason2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        Referee refereeRole = new Referee(newRefereeUser, "refTraining");
        Season season = new Season(new League("noName"), "2020/21");


        boolean thrown = false;
        try {
            aR.assignRefereeToSeason(season, refereeRole);
        } catch (Exception e) {
            thrown = true;
        }
        assertFalse(thrown);
        assertTrue(season.doesContainsReferee(refereeRole));
        assertTrue(refereeRole.getSeasons().contains(season));
        //duplicated assign
        try {
            aR.assignRefereeToSeason(season, refereeRole);
        } catch (Exception e) {
            assertEquals("This referee is already assigned to the chosen season", e.getMessage());
        }
    }

    @After
    public void tearDown() {
        EntityManager.getInstance().clearAll();
    }
}



