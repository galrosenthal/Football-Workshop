package Domain.GameLogger;

import Domain.Game.Team;
import Domain.Users.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventsLogger {
    private List<Event> gameEvents;

    public EventsLogger() {
        this.gameEvents = new ArrayList<>();
    }

    /**
     * Retrieves all the goal events of the game
     *
     * @return - List<Goal> - all the goals of the game
     */
    public List<Goal> getGoals() {
        List<Goal> goals = new ArrayList<>();
        for (Event event : gameEvents) {
            if (event instanceof Goal) {
                goals.add((Goal) event);
            }
        }
        if (goals.size() == 0) {
            return null;
        }
        return goals;
    }

    public List<String> getEventsStringList() {
        List<String> eventsString = new ArrayList<>();
        for (Event event : this.gameEvents) {
            eventsString.add(event.toString());
        }
        return eventsString;
    }

    /**
     * Logs a new goal
     *
     * @param scoringTeam  - Team - The scoring team - must play in this game
     * @param scoredOnTeam - Team - The scored on team - must play in this game
     * @param minute       - int - The minute the goal was scored - positive integer
     */
    public void logGoal(Team scoringTeam, Team scoredOnTeam, Player playerScored, int minute) {
        Goal goal = new Goal(scoringTeam, scoredOnTeam, playerScored,  minute);
        this.gameEvents.add(goal);
    }

    /**
     * Adds a new card event based on the given parameters
     *
     * @param cardType - String - The type of the card
     * @param player   - Player - The player who got the card
     * @param minute   - int - The minute the event happened
     */
    public void logCardEvent(String cardType, Player player, int minute) {
        Card card = null;
        switch (cardType) {
            case "Red Card":
                card = new RedCard(player, minute);
                break;
            case "Yellow Card":
                card = new YellowCard(player, minute);
                break;
        }
        this.gameEvents.add(card);
    }

    /**
     * Adds a new offside event based on the given parameters
     *
     * @param teamWhoCommitted - Team - The team which committed the offside
     * @param minute           - int - The minute the event happened
     */
    public void logOffsideEvent(Team teamWhoCommitted, int minute) {
        this.gameEvents.add(new Offside(teamWhoCommitted, minute));
    }

    /**
     * Adds a new penalty event based on the given parameters
     *
     * @param teamWhoCommitted - Team - The team which committed the penalty
     * @param minute           - int - The minute the event happened
     */
    public void logPenaltyEvent(Team teamWhoCommitted, int minute) {
        this.gameEvents.add(new Penalty(teamWhoCommitted, minute));
    }

    /**
     * Adds a new players switching event based on the given parameters
     *
     * @param teamWhoCommitted - Team - The team which committed the switch
     * @param enteringPlayer   - Player - The player who is entering
     * @param exitingPlayer    - Player - The player who is exiting
     * @param minute           - int - The minute the event happened
     */
    public void logSwitchPlayersEvent(Team teamWhoCommitted, Player enteringPlayer, Player exitingPlayer, int minute) {
        this.gameEvents.add(new SwitchPlayers(teamWhoCommitted, enteringPlayer, exitingPlayer, minute));
    }

    /**
     * Adds a new injury event based on the given parameters
     *
     * @param player - Player - The player who was injured
     * @param minute - int - The minute the event happened
     */
    public void logInjuryEvent(Player player, int minute) {
        this.gameEvents.add(new Injury(player, minute));
    }

    /**
     * Adds a new game end event based on the given parameters
     * @param endDate - Date - The time the game ended
     * @param minute - int - The minute game ended (maybe it ended before the 90th or 120th minute)
     */
    public void logEndGameEvent(Date endDate, int minute) {
        this.gameEvents.add(new GameEnd(endDate, minute));
    }

    public List<Event> getGameEvents() {
        return gameEvents;
    }


}
