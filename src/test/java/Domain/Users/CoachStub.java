package Domain.Users;

import Domain.Game.Team;

import java.util.List;

public class CoachStub extends Coach {

    int selector;
    public CoachStub(SystemUser user) {
        super(user);
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

    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName)
    {
        return null;
    }
}
