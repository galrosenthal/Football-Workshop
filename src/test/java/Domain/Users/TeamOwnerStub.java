package Domain.Users;

import Domain.Game.Team;
import Domain.Game.TeamStub;

import java.util.ArrayList;
import java.util.List;

public class TeamOwnerStub extends TeamOwner {
    private int selector;

    /**
     * Selector latest number: 1
     */
    public TeamOwnerStub(SystemUser systemUser) {
        super(systemUser);
    }




    public void setSelector(int select) {
        selector = select;
    }

    @Override
    public List<Team> getOwnedTeams() {
        List<Team> test = new ArrayList<>();
        if (selector == 0 || selector == 66143 || selector == 66144 || selector == 66151 || selector == 66163
                || selector == 66251) {
            TeamStub teamStub = new TeamStub(selector);
            test.add(teamStub);
            teamStub.addTeamOwner(this);
        }
        else if(selector == 6111)
        {
            return null;
        }else if(selector == 6112)
        {
            TeamStub teamStub = new TeamStub(6112);
            test.add(teamStub);
            teamStub.addTeamOwner(this);
        }else if(selector == 6113)
        {
            TeamStub teamStub = new TeamStub(6113);
            test.add(teamStub);
            teamStub.addTeamOwner(this);
        }
        return test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (selector == 0){
            return true;
        }else if(selector == 1)
        {
            return false;
        }else{
            return false;
        }
    }

    @Override
    public boolean addTeamToOwn(Team teamToOwn) {
        return super.addTeamToOwn(teamToOwn);
    }

    @Override
    public SystemUser getAppointedOwner() {
        return super.getAppointedOwner();
    }

    @Override
    public void setAppointedOwner(SystemUser appointedOwner) {
        super.setAppointedOwner(appointedOwner);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
