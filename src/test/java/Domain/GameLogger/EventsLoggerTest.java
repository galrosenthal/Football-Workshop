package Domain.GameLogger;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.Game.TeamStub;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.PlayerStub;
import Domain.Users.SystemUserStub;
import org.junit.*;
import java.util.List;

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
        eventsLogger.logGoal(new TeamStub(0, true), new TeamStub(0, true),
                new PlayerStub(new SystemUserStub("a", "a",555, true)),1);
        assertNotNull(eventsLogger.getGoals());
    }

    @Test
    public void logCardEventITest() {
        eventsLogger.logCardEvent("Red Card", new PlayerStub(new SystemUserStub("userName", "name", 0, true)), 1);
        List<Event> eventList = eventsLogger.getGameEvents();

        boolean found = false;
        for (Event event : eventList) {
            if (event instanceof RedCard) {
                assertTrue(((RedCard) event).getMinute() == 1);
                found = true;
            }
        }
        assertTrue(found);
    }
    @Test
    public void logCardEvent2ITest() {
        eventsLogger.logCardEvent("Yellow Card", new PlayerStub(new SystemUserStub("userName", "name", 0, true)), 1);
        List<Event> eventList = eventsLogger.getGameEvents();

        boolean found = false;
        for (Event event : eventList) {
            if (event instanceof YellowCard) {
                assertTrue(((YellowCard) event).getMinute() == 1);
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void logOffsideEventITest() {
        eventsLogger.logOffsideEvent(new TeamStub(0, true),1);
        List<Event> eventList = eventsLogger.getGameEvents();

        boolean found = false;
        for (Event event : eventList) {
            if (event instanceof Offside) {
                assertTrue(((Offside) event).getMinute() == 1);
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void logPenaltyEventITest() {
        eventsLogger.logPenaltyEvent(new TeamStub(0, true),1);
        List<Event> eventList = eventsLogger.getGameEvents();

        boolean found = false;
        for (Event event : eventList) {
            if (event instanceof Penalty) {
                assertTrue(((Penalty) event).getMinute() == 1);
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void logSwitchPlayersEventITest() {
        eventsLogger.logSwitchPlayersEvent(new TeamStub(0, true), new PlayerStub(new SystemUserStub("userName1", "name1", 0, true)), new PlayerStub(new SystemUserStub("userName2", "name2", 0, true)),1);
        List<Event> eventList = eventsLogger.getGameEvents();

        boolean found = false;
        for (Event event : eventList) {
            if (event instanceof SwitchPlayers) {
                assertTrue(((SwitchPlayers) event).getMinute() == 1);
                found = true;
            }
        }
        assertTrue(found);

    }

    @Test
    public void logInjuryEventITest() {
        eventsLogger.logInjuryEvent( new PlayerStub(new SystemUserStub("userName", "name", 0, true)),1);
        List<Event> eventList = eventsLogger.getGameEvents();

        boolean found = false;
        for (Event event : eventList) {
            if (event instanceof Injury) {
                assertTrue(((Injury) event).getMinute() == 1);
                found = true;
            }
        }
        assertTrue(found);
    }

    @After
    public void tearDown() throws Exception {
        eventsLogger = null;
    }


}