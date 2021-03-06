package Domain.Game;

import DB.DBManager;
import Domain.EntityManager;
import Domain.GameLogger.EventsLogger;
import Domain.GameLogger.Goal;
import Domain.Reports.GameReport;
import Domain.Users.Player;
import Domain.Users.Referee;

import java.util.*;

public class Game extends Observable {

    private Stadium stadium;
    private Team homeTeam;
    private Team awayTeam;
    private Date gameDate;
    private Date endDate;
    private List<Referee> referees;
    private EventsLogger eventsLogger;
    private GameReport gameReport;

    public Game(Stadium stadium, Team homeTeam, Team awayTeam, Date gameDate, List<Referee> referees, boolean addToDB) {
        this.stadium = stadium;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
//        this.referees = referees;
        this.eventsLogger = new EventsLogger();
        this.endDate = null;
        this.gameReport = new GameReport(this);
        if (addToDB) {
            EntityManager.getInstance().addGame(this);
        }
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
        return endDate != null;
    }


    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public List<Referee> getReferees() {
        if (this.referees == null) {
            return EntityManager.getInstance().getAllRefereesInGame(this);
        }
        return referees;
    }

    public EventsLogger getEventsLogger() {
        if (this.eventsLogger == null) {
            //TODO:Pull From DB
        }
        return eventsLogger;
    }


    public List<String> getGameEventsStringList() {
        return getEventsLogger().getEventsStringList();
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
    public void addGoal(Team scoringTeam, Team scoredOnTeam, Player playerScored, int minute) throws IllegalArgumentException {
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
        getEventsLogger().logGoal(scoringTeam, scoredOnTeam, playerScored, minute);
        EntityManager.getInstance().updateGoalEvent(this, scoringTeam,scoredOnTeam ,playerScored.getSystemUser().getUsername(), minute);
        String notification = scoringTeam.getTeamName() + " scored on " + scoredOnTeam.getTeamName();
        notifyObservers(notification);
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
        EntityManager.getInstance().updateCardEvent(this, cardType, player.getSystemUser().getUsername(), minute);
        getEventsLogger().logCardEvent(cardType, player, minute);
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
        EntityManager.getInstance().updateOffsideEvent(this,teamWhoCommitted,minute);
        getEventsLogger().logOffsideEvent(teamWhoCommitted, minute);
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
        EntityManager.getInstance().updatePenaltyEvent(this, teamWhoCommitted, minute);
        getEventsLogger().logPenaltyEvent(teamWhoCommitted, minute);
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
        EntityManager.getInstance().updateSwitchEvent(this, teamWhoCommitted,enteringPlayer,exitingPlayer ,minute);
        getEventsLogger().logSwitchPlayersEvent(teamWhoCommitted, enteringPlayer, exitingPlayer, minute);
    }

    /**
     * Adds an injury event to the game
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
        EntityManager.getInstance().addInjuryEvent(this, player ,minute);
        getEventsLogger().logInjuryEvent(player, minute);
    }

    /**
     * Adds an game-end event to the game
     *
     * @param endDate - Date - The time the game ended
     * @param minute  - int - The minute game ended (maybe it ended before the 90th or 120th minute)
     */
    public void addEndGame(Date endDate, int minute) {
        EntityManager.getInstance().updateEndGame(this, endDate);
        getEventsLogger().logEndGameEvent(endDate, minute);
    }


    public void addReferee(Referee referee) {
        getReferees().add(referee);
    }

    /**
     * Returns how many hours passed since the game ended, from a given date.
     *
     * @param currDate The date to measure the difference from the endDate to.
     * @return
     */
    public int getHoursPassedSinceGameEnd(Date currDate) {
        if (!hasFinished())
            return 0;
        //milliseconds
        long different = currDate.getTime() - endDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = different / hoursInMilli;
        return (int) elapsedHours;
    }

    @Override
    public String toString() {
        String str = "Game{" +
                "stadium=" + stadium.getName() +
                ", homeTeam=" + homeTeam.getTeamName() +
                ", awayTeam=" + awayTeam.getTeamName() +
                ", gameDate=" + gameDate;
        if (endDate != null) {
            str += ", endDate=" + endDate;
        }
        str += '}';
        return str;
    }


    public Date getGameDate() {
        return this.gameDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public GameReport getGameReport() {
        return gameReport;
    }

    //    public void setScore(Score score) {
//        this.score = score;
//    }


    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    public String getGameTitle() {
        return homeTeam.getTeamName() + " vs. " + awayTeam.getTeamName();
    }

    /**
     * Un-assigns a referee from this game
     *
     * @param referee - Referee - a referee role to be removed
     */
    public void unAssignReferee(Referee referee) {
        if (referee != null) {
            getReferees().remove(referee);
        }
    }
}
