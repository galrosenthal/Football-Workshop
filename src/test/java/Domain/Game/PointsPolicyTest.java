package Domain.Game;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PointsPolicyTest {

    private PointsPolicy pointsPolicy;

    @Before
    public void setUp() throws Exception {
        pointsPolicy = PointsPolicy.getDefaultPointsPolicy();
    }

    @Test
    public void getPointsITest() {
        Game game = new Game(new StadiumStub("staName", "staLoca"), new TeamStub(9511), new TeamStub(9512), new Date(2020, 01, 01), null);
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(), 1);
        } catch (Exception e) {
        }
        Points points = pointsPolicy.getPoints(game);
        assertEquals(points.getHomeTeamPoints(),3);
        assertEquals(points.getAwayTeamPoints(),0);
    }

    @Test
    public void getPoints2ITest() {
        Game game = new Game(new StadiumStub("staName", "staLoca"), new TeamStub(9511), new TeamStub(9512), new Date(2020, 01, 01), null);
        try {
            game.addGoal(game.getAwayTeam(),game.getHomeTeam(), 1);
        } catch (Exception e) {
        }
        Points points = pointsPolicy.getPoints(game);
        assertEquals(points.getHomeTeamPoints(), 0);
        assertEquals(points.getAwayTeamPoints(), 3);
    }

    @Test
    public void getPoints3ITest() {
        Game game = new Game(new StadiumStub("staName", "staLoca"), new TeamStub(9511), new TeamStub(9512), new Date(2020, 01, 01), null);
        Points points = pointsPolicy.getPoints(game);
        assertEquals(points.getHomeTeamPoints(),1);
        assertEquals(points.getAwayTeamPoints(),1);
    }
}