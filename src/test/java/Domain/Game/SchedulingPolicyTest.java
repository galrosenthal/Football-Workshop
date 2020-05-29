package Domain.Game;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.Referee;
import Domain.Users.RefereeQualification;
import Domain.Users.RefereeStub;
import Domain.Users.SystemUser;
import Generic.GenericTestAbstract;
import org.junit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class SchedulingPolicyTest  extends GenericTestAbstract {

    private SchedulingPolicy schedulingPolicy;


    @Before
    public void setUp() throws Exception {
        schedulingPolicy = SchedulingPolicy.getDefaultSchedulingPolicy();
    }

    @Test
    public void getDefaultSchedulingPolicyUTest() {
        assertTrue(schedulingPolicy.equals(2,2,2));
    }

    @Test
    public void generateScheduleITest() throws Exception {
        SchedulingPolicy sp = new SchedulingPolicy(2,4,7);
        Team team1 = new TeamStub(1, true);
        team1.addStadium(new StadiumStub("studium1","loc1"));
        Team team2 = new TeamStub(2, true);
        team2.addStadium(new StadiumStub("studium2","loc2"));
        Team team3 = new TeamStub(3, true);
        team3.addStadium(new StadiumStub("studium3","loc3"));
        Team team4 = new TeamStub(4, true);
        team4.addStadium(new StadiumStub("studium4","loc4"));
        Team team5 = new TeamStub(5, true);
        team5.addStadium(new StadiumStub("studium5","loc5"));
        Team team6 = new TeamStub(6, true);
        team6.addStadium(new StadiumStub("studium6","loc6"));
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        teams.add(team5);
        teams.add(team6);
        List<Referee> referees = new ArrayList<>();
        referees.add(new RefereeStub(new SystemUser("a","a","a","test@gmail.com", false, true),RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("b","b","b","test@gmail.com", false, true),RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("c","c","c","test@gmail.com", false, true),RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("d","d","d","test@gmail.com", false, true),RefereeQualification.VAR_REFEREE));
        referees.add(new RefereeStub(new SystemUser("e","e","e","test@gmail.com", false, true),RefereeQualification.VAR_REFEREE));

        List<ScheduleMatch> scheduleMatches = sp.generateSchedule(new Date(),teams,referees );
        assertEquals(30,scheduleMatches.size());

        Team team7 = new TeamStub(7, true);
        teams.add(team7);
        //no stadium
        try{
            scheduleMatches = sp.generateSchedule(new Date(),teams,referees );
            Assert.fail();
        }
        catch (Exception e){
            e.printStackTrace();
        }
         team7.addStadium(new StadiumStub("studium7","loc7"));
        //no 11 players
        try{
            scheduleMatches = sp.generateSchedule(new Date(),teams,referees );
            Assert.fail();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}