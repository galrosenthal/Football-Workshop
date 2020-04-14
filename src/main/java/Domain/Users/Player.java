package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Player extends Role implements Asset {

    private PlayerFieldJobs fieldJob;
    private Team playerTeam;
    private Date bday;

    public Player(SystemUser systemUser, PlayerFieldJobs fieldJob, Date brthDay) {
        super(RoleTypes.PLAYER, systemUser);
        this.fieldJob = fieldJob;
        bday = brthDay;
    }

    public boolean addTeam(Team playTeam)
    {
        if(playerTeam == null)
        {
            playerTeam = playTeam;
            return true;
        }
        return false;
    }

    @Override
    public List<String> getProperties() {
        List<String>properties = new ArrayList<>();
        properties.add("Field Job");
        return properties;
    }

    @Override
    public boolean changeProperty(String property, String toChange)
    {
        if(property.equals("Field Job"))
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
        if(property.equals("Field Job"))
        {
            return true;
        }
        return false;
    }

    @Override
    public void addProperty() {

    }

    @Override
    public void removeProperty() {

    }

    @Override
    public List<Enum> getAllValues(String property) {
        List<Enum> allEnumValues = new ArrayList<>();
        if(property.equals("Qualification"))
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
                Objects.equals(bday, player.bday);
    }

}
