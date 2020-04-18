package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;
import Service.UIController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Player extends Role implements Asset {

    private PlayerFieldJobs fieldJob;
    private Team playerTeam;
    private Date bday;

    public final String fieldJobString = "Filed Job";

    public Player(SystemUser systemUser,Date birthDate) {
        super(RoleTypes.PLAYER, systemUser);
        bday = birthDate;
    }

    public boolean addTeam(Team playTeam,TeamOwner teamOwner)
    {
        if(playTeam != null)
        {
            playerTeam = playTeam;
            return playerTeam.addTeamPlayer(teamOwner,this);

        }
        return false;
    }

    @Override
    public List<String> getProperties() {
        List<String>properties = new ArrayList<>();
        properties.add(fieldJobString);
        return properties;
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
        UIController.printMessage("Please Choose a field Job for the Player:");

        int i = 0;
        for (PlayerFieldJobs pfj: PlayerFieldJobs.values())
        {
            UIController.printMessage(i++ +". " + pfj.toString());
        }

        int index;

        do{
            index = UIController.receiveInt();
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
                //todo: check!
                allEnumValues.add(playerFieldJobs[i]);
            }
            return allEnumValues;
        }
        return allEnumValues;
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

}
