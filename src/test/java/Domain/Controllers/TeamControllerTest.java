package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.*;
import Domain.Users.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.EmptyStackException;

public class TeamControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SystemUser teamOwnerUser = new SystemUser("oranShich", "Oran2802", "name");
    private SystemUser teamOwnerToAdd = new SystemUser("oranSh", "Oran2802", "name");
    private Team hapoelBash = new Team();
    //TeamOwner originalOwner = new TeamOwner(teamOwnerUser);
    private League league = new League("Premier League");
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
            TeamController.addTeamOwner("oranSh", hapoelBash, (TeamOwner)teamOwnerUser.getRole(RoleTypes.TEAM_OWNER));
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.assertEquals("Could not find a user by the given username",e.getMessage());
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
            Assert.assertEquals("This User is already a team owner of a different team in same league",e.getMessage());
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
            Assert.fail();
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.assertEquals("This User is already a team owner of this team",e.getMessage());
        }
    }

    @Test
    public void closeTeamITest(){
        //Integration with player, coach, stadium, team manager but only calls simple method from them.
        TeamStub teamStub = new TeamStub(66171);
        Assert.assertTrue(TeamController.closeTeam(teamStub));
        Assert.assertEquals(TeamStatus.CLOSED, teamStub.getStatus());
    }

    @Test
    public void closeTeamUTest(){
        TeamStub teamStub = new TeamStub(66172);
        SystemUserStub su1 = new SystemUserStub("rosengal", "gal",661721);
        PlayerStub p1 = (PlayerStub)su1.getRole(RoleTypes.PLAYER);
        p1.addTeam(teamStub);
        teamStub.addPlayer(p1);

        SystemUserStub su2 = new SystemUserStub("nirdz", "nir",661722);
        TeamManagerStub tm1 = (TeamManagerStub)su2.getRole(RoleTypes.TEAM_MANAGER);
        tm1.addTeam(teamStub);
        teamStub.addTeamManager(tm1);

        SystemUserStub su3 = new SystemUserStub("coach", "coach",661723);
        CoachStub co1 = (CoachStub)su3.getRole(RoleTypes.COACH);
        co1.addTeamToCoach(teamStub);
        teamStub.addCoach(co1);

        SystemUserStub su4 = new SystemUserStub("iamowner", "owner",661724);
        TeamOwnerStub to = (TeamOwnerStub)su4.getRole(RoleTypes.TEAM_OWNER);
        to.addTeamToOwn(teamStub);
        teamStub.addTeamOwner(to);


        StadiumStub st1 = new StadiumStub();
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
        Assert.assertEquals(1, p1.getPlayerTeams().size());
        Assert.assertEquals(1, tm1.getTeamsManaged().size());
        Assert.assertEquals(1, co1.getCoachedTeams().size());
        Assert.assertEquals(1, st1.getTeams().size());
        Assert.assertTrue(TeamController.closeTeam(teamStub));
        Assert.assertEquals(TeamStatus.CLOSED, teamStub.getStatus());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(0, p1.getPlayerTeams().size());
        Assert.assertEquals(0, tm1.getTeamsManaged().size());
        Assert.assertEquals(0, co1.getCoachedTeams().size());
        Assert.assertEquals(0, st1.getTeams().size());

        //cleanup
        EntityManager.getInstance().removeUserByReference(su1);
        EntityManager.getInstance().removeUserByReference(su2);
        EntityManager.getInstance().removeUserByReference(su3);
        EntityManager.getInstance().removeUserByReference(su4);
        EntityManager.getInstance().removeTeamByReference(teamStub);
    }

    @Test
    public void reopenTeamUTest(){
        TeamStub teamStub = new TeamStub(66271);
        SystemUserStub su1 = new SystemUserStub("rosengal", "gal",662721);
        PlayerStub p1 = (PlayerStub)su1.getRole(RoleTypes.PLAYER);
        teamStub.addPlayer(p1);

        SystemUserStub su2 = new SystemUserStub("nirdz", "nir",662722);
        TeamManagerStub tm1 = (TeamManagerStub)su2.getRole(RoleTypes.TEAM_MANAGER);
        teamStub.addTeamManager(tm1);

        SystemUserStub su3 = new SystemUserStub("coach", "coach",662723);
        CoachStub co1 = (CoachStub)su3.getRole(RoleTypes.COACH);
        teamStub.addCoach(co1);

        SystemUserStub su4 = new SystemUserStub("iamowner", "owner",662724);
        TeamOwnerStub to = (TeamOwnerStub)su4.getRole(RoleTypes.TEAM_OWNER);
        to.addTeamToOwn(teamStub);
        teamStub.addTeamOwner(to);

        StadiumStub st1 = new StadiumStub();
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
        Assert.assertEquals(0, p1.getPlayerTeams().size());
        Assert.assertEquals(0, tm1.getTeamsManaged().size());
        Assert.assertEquals(0, co1.getCoachedTeams().size());
        Assert.assertEquals(0, st1.getTeams().size());
        Assert.assertTrue(TeamController.reopenTeam(teamStub));
        Assert.assertEquals(TeamStatus.OPEN, teamStub.getStatus());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(1, p1.getPlayerTeams().size());
        Assert.assertEquals(1, tm1.getTeamsManaged().size());
        Assert.assertEquals(1, co1.getCoachedTeams().size());
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
    public void reopenTeam2UTest(){
        TeamStub teamStub = new TeamStub(66271);
        SystemUserStub su1 = new SystemUserStub("rosengal", "gal",662721);
        PlayerStub p1 = (PlayerStub)su1.getRole(RoleTypes.PLAYER);
        teamStub.addPlayer(p1);

        SystemUserStub su2 = new SystemUserStub("nirdz", "nir",662722);
        TeamManagerStub tm1 = (TeamManagerStub)su2.getRole(RoleTypes.TEAM_MANAGER);
        teamStub.addTeamManager(tm1);

        SystemUserStub su3 = new SystemUserStub("coach", "coach",662723);
        CoachStub co1 = (CoachStub)su3.getRole(RoleTypes.COACH);
        teamStub.addCoach(co1);

        SystemUserStub su4 = new SystemUserStub("iamowner", "owner",662724);
        TeamOwnerStub to = (TeamOwnerStub)su4.getRole(RoleTypes.TEAM_OWNER);
        to.addTeamToOwn(teamStub);
        teamStub.addTeamOwner(to);

        StadiumStub st1 = new StadiumStub();
        teamStub.addStadium(st1);

        teamStub.setStatus(TeamStatus.CLOSED);

        //add to Entity Manager
        EntityManager.getInstance().addUser(su1);
        //removing the team manager
        su2.removeRole(tm1);
        EntityManager.getInstance().removeUserByReference(su2);
        EntityManager.getInstance().addUser(su3);
        EntityManager.getInstance().addUser(su4);
        EntityManager.getInstance().addTeam(teamStub);
        EntityManager.getInstance().addStadium(st1);

        //success, but could not restore nirdz the team's manager because he is not a user anymore
        Assert.assertEquals(1, teamStub.getTeamManagers().size());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(0, p1.getPlayerTeams().size());
        Assert.assertEquals(0, co1.getCoachedTeams().size());
        Assert.assertEquals(0, st1.getTeams().size());
        Assert.assertTrue(TeamController.reopenTeam(teamStub));
        Assert.assertEquals(TeamStatus.OPEN, teamStub.getStatus());
        Assert.assertEquals(0, teamStub.getTeamManagers().size());
        Assert.assertEquals(1, to.getOwnedTeams().size());
        Assert.assertEquals(1, p1.getPlayerTeams().size());
        Assert.assertEquals(1, co1.getCoachedTeams().size());
        Assert.assertEquals(1, st1.getTeams().size());

        //cleanup
        EntityManager.getInstance().removeUserByReference(su1);
        EntityManager.getInstance().removeUserByReference(su2);
        EntityManager.getInstance().removeUserByReference(su3);
        EntityManager.getInstance().removeUserByReference(su4);
        EntityManager.getInstance().removeTeamByReference(teamStub);
        EntityManager.getInstance().removeStadiumByReference(st1);

    }

}

