package Domain.Game;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Logger.*;
import Domain.Users.Player;
import Domain.Users.PlayerStub;
import Domain.Users.SystemUserStub;
import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private TeamStub firstTeam;
    private TeamStub secondTeam;

    @BeforeClass
    public static void beforeClass() {
        DBManager.getInstance().startTest();
        DBManagerForTest.startConnection();
    }

    @Before
    public void setUp() throws Exception {
        firstTeam = new TeamStub(9511);
        secondTeam = new TeamStub(9512);
        game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), null);
    }


    @After
    public void tearDown() throws Exception {
        game = null;
        EntityManager.getInstance().clearAll();
    }

    @Test
    public void getScoreITest() {
        Score score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount() == 0);
        assertTrue(score.getAwayTeamGoalCount() == 0);
    }

    @Test
    public void getScore2ITest() {
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 1);
        } catch (Exception e) {
        }
        Score score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount() == 1);
        assertTrue(score.getAwayTeamGoalCount() == 0);
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 2);
        } catch (Exception e) {
        }
        score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount() == 2);
        assertTrue(score.getAwayTeamGoalCount() == 0);
        try {
            game.addGoal(game.getAwayTeam(), game.getHomeTeam(), 3);
        } catch (Exception e) {
        }
        score = game.getScore();
        assertTrue(score.getHomeTeamGoalCount() == 2);
        assertTrue(score.getAwayTeamGoalCount() == 1);
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
        assertTrue(game.getEventsLogger().getGoals().size() == 1);
    }

    @Test
    public void addGoal3ITest() {
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 1);
        } catch (Exception e) {
        }
        assertTrue(game.getEventsLogger().getGoals().size() == 1);
    }

    @Test
    public void addGoal4ITest() {
        try {
            game.addGoal(new TeamStub(9513), game.getAwayTeam(), 1);
        } catch (Exception e) {
            assertEquals("The given scoring Team doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addGoal5ITest() {
        try {
            game.addGoal(game.getHomeTeam(), new TeamStub(9513), 1);
        } catch (Exception e) {
            assertEquals("The given scored On Team doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addCardITest() {
        Player player = new PlayerStub(new SystemUserStub("UserName", "Name", 0));
        try {
            game.addCard("Red Card", player, 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The given player doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addCard2ITest() {
        Player player = new PlayerStub(new SystemUserStub("UserName", "Name", 0));
        firstTeam.addPlayer(player);
        try {
            game.addCard("Red Card", player, -1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("minute must be positive integer", e.getMessage());
        }
    }

    @Test
    public void addCard3ITest() {
        Player player = new PlayerStub(new SystemUserStub("UserName", "Name", 0));
        firstTeam.addPlayer(player);
        try {
            game.addCard("Red Card", player, 1);
        } catch (Exception e) {
        }
        boolean found = false;
        for (Event event : game.getEventsLogger().getGameEvents()) {
            if (event instanceof Card) {
                assertTrue(((Card) event).getMinute() == 1);
                assertTrue(((Card) event).getOffender() == player);
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void addOffsideITest() {
        try {
            game.addOffside(new TeamStub(0), 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The given team doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addOffside2ITest() {
        try {
            game.addOffside(firstTeam, -1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("minute must be positive integer", e.getMessage());
        }
    }

    @Test
    public void addOffside3ITest() {
        try {
            game.addOffside(firstTeam, 1);
        } catch (Exception e) {
        }
        boolean found = false;
        for (Event event : game.getEventsLogger().getGameEvents()) {
            if (event instanceof Offside) {
                assertTrue(((Offside) event).getMinute() == 1);
                assertTrue(((Offside) event).getTeamWhoCommitted().equals(firstTeam));
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void addPenaltyITest() {
        try {
            game.addPenalty(new TeamStub(0), 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The given team doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addPenalty2ITest() {
        try {
            game.addPenalty(firstTeam, -1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("minute must be positive integer", e.getMessage());
        }
    }

    @Test
    public void addPenalty3ITest() {
        try {
            game.addPenalty(firstTeam, 1);
        } catch (Exception e) {
        }
        boolean found = false;
        for (Event event : game.getEventsLogger().getGameEvents()) {
            if (event instanceof Penalty) {
                assertTrue(((Penalty) event).getMinute() == 1);
                assertTrue(((Penalty) event).getTeamWhoCommitted().equals(firstTeam));
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void addInjuryITest() {
        Player player = new PlayerStub(new SystemUserStub("UserName", "Name", 0));
        try {
            game.addInjury(player, 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The given player doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addInjury2ITest() {
        Player player = new PlayerStub(new SystemUserStub("UserName", "Name", 0));
        firstTeam.addPlayer(player);
        try {
            game.addInjury(player, -1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("minute must be positive integer", e.getMessage());
        }
    }

    @Test
    public void addInjury3ITest() {
        Player player = new PlayerStub(new SystemUserStub("UserName", "Name", 0));
        firstTeam.addPlayer(player);
        try {
            game.addInjury(player, 1);
        } catch (Exception e) {
        }
        boolean found = false;
        for (Event event : game.getEventsLogger().getGameEvents()) {
            if (event instanceof Injury) {
                assertTrue(((Injury) event).getMinute() == 1);
                assertTrue(((Injury) event).getInjuredPlayer().equals(player));
                found = true;
            }
        }
        assertTrue(found);
    }


    @Test
    public void addSwitchPlayersITest() {
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        try {
            game.addSwitchPlayers(new TeamStub(0), player1, player2, 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The given team doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addSwitchPlayers2ITest() {
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        try {
            game.addSwitchPlayers(firstTeam, player1, player2, 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The given entering Player doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addSwitchPlayers3ITest() {
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        try {
            game.addSwitchPlayers(firstTeam, player1, player2, 1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("The given exiting Player doesn't play in this game", e.getMessage());
        }
    }

    @Test
    public void addSwitchPlayers4ITest() {
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);
        try {
            game.addSwitchPlayers(firstTeam, player1, player2, -1);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("minute must be positive integer", e.getMessage());
        }
    }

    @Test
    public void addSwitchPlayers5ITest() {
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);
        try {
            game.addSwitchPlayers(firstTeam, player1, player2, 1);
        } catch (Exception e) {
        }
        boolean found = false;
        for (Event event : game.getEventsLogger().getGameEvents()) {
            if (event instanceof SwitchPlayers) {
                assertTrue(((SwitchPlayers) event).getMinute() == 1);
                assertTrue(((SwitchPlayers) event).getEnteringPlayer().equals(player1));
                assertTrue(((SwitchPlayers) event).getExitingPlayer().equals(player2));
                found = true;
            }
        }
        assertTrue(found);
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}