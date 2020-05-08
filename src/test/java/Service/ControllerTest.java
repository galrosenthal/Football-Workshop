package Service;

import Domain.EntityManager;
import Domain.Exceptions.NoTeamExistsException;
import Domain.Game.Stadium;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import Domain.Game.Team;
import Domain.Users.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ControllerTest {
    @Mock
    SystemUser systemUser; //For testing login and signUp. Moved From UnregisteredTest

    @Before
    public void setUp() throws Exception {
        UIController.setIsTest(true);
        //For testing login and signUp. Moved From UnregisteredTest
        MockitoAnnotations.initMocks(this);
        when(systemUser.getPassword()).thenReturn("12aA34567");
        when(systemUser.getName()).thenReturn("Nir");
        when(systemUser.getUsername()).thenReturn("nir");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loginUTest() throws Exception {
        //userName not exists
        try{
            Controller.login("usrNmNotExists", "pswrd");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Username or Password was incorrect!",e.getMessage());
        }

        //working good
        EntityManager.getInstance().addUser(systemUser);

        SystemUser result2 = Controller.login("nir", "12aA34567");
        Assert.assertNotNull(result2);
        Assert.assertEquals("nir", result2.getUsername());
        Assert.assertEquals("Nir", result2.getName());
        Assert.assertEquals("12aA34567", result2.getPassword());

        //password incorrect
        try{
            Controller.login("nir", "pswrdNotCorrect");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Username or Password was incorrect!",e.getMessage());
        }
        EntityManager.getInstance().removeUserByReference(systemUser);
    }

    @Test
    public void signUpUTest() throws Exception {
        //success
        SystemUser newUser = Controller.signUp("Avi", "avi", "1234cB57");
        Assert.assertNotNull(newUser);
        Assert.assertEquals("avi", newUser.getUsername());
        Assert.assertEquals("Avi", newUser.getName());
        Assert.assertEquals("1234cB57", newUser.getPassword());


        //userName already exists
        EntityManager.getInstance().addUser(systemUser);
        try{
            Controller.signUp("Avi", "nir", "1234cB57");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Username already exists",e.getMessage());
        }

        try {
            //password does not meet security req
            Controller.signUp("Yosi", "yos", "12a34567");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Password does not meet the requirements",e.getMessage());
        }

        try {
            //password does not meet security req
            Controller.signUp("Yossi", "yos1", "55bB");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Password does not meet the requirements",e.getMessage());
        }

        EntityManager.getInstance().removeUserByReference(systemUser);

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

//    @Test
//    public void addTeamOwnerITest() {
//        UIController.setIsTest(true);
//        UIController.setSelector(1);
////        TeamOwnerStub.setSelector(0);
//        //false because of wrong username from user
//        assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
//    }

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
        new SystemUser("anotherUser","another test User");
        TeamOwner to = new TeamOwner(test);
        Team team = new Team();
        team.getTeamOwners().add(to);
        assertTrue(to.addTeamToOwn(team));
        UIController.setSelector(61115);
        assertTrue(Controller.addAsset(test));
    }


    @Test
    public void modifyTeamAssetDetails1UTest() throws Exception {
        assertFalse(Controller.modifyTeamAssetDetails(new SystemUserStub("rosengal", "gal", 0)));

    }

    @Test(expected = NoTeamExistsException.class)
    public void modifyTeamAssetDetails2UTest() throws Exception {
        Controller.modifyTeamAssetDetails(new SystemUserStub("rosengal", "gal", 6131));
    }


    @Test
    public void modifyTeamAssetDetails1ITest() throws Exception {
        assertFalse(Controller.modifyTeamAssetDetails(new SystemUser("rosengal", "gal")));

    }

    @Test(expected = NoTeamExistsException.class)
    public void modifyTeamAssetDetails2ITest() throws Exception {
        SystemUser systemUser = new SystemUser("rosengal", "gal");
        TeamOwner teamOwner = new TeamOwner(systemUser);
        Controller.modifyTeamAssetDetails(systemUser);
    }

    @Test
    public void modifyTeamAssetDetails3ITest() throws Exception {
        SystemUser systemUser = new SystemUser("rosengal", "gal");
        TeamOwner teamOwner = new TeamOwner(systemUser);
        Team team = new Team();
        teamOwner.addTeamToOwn(team);
        Stadium stadium = new Stadium("AESEAL" , "New York");
        team.addStadium(stadium);
        UIController.setSelector(6139);
        assertTrue(Controller.modifyTeamAssetDetails(systemUser));
        System.out.println(stadium.getName());
    }

}