package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TeamManager extends Role implements Asset {
    private List<Team> managedTeams;
    private HashMap<Team , List<TeamManagerPermissions>> permissionsPerTeam;

    public final String permissionsString = "Permissions";

    public TeamManager(SystemUser systemUser)
    {
        super(RoleTypes.TEAM_MANAGER, systemUser);
        managedTeams = new ArrayList<>();
        permissionsPerTeam = new HashMap<>();
    }


    public boolean addTeam(Team teamToMange, TeamOwner teamOwner)
    {
        if(teamToMange != null && teamToMange.isTeamOwner(teamOwner))
        {
            managedTeams.add(teamToMange);
            if(teamToMange.addTeamManager(teamOwner,this))
            {
                List<TeamManagerPermissions> permissions = new ArrayList<>();
                this.permissionsPerTeam.put(teamToMange , permissions);

                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getAssetName()
    {
        return this.systemUser.getUsername();
    }

    private boolean addPermission(Team team , TeamManagerPermissions permission)
    {
        return false;
    }

    private boolean removePermission(Team team , TeamManagerPermissions permission)
    {
        return false;
    }

    private List<TeamManagerPermissions> getAllPermissionsPerTeam(Team team)
    {
        List<TeamManagerPermissions> permissions = new ArrayList<>();
        if(this.permissionsPerTeam.get(team) != null)
        {
            permissions = this.permissionsPerTeam.get(team);
        }
        return permissions;
    }

    @Override
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        properties.add(permissionsString);
        return properties;
    }

    @Override
    public boolean changeProperty(String toChange, String property)
    {
        return false;
    }

    @Override
    public boolean isListProperty(String property) {
        if(property.equals(this.permissionsString))
        {
            return true;
        }
        return false;

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
        return true;
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

        List<Enum> allEnumValues = new ArrayList<>();
        if (property.equals(permissionsString))
        {
            TeamManagerPermissions[] teamManagerPermissions = TeamManagerPermissions.values();
            for (int i = 0; i < teamManagerPermissions.length; i++) {
                allEnumValues.add(teamManagerPermissions[i]);
            }
            return allEnumValues;
        }
        return null;
    }

    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName) {
        if(propertyName.equals(this.permissionsString)) {
            List<TeamManagerPermissions> permissions = this.getAllPermissionsPerTeam(team);
            List<Enum> enumList = new ArrayList<>();
            for (int i = 0; i < permissions.size(); i++) {
                enumList.add(permissions.get(i));
            }
            return enumList;
        }
        return null;
    }

    @Override
    public boolean addProperty(String propertyName, Enum anEnum , Team team) {
        if(propertyName.equals(this.permissionsString))
        {
            List<TeamManagerPermissions> permissions = this.getAllPermissionsPerTeam(team);
            if(!(permissions.contains(anEnum)))
            {
                permissions.add((TeamManagerPermissions) anEnum);
                this.permissionsPerTeam.put(team, permissions);
                return true;
            }

        }
        return  false;
    }

    @Override
    public boolean removeProperty(String propertyName, Enum anEnum, Team team) {
        if(propertyName.equals(this.permissionsString))
        {
            List<TeamManagerPermissions> permissions = this.getAllPermissionsPerTeam(team);
            if(permissions.contains(anEnum))
            {
                permissions.remove(anEnum);
                this.permissionsPerTeam.put(team, permissions);
                return true;
            }

        }
        return  false;
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
        if(!this.managedTeams.contains(team)) {
            this.managedTeams.add(team);
            return true;
        }
        return false;
    }

    public boolean removeTeam(Team team){
        return this.managedTeams.remove(team);
    }

    public List<Team> getTeamsManaged() {
        return managedTeams;
    }

}
