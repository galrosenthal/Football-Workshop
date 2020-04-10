package Domain.Users;

import Domain.Game.Team;

public class TeamManager extends Role {
    public TeamManager(String username, String pass, String name) {
        super(RoleTypes.TEAM_MANAGER, username, pass, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        TeamManager teamManager = (TeamManager) o;
        return this.getName().equals(teamManager.getName()) &&
                this.getUsername().equals(teamManager.getUsername());
    }
}
