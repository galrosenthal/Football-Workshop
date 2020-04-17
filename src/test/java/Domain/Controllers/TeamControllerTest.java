package Domain.Controllers;

import Domain.Exceptions.AssetCantBeModifiedException;
import Domain.Exceptions.AssetsNotExistsException;
import Domain.Game.Team;
import Domain.Game.TeamAsset;
import Domain.Game.TeamStub;
import Domain.Users.*;
import Service.UIController;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;



public class TeamControllerTest {


    /*No asset*/
    @Test (expected = AssetsNotExistsException.class)
    public void editAssetsTest1UTest() throws Exception {
        Team team = new TeamStub(6131);
        TeamController.editAssets(team);

    }

    /*can not modified asset - no property*/
    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest2UTest() throws Exception {
        Team team = new TeamStub(6132);


        UIController.setIsTest(true);
        UIController.setSelector(6132);

        TeamController.editAssets(team);
    }


    /*can not modified asset - add property value*/
    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest3UTest() throws Exception {
        Team team = new TeamStub(6133);

        UIController.setIsTest(true);
        UIController.setSelector(6133);

        TeamController.editAssets(team);
    }

    /*can not modified asset - remove property value*/
    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest4UTest() throws Exception {
        Team team = new TeamStub(6134);

        UIController.setIsTest(true);
        UIController.setSelector(6134);

        TeamController.editAssets(team);
    }

    /*success add property list*/
    @Test
    public void editAssetsTest5UTest() throws Exception {
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
        Assert.assertFalse(TeamController.editAssets(team));

    }

    /*No asset*/
    @Test (expected = AssetsNotExistsException.class)
    public void editAssetsTest1ITest() throws Exception {
        Team team = new Team();
        UIController.setIsTest(true);
        UIController.setSelector(6131);
        TeamController.editAssets(team);

    }


    /*can not modified asset - no property*/
    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest2ITest() throws Exception {
        Team team = new Team();
        UIController.setIsTest(true);
        UIController.setSelector(6132);
        TeamOwnerStub teamOwnerStub = new TeamOwnerStub(new SystemUserStub("teamOwnerStub" , "gal" , 6132));
        PlayerStub playerStub = new PlayerStub(new  SystemUserStub("playerStub" , "gal" , 6132), new Date(),6132);
        team.addTeamOwner(teamOwnerStub);
        team.addTeamPlayer(teamOwnerStub,playerStub);
        TeamController.editAssets(team);
    }


    /*can not modified asset - add property value*/
    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest3ITest() throws Exception {
        Team team = new Team();
        UIController.setIsTest(true);
        UIController.setSelector(6133);
        TeamOwnerStub teamOwnerStub = new TeamOwnerStub(new SystemUserStub("teamOwnerStub" , "gal" , 6133));
        PlayerStub playerStub = new PlayerStub(new  SystemUserStub("playerStub" , "gal" , 6133), new Date(),6132);
        team.addTeamOwner(teamOwnerStub);
        team.addTeamPlayer(teamOwnerStub,playerStub);
        TeamController.editAssets(team);
    }

    /*can not modified asset - remove property value*/
    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest4ITest() throws Exception {
        Team team = new TeamStub(6134);

        UIController.setIsTest(true);
        UIController.setSelector(6134);

        TeamController.editAssets(team);
    }

    /*success add property list*/
    @Test
    public void editAssetsTest5ITest() throws Exception {
        Team team = new TeamStub(6135);

        UIController.setIsTest(true);
        UIController.setSelector(6135);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property enum value*/
    @Test
    public void editAssetsTest6ITest() throws Exception {
        Team team = new TeamStub(6136);

        UIController.setIsTest(true);
        UIController.setSelector(6136);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    /*success change property string value*/
    @Test
    public void editAssetsTest7ITest() throws Exception {
        Team team = new TeamStub(6137);

        UIController.setIsTest(true);
        UIController.setSelector(6137);
        Assert.assertFalse(TeamController.editAssets(team));

    }
}