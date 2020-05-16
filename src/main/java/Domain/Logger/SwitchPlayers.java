package Domain.Logger;

import Domain.Game.Team;
import Domain.Users.Player;

public class SwitchPlayers extends Event {
    private Team team;
    private Player enteringPlayer;
    private Player exitingPlayer;

    public SwitchPlayers(Team team, Player enteringPlayer, Player exitingPlayer, int minute) {
        super(minute);
        this.team = team;
        this.enteringPlayer = enteringPlayer;
        this.exitingPlayer = exitingPlayer;
    }

    public Team getTeam() {
        return team;
    }

    public Player getEnteringPlayer() {
        return enteringPlayer;
    }

    public Player getExitingPlayer() {
        return exitingPlayer;
    }

    @Override
    public String toString() {
        return "SwitchPlayers team=" + team.getTeamName() +", enteringPlayer=" + enteringPlayer.getAssetName() +", exitingPlayer=" + exitingPlayer.getAssetName() +" "+super.toString();
    }
}
