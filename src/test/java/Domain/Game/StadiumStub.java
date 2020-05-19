package Domain.Game;

import Domain.Users.TeamOwner;

import java.util.ArrayList;
import java.util.List;

public class StadiumStub extends Stadium{

    private int selector;
    private List<Team> homeTeams;

    /**
     * Latest Selector number: 1
     * @param name
     * @param location
     */
    public StadiumStub(String name,String location) {
        super(name,location);
        homeTeams = new ArrayList<>();
        selector = 0;
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public boolean addAllProperties(Team t) {
        return true;
    }

    @Override
    public boolean addTeam(Team team, TeamOwner teamOwner) {
        this.homeTeams.add(team);
        return true;
    }

    public boolean addTeam (Team team){
        return this.homeTeams.add(team);
    }

    @Override
    public boolean removeTeam(Team team) {
        return this.homeTeams.remove(team);
    }

    @Override
    public List<Team> getTeams() {
        return this.homeTeams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (selector == 0){
            return true;
        }else if (selector == 1){
            return false;
        }
        else
        {
            return false;
        }
    }

    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName)
    {
        return null;
    }
}
