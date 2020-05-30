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



    public Stadium(String stadName, String stadLocation, boolean addToDB) {
        this.homeTeams = new ArrayList<>();
        this.stadLocation = stadLocation;
        this.stadName = stadName;
        if(addToDB){
            EntityManager.getInstance().addStadium(this);
        }
    }

    @Override
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        properties.add(namePropertyString);
        return properties;
    }


    @Override
    public String getAssetName()
    {
        return getName();
    }

    @Override
    public boolean changeProperty(Team teamOfAsset, String property, String toChange)
    {
        if(property.equalsIgnoreCase(namePropertyString))
        {
            /*TODO: update stadium name*/

            EntityManager.getInstance().updateStadiumName(this , toChange);
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
            /*already update in team*/
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
    public boolean addAllProperties(Team teamOfAsset) {
        return addProperty(teamOfAsset, namePropertyString);
    }

    @Override
    public boolean addProperty(Team teamOfAsset, String property) {
        String stringProp = "";
        if(property.equalsIgnoreCase(namePropertyString))
        {
            stringProp = UIController.receiveString("Please Enter Stadium name");
            return changeProperty(teamOfAsset, property,stringProp);
        }

        return false;
    }



    @Override
    public boolean removeProperty(Team teamOfAsset, String property) {
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

    public boolean removeTeam(Team team){
        /*already update db in team*/
        return this.homeTeams.remove(team);
    }

    public List<Team> getTeams() {

        homeTeams = EntityManager.getInstance().getAllStadiumTeams(this);
        return this.homeTeams;
    }
}
