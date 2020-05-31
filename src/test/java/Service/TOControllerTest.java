package Service;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TOControllerTest {


    @Test
    public void closeTeamUTest() throws Exception {
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66141, true)));
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66142, true)));

        UIController.setIsTest(true);
        UIController.setSelector(66143);
        new TeamOwnerStub(new SystemUserStub("a","a",0, true)).setSelector(66143);
        //false because team is already closed
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66143, true)));

        UIController.setSelector(66144);
        new TeamOwnerStub(new SystemUserStub("a","a",0, true)).setSelector(66144);
        //false because chose n
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66144, true)));
    }

    @Test
    public void closeTeamITest() throws Exception {
        //Integration with TeamController
        UIController.setIsTest(true);
        UIController.setSelector(66151);
        new TeamOwnerStub(new SystemUserStub("a","a",0, true)).setSelector(66151);
        //success
        assertTrue(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66151, true)));

    }


    @Test
    public void reopenTeamUTest() throws Exception {
        assertFalse(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66161, true)));
        assertFalse(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66162, true)));

        UIController.setIsTest(true);
        UIController.setSelector(66163);
        new TeamOwnerStub(new SystemUserStub("a","a",0, true)).setSelector(66163);
        //false because chose n
        assertFalse(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66163, true)));
    }

    @Test
    public void reopenTeamITest() throws Exception {
        //Integration with TeamController
        UIController.setIsTest(true);
        UIController.setSelector(66251);
        new TeamOwnerStub(new SystemUserStub("a","a",0, true)).setSelector(66251);
        //success
        assertTrue(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66251, true)));
    }

    @After
    public void tearDown() {
        EntityManager.getInstance().clearAll();
    }


}

