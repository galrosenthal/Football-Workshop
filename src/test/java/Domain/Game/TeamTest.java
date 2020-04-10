package Domain.Game;

import Domain.Users.Player;
import Domain.Users.PlayerFieldJobs;
import Domain.Users.TeamOwner;
import org.junit.*;
import static org.junit.Assert.*;
import sun.util.calendar.LocalGregorianCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TeamTest {
    private Team team;
    private TeamOwner to;

    @Before
    public void setUp() {
//        to = new TeamOwner("rosengal","12345678","Gal Rosenthal");
        team = new Team();
    }



    @Test
    public void addTeamPlayer() {
        String sDate = "01/11/1993";
        Date bday = null;
        try
        {
            bday = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);

        }catch (Exception e)
        {
//            e.printStackTrace();
        }
//        Player p = new Player("stam","12345678","kaka", PlayerFieldJobs.FRONT,bday);
//        assertEquals(0, team.getTeamPlayers().size());
//        assertTrue(team.addTeamPlayer(to,p));
//        assertEquals(1, team.getTeamPlayers().size());
//        System.out.println(team);

    }

    @Test
    public void addTeamCoach() {
    }

    @Test
    public void addTeamManager() {
    }

    @Test
    public void testEquals() {
    }
}