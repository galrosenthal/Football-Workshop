package Domain.Reports;

import Domain.EntityManager;
import Domain.Game.Game;
import Domain.Game.League;
import Domain.Game.StadiumStub;
import Domain.Game.TeamStub;
import Domain.Users.Player;
import Domain.Users.PlayerStub;
import Domain.Users.SystemUserStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameReportTest {
    private Game game;
    private GameReport gameReport;
    private TeamStub firstTeam;
    private TeamStub secondTeam;

    @Before
    public void setUp() throws Exception {
        firstTeam = new TeamStub(9511);
        secondTeam =  new TeamStub(9512);
        game = new Game(new StadiumStub("staName", "staLoca"), firstTeam,secondTeam, new Date(2020, 01, 01), null);
        gameReport = new GameReport(game);
    }


    @After
    public void tearDown() throws Exception {
        game = null;
        gameReport = null;
        EntityManager.getInstance().clearAll();
    }

    @Test
    public void produceReportITest(){
        //Adding Events
        try {
            game.addPenalty(firstTeam,1);
        } catch (Exception e) {
        }
        try {
            game.addGoal(game.getHomeTeam(), game.getAwayTeam(),
                    new PlayerStub(new SystemUserStub("a", "a",555)),2);
        } catch (Exception e) {
        }
        Player player = new PlayerStub(new SystemUserStub("UserName","Name",0 ));
        firstTeam.addPlayer(player);
        try {
            game.addInjury(player,10);
        } catch (Exception e) {
        }
        game.addEndGame(new Date(),90);

        //Success
        try {
            gameReport.produceReport("D:\\ZData");
        } catch (Exception e) {
        }
    }
}