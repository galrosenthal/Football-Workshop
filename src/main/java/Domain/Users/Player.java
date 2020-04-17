package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Player extends Role {

    private PlayerFieldJobs fieldJob;
    private List<Team> playerTeams;
    private Date bday;

    public Player(SystemUser systemUser) {
        super(RoleTypes.PLAYER, systemUser);
        playerTeams= new ArrayList<>();
    }

    public Player(SystemUser systemUser,
                  PlayerFieldJobs fieldJob, Date brthDay) {
        super(RoleTypes.PLAYER, systemUser);
        this.fieldJob = fieldJob;
        bday = brthDay;
        playerTeams= new ArrayList<>();
    }


    public boolean addTeam(Team playTeam){
        if(!this.playerTeams.contains(playTeam)) {
            this.playerTeams.add(playTeam);
            return true;
        }
        return false;
    }

    public boolean removeTeam(Team team){
        return this.playerTeams.remove(team);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return fieldJob == player.fieldJob &&
                Objects.equals(bday, player.bday);
    }

    public List<Team> getPlayerTeams() {
        return playerTeams;
    }
}
