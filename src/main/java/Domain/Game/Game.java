package Domain.Game;

import Domain.EntityManager;
import Domain.Logger.EventsLogger;
import Domain.Logger.Goal;
import Domain.Users.Player;
import Domain.Users.Referee;
import Domain.Users.SystemUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private Stadium stadium;
    private Team homeTeam;
    private Team awayTeam;
    private Date gameDate;
    private List<Referee> referees; // - maybe array?
    private EventsLogger eventsLogger;
    private boolean hasFinished;
    //TODO: should be more properties

    public Game(Stadium stadium, Team homeTeam, Team awayTeam, Date gameDate, List<Referee> referees) {
        this.stadium = stadium;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
        this.referees = referees;
        this.eventsLogger = new EventsLogger();
        this.hasFinished = false;
        //TODO: Add to EntityManager?
    }

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
        if (goals != null) {
            for (Goal goal : goals) {
                if (goal.getScoringTeam().equals(homeTeam)) {
                    homeTeamGoals++;
                } else {
                    awayTeamGoals++;
                }
            }
        }
        return new Score(homeTeam, awayTeam, homeTeamGoals, awayTeamGoals);
    }

    /**
     * Returns true if the game has finished
     *
     * @return - boolean - true if the game has finished, else false
     */
    public boolean hasFinished() {
        //TODO: Check dates or game status
        return hasFinished;
    }


    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    public EventsLogger getEventsLogger() {
        return eventsLogger;
    }


    public List<String> getGameEventsStringList() {
        return this.eventsLogger.getEventsStringList();
    }

    /**
     * Returns the players of both teams
     *
     * @return - List<Player> - a list of the players of both teams
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        players.addAll(this.homeTeam.getTeamPlayers());
        players.addAll(this.awayTeam.getTeamPlayers());
        return players;
    }

    /**
     * Returns the teams playing in this game
     *
     * @return - List<Team> - A list of the teams playing in this game
     */
    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(this.homeTeam);
        teams.add(this.awayTeam);
        return teams;
    }

    /**
     * Adds a goal to the game.
     *
     * @param scoringTeam  - Team - The scoring team - must play in this game
     * @param scoredOnTeam - Team - The scored on team - must play in this game
     * @param minute       - int - The minute the goal was scored - positive integer
     * @throws IllegalArgumentException - if the arguments aren't valid
     */
    public void addGoal(Team scoringTeam, Team scoredOnTeam, int minute) throws IllegalArgumentException {
        if ((!scoringTeam.equals(homeTeam)) && (!scoringTeam.equals(awayTeam))) {
            throw new IllegalArgumentException("The given scoring Team doesn't play in this game");
        }
        if ((!scoredOnTeam.equals(homeTeam)) && (!scoredOnTeam.equals(awayTeam))) {
            throw new IllegalArgumentException("The given scored On Team doesn't play in this game");
        }
        if (scoredOnTeam.equals(scoringTeam)) {
            throw new IllegalArgumentException("The teams given are the same team");
        }
        if (minute < 0) {
            throw new IllegalArgumentException("minute must be positive integer");
        }
        this.eventsLogger.logGoal(scoringTeam, scoredOnTeam, minute);
    }

    /**
     * Adds a card event to the game
     *
     * @param cardType - String - The type of the card
     * @param player   - Player - The player who got the card
     * @param minute   - int - The minute the event happened
     * @throws IllegalArgumentException - if the arguments aren't valid
     */
    public void addCard(String cardType, Player player, int minute) throws IllegalArgumentException {
        if ((!this.homeTeam.getTeamPlayers().contains(player)) && (!this.awayTeam.getTeamPlayers().contains(player))) {
            throw new IllegalArgumentException("The given player doesn't play in this game");
        }
        if (minute < 0) {
            throw new IllegalArgumentException("minute must be positive integer");
        }
        this.eventsLogger.logCardEvent(cardType, player, minute);
    }

    /**
     * Adds an offside event to the game
     *
     * @param teamWhoCommitted - Team - The team which committed the offside
     * @param minute           - int - The minute the event happened
     * @throws IllegalArgumentException - if the arguments aren't valid
     */
    public void addOffside(Team teamWhoCommitted, int minute) throws IllegalArgumentException {
        if ((!teamWhoCommitted.equals(homeTeam)) && (!teamWhoCommitted.equals(awayTeam))) {
            throw new IllegalArgumentException("The given team doesn't play in this game");
        }
        if (minute < 0) {
            throw new IllegalArgumentException("minute must be positive integer");
        }
        this.eventsLogger.logOffsideEvent(teamWhoCommitted, minute);
    }

    /**
     * Adds a penalty event to the game
     *
     * @param teamWhoCommitted - Team - The team which committed the penalty
     * @param minute           - int - The minute the event happened
     * @throws IllegalArgumentException - if the arguments aren't valid
     */
    public void addPenalty(Team teamWhoCommitted, int minute) throws IllegalArgumentException {
        if ((!teamWhoCommitted.equals(homeTeam)) && (!teamWhoCommitted.equals(awayTeam))) {
            throw new IllegalArgumentException("The given team doesn't play in this game");
        }
        if (minute < 0) {
            throw new IllegalArgumentException("minute must be positive integer");
        }
        this.eventsLogger.logPenaltyEvent(teamWhoCommitted, minute);
    }

    /**
     * Adds a players switch event to the game
     *
     * @param teamWhoCommitted - Team - The team which committed the switch
     * @param enteringPlayer   - Player - The player who is entering the game
     * @param exitingPlayer    - Player - The player who is exiting the game
     * @param minute           - int - The minute the event happened
     * @throws IllegalArgumentException - if the arguments aren't valid
     */
    public void addSwitchPlayers(Team teamWhoCommitted, Player enteringPlayer, Player exitingPlayer, int minute) throws IllegalArgumentException {
        if ((!teamWhoCommitted.equals(homeTeam)) && (!teamWhoCommitted.equals(awayTeam))) {
            throw new IllegalArgumentException("The given team doesn't play in this game");
        }
        if ((!teamWhoCommitted.getTeamPlayers().contains(enteringPlayer))) {
            throw new IllegalArgumentException("The given entering Player doesn't play in this game");
        }
        if ((!teamWhoCommitted.getTeamPlayers().contains(exitingPlayer))) {
            throw new IllegalArgumentException("The given exiting Player doesn't play in this game");
        }
        if (minute < 0) {
            throw new IllegalArgumentException("minute must be positive integer");
        }
        this.eventsLogger.logSwitchPlayersEvent(teamWhoCommitted, enteringPlayer, exitingPlayer, minute);
    }

    /**
     * Adds a injury event to the game
     *
     * @param player - Player - The player who got injured
     * @param minute - int - The minute the event happened
     * @throws IllegalArgumentException - if the arguments aren't valid
     */
    public void addInjury(Player player, int minute) throws IllegalArgumentException {
        if ((!this.homeTeam.getTeamPlayers().contains(player)) && (!this.awayTeam.getTeamPlayers().contains(player))) {
            throw new IllegalArgumentException("The given player doesn't play in this game");
        }
        if (minute < 0) {
            throw new IllegalArgumentException("minute must be positive integer");
        }
        this.eventsLogger.logInjuryEvent(player, minute);
    }


    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public void addReferee(Referee referee) {
        this.referees.add(referee);
    }

    @Override
    public String toString() {
        return "Game{" +
                "stadium=" + stadium.getName() +
                ", homeTeam=" + homeTeam.getTeamName() +
                ", awayTeam=" + awayTeam.getTeamName() +
                ", gameDate=" + gameDate + '}';
    }


    public Date getGameDate()
    {
        return this.gameDate;
    }

//    public void setScore(Score score) {
//        this.score = score;
//    }
}
