package Domain.Game;

import Domain.EntityManager;
import Domain.Logger.EventsLogger;
import Domain.Users.Referee;
import Domain.Users.SystemUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    Stadium stadium;
    Team homeTeam;
    Team awayTeam;
    Date gameDate;
    Score score;
    List<Referee> referees; // - maybe array?
    EventsLogger eventsLogger;

    //TODO: should be more properties

    public Game(Stadium stadium, Team homeTeam, Team awayTeam, Date gameDate , List<Referee> referees) {
        this.stadium = stadium;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
        this.score = new Score();
        this.referees = referees;
        this.eventsLogger = new EventsLogger();
    }

    //TODO: ADD CONSTRUCTOR!

    public Stadium getStadium() {
        return stadium;
    }

    //TODO: can be change if someone put wrong Stadium- only by user who has permission!
    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }


    public Date getGameDate()
    {
        return this.gameDate;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
