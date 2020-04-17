package Service;

import Domain.EntityManager;
import Domain.Exceptions.NoTeamExistsException;
import Domain.Game.Team;
import Domain.Users.*;
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

    @Test
    public void addTeamOwnerUTest() {
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 0)));
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 1)));

        UIController.setIsTest(true);
        UIController.setSelector(0);
        //false because of wrong username from user
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));

    }

    @Test
    public void addTeamOwnerITest() {
        UIController.setIsTest(true);
        UIController.setSelector(1);
//        TeamOwnerStub.setSelector(0);
        //false because of wrong username from user
        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
    }

    @Test
    public void addTeamOwner2ITest() {
        UIController.setIsTest(true);
//        TeamOwnerStub.setSelector(0);
        new SystemUserStub("newTOUsername", "newTO", 3);
        UIController.setSelector(2);
        //false because of wrong username from user
        assertTrue(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
    }

    @Test
    public void addAssetUTest() throws Exception
    {
        SystemUserStub test = new SystemUserStub("test","test User",0);
        assertFalse(Controller.addAsset(test));
        CoachStub c = new CoachStub(test);
        test.getRoles().add(c);
        test.setSelector(6111);
        assertFalse(Controller.addAsset(test));
        test.getRoles().remove(c);


    }
    @Test (expected = NoTeamExistsException.class)
    public void addAssetNoSuchTeamUTest() throws Exception
    {
        SystemUser test = new SystemUserStub("test","test User",6111);
        TeamOwnerStub to = new TeamOwnerStub(test);
        to.setSelector(6111);
        assertFalse(Controller.addAsset(test));
    }

    @Test
    public void addAssetFalseUTest() throws Exception
    {
        SystemUser test = new SystemUserStub("test","test User",6111);
        TeamOwnerStub to = new TeamOwnerStub(test);
        to.setSelector(6113);
        UIController.setSelector(0);
        assertFalse(Controller.addAsset(test));
    }

    @Test
    public void addAssetTestAssetTypeUTest() throws Exception
    {
        SystemUser test = new SystemUserStub("test","test User",6111);
        TeamOwnerStub to = new TeamOwnerStub(test);
        to.setSelector(6112);
        UIController.setSelector(61111);
        assertTrue(Controller.addAsset(test));
    }


    @Test
    public void addAssetSystemUserStubITest() throws Exception
    {
        SystemUser test = new SystemUserStub("test","test User",6111);
        SystemUser anotherUser = new SystemUserStub("anotherUser","another test User",6112);
        TeamOwner to = new TeamOwner(test);
        Team team = new Team();
        team.getTeamOwners().add(to);
        assertTrue(to.addTeamToOwn(team));
        UIController.setSelector(61114);
        assertTrue(Controller.addAsset(test));
    }

    @Test
    public void addAssetSystemUserNoStubITest() throws Exception
    {
        SystemUser test = new SystemUser("test","test User");
        SystemUser anotherUser = new SystemUser("anotherUser","another test User");
        TeamOwner to = new TeamOwner(test);
        Team team = new Team();
        team.getTeamOwners().add(to);
        assertTrue(to.addTeamToOwn(team));
        UIController.setSelector(61115);
        assertTrue(Controller.addAsset(test));
    }

}