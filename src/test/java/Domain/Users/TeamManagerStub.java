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
        if(selector == 0 || selector == 661722)
        {
            return super.addTeam(teamToMange,teamOwner);
        }
        return true;
    }

    @Override
    public boolean addAllProperties(Team t) {
        return true;
    }

    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName)
    {
        return null;
    }
}
