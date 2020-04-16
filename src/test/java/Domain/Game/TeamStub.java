package Domain.Game;

import Domain.Users.Coach;
import Domain.Users.Player;
import Domain.Users.TeamManager;
import Domain.Users.TeamOwner;

import java.util.ArrayList;
import java.util.List;

public class TeamStub extends Team{


    private List<TeamOwner> teamOwners;
    private int selector;

    public TeamStub(int selector) {
        this.teamOwners = new ArrayList<>();
        this.selector = selector;

    }

    @Override
    public List<TeamOwner> getTeamOwners() {
        return this.teamOwners;
    }


    public boolean addTeamOwner(TeamOwner townr) {
        return this.teamOwners.add(townr);
    }

    @Override
    public String getTeamName() {
        if(this.selector ==0){
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

        if(selector == 6131)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            return allTeamAssets;
        }
        return null;

    }
}
