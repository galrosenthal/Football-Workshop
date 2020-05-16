package Domain.Logger;

import Domain.Game.Team;
import Domain.Users.Player;

import java.util.ArrayList;
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

    /**
     * Logs a new goal
     *
     * @param scoringTeam  - Team - The scoring team - must play in this game
     * @param scoredOnTeam - Team - The scored on team - must play in this game
     * @param minute       - int - The minute the goal was scored - positive integer
     */
    public void logGoal(Team scoringTeam, Team scoredOnTeam, int minute) {
        Goal goal = new Goal(scoringTeam, scoredOnTeam, minute);
        this.gameEvents.add(goal);
    }

    public List<String> getEventsStringList() {
        List<String> eventsString = new ArrayList<>();
        for (Event event : this.gameEvents) {
            eventsString.add(event.toString());
        }
        return eventsString;
    }

    /**
     * Adds a new card event based on the given parameters
     *
     * @param cardType - String - The type of the card
     * @param player   - Player - The player who got the card
     * @param minute   - int - The minute the event happened
     */
    public void addCardEvent(String cardType, Player player, int minute) {
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
     * Adds a new goal event based on the given parameters
     *
     * @param scored   - Team - The scoring team
     * @param scoredOn - Team - The team which got scored on
     * @param minute   - int - The minute the event happened
     */
    public void addGoalEvent(Team scored, Team scoredOn, int minute) {
        this.gameEvents.add(new Goal(scored, scoredOn, minute));

    }

    /**
     * Adds a new offside event based on the given parameters
     *
     * @param teamWhoCommitted - Team - The team which committed the offside
     * @param minute           - int - The minute the event happened
     */
    public void addOffsideEvent(Team teamWhoCommitted, int minute) {
        this.gameEvents.add(new Offside(teamWhoCommitted, minute));
    }

    /**
     * Adds a new penalty event based on the given parameters
     *
     * @param teamWhoCommitted - Team - The team which committed the penalty
     * @param minute           - int - The minute the event happened
     */
    public void addPenaltyEvent(Team teamWhoCommitted, int minute) {
        this.gameEvents.add(new Offside(teamWhoCommitted, minute));
    }

    /**
     * Adds a new players switching event based on the given parameters
     *
     * @param teamWhoCommitted - Team - The team which committed the switch
     * @param enteringPlayer   - Player - The player who is entering
     * @param exitingPlayer    - Player - The player who is exiting
     * @param minute           - int - The minute the event happened
     */
    public void addSwitchPlayersEvent(Team teamWhoCommitted, Player enteringPlayer, Player exitingPlayer, int minute) {
        this.gameEvents.add(new SwitchPlayers(teamWhoCommitted, enteringPlayer, exitingPlayer, minute));
    }

    /**
     * Adds a new injury event based on the given parameters
     *
     * @param player - Player - The player who was injured
     * @param minute - int - The minute the event happened
     */
    public void addInjuryEvent(Player player, int minute) {
        this.gameEvents.add(new Injury(player, minute));
    }
}
