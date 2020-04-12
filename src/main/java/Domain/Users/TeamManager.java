package Domain.Users;

import Domain.Game.Team;

public class TeamManager extends Role {
    public TeamManager(SystemUser systemUser) {
        super(RoleTypes.TEAM_MANAGER, systemUser);
    }

    @Override
    public String[] getProperties() {
        String [] properties = new String[3];
        return properties;
    }
    @Override
    public boolean changeProperty(String property)
    {
        return false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        TeamManager teamManager = (TeamManager) o;
        return this.getSystemUser().getName().equals(teamManager.getSystemUser().getName()) &&
                this.getSystemUser().getUsername().equals(teamManager.getSystemUser().getUsername());
    }
}
