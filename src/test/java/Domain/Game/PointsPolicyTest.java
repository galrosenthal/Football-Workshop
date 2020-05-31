package Domain.Game;

import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.PlayerStub;
import Domain.Users.SystemUserStub;
import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Generic.GenericTestAbstract;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PointsPolicyTest extends GenericTestAbstract {

    private PointsPolicy pointsPolicy;



    @Before
    public void setUp() throws Exception {
        pointsPolicy = PointsPolicy.getDefaultPointsPolicy();
    }

    @Test
    public void testForYiftach() {
        System.out.println(pointsPolicy);
    }

    @Test
    public void testForYiftach2() {
        PointsPolicy newPointsPolicy = new PointsPolicy(1, -15, 0);
        EntityManager.getInstance().addPointsPolicy(newPointsPolicy);
    }

    @Test
    public void getPointsITest() {
        Game game = new Game(new StadiumStub("staName", "staLoca"), new TeamStub(9511, true), new TeamStub(9512, true), new Date(2020, 01, 01), null, true);
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(),
                    new PlayerStub(new SystemUserStub("a", "a",555, true)),1);
        } catch (Exception e) {
        }
        Points points = pointsPolicy.getPoints(game);
        assertEquals(points.getHomeTeamPoints(),3);
        assertEquals(points.getAwayTeamPoints(),0);
    }

    @Test
    public void getPoints2ITest() {
        Game game = new Game(new StadiumStub("staName", "staLoca"), new TeamStub(9511, true), new TeamStub(9512, true), new Date(2020, 01, 01), null, true);
        try {
            game.addGoal(game.getAwayTeam(),game.getHomeTeam(),
                    new PlayerStub(new SystemUserStub("a", "a",555, true)),1);
        } catch (Exception e) {
        }
        Points points = pointsPolicy.getPoints(game);
        assertEquals(points.getHomeTeamPoints(), 0);
        assertEquals(points.getAwayTeamPoints(), 3);
    }

    @Test
    public void getPoints3ITest() {
        Game game = new Game(new StadiumStub("staName", "staLoca"), new TeamStub(9511, true), new TeamStub(9512, true), new Date(2020, 01, 01), null, true);
        Points points = pointsPolicy.getPoints(game);
        assertEquals(points.getHomeTeamPoints(),1);
        assertEquals(points.getAwayTeamPoints(),1);
    }

}