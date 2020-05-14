package Domain.Game;

import Domain.EntityManager;
import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game(new StadiumStub("staName", "staLoca"), new TeamStub(9511), new TeamStub(9512), new Date(2020, 01, 01), null);
    }


    @After
    public void tearDown() throws Exception {
        game = null;
        EntityManager.getInstance().clearAll();
    }

    @Test
    public void getScoreITest() {
        Score score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount()==0);
        assertTrue(score.getAwayTeamGoalCount()==0);
    }
    @Test
    public void getScore2ITest() {
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 1);
        } catch (Exception e) {
        }
        Score score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount()==1);
        assertTrue(score.getAwayTeamGoalCount()==0);
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 2);
        } catch (Exception e) {
        }
        score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount()==2);
        assertTrue(score.getAwayTeamGoalCount()==0);
        try {
            game.addGoal( game.getAwayTeam(),game.getHomeTeam(), 3);
        } catch (Exception e) {
        }
        score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount()==2);
        assertTrue(score.getAwayTeamGoalCount()==1);
    }

    @Test
    public void addGoalITest() {
        try {
            game.addGoal(game.getAwayTeam(), game.getAwayTeam(), 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The teams given are the same team", e.getMessage());
        }
    }
    @Test
    public void addGoal2ITest() {
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), -1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("minute must be positive integer", e.getMessage());
        }
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 0);
        } catch (Exception e) {
        }
        assertTrue(game.getEventsLogger().getGoals().size()==1);
    }
    @Test
    public void addGoal3ITest() {
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 1);
        } catch (Exception e) {
        }
        assertTrue(game.getEventsLogger().getGoals().size()==1);
    }
    @Test
    public void addGoal4ITest() {
        try {
            game.addGoal(new TeamStub(9513), game.getAwayTeam(), 1);
        } catch (Exception e) {
            assertEquals("The given scoringTeam doesn't play in this game", e.getMessage());
        }
    }
    @Test
    public void addGoal5ITest() {
        try {
            game.addGoal(game.getHomeTeam(), new TeamStub(9513), 1);
        } catch (Exception e) {
            assertEquals("The given scoredOnTeam doesn't play in this game", e.getMessage());
        }
    }
}