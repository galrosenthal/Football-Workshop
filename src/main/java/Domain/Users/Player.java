package Domain.Users;

import Domain.Game.Team;
import Service.UIController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Player extends PartOfTeam{

    private Date bday;

    public final String fieldJobString = "Filed Job";

    public Player(SystemUser systemUser,Date birthDate) {
        super(RoleTypes.PLAYER, systemUser);
        bday = birthDate;
    }



    @Override
    public List<String> getProperties() {
        List<String>properties = new ArrayList<>();
        properties.add(fieldJobString);
        return properties;
    }


    @Override
    public boolean changeProperty(Team teamOfAsset, String property, String toChange)
    {
        if(property.equalsIgnoreCase(fieldJobString))
        {
//            this.fieldJob =
            BelongToTeam assetBelongsTo = getBelongTeamByTeam(teamOfAsset);
            if(assetBelongsTo == null)
            {
                return false;
            }
            assetBelongsTo.setTeamJob(toChange);
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



    @Override
    public boolean addAllProperties(Team teamOfAsset) {
        return addProperty(teamOfAsset, fieldJobString);
    }

    @Override
    public boolean addProperty(Team teamOfAsset, String property) {
        if(property.equalsIgnoreCase(fieldJobString))
        {
            int fieldJobIndex = getEnumIndex();

            return this.changeProperty(teamOfAsset, property,PlayerFieldJobs.values()[fieldJobIndex].toString());
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
    public boolean removeProperty(Team teamOfAsset, String property) {
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
        return Objects.equals(bday, player.bday)&&
                super.systemUser.equals(player.systemUser);

    }



}
