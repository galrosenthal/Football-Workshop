package Domain.Controllers;

import Domain.Exceptions.AssetCantBeModifiedException;
import Domain.Exceptions.AssetsNotExistsException;
import Domain.Game.Team;
import Domain.Game.TeamStub;
import Service.UIController;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;



public class TeamControllerTest {


    @Test (expected = AssetsNotExistsException.class)
    public void editAssetsTest1UTest() throws Exception {
        Team team = new TeamStub(6131);
        TeamController.editAssets(team);

    }

    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest2UTest() throws Exception {
        Team team = new TeamStub(6132);


        UIController.setIsTest(true);
        UIController.setSelector(6132);

        TeamController.editAssets(team);
    }

    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest3UTest() throws Exception {
        Team team = new TeamStub(6133);

        UIController.setIsTest(true);
        UIController.setSelector(6133);

        TeamController.editAssets(team);
    }

    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest4UTest() throws Exception {
        Team team = new TeamStub(6134);

        UIController.setIsTest(true);
        UIController.setSelector(6134);

        TeamController.editAssets(team);
    }

    @Test
    public void editAssetsTest5UTest() throws Exception {
        Team team = new TeamStub(6135);

        UIController.setIsTest(true);
        UIController.setSelector(6135);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    @Test
    public void editAssetsTest6UTest() throws Exception {
        Team team = new TeamStub(6136);

        UIController.setIsTest(true);
        UIController.setSelector(6136);
        Assert.assertTrue(TeamController.editAssets(team));

    }

    @Test
    public void editAssetsTest7UTest() throws Exception {
        Team team = new TeamStub(6137);

        UIController.setIsTest(true);
        UIController.setSelector(6137);
        Assert.assertFalse(TeamController.editAssets(team));

    }
}