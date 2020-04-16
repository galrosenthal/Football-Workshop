package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamManager extends Role implements Asset {
    private List<Team> managedTeams;
    public TeamManager(SystemUser systemUser)
    {
        super(RoleTypes.TEAM_MANAGER, systemUser);
        managedTeams = new ArrayList<>();
    }


    public boolean addTeam(Team teamToMange, TeamOwner teamOwner)
    {
        if(teamToMange != null && teamToMange.isTeamOwner(teamOwner))
        {
            managedTeams.add(teamToMange);
            return teamToMange.addTeamManager(teamOwner,this);
        }
        return false;
    }


    //todo: add permissions!
    @Override
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        return properties;
    }

    @Override
    public boolean changeProperty(String toChange, String property)
    {
        return false;
    }

    @Override
    public boolean isListProperty(String property) {
        return true;
    }

    @Override
    public boolean isStringProperty(String property) {
        return false;
    }

    @Override
    public boolean isEnumProperty(String property) {
        return false;
    }


    @Override
    public boolean addAllProperties() {
        return false;
    }

    @Override
    public boolean addProperty(String property) {
        return false;

    }

    @Override
    public boolean removeProperty(String property) {
        return false;
    }

    @Override
    public List<Enum> getAllValues(String property) {
        return null;
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
