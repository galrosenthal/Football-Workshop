package Domain.Logger;

import Domain.Game.Team;

public class Penalty extends Event {
    private Team teamWhoCommitted;
    private int penaltyMinute;

    public Penalty(Team teamWhoCommitted) {
        this.teamWhoCommitted = teamWhoCommitted;
    }
}
