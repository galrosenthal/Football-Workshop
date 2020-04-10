package Service;

import Domain.Controllers.TeamController;
import Domain.Controllers.TeamControllerStub;
import Domain.Controllers.TopDomainController;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import Stubs.UIControllerStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    TopServiceController tpSvcCntrlr = TopServiceController.getInstance();
    TopDomainController tpDmnCntrlr = TopDomainController.getInstance();
    UIController uiControllerStub;
    TeamController teamControllerStub;

    @Before
    public void setUp() throws Exception {
        uiControllerStub = new UIControllerStub();
        teamControllerStub = new TeamControllerStub();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addTeamOwner() {
        System.out.println("First");
        assertFalse(Controller.getInstance().addTeamOwner(new SystemUserStub("rosengal","gal",0)));
        System.out.println("Second");
        assertFalse(Controller.getInstance().addTeamOwner(new SystemUserStub("rosengal","gal",1)));



        ((UIControllerStub)uiControllerStub).setSelector(0);
        ((TeamControllerStub)teamControllerStub).setSelector(0);

        TopDomainController.getInstance().setTeamController(teamControllerStub);
        TopServiceController.getInstance().setUiController(uiControllerStub);


        System.out.println("Third");
        assertTrue(Controller.getInstance().addTeamOwner(new SystemUserStub("rosengal","gal",2)));

        ((TeamControllerStub)teamControllerStub).setSelector(1);
        System.out.println("-------------------------");
        System.out.println("|   4 PRINTS EXCEPTION  |");
        System.out.println("-------------------------");
        assertFalse(Controller.getInstance().addTeamOwner(new SystemUserStub("rosengal","gal",2)));




    }
}