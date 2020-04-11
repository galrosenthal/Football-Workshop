package Domain.Game;

import Domain.Users.TeamOwner;

import java.util.ArrayList;
import java.util.List;

public class TeamStub extends Team{

    private List<TeamOwner> teamOwners;
    private int selector;

    public TeamStub(int selector) {
        this.selector = selector;
        this.teamOwners = new ArrayList<>();
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
}
