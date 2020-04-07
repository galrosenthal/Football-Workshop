package Domain.Users;

import Domain.Game.Team;

import java.util.Date;
import java.util.Objects;


public class Player extends Registered {

    private PlayerFieldJobs fieldJob;
    private Team playerTeam;
    private Date bday;

    public Player(String username, String pass, String name,
                  PlayerFieldJobs fieldJob, Team playTeam, Date brthDay) {
        super(RegisteredTypes.PLAYER, username, pass, name);
        this.fieldJob = fieldJob;
        playerTeam = playTeam;
        bday = brthDay;
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
