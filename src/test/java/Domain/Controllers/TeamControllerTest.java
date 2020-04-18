package Domain.Controllers;

import Domain.Exceptions.AssetCantBeModifiedException;
import Domain.Exceptions.AssetsNotExistsException;
import Domain.Game.Stadium;
import Domain.Game.Team;
import Domain.Game.TeamStub;
import Domain.Users.*;
import Service.UIController;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public class TeamControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    SystemUser teamOwnerUser = new SystemUser("oranShich", "Oran2802", "name");
    SystemUser teamOwnerToAdd = new SystemUser("oranSh", "Oran2802", "name");
    Team hapoelBash = new Team();
    TeamStub stubTeam = new TeamStub(0);
    //TeamOwner originalOwner = new TeamOwner(teamOwnerUser);
    League league = new League("Premier League");
    TeamOwnerStub to;
    @Before
    public void runBeforeTests(){
        to = new TeamOwnerStub(teamOwnerUser);
        teamOwnerUser.addNewRole(to);
    }

    @After
    public void runAfterTests(){
        hapoelBash.removeTeamOwner((TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        hapoelBash = new Team();
        EntityManager.getInstance().removeUserByReference(teamOwnerUser);
        EntityManager.getInstance().removeUserByReference(teamOwnerToAdd);
    }

    /**
     * Check that only the owner of this team can add a new team owner to the team
     *
     * @throws Exception
     */
    @Test
    public void AddTeamOwner1UTest(){

        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);
        try{
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.assertEquals("Only the owner of this team can add a new owner",e.getMessage());
        }


        // Adding the original owner to the team. Expected to succeed
        try{
            hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            boolean result = TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            Assert.assertTrue(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Check that by given a user name the does not exist throw appropriate message
     * @throws Exception
     */
    @Test
    public void AddTeamOwner2UTest() throws Exception{
        EntityManager.getInstance().addUser(teamOwnerUser);
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        try{
            TeamController.addTeamOwner("dfds", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            fail();
        }
        catch (Exception e){
            e.printStackTrace();
            assertEquals("Could not find a user by the given username",e.getMessage());
        }

        //Add the user to the system' expected to succeed
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        boolean result = TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        Assert.assertTrue(result);
    }

    /**
     * Check that a owner of a team cant be another team owner of a team in a same league
     * @throws Exception
     */
    @Test
    public void AddTeamOwner3UTest() throws Exception {
        this.teamOwnerToAdd.addNewRole(new TeamOwner(teamOwnerToAdd));
        Team hapoelTa = new Team();
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        hapoelTa.addTeamOwner(teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        Season season = new Season(league,"2019/20");

        season.addTeam(hapoelBash);
        season.addTeam(hapoelTa);

        hapoelTa.addSeason(season);
        hapoelBash.addSeason(season);

        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        try{
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        }
        catch (Exception e){
            e.printStackTrace();
            assertEquals("This User is already a team owner of a different team in same league",e.getMessage());
        }


        hapoelTa.removeTeamOwner((TeamOwner)teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        Assert.assertTrue(TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER)));
    }

    /**
     *Check that a user is not already team owner of this team
     * @throws Exception
     */
    @Test
    public void AddTeamOwner4UTest() throws Exception {
        hapoelBash.addTeamOwner(teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);


        try{
            TeamController.addTeamOwner("oranShich", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            //thrown.expectMessage("This User is already a team owner of this team");

        }
        catch (Exception e){
            e.printStackTrace();
            Assert.assertEquals("This User is already a team owner of this team",e.getMessage());
        }

        Season season = new Season(league,"2019/20");
        season.addTeam(hapoelBash);
        hapoelBash.addSeason(season);

        try{
            TeamController.addTeamOwner("oranShich", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        }
        catch (Exception e){
            e.printStackTrace();
            assertEquals("This User is already a team owner of this team",e.getMessage());
        }
    }


    @Test
    public void addAssetUTest() throws Exception
    {
        SystemUserStub assetToAdd = new SystemUserStub("asset","asset user", 0);
        try{
            TeamController.addAssetToTeam("asset",hapoelBash,to, TeamAsset.PLAYER);
        }
        catch (NotATeamOwner e)
        {
            assertEquals("Not One of the Team Owners",e.getMessage());
        }

        try{
            TeamController.addAssetToTeam("asset",hapoelBash,to, null);
        }
        catch (NullPointerException e)
        {
            assertEquals("No Asset Type was given",e.getMessage());
        }

        try{
            TeamController.addAssetToTeam("asset",null,to, null);
        }
        catch (NoTeamExistsException e)
        {
            assertEquals("No Team was given", e.getMessage());
        }

        stubTeam.setSelector(6110);
        assertTrue(TeamController.addAssetToTeam("asset",stubTeam,to, TeamAsset.PLAYER));
        stubTeam.setSelector(6111);
        assertFalse(TeamController.addAssetToTeam("asset",stubTeam,to,  TeamAsset.PLAYER));
    }



    @Test
    public void addAssetITest() throws Exception
    {
        SystemUser anotherUser = new SystemUser("test","testUser");
        hapoelBash.getTeamOwners().add(to);
        assertTrue(TeamController.addAssetToTeam("test",hapoelBash,to,TeamAsset.TEAM_MANAGER));
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
        Team team = new Team();
        UIController.setIsTest(true);
        UIController.setSelector(6131);
        TeamController.editAssets(team);

    }


    /*can not modified asset - no property*/
    @Test(expected = AssetCantBeModifiedException.class)
    public void editAssetsTest2ITest() throws Exception {
        Team team = new Team();
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
        Team team = new Team();
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"));
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"));
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
        Team team = new Team();
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"));
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"));
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner, teamManager);
        UIController.setIsTest(true);
        UIController.setSelector(6134);

        TeamController.editAssets(team);
    }

    /*success remove property list*/
    @Test
    public void editAssetsTest5ITest() throws Exception {

        Team team = new Team();
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"));
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"));
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
        Team team = new Team();
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest", "gal"));
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"));
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner, teamManager);
        UIController.setIsTest(true);
        UIController.setSelector(6135);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property enum value*/
    @Test
    public void editAssetsTest6ITest() throws Exception {
        Team team = new Team();
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest1", "gal"));
        team.addTeamOwner(teamOwner);
        Player player = new Player(new SystemUser("teamOwnerTest2", "gal"), new Date());
        team.addTeamPlayer(teamOwner, player);
        UIController.setIsTest(true);
        UIController.setSelector(6136);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property string value*/
    @Test
    public void editAssetsTest7ITest() throws Exception {
        Team team = new Team();
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamOwnerTest1", "gal"));
        team.addTeamOwner(teamOwner);
        Stadium stadium = new Stadium("testStadium", "bs");
        team.addStadium(stadium);
        UIController.setIsTest(true);
        UIController.setSelector(6137);
        assertTrue(TeamController.editAssets(team));

    }
}

