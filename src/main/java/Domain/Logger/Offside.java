package Domain.Logger;

import Domain.Game.Team;

public class Offside extends Event {
    private Team teamWhoCommitted;

    public Offside(Team teamWhoCommitted, int offsideMinute) {
        super(offsideMinute);
        this.teamWhoCommitted = teamWhoCommitted;
    }

    public Team getTeamWhoCommitted() {
        return teamWhoCommitted;
    }

    @Override
    public String toString() {
        return "Offside teamWhoCommitted=" + teamWhoCommitted.getTeamName() +" "+super.toString();
    }
}
