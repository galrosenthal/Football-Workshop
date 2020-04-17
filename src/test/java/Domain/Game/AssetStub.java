package Domain.Game;

import Domain.Users.PlayerFieldJobs;
import Domain.Users.TeamOwner;

import java.util.ArrayList;
import java.util.List;

public class AssetStub implements Asset {

    private int selector=0;

    public AssetStub(int selector) {
        this.selector = selector;
    }

    @Override
    public List<String> getProperties() {
        if(this.selector == 6132)
        {
            List<String> properties = new ArrayList<>();
            return properties;
        }
        else if(this.selector == 6133 || this.selector == 6134 ||this.selector == 6135 ||this.selector == 6136 || this.selector == 6137)
        {
            List<String> properties = new ArrayList<>();
            properties.add("Property Test");
            return properties;
        }
        return null;
    }

    @Override
    public boolean addTeam(Team team, TeamOwner teamOwner) {
        return false;
    }

    @Override
    public boolean changeProperty(String propertyToChange, String newValue) {

        if(selector == 6135 || selector ==6136)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isListProperty(String property) {
        if(this.selector == 6133 || this.selector == 6134)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isStringProperty(String property) {
        if(this.selector == 6136 || this.selector == 6137)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEnumProperty(String property) {
        if(this.selector == 6135)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean addProperty(String property) {
        return false;
    }

    @Override
    public boolean addAllProperties() {
        return false;
    }

    @Override
    public boolean removeProperty(String property) {
        return false;
    }


    @Override
    public List<Enum> getAllValues(String property) {

        if(this.selector == 6135)
        {
            List<Enum> enumList = new ArrayList<>();
            enumList.add(PlayerFieldJobs.DEFENSE);
            return enumList;
        }
        return null;
    }

    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName) {
        if(this.selector == 6132)
        {
            List<Enum> enumList = new ArrayList<>();
            return enumList;
        }
        return null;
    }

    @Override
    public boolean addProperty(String propertyName, Enum anEnum , Team team) {
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName, Enum anEnum, Team team) {
        return false;
    }
}
