package Domain.Game;

import Domain.Users.Coach;
import Domain.Users.Player;
import Domain.Users.TeamManager;
import Domain.Users.TeamOwner;
import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;


public class TeamStub extends Team{


    private List<TeamOwner> teamOwners;
    private List<Asset> assetStubs;
    private int selector;

    /**
     * Selector latest Number: 2
     */
    public TeamStub(int selector) {
        super();
        this.selector = selector;
        this.teamOwners = new ArrayList<>();
        this.selector = selector;
        this.assetStubs = new ArrayList<>();

    }

    public void setSelector(int selector) {
        this.selector = selector;
    }




    public boolean addTeamOwner(TeamOwner townr) {
        return super.getTeamOwners().add(townr);
    }

    @Override
    public String getTeamName() {
        if(this.selector >= 0){
            return "TeamStub 1";
        } else {
            return null;
        }
    }

    @Override
    public void setTeamName(String testName) {
        super.setTeamName(testName);
    }

    @Override
    public List<Asset> getAllAssets() {

        if(this.selector == 6131)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            return allTeamAssets;
        }
        if(this.selector == 6132 || this.selector == 6133 || this.selector == 6134 || this.selector == 6135 ||this.selector == 6136 ||this.selector == 6137 )
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(this.selector));
            return allTeamAssets;
        }

        return null;

    }

    @Override
    public boolean addTeamPlayer(TeamOwner townr, Role teamPlayer) {
        if(selector == 1)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean addTeamCoach(TeamOwner townr, Role coach) {
        return super.addTeamCoach(townr, coach);
    }

    @Override
    public boolean addTeamManager(TeamOwner townr, Role teamManager) {
        return super.addTeamManager(townr, teamManager);
    }

    @Override
    public boolean addTeamOwner(Role teamOwner) {
        return super.addTeamOwner(teamOwner);
    }

    @Override
    public List<Player> getTeamPlayers() {
        return super.getTeamPlayers();
    }

    @Override
    public List<Coach> getTeamCoaches() {
        return super.getTeamCoaches();
    }

    @Override
    public List<TeamManager> getTeamManagers() {
        return super.getTeamManagers();
    }
}
