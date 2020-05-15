package Domain.Logger;

import Domain.Game.Team;

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
}
