package Domain.Logger;

import Domain.Game.Game;
import Domain.Game.Score;

import java.util.ArrayList;
import java.util.List;

public class EventsLogger {

    private List<Event> gameEvents;

    public EventsLogger(List<Event> gameEvents) {
        this.gameEvents = gameEvents;
    }

    /**
     * Retrieves all the goal events of the game
     *
     * @return - List<Goal> - all the goals of the game
     */
    public List<Goal> getGoals() {
        //TODO: Return only the Goal events
        List<Goal> goals = new ArrayList<>();
        for (Event event : gameEvents) {
            if (event instanceof Goal) {
                goals.add((Goal) event);
            }
        }
        return goals;
    }

}
