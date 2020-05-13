package Domain.Game;

import Domain.Logger.EventsLogger;
import Domain.Logger.Goal;
import Domain.Users.Referee;

import java.util.Date;
import java.util.List;

public class Game {

    Stadium stadium;
    Team homeTeam;
    Team awayTeam;
    Date gameDate;
    List<Referee> referees; // - maybe array?
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

    /**
     * Returns the score of the game
     *
     * @return - Score - the score of the game, each team and it's goal count
     */
    public Score getScore() {
        List<Goal> goals = eventsLogger.getGoals();
        int homeTeamGoals = 0;
        int awayTeamGoals = 0;
        for (Goal goal : goals) {
            if(goal.getScoringTeam().equals(homeTeam)){
                homeTeamGoals++;
            }else {
                awayTeamGoals++;
            }
        }
        return new Score(homeTeam,awayTeam,homeTeamGoals,awayTeamGoals);
    }

    /**
     * Returns true if the game has finished
     * @return - boolean - true if the game has finished, else false
     */
    public boolean hasFinished() {
        //TODO: Check dates or game status
        return true;
    }
}
