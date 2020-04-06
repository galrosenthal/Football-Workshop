package Users;

import Game.Team;

import java.util.Date;


public class Player extends Registered {

    private PlayerFieldJobs fieldJob;
    private Team playerTeam;
    private Date bday;

    public Player(String type, String username, String pass, String name,
                  PlayerFieldJobs fieldJob, Team playTeam, Date brthDay) {
        super(RegisteredTypes.PLAYER, username, pass, name);
        this.fieldJob = fieldJob;
        playerTeam = playTeam;
        bday = brthDay;
    }



}
