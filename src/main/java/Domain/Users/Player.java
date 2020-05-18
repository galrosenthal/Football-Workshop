package Domain.Users;

import Domain.Game.Team;
import Service.UIController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Player extends PartOfTeam{

    private PlayerFieldJobs fieldJob;
    private List<Team> playerTeams;
    private Date bday;

    public final String fieldJobString = "Filed Job";

    public Player(SystemUser systemUser,Date birthDate) {
        super(RoleTypes.PLAYER, systemUser);
        playerTeams= new ArrayList<>();
        bday = birthDate;
    }


    public boolean addTeam(Team playTeam){
        if(!this.playerTeams.contains(playTeam)) {
            this.playerTeams.add(playTeam);
        }
        return false;
    }


    @Override
    public String getAssetName()
    {
        return this.systemUser.getUsername();
    }

    @Override
    public List<String> getProperties() {
        List<String>properties = new ArrayList<>();
        properties.add(fieldJobString);
        return properties;
    }

    @Override
    public boolean addTeam(Team team, TeamOwner teamOwner) {
        if(team != null)
        {
            playerTeams.add(team);
            return team.addTeamPlayer(teamOwner,this);

        }
        return false;
    }

    @Override
    public boolean changeProperty(String property, String toChange)
    {
        if(property.equalsIgnoreCase(fieldJobString))
        {
            this.fieldJob = PlayerFieldJobs.valueOf(toChange);
            return true;
        }
        return false;
    }

    @Override
    public boolean isListProperty(String property) {
        return false;
    }

    @Override
    public boolean isStringProperty(String property) {
        return false;
    }

    @Override
    public boolean isEnumProperty(String property) {
        if(property.equalsIgnoreCase(fieldJobString))
        {
            return true;
        }
        return false;
    }

    public boolean removeTeam(Team team){
        return this.playerTeams.remove(team);
    }




    @Override
    public boolean addAllProperties() {
        return addProperty(fieldJobString);
    }

    @Override
    public boolean addProperty(String property) {
        if(property.equalsIgnoreCase(fieldJobString))
        {
            int fieldJobIndex = getEnumIndex();
            return this.changeProperty(property,PlayerFieldJobs.values()[fieldJobIndex].toString());
        }

        return false;

    }

    private int getEnumIndex() {

        List<String> playerJobsList = new ArrayList<>();
        for (PlayerFieldJobs pfj: PlayerFieldJobs.values())
        {
            playerJobsList.add(pfj.name());
        }

        int index;

        do{
            index = UIController.receiveInt("Please Choose a field Job for the Player:",playerJobsList);
        }while (!(index >= 0 && index < PlayerFieldJobs.values().length));



        return index;
    }

    @Override
    public boolean removeProperty(String property) {
        return false;
    }

    @Override
    public List<Enum> getAllValues(String property) {
        List<Enum> allEnumValues = new ArrayList<>();
        if(property.equals(fieldJobString))
        {
            PlayerFieldJobs[] playerFieldJobs = PlayerFieldJobs.values();
            for (int i = 0; i < playerFieldJobs.length; i++) {
                allEnumValues.add(playerFieldJobs[i]);
            }
            return allEnumValues;
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return fieldJob == player.fieldJob &&
                Objects.equals(bday, player.bday)&&
                super.systemUser.equals(player.systemUser);

    }

    public List<Team> getPlayerTeams() {
        return playerTeams;
    }

}
