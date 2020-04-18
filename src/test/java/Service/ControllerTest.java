package Service;

import Domain.EntityManager;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Check addTeamOwner Controller method,only a team owner of the team can add a new
     * team owner which he is system user
     */
    @Test
    public void addTeamOwnerUTest() {

        //"rosengal" Not a team owner
        //assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 0)));

        // "rosengal" role type not TeamOwner
        //assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 1)));

        UIController.setIsTest(true);
        UIController.setSelector(0);
        //false because of wrong username from user
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));

    }

    /**
     * Check that if the user already owner of this team return false
     */
    @Test
    public void addTeamOwnerITest() {
        UIController.setIsTest(true);
        UIController.setSelector(1);
        TeamOwnerStub.setSelector(0);

        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
    }

    /**
     * Success Check, a user who never owned a team before
     */
    @Test
    public void addTeamOwner2ITest() {
        UIController.setIsTest(true);
        TeamOwnerStub.setSelector(0);
        new SystemUserStub("newTOUsername", "newTO", 3);
        UIController.setSelector(2);

        assertTrue(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
    }

    @Test
    public void removeTeamOwner1UTest(){
        UIController.setIsTest(true);

    }
}