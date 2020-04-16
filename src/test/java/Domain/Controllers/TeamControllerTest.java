package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Season;
import Domain.Game.Team;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class TeamControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    SystemUser teamOwnerUser = new SystemUser("oranShich", "Oran2802", "name");
    SystemUser teamOwnerToAdd = new SystemUser("oranSh", "Oran2802", "name");
    Team hapoelBash = new Team();
    //TeamOwner originalOwner = new TeamOwner(teamOwnerUser);

    @Before
    public void runBeforeTests(){
        teamOwnerUser.addNewRole(new TeamOwner(teamOwnerUser));
    }

    @After
    public void runAfterTests(){
        hapoelBash = new Team();
        EntityManager.getInstance().removeUserByReference(teamOwnerUser);
        EntityManager.getInstance().removeUserByReference(teamOwnerToAdd);
        hapoelBash.removeTeamOwner((TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
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
            thrown.expectMessage("Only the owner of this team can add a new owner");
        }
        catch (Exception e){
            e.printStackTrace();

        }


        // Adding the original owner to the team. Expected to succeed
        try{
            hapoelBash.addTeamOwner((TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
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
        hapoelBash.addTeamOwner((TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        try{
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            thrown.expectMessage("Could not find a user by the given username");

        }
        catch (Exception e){
            e.printStackTrace();
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
        hapoelBash.addTeamOwner((TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        hapoelTa.addTeamOwner((TeamOwner)teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        Season season = new Season("2019/20");

        season.addTeam(hapoelBash);
        season.addTeam(hapoelTa);

        hapoelTa.addSeason(season);
        hapoelBash.addSeason(season);

        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        try{
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            thrown.expectMessage("This User is already a team owner of a different team in same league");
        }
        catch (Exception e){
            e.printStackTrace();
        }


        hapoelTa.removeTeamOwner((TeamOwner)teamOwnerToAdd.getRole(RoleTypes.TEAM_OWNER));
        //Assert.assertTrue(TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER)));
    }

    /**
     *Check that a user is not already team owner of this team
     * @throws Exception
     */
    @Test
    public void AddTeamOwner4UTest() throws Exception {
        hapoelBash.addTeamOwner((TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        try{
            TeamController.addTeamOwner("oranShich", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
            thrown.expectMessage("This User is already a team owner of this team");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

