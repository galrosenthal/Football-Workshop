package Domain.GameLogger;

import java.util.ArrayList;
import java.util.List;

public class Event {

    protected int minute;

    public Event(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public static List<String> getEventsTypes() {
        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("Red Card");
        eventTypes.add("Yellow Card");
        eventTypes.add("Goal");
        eventTypes.add("Offside");
        eventTypes.add("Penalty");
        eventTypes.add("Switch Players");
        eventTypes.add("Injury");
        eventTypes.add("Game End");
        return eventTypes;
    }

    @Override
    public String toString() {
        return "minute=" + minute+".";
    }
}
