package Service;

import Domain.Exceptions.NoTeamExistsException;
import Domain.Exceptions.alreadyTeamOwnerException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {


    @Before
    public void setUp() throws Exception {
        UIController.setIsTest(true);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = UserNotFoundException.class)
    public void addTeamOwnerUTest() throws Exception {
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 0)));
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 1)));

        UIController.setIsTest(true);
        UIController.setSelector(0);
        //false because of wrong username from user
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));

    }


    @Test
    public void modifyTeamAssetDetails1Itest() throws Exception{
        UIController.setIsTest(true);
        TeamOwnerStub.setSelector(0);
        new SystemUserStub("newTOUsername", "newTO", 3);
        UIController.setSelector(2);
        //false because of wrong username from user
        assertTrue(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
        UIController.setSelector(0);

    }


    @Test
    public void modifyTeamAssetDetailsUTest1() throws Exception {
        assertFalse(Controller.modifyTeamAssetDetails(new SystemUserStub("rosengal", "gal", 0)));

    }

    @Test(expected = NoTeamExistsException.class)
    public void modifyTeamAssetDetailsUTest2() throws Exception {
       Controller.modifyTeamAssetDetails(new SystemUserStub("rosengal", "gal", 6131));
    }


}