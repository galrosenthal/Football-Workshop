package Domain.Game;

import Domain.Users.TeamOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stadium implements Asset{

    private String stadName;
    private String stadLocation;
    private List<Team> homeTeams;



    public Stadium(String location)
    {
        homeTeams = new ArrayList<>();
        stadLocation = location;
    }



    public Stadium(String stadName, String stadLocation) {
        this(stadLocation);
        this.stadName = stadName;
        this.stadLocation = stadLocation;
    }

    @Override
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        properties.add("Name");
        return properties;
    }

    @Override
    public boolean changeProperty(String property, String toChange)
    {
        if(property.equalsIgnoreCase("Name"))
        {
            this.stadName=toChange;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stadium)) return false;
        Stadium stadium = (Stadium) o;
        return stadName.equals(stadium.stadName) &&
                stadLocation.equals(stadium.stadLocation);
    }
    @Override
    public boolean isListProperty(String property) {
        return false;
    }

    @Override
    public boolean isStringProperty(String property) {
        if(property.equals("Name"))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean addTeam(Team team, TeamOwner teamOwner) {
        if(team != null && team.isTeamOwner(teamOwner))
        {
            homeTeams.add(team);
            return team.addStadium(this);
        }
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
    public int hashCode() {
        return Objects.hash(stadName, stadLocation);
    }


    public String getName() {
        return stadName;
    }

    public String getLocation() {
        return stadLocation;
    }
}
