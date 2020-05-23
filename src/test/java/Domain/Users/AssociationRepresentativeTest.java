package Domain.Users;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Exceptions.ExistsAlreadyException;
import Domain.Exceptions.RoleExistsAlreadyException;
import Domain.Game.League;
import Domain.Game.PointsPolicy;
import Domain.Game.Season;
import org.junit.*;


import static org.junit.Assert.*;

public class AssociationRepresentativeTest {

    private AssociationRepresentative aR;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.getInstance().startTest();
        DBManagerForTest.startConnection();    }

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
        Season season = new Season(new League("noName"), "2020/21");
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
    public void assignRefereeToSeasonITest() {
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
            Assert.fail();
        } catch (Exception e) {
            assertEquals("This referee is already assigned to the chosen season", e.getMessage());
        }
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
    }

    @Test
    public void assignRefereeToSeason3ITest() {
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
            Assert.fail();
        } catch (Exception e) {
            assertEquals("This referee is already assigned to the chosen season", e.getMessage());
        }
    }


    @Test
    public void addPointsPolicyITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        try {
            aR.addPointsPolicy(-1, 0, 0);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The victory points most be positive", e.getMessage());
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void addPointsPolicy2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        try {
            aR.addPointsPolicy(1, 1, 0);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The loss points most be negative or zero", e.getMessage());
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void addPointsPolicy3ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        try {
            aR.addPointsPolicy(1, 0, 0);
        } catch (Exception e) {

        }
        try {
            aR.addPointsPolicy(1, 0, 0);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("This points policy already exists", e.getMessage());
            assertTrue(e instanceof ExistsAlreadyException);
        }
    }

    @Test
    public void addPointsPolicy4ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        boolean succeeded = true;
        try {
            aR.addPointsPolicy(1, 0, 0);
        } catch (Exception e) {
            succeeded = false;
        }
        assertTrue(succeeded);
        assertTrue(EntityManager.getInstance().getPointsPolicy(1, 0, 0) != null);
        assertTrue(EntityManager.getInstance().doesPointsPolicyExists(1, 0, 0));
    }

    @Test
    public void setPointsPolicyITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        Season season = new Season(new League("noName"), "2020/21");
        PointsPolicy pointsPolicy = new PointsPolicy(1, -1, 0);

        assertTrue(season.getPointsPolicy().equals(PointsPolicy.getDefaultPointsPolicy()));
        aR.setPointsPolicy(season, pointsPolicy);
        assertTrue(season.getPointsPolicy().equals(pointsPolicy));
    }

    @Test
    public void setPointsPolicy2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        Season season = new Season(new League("noName"), "2020/21");

        assertTrue(season.getPointsPolicy().equals(PointsPolicy.getDefaultPointsPolicy()));
        aR.setPointsPolicy(season, null);
        assertTrue(season.getPointsPolicy().equals(PointsPolicy.getDefaultPointsPolicy()));
    }


    @Test
    public void addSchedulingPolicyITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        try {
            aR.addSchedulingPolicy(0, 0, 0);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The number of games for each team per season must be positive integer", e.getMessage());
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void addSchedulingPolicy2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        try {
            aR.addSchedulingPolicy(1, 0, 0);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The number of games on the same day must be positive integer", e.getMessage());
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void addSchedulingPolicy3ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        try {
            aR.addSchedulingPolicy(1, 1, -1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The minimum rest days between games must be non-negative integer", e.getMessage());
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void addSchedulingPolicy4ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        try {
            aR.addSchedulingPolicy(1, 1, 0);
        } catch (Exception e) {
        }
        try {
            aR.addSchedulingPolicy(1, 1, 0);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("This scheduling policy already exists", e.getMessage());
            assertTrue(e instanceof ExistsAlreadyException);
        }
    }

    @Test
    public void addSchedulingPolicy5ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        boolean succeeded = true;
        try {
            aR.addSchedulingPolicy(1, 1, 0);
        } catch (Exception e) {
            succeeded = false;
        }
        assertTrue(succeeded);
        assertTrue(EntityManager.getInstance().getSchedulingPolicy(1, 1, 0) != null);
        assertTrue(EntityManager.getInstance().doesSchedulingPolicyExists(1, 1, 0));
    }


    @After
    public void tearDown() {
        EntityManager.getInstance().clearAll();
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}



