package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class CoachStub extends Coach {

    int selector;

    private List<Team> teamOfTest;

    public CoachStub(SystemUser user) {
        super(user);
        teamOfTest = new ArrayList<>();
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public boolean addTeam(Team teamToCoach, TeamOwner teamOwner) {
        if(selector == 0)
        {
            this.teamOfTest.add(teamToCoach);
            return super.addTeam(teamToCoach,teamOwner);
        }
        return true;
    }

    @Override
    public boolean removeTeam(Team teamToRemove) {
        return this.teamOfTest.remove(teamToRemove);
    }

    @Override
    public boolean addAllProperties(Team team) {
        return true;
    }

    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName)
    {
        return null;
    }

    @Override
    public List<Team> getTeams() {
        return teamOfTest;
    }
}
