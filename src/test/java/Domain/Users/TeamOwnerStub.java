package Domain.Users;

import Domain.Game.Team;
import Domain.Game.TeamStub;

import java.util.ArrayList;
import java.util.List;

public class TeamOwnerStub extends TeamOwner {

    public TeamOwnerStub(SystemUser systemUser) {
        super(systemUser);
    }

    private static int selector = 0;

    public static void setSelector(int select) {
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
        return test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (selector == 0){
            return true;
        }else{
            return false;
        }
    }
}
