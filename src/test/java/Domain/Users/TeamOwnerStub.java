package Domain.Users;

import Domain.Game.Team;
import Domain.Game.TeamStub;

import java.util.ArrayList;
import java.util.List;

public class TeamOwnerStub extends TeamOwner {
    private static int selector;

    /**
     * Selector latest number: 1
     */
    public TeamOwnerStub(SystemUser systemUser) {
        super(systemUser);
    }





    public static void setSelector(int select) {
        selector = select;
    }

    @Override
    public List<Team> getOwnedTeams() {
        List<Team> test = new ArrayList<>();
        if (selector == 0 ) {
            TeamStub teamStub = new TeamStub(0);
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

}
