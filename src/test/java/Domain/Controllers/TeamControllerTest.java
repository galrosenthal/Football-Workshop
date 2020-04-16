package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TeamControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Check that only the owner of this team can add a new team owner to the team
     *
     * @throws Exception
     */
    @Test
    public void AddTeamOwner1UTest(){

        SystemUser teamOwnerUser = new SystemUser("oranShich", "Oran2802", "name");
        SystemUser teamOwnerToAdd = new SystemUser("oranSh", "Oran2802", "name");
        Team hapoelBash = new Team();
        TeamOwner originalOwner = new TeamOwner(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerToAdd);
        try{
            boolean result = TeamController.addTeamOwner("oranSh", hapoelBash, originalOwner);
            //Assert.fail();
            thrown.expectMessage("Only the owner of this team can add a new owner");
        }
        catch (Exception e){
            e.printStackTrace();

        }


        // Adding the original owner to the team. Expected to succeed
        try{
            hapoelBash.addTeamOwner(originalOwner);
            boolean result = TeamController.addTeamOwner("oranSh", hapoelBash, originalOwner);
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
        SystemUser teamOwnerUser = new SystemUser("oranShich", "Oran2802", "name");
        Team hapoelBash = new Team();
        TeamOwner originalOwner = new TeamOwner(teamOwnerUser);
        EntityManager.getInstance().addUser(teamOwnerUser);
        hapoelBash.addTeamOwner(originalOwner);
        try{
            TeamController.addTeamOwner("oranSh", hapoelBash, originalOwner);
            thrown.expectMessage("Could not find a user by the given username");

        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Add the user to the system' expected to succeed
        SystemUser teamOwnerToAdd = new SystemUser("oranSh", "Oran2802", "name");
        EntityManager.getInstance().addUser(teamOwnerToAdd);

        boolean result = TeamController.addTeamOwner("oranSh", hapoelBash, originalOwner);
        Assert.assertTrue(result);
    }

    /**
     * Check that a owner of a team cant be another team owner of a team in a same league
     * @throws Exception
     */
    @Test
    public void AddTeamOwner3UTest() throws Exception {

    }
}

