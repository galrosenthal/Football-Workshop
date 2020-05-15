package Domain.Logger;

import Domain.Game.Team;
import Domain.Users.Player;

public class SwitchPlayers extends Event {
    private Team team;
    private Player enteringPlayer;
    private Player exitingPlayer;
    private int minute;
}
