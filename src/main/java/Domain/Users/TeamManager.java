package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamManager extends Role {
    private List<Team> teamsManaged;

    public TeamManager(SystemUser systemUser) {
        super(RoleTypes.TEAM_MANAGER, systemUser);
        teamsManaged = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        TeamManager teamManager = (TeamManager) o;
        return this.getSystemUser().getName().equals(teamManager.getSystemUser().getName()) &&
                this.getSystemUser().getUsername().equals(teamManager.getSystemUser().getUsername());
    }

    public boolean addTeam(Team team){
        if(!this.teamsManaged.contains(team)) {
            this.teamsManaged.add(team);
            return true;
        }
        return false;
    }

    public boolean removeTeam(Team team){
        return this.teamsManaged.remove(team);
    }

    public List<Team> getTeamsManaged() {
        return teamsManaged;
    }
}
