package Domain.Game;

import Domain.Logger.EventsLogger;
import Domain.Users.Refree;

import java.util.Date;
import java.util.List;

public class Game {

    Stadium stadium;
    Team homeTeam;
    Team awayTeam;
    Date gameDate;
    Score score;
    List<Refree> referees; // - maybe array?
    EventsLogger eventsLogger;
    //TODO: should be more properties


    //TODO: ADD CONSTRUCTOR!

    public Stadium getStadium() {
        return stadium;
    }

    //TODO: can be change if someone put wrong Stadium- only by user who has permission!
    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }
}
