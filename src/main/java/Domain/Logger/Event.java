package Domain.Logger;

import java.util.ArrayList;
import java.util.List;

public class Event {
    public static List<String> getEventsTypes() {
        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("Red Card");
        eventTypes.add("Yellow Card");
        eventTypes.add("Goal");
        eventTypes.add("Offside");
        eventTypes.add("Penalty");
        eventTypes.add("Switch Players");
        eventTypes.add("Injury");
        return eventTypes;
    }
}
