package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamManager extends Role implements Asset {
    public TeamManager(SystemUser systemUser) {
        super(RoleTypes.TEAM_MANAGER, systemUser);
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
    public void addProperty() {

    }

    @Override
    public void removeProperty() {

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
