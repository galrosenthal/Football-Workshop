package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerStub extends Player{
    private int selector;
    public PlayerStub(SystemUser user, Date bdate) {

        super(user,bdate);
    }

    public PlayerStub(SystemUser user, Date bdate , int selector) {
        super(user,bdate);
        this.selector = selector;
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public boolean addAllProperties() {
        return true;
    }

    @Override
    public List<String> getProperties()
    {
        if (this.selector == 6132)
        {
            return new ArrayList<>();
        }
        if(this.selector == 6133)
        {
            List<String> propertiesTest = new ArrayList<>();
            propertiesTest.add("Test");
            return  propertiesTest;
        }
        return null;
    }
    @Override
    public boolean isListProperty(String property)
    {
        if(this.selector == 6133)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isStringProperty(String property)
    {
        return false;
    }

    @Override
    public boolean isEnumProperty(String property)
    {
        return false;
    }


    @Override
    public boolean addTeam(Team playTeam, TeamOwner teamOwner) {
        if(selector == 0)
        {
            return super.addTeam(playTeam,teamOwner);
        }

        return true;
    }


    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName)
    {
        return null;
    }

}
