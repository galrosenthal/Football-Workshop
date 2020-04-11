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

    @Test
    public void addTeamOwner() {
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 0)));
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 1)));

        UIController.setIsTest(true);
        UIController.setSelector(0);
        //false because of wrong username from user
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));

//
//        System.out.println("-------------------------");
//        System.out.println("|   4 PRINTS EXCEPTION  |");
//        System.out.println("-------------------------");
//        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal","gal",2)));
    }

    @Test
    public void addTeamOwnerTest4() {
        UIController.setIsTest(true);
        UIController.setSelector(1);
        TeamOwnerStub.setSelector(0);
        //false because of wrong username from user
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
    }

    @Test
    public void addTeamOwnerTest5() {
        UIController.setIsTest(true);
        TeamOwnerStub.setSelector(1);
        new SystemUserStub("newTOUsername", "newTO", 3);
        UIController.setSelector(1);
        //false because of wrong username from user
        assertTrue(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
    }

}