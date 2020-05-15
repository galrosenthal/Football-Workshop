package Domain.Logger;

import Domain.Game.Team;

public class Offside extends Event {
    private Team teamWhoCommitted;
    private int offsideMinute;

    public Offside(Team teamWhoCommitted, int offsideMinute) {
        this.teamWhoCommitted = teamWhoCommitted;
        this.offsideMinute = offsideMinute;
    }
}
