package Domain.Users;

import Domain.Game.Team;

import java.util.List;

public class TeamManagerStub extends TeamManager {

    int selector;

    public TeamManagerStub(SystemUser systemUser) {
        super(systemUser);
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public boolean addTeam(Team teamToMange, TeamOwner teamOwner) {
        if(selector == 0)
        {
            return super.addTeam(teamToMange,teamOwner);
        }
        return true;
    }

    @Override
    public boolean addAllProperties() {
        return true;
    }
}
