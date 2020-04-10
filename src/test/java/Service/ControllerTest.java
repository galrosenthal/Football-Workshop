package Service;

import Domain.Controllers.TeamControllerStub;
import Domain.Users.SystemUserStub;
import Stubs.UIControllerStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    UIControllerStub uicstb;
    TeamControllerStub tcstb;

    @Before
    public void setUp() throws Exception {
        uicstb = new UIControllerStub();
        tcstb = new TeamControllerStub();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addTeamOwner() {
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",0)));
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",1)));



        UIControllerStub.setSelector(0);
        TeamControllerStub.setSelector(0);
        Controller.setTc(tcstb);
        Controller.setCurrentUICtrlr(uicstb);

        assertTrue(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",2)));

        TeamControllerStub.setSelector(1);
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",2)));




    }
}