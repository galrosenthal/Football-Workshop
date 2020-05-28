package Domain.Controllers;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Exceptions.AssetCantBeModifiedException;
import Domain.Exceptions.AssetsNotExistsException;
import Domain.Exceptions.NoTeamExistsException;
import Domain.Exceptions.NotATeamOwner;
import Domain.Game.*;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.*;
import Service.UIController;
import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

public class TeamControllerTest {

    @BeforeClass
    public static void beforeClass() {
        DBManager.startTest();
        DBManagerForTest.startConnection();
        SystemLoggerManager.disableLoggers(); // disable loggers in tests
    }

    SystemUser teamOwnerUser;
    SystemUser teamOwnerToAdd;
    Team hapoelBash;
    TeamStub stubTeam;
    League league;
    TeamOwnerStub to;
    //For removeOwner tests
    SystemUser teamOwnerUser2;
    SystemUser teamOwnerUser3;
    SystemUser teamOwnerUser4;

    @Before
    public void runBeforeTests() {
        teamOwnerUser = new SystemUser("oranShich", "Oran2802", "Oran", "test@gmail.com", false, true);
        teamOwnerToAdd = new SystemUser("oranSh", "Oran2802", "Shichman", "test@gmail.com", false, true);
        hapoelBash = new Team("hapoelBash", true);
        stubTeam = new TeamStub(0);
        //TeamOwner originalOwner = new TeamOwner(teamOwnerUser);
        league = new League("Premier League", true);
        //For removeOwner tests
        teamOwnerUser2 = new SystemUser("rosengal", "Gal12345", "Gal", "test@gmail.com", false, true);
        teamOwnerUser3 = new SystemUser("nirdz", "Nir12345", "Nir", "test@gmail.com", false, true);

        teamOwnerUser4 = new SystemUser("merav", "Merav12345", "Mer", "test@gmail.com", false, true);

        to = new TeamOwnerStub(teamOwnerUser);
        teamOwnerUser.addNewRole(to);
        teamOwnerUser.addNewRole(new TeamOwner(teamOwnerUser, true));
        hapoelBash.setTeamName("Hapoel Beer Sheva");
        teamOwnerUser2.addNewRole(new TeamOwner(teamOwnerUser2, true));
        teamOwnerUser3.addNewRole(new TeamOwner(teamOwnerUser3, true));
        teamOwnerUser4.addNewRole(new TeamOwner(teamOwnerUser4, true));
        EntityManager.getInstance().addUser(teamOwnerUser2);
        EntityManager.getInstance().addUser(teamOwnerUser3);
        EntityManager.getInstance().addUser(teamOwnerUser4);
        UIController.setIsTest(true);
    }

    @After
    public void runAfterTests() {
        hapoelBash.removeTeamOwner((TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        hapoelBash = new Team("hapoelBash", true);
        EntityManager.getInstance().removeUserByReference(teamOwnerUser);
        EntityManager.getInstance().removeUserByReference(teamOwnerToAdd);
        hapoelBash.removeTeamOwner((TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        try {
            DBManager.deleteData("fwdb_test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check that only the owner of this team can add a new team owner to the team
     *
     * @throws Exception
     */
    @Test
    public void AddTeamOwner1UTest() {

        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);
        try {
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Only the owner of this team can add a new owner", e.getMessage());
        }


        // Adding the original owner to the team. Expected to succeed
        try {
            hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            boolean result = TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            Assert.assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Check that by given a user name the does not exist throw appropriate message
     *
     * @throws Exception
     */
    @Test
    public void AddTeamOwner2UTest() throws Exception {
        EntityManager.getInstance().addUser(teamOwnerUser);
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        try {
            TeamController.addTeamOwner("dfds", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("Could not find a user by the given username", e.getMessage());
        }

        //Add the user to the system' expected to succeed
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        boolean result = TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        Assert.assertTrue(result);
    }

    /**
     * Check that a owner of a team cant be another team owner of a team in a same league
     *
     * @throws Exception
     */
    @Test
    public void AddTeamOwner3UTest() throws Exception {
        this.teamOwnerToAdd.addNewRole(new TeamOwner(teamOwnerToAdd, true));
        Team hapoelTa = new Team("hapoelTa", true);
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        hapoelTa.addTeamOwner(teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        Season season = new Season(league, "2019/20");

        season.addTeam(hapoelBash);
        season.addTeam(hapoelTa);

        hapoelTa.addSeason(season);
        hapoelBash.addSeason(season);

        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        try {
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("This User is already a team owner of a different team in same league", e.getMessage());
        }


        hapoelTa.removeTeamOwner((TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        Assert.assertTrue(TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER)));
    }

    /**
     * Check that a user is not already team owner of this team
     *
     * @throws Exception
     */
    @Test
    public void AddTeamOwner4UTest() throws Exception {
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);


        try {
            TeamController.addTeamOwner("oranShich", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            //thrown.expectMessage("This User is already a team owner of this team");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals("This User is already a team owner of this team", e.getMessage());
        }

        Season season = new Season(league, "2019/20");
        league.addSeason("2019/20");
        season.addTeam(hapoelBash);
        hapoelBash.addSeason(season);

        try {
            TeamController.addTeamOwner("oranShich", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("This User is already a team owner of this team", e.getMessage());
        }
    }


    @Test
    public void addAssetUTest() throws Exception {
        SystemUserStub assetToAdd = new SystemUserStub("asset", "asset user", 0);
        try {
            TeamController.addAssetToTeam("asset", hapoelBash, to, TeamAsset.PLAYER);
        } catch (NotATeamOwner e) {
            assertEquals("Not One of the Team Owners", e.getMessage());
        }

        try {
            TeamController.addAssetToTeam("asset", hapoelBash, to, null);
        } catch (NullPointerException e) {
            assertEquals("No Asset Type was given", e.getMessage());
        }

        try {
            TeamController.addAssetToTeam("asset", null, to, null);
        } catch (NoTeamExistsException e) {
            assertEquals("No Team was given", e.getMessage());
        }

        stubTeam.setSelector(6110);
        assertTrue(TeamController.addAssetToTeam("asset", stubTeam, to, TeamAsset.PLAYER));
        stubTeam.setSelector(6111);
        assertFalse(TeamController.addAssetToTeam("asset", stubTeam, to, TeamAsset.PLAYER));
    }


    @Test
    public void addAssetITest() throws Exception {
        SystemUser anotherUser = new SystemUser("test", "testUser");
        hapoelBash.getTeamOwners().add(to);
        assertTrue(TeamController.addAssetToTeam("test", hapoelBash, to, TeamAsset.TEAM_MANAGER));
    }


    /*No asset*/
    @Test(expected = AssetsNotExistsException.class)
    public void editAssetsTest1UTest() throws Exception {

        Team team = new TeamStub(6131);
        TeamController.editAssets(team);


    }

    /*can not modified asset - no property*/
    @Test(expected = AssetCantBeModifiedException.class)
    public void editAssetsTest2UTest() throws Exception {
        Team team = new TeamStub(6132);
        UIController.setIsTest(true);
        UIController.setSelector(6132);
        TeamController.editAssets(team);
    }


    /*can not modified asset - add property value*/
    @Test(expected = AssetCantBeModifiedException.class)
    public void editAssetsTest3UTest() throws Exception {
        Team team = new TeamStub(6133);
        UIController.setIsTest(true);
        UIController.setSelector(6133);
        TeamController.editAssets(team);
    }

    /*can not modified asset - remove property value*/
    @Test(expected = AssetCantBeModifiedException.class)
    public void editAssetsTest4UTest() throws Exception {
        Team team = new TeamStub(6134);

        UIController.setIsTest(true);
        UIController.setSelector(6134);

        TeamController.editAssets(team);
    }

    /*success remove property list*/
    @Test
    public void editAssetsTest5UTest() throws Exception {
        Team team = new TeamStub(6138);

        UIController.setIsTest(true);
        UIController.setSelector(6138);

        assertTrue(TeamController.editAssets(team));

    }

    /*success add property list*/
    @Test
    public void editAssetsTest8UTest() throws Exception {
        Team team = new TeamStub(6135);

        UIController.setIsTest(true);
        UIController.setSelector(6135);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property enum value*/
    @Test
    public void editAssetsTest6UTest() throws Exception {
        Team team = new TeamStub(6136);

        UIController.setIsTest(true);
        UIController.setSelector(6136);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property string value*/
    @Test
    public void editAssetsTest7UTest() throws Exception {
        Team team = new TeamStub(6137);

        UIController.setIsTest(true);
        UIController.setSelector(6137);
        assertTrue(TeamController.editAssets(team));

    }

    /*No asset*/
    @Test(expected = AssetsNotExistsException.class)
    public void editAssetsTest1ITest() throws Exception {
        Team team = new Team("Test", true);
        UIController.setIsTest(true);
        UIController.setSelector(6131);
        TeamController.editAssets(team);

    }


    /*can not modified asset - no property*/
    @Test(expected = AssetCantBeModifiedException.class)
    public void editAssetsTest2ITest() throws Exception {
        Team team = new Team("Test", true);
        UIController.setIsTest(true);
        UIController.setSelector(6132);
        TeamOwnerStub teamOwnerStub = new TeamOwnerStub(new SystemUserStub("teamOwnerStub", "gal", 6132));
        PlayerStub playerStub = new PlayerStub(new SystemUserStub("playerStub", "gal", 6132), new Date(), 6132);
        team.addTeamOwner(teamOwnerStub);
        team.addTeamPlayer(teamOwnerStub, playerStub);
        TeamController.editAssets(team);
    }


    /*can not modified asset - add property value*/
    @Test(expected = AssetCantBeModifiedException.class)
    public void editAssetsTest3ITest() throws Exception {
        Team team = new Team("Test", true);
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"), true);
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner, teamManager);
        UIController.setIsTest(true);
        UIController.setSelector(6135);
        TeamController.editAssets(team);
        TeamController.editAssets(team);
        TeamController.editAssets(team);
        TeamController.editAssets(team);
        TeamController.editAssets(team);
        TeamController.editAssets(team);
        TeamController.editAssets(team);


    }

    /*can not modified asset - remove property value*/
    @Test(expected = AssetCantBeModifiedException.class)
    public void editAssetsTest4ITest() throws Exception {
        Team team = new Team("Test", true);
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"), true);
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner, teamManager);
        UIController.setIsTest(true);
        UIController.setSelector(6134);

        TeamController.editAssets(team);
    }

    /*success remove property list*/
    @Test
    public void editAssetsTest5ITest() throws Exception {

        Team team = new Team("Test", true);
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"), true);
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner, teamManager);
        UIController.setIsTest(true);
        UIController.setSelector(6135);
        Assert.assertTrue(TeamController.editAssets(team));

        UIController.setIsTest(true);
        UIController.setSelector(6138);

        assertTrue(TeamController.editAssets(team));

    }

    /*success add property list*/
    @Test
    public void editAssetsTest8ITest() throws Exception {
        Team team = new Team("Test", true);
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"), true);
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner, teamManager);
        UIController.setIsTest(true);
        UIController.setSelector(6135);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property enum value*/
    @Test
    public void editAssetsTest6ITest() throws Exception {
        Team team = new Team("Test", true);
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest1", "gal"), true);
        team.addTeamOwner(teamOwner);
        Player player = new Player(new SystemUser("teamOwnerTest2", "gal"), new Date(), true);
        team.addTeamPlayer(teamOwner, player);
        UIController.setIsTest(true);
        UIController.setSelector(6136);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property string value*/
    @Test
    public void editAssetsTest7ITest() throws Exception {
        Team team = new Team("Test", true);
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest1", "gal"), true);
        team.addTeamOwner(teamOwner);
        Stadium stadium = new Stadium("testStadium", "bs");
        team.addStadium(stadium);
        UIController.setIsTest(true);
        UIController.setSelector(6137);
        assertTrue(TeamController.editAssets(team));

    }

    /**
     * Check that the team owner has added to the team and the team has added to the team owner
     *
     * @throws Exception
     */
    @Test
    public void AddTeamOwner5UTest() throws Exception {
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        Assert.assertEquals(((TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER)).getOwnedTeams().get(0), hapoelBash);
        Assert.assertEquals(hapoelBash.getTeamOwners().get(0), ((TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER)));
        Assert.assertEquals(hapoelBash.getTeamOwners().get(1), ((TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER)));
    }


    /**
     * Check that if we remove the owner who appointed the others, everyone removed.
     *
     * @throws Exception
     */
    @Test
    public void removeTeamOwner1UTest() throws Exception {

        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        TeamController.addTeamOwner("nirdz", hapoelBash, (TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        TeamController.addTeamOwner("rosengal", hapoelBash, (TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));

        Assert.assertEquals(4, hapoelBash.getTeamOwners().size());

        TeamController.removeTeamOwner("oranShich", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));

        Assert.assertEquals(0, hapoelBash.getTeamOwners().size());
    }

    /**
     * Check only one removed
     */
    @Test
    public void removeTeamOwner2UTest() throws Exception {
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        TeamController.addTeamOwner("nirdz", hapoelBash, (TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        TeamController.addTeamOwner("merav", hapoelBash, (TeamOwner) teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));

        Assert.assertEquals(4, hapoelBash.getTeamOwners().size());

        TeamController.removeTeamOwner("nirdz", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));

        Assert.assertEquals(3, hapoelBash.getTeamOwners().size());
    }

    /**
     * Check input of wrong username
     *
     * @throws Exception
     */
    @Test
    public void removeTeamOwner3UTest() {
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);


        try {
            TeamController.removeTeamOwner("orah", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Could not find a user by the given username", e.getMessage());
        }
    }

    /**
     * Check the owner is the owner of the team
     */
    @Test
    public void removeTeamOwner4UTest() {
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        try {
            TeamController.removeTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Only the owner of this team can remove owner", e.getMessage());
        }

        try {
            hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            Assert.assertTrue(TeamController.removeTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the input of the owner we want to remove.
     */
    @Test
    public void removeTeamOwner5UTest() {
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        try {
            TeamController.removeTeamOwner("oranSh", hapoelBash, (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals("The user is not a owner of this team", e.getMessage());
        }
    }

    @Test
    public void closeTeamITest() {
        //Integration with player, coach, stadium, team manager but only calls simple method from them.
        TeamStub teamStub = new TeamStub(66171);
        Assert.assertTrue(TeamController.closeTeam(teamStub));
        Assert.assertEquals(TeamStatus.CLOSED, teamStub.getStatus());
    }

    @Test
    public void closeTeamUTest() {
        TeamStub teamStub = new TeamStub(66172);
        SystemUserStub su1 = new SystemUserStub("rosengal", "gal", 661721);
        TeamOwnerStub teamOwnerStub = new TeamOwnerStub(su1);
        teamStub.addTeamOwner(teamOwnerStub);
        PlayerStub p1 = (PlayerStub) su1.getRole(RoleTypes.PLAYER);
        p1.addTeam(teamStub, teamOwnerStub);
        teamStub.addPlayer(p1);

        SystemUserStub su2 = new SystemUserStub("nirdz", "nir", 661722);
        TeamManagerStub tm1 = (TeamManagerStub) su2.getRole(RoleTypes.TEAM_MANAGER);
        tm1.addTeam(teamStub, teamOwnerStub);
        teamStub.addTeamManager(tm1);

        SystemUserStub su3 = new SystemUserStub("coach", "coach", 661723);
        CoachStub co1 = (CoachStub) su3.getRole(RoleTypes.COACH);
        co1.addTeam(teamStub, teamOwnerStub);
        teamStub.addCoach(co1);

        SystemUserStub su4 = new SystemUserStub("iamowner", "owner", 661724);
        TeamOwnerStub to = (TeamOwnerStub) su4.getRole(RoleTypes.TEAM_OWNER);
        to.addTeamToOwn(teamStub, su4);
        teamStub.addTeamOwner(to);


        StadiumStub st1 = new StadiumStub("stadiumStub", "stub street 22");
        st1.addTeam(teamStub);
        teamStub.addStadium(st1);

        //add to Entity Manager
        EntityManager.getInstance().addUser(su1);
        EntityManager.getInstance().addUser(su2);
        EntityManager.getInstance().addUser(su3);
        EntityManager.getInstance().addUser(su4);
        EntityManager.getInstance().addTeam(teamStub);

        //success
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(1, p1.getTeams().size());
        Assert.assertEquals(1, tm1.getTeamsManaged().size());
        Assert.assertEquals(1, co1.getTeams().size());
        Assert.assertEquals(1, st1.getTeams().size());
        Assert.assertTrue(TeamController.closeTeam(teamStub));
        Assert.assertEquals(TeamStatus.CLOSED, teamStub.getStatus());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(0, p1.getTeams().size());
        Assert.assertEquals(0, tm1.getTeamsManaged().size());
        Assert.assertEquals(0, co1.getTeams().size());
        Assert.assertEquals(0, st1.getTeams().size());

        //cleanup
        EntityManager.getInstance().removeUserByReference(su1);
        EntityManager.getInstance().removeUserByReference(su2);
        EntityManager.getInstance().removeUserByReference(su3);
        EntityManager.getInstance().removeUserByReference(su4);
        EntityManager.getInstance().removeTeamByReference(teamStub);
    }

    @Test
    public void reopenTeamUTest() {
        TeamStub teamStub = new TeamStub(66271);
        SystemUserStub su1 = new SystemUserStub("rosengal", "gal", 662721);
        PlayerStub p1 = (PlayerStub) su1.getRole(RoleTypes.PLAYER);
        teamStub.addPlayer(p1);

        SystemUserStub su2 = new SystemUserStub("nirdz", "nir", 662722);
        TeamManagerStub tm1 = (TeamManagerStub) su2.getRole(RoleTypes.TEAM_MANAGER);
        teamStub.addTeamManager(tm1);

        SystemUserStub su3 = new SystemUserStub("coach", "coach", 662723);
        CoachStub co1 = (CoachStub) su3.getRole(RoleTypes.COACH);
        teamStub.addCoach(co1);

        SystemUserStub su4 = new SystemUserStub("iamowner", "owner", 662724);
        TeamOwnerStub to = (TeamOwnerStub) su4.getRole(RoleTypes.TEAM_OWNER);
        to.addTeamToOwn(teamStub, su4);
        teamStub.addTeamOwner(to);

        StadiumStub st1 = new StadiumStub("stadiumStub", "stub street 22");
        teamStub.addStadium(st1);

        teamStub.setStatus(TeamStatus.CLOSED);

        //add to Entity Manager
        EntityManager.getInstance().addUser(su1);
        EntityManager.getInstance().addUser(su2);
        EntityManager.getInstance().addUser(su3);
        EntityManager.getInstance().addUser(su4);
        EntityManager.getInstance().addTeam(teamStub);
        EntityManager.getInstance().addStadium(st1);

        //success
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(0, p1.getTeams().size());
        Assert.assertEquals(0, tm1.getTeamsManaged().size());
        Assert.assertEquals(0, co1.getTeams().size());
        Assert.assertEquals(0, st1.getTeams().size());
        Assert.assertTrue(TeamController.reopenTeam(teamStub, to));
        Assert.assertEquals(TeamStatus.OPEN, teamStub.getStatus());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(1, p1.getTeams().size());
        Assert.assertEquals(1, tm1.getTeamsManaged().size());
        Assert.assertEquals(1, co1.getTeams().size());
        Assert.assertEquals(1, st1.getTeams().size());

        //cleanup
        EntityManager.getInstance().removeUserByReference(su1);
        EntityManager.getInstance().removeUserByReference(su2);
        EntityManager.getInstance().removeUserByReference(su3);
        EntityManager.getInstance().removeUserByReference(su4);
        EntityManager.getInstance().removeTeamByReference(teamStub);
        EntityManager.getInstance().removeStadiumByReference(st1);
    }


    @Test
    public void reopenTeam2UTest() {
        UIController.setIsTest(true);
        TeamStub teamStub = new TeamStub(66271);
        SystemUserStub su1 = new SystemUserStub("rosengal", "gal", 662721);
        PlayerStub p1 = (PlayerStub) su1.getRole(RoleTypes.PLAYER);
        teamStub.addPlayer(p1);

        SystemUserStub su2 = new SystemUserStub("nirdz", "nir", 662722);
        TeamManagerStub tm1 = (TeamManagerStub) su2.getRole(RoleTypes.TEAM_MANAGER);
        teamStub.addTeamManager(tm1);

        SystemUserStub su3 = new SystemUserStub("coach", "coach", 662723);
        CoachStub co1 = (CoachStub) su3.getRole(RoleTypes.COACH);
        teamStub.addCoach(co1);

        SystemUserStub su4 = new SystemUserStub("iamowner", "owner", 662724);
        TeamOwnerStub to = (TeamOwnerStub) su4.getRole(RoleTypes.TEAM_OWNER);
        to.addTeamToOwn(teamStub, su4);
        teamStub.addTeamOwner(to);

        StadiumStub st1 = new StadiumStub("stadiumStub", "stub street 22");
        teamStub.addStadium(st1);

        teamStub.setStatus(TeamStatus.CLOSED);

        //add to Entity Manager
        EntityManager.getInstance().addUser(su1);
        //removing the team manager
        su2.removeRole(tm1);
        su2.setSelector(66162); //get role will return null
        EntityManager.getInstance().removeUserByReference(su2);
        EntityManager.getInstance().addUser(su3);
        EntityManager.getInstance().addUser(su4);
        EntityManager.getInstance().addTeam(teamStub);
        EntityManager.getInstance().addStadium(st1);

        //success, but could not restore nirdz the team's manager because he is not a user anymore
        Assert.assertEquals(1, teamStub.getTeamManagers().size());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(0, p1.getTeams().size());
        Assert.assertEquals(0, co1.getTeams().size());
        Assert.assertEquals(0, st1.getTeams().size());
        Assert.assertTrue(TeamController.reopenTeam(teamStub, to));
        Assert.assertEquals(TeamStatus.OPEN, teamStub.getStatus());
        Assert.assertEquals(0, teamStub.getTeamManagers().size());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(1, p1.getTeams().size());
        Assert.assertEquals(1, co1.getTeams().size());
        Assert.assertEquals(1, st1.getTeams().size());

        //cleanup
        EntityManager.getInstance().removeUserByReference(su1);
        EntityManager.getInstance().removeUserByReference(su2);
        EntityManager.getInstance().removeUserByReference(su3);
        EntityManager.getInstance().removeUserByReference(su4);
        EntityManager.getInstance().removeTeamByReference(teamStub);
        EntityManager.getInstance().removeStadiumByReference(st1);

    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}