package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerStub extends Player{
    private int selector;
    private List<Team> teamsOfTest;

    public PlayerStub(SystemUser systemUser) {
        this(systemUser, null);
    }

    public PlayerStub(SystemUser user, Date bdate) {
        super(user,bdate, true);
        teamsOfTest = new ArrayList<>();
    }

    public PlayerStub(SystemUser user, Date bdate , int selector) {
        this(user,bdate);
        this.selector = selector;
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public boolean addAllProperties(Team team) {
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
            this.teamsOfTest.add(playTeam);
            return super.addTeam(playTeam,teamOwner);
        }

        return true;
    }



    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName)
    {
        return null;
    }


    @Override
    public List<Team> getTeams() {
        return teamsOfTest;
    }

    @Override
    public boolean removeTeam(Team teamToRemove) {
        return this.teamsOfTest.remove(teamToRemove);
    }
}
