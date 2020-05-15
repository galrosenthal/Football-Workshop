package Domain.Game;

import Domain.Logger.EventsLogger;
import Domain.Users.Referee;

import java.util.Date;
import java.util.List;

public class Game {

    private Stadium stadium;
    private Team homeTeam;
    private Team awayTeam;
    private Date gameDate;
    private List<Referee> referees; // - maybe array?
    private EventsLogger eventsLogger;
    //TODO: should be more properties

    public Game(Stadium stadium, Team homeTeam, Team awayTeam, Date gameDate, List<Referee> referees) {
        this.stadium = stadium;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
        this.referees = referees;
        this.eventsLogger = new EventsLogger();
        //TODO: Add to EntityManager?
    }

    public Stadium getStadium() {
        return stadium;
    }

    //TODO: can be change if someone put wrong Stadium- only by user who has permission!
//    public void setStadium(Stadium stadium) {
//        this.stadium = stadium;
//    }
//
//    /**
//     * Returns the score of the game
//     *
//     * @return - Score - the score of the game, each team and it's goal count
//     */
//    public Score getScore() {
//        List<Goal> goals = eventsLogger.getGoals();
//        int homeTeamGoals = 0;
//        int awayTeamGoals = 0;
//        if(goals != null) {
//            for (Goal goal : goals) {
//                if (goal.getScoringTeam().equals(homeTeam)) {
//                    homeTeamGoals++;
//                } else {
//                    awayTeamGoals++;
//                }
//            }
//        }
//        return new Score(homeTeam, awayTeam, homeTeamGoals, awayTeamGoals);
//    }

    /**
     * Returns true if the game has finished
     *
     * @return - boolean - true if the game has finished, else false
     */
    public boolean hasFinished() {
        //TODO: Check dates or game status
        return true;
    }

//    /**
//     * Adds a goal to the game.
//     *
//     * @param scoringTeam  - Team - The scoring team - must play in this game
//     * @param scoredOnTeam - Team - The scored on team - must play in this game
//     * @param minute       - int - The minute the goal was scored - positive integer
//     */
//    public void addGoal(Team scoringTeam, Team scoredOnTeam, int minute) throws Exception {
//        if ((!scoringTeam.equals(homeTeam)) && (!scoringTeam.equals(awayTeam))) {
//            throw new IllegalArgumentException("The given scoringTeam doesn't play in this game");
//        }
//        if ((!scoredOnTeam.equals(homeTeam)) && (!scoredOnTeam.equals(awayTeam))) {
//            throw new IllegalArgumentException("The given scoredOnTeam doesn't play in this game");
//        }
//        if (scoredOnTeam.equals(scoringTeam)) {
//            throw new IllegalArgumentException("The teams given are the same team");
//        }
//        if (minute < 0) {
//            throw new IllegalArgumentException("minute must be positive integer");
//        }
//        this.eventsLogger.logGoal(scoringTeam, scoredOnTeam, minute);
//    }


    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    public EventsLogger getEventsLogger() {
        return eventsLogger;
    }
}
