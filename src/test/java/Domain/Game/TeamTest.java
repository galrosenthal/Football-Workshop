package Domain.Game;

import Domain.Users.Player;
import Domain.Users.PlayerFieldJobs;
import Domain.Users.TeamOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.util.calendar.LocalGregorianCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private Team team;
    private TeamOwner to;

    @BeforeEach
    void setUp() {
        to = new TeamOwner("rosengal","12345678","Gal Rosenthal");
        team = new Team(to);
    }



    @Test
    void addTeamPlayer() {
        String sDate = "01/11/1993";
        Date bday = null;
        try
        {
            bday = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);

        }catch (Exception e)
        {
//            e.printStackTrace();
        }
        Player p = new Player("stam","12345678","kaka", PlayerFieldJobs.FRONT,bday);
        assertEquals(0, team.getTeamPlayers().size());
        assertTrue(team.addTeamPlayer(to,p));
        assertEquals(1, team.getTeamPlayers().size());

    }

    @Test
    void addTeamCoach() {
    }

    @Test
    void addTeamManager() {
    }

    @Test
    void testEquals() {
    }
}