package Domain.Logger;

import Domain.Game.Team;
import Domain.Game.TeamStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventsLoggerTest {
    private EventsLogger eventsLogger;

    @Before
    public void setUp() throws Exception {
        eventsLogger = new EventsLogger();
    }

    @Test
    public void getGoalsITest() {
        assertNull(eventsLogger.getGoals());
    }

    @Test
    public void logGoalITest() {
        assertNull(eventsLogger.getGoals());
        eventsLogger.logGoal(new TeamStub(0),new TeamStub(0), 1);
        assertNotNull(eventsLogger.getGoals());
    }

    @After
    public void tearDown() throws Exception {
        eventsLogger = null;
    }
}