package Domain.Game;

import Domain.EntityManager;
import Domain.Users.TeamOwner;
import Service.UIController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stadium implements Asset{

    private String stadName;
    private String stadLocation;
    private List<Team> homeTeams;

    public final String namePropertyString = "Name";


    public Stadium(String location)
    {
        homeTeams = new ArrayList<>();
        stadLocation = location;
    }



    public Stadium(String stadName, String stadLocation) {
        this(stadLocation);
        this.stadName = stadName;
        EntityManager.getInstance().addStadium(this);
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
        if(property.equalsIgnoreCase(namePropertyString))
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
                stadLocation.equals(stadium.stadLocation) &&
                homeTeams.size() == stadium.homeTeams.size();
    }
    @Override
    public boolean isListProperty(String property) {
        return false;
    }

    @Override
    public boolean isStringProperty(String property) {
        if(property.equalsIgnoreCase(namePropertyString))
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
        return addProperty(namePropertyString);
    }

    @Override
    public boolean addProperty(String property) {
        String stringProp = "";
        if(property.equalsIgnoreCase(namePropertyString))
        {
            UIController.printMessage("Please Enter Stadium name");

            stringProp = UIController.receiveString();
            changeProperty(property,stringProp);
        }

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
    public List<Enum> getAllPropertyList(Team team, String propertyName) {
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
