package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamOwnerStub extends TeamOwner {

    public TeamOwnerStub(SystemUser systemUser) {
        super(systemUser);
    }

    @Override
    public List<Team> getOwnedTeams() {
        List<Team> test = new ArrayList<>();
        test.add(new Team());
        test.get(0).setTeamName("testName");
        return test;
    }
}
