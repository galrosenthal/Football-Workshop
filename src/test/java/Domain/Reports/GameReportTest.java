package Domain.Reports;

import Domain.EntityManager;
import Domain.Game.Game;
import Domain.Game.League;
import Domain.Game.StadiumStub;
import Domain.Game.TeamStub;
import Domain.Users.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setUp() throws Exception {
        firstTeam = new TeamStub(10411);
        secondTeam =  new TeamStub(10412);
        List<Referee> referees = new ArrayList<>();
        referees.add(new RefereeStub(new SystemUser("a","a","a","@",false), RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("b","b","b","@",false),RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("c","c","c","@",false),RefereeQualification.VAR_REFEREE));
        game = new Game(new StadiumStub("staName", "staLoca"), firstTeam,secondTeam, new Date(2020, 01, 01), referees);
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
        String report;
        try {
            gameReport.produceReport();
            report = gameReport.produceReport();//save to current project path
        } catch (Exception e) {
        }

        try {
        //    gameReport.produceReport(".");
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