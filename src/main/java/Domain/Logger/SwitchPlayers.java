package Domain.Logger;

import Domain.Game.Team;
import Domain.Users.Player;

public class SwitchPlayers extends Event {
    private Team team;
    private Player enteringPlayer;
    private Player exitingPlayer;
    private int minute;

    public SwitchPlayers(Team team, Player enteringPlayer, Player exitingPlayer, int minute) {
        this.team = team;
        this.enteringPlayer = enteringPlayer;
        this.exitingPlayer = exitingPlayer;
        this.minute = minute;
    }
}
