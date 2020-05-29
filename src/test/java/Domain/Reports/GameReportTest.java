package Domain.Reports;

import Domain.EntityManager;
import Domain.Game.Game;
import Domain.Game.StadiumStub;
import Domain.Game.TeamStub;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.*;
import org.junit.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameReportTest {
    private Game game;
    private GameReport gameReport;
    private TeamStub firstTeam;
    private TeamStub secondTeam;

    @BeforeClass
    public static void setUpBeforeAll() { //Will be called only once
        SystemLoggerManager.disableLoggers(); // disable loggers in tests
    }

    @Before
    public void setUp() throws Exception {
        firstTeam = new TeamStub(10411);
        secondTeam =  new TeamStub(10412);
        List<Referee> referees = new ArrayList<>();
        referees.add(new RefereeStub(new SystemUser("a","a","a","@",false, true), RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("b","b","b","@",false, true),RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("c","c","c","@",false, true),RefereeQualification.VAR_REFEREE));
        game = new Game(new StadiumStub("staName", "staLoca"), firstTeam,secondTeam, new Date(2020, 01, 01), referees, true);
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
        File file = null;
        try {
            //gameReport.produceReport("D:\\ZData");
            file = gameReport.produceReport(".");//save to current project path
        } catch (Exception e) {
        }
        //Fail, file already exists
        try {
            gameReport.produceReport(".");
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Now delete the file
        if(file != null){
            assertTrue(file.delete());
        }
    }
}