package Domain.Logger;

import Domain.Game.Team;

public class Penalty extends Event {
    private Team teamWhoCommitted;

    public Penalty(Team teamWhoCommitted, int penaltyMinute) {
        super(penaltyMinute);
        this.teamWhoCommitted = teamWhoCommitted;
    }

    public Team getTeamWhoCommitted() {
        return teamWhoCommitted;
    }

    @Override
    public String toString() {
        return super.toString()+" Penalty teamWhoCommitted=" + teamWhoCommitted.getTeamName()+".";
    }
}
