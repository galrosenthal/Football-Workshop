package Service;

import Domain.Controllers.TeamController;
import Domain.Controllers.TeamControllerStub;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import Stubs.UIControllerStub;
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

    @Test
    public void addTeamOwner() {
        System.out.println("First");
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",0)));
        System.out.println("Second");
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",1)));






        System.out.println("Third");
        assertTrue(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",2)));

//
//        System.out.println("-------------------------");
//        System.out.println("|   4 PRINTS EXCEPTION  |");
//        System.out.println("-------------------------");
//        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",2)));




    }
}