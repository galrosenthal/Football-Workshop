package Domain.Controllers;

import Domain.Exceptions.AssetCantBeModifiedException;
import Domain.Exceptions.AssetsNotExistsException;
import Domain.Game.Team;
import Domain.Game.TeamStub;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamControllerTest {

    @Test
    public void addTeamOwner() {
    }

    @Test
    public void addPlayer() {
    }

    @Test
    public void addCoach() {
    }

    @Test
    public void addTeamManager() {
    }

    @Test
    public void addStadium() {
    }

    @Test (expected = AssetsNotExistsException.class)
    public void editAssetsTest1UTest() throws Exception {
        Team team = new TeamStub(6131);
        TeamController.editAssets(team);

    }

    @Test (expected = AssetCantBeModifiedException.class)
    public void editAssetsTest2UTest() throws Exception {
        Team team = new TeamStub(6131);
        TeamController.editAssets(team);

    }
}