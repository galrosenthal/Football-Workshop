package Domain.Users;

import Domain.Game.Team;
import Domain.Game.TeamStub;
import Service.UIController;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class PlayerTest {

    Team teamStub;
    TeamOwner ownerStub;

    Player playerToTest;
    SystemUser testUser;


    @Before
    public void setUp() throws Exception {
        teamStub = new TeamStub(0);
        testUser = new SystemUserStub("test","testUser",4);
        ownerStub = new TeamOwnerStub(testUser);



    }

    @Test
    public void addTeam() throws Exception{
        Date bday = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993");
        Player p1 = new Player(testUser,bday);

        //Selector is 0
        assertFalse(p1.addTeam(teamStub,ownerStub));
        ((TeamStub)teamStub).setSelector(1);
        //Null Team returns false
        assertFalse(p1.addTeam(null,ownerStub));

        assertTrue(p1.addTeam(teamStub,ownerStub));
        ((TeamStub)teamStub).setSelector(2);
        assertFalse(p1.addTeam(teamStub,ownerStub));
    }


    @Test
    public void addAllProperties() throws Exception{
        UIController.setIsTest(true);
        Date bday = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993");
        Player p1 = new Player(testUser,bday);

        assertTrue(p1.addAllProperties());

    }

    @Test
    public void addProperty() throws Exception{
        UIController.setIsTest(true);
        Date bday = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993");
        Player p1 = new Player(testUser,bday);

        assertFalse(p1.addProperty("NotAValidString"));
        assertTrue(p1.addProperty(p1.fieldJobString));

    }

//    @Test
//    public void removeProperty() {
//    }
}