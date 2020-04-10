package Domain.Users;

import Domain.Game.Team;

import java.util.Date;
import java.util.Objects;


public class Player extends Role {

    private PlayerFieldJobs fieldJob;
    private Team playerTeam;
    private Date bday;

    public Player(SystemUser systemUser,
                  PlayerFieldJobs fieldJob, Date brthDay) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return fieldJob == player.fieldJob &&
                Objects.equals(bday, player.bday);
    }

}
