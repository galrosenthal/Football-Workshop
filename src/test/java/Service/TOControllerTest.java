package Service;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.SystemUser;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TOControllerTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();
        SystemLoggerManager.disableLoggers(); // disable loggers in tests
    }

    @Test
    public void closeTeamUTest() throws Exception {
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66141)));
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66142)));

        UIController.setIsTest(true);
        UIController.setSelector(66143);
        new TeamOwnerStub(new SystemUserStub("a","a",0)).setSelector(66143);
        //false because team is already closed
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66143)));

        UIController.setSelector(66144);
        new TeamOwnerStub(new SystemUserStub("a","a",0)).setSelector(66144);
        //false because chose n
        assertFalse(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66144)));
    }

    @Test
    public void closeTeamITest() throws Exception {
        //Integration with TeamController
        UIController.setIsTest(true);
        UIController.setSelector(66151);
        new TeamOwnerStub(new SystemUserStub("a","a",0)).setSelector(66151);
        //success
        assertTrue(TOController.closeTeam(new SystemUserStub("rosengal", "gal", 66151)));

    }


    @Test
    public void reopenTeamUTest() throws Exception {
        assertFalse(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66161)));
        assertFalse(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66162)));

        UIController.setIsTest(true);
        UIController.setSelector(66163);
        new TeamOwnerStub(new SystemUserStub("a","a",0)).setSelector(66163);
        //false because chose n
        assertFalse(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66163)));
    }

    @Test
    public void reopenTeamITest() throws Exception {
        //Integration with TeamController
        UIController.setIsTest(true);
        UIController.setSelector(66251);
        new TeamOwnerStub(new SystemUserStub("a","a",0)).setSelector(66251);
        //success
        assertTrue(TOController.reopenTeam(new SystemUserStub("rosengal", "gal", 66251)));
    }

    @After
    public void tearDown() {
        EntityManager.getInstance().clearAll();
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}

