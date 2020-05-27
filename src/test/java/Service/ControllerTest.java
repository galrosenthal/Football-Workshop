package Service;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Exceptions.NoTeamExistsException;
import Domain.Game.Stadium;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.SystemUserStub;
import Domain.Users.TeamOwnerStub;
import Domain.Game.Team;
import Domain.Users.*;
import org.junit.*;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ControllerTest {
    @Mock
    SystemUser systemUser; //For testing login and signUp. Moved From UnregisteredTest
    String hashedPassword;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();
        SystemLoggerManager.disableLoggers(); // disable loggers in tests

    }


    @Before
    public void setUp() throws Exception {
        UIController.setIsTest(true);
        //For testing login and signUp. Moved From UnregisteredTest
        MockitoAnnotations.initMocks(this);
        hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex("12aA34567");
        when(systemUser.getPassword()).thenReturn(hashedPassword);
        when(systemUser.getName()).thenReturn("Nir");
        when(systemUser.getUsername()).thenReturn("nir");
    }

    /**
     * Check addTeamOwner Controller method,only a team owner of the team can add a new
     * team owner which he is system user
     */
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
        Assert.assertEquals(hashedPassword, result2.getPassword());
        EntityManager.getInstance().logout(result2);
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
        SystemUser newUser = Controller.signUp("Avi", "avi", "1234cB57","test@gmail.com", false);
        Assert.assertNotNull(newUser);
        Assert.assertEquals("avi", newUser.getUsername());
        Assert.assertEquals("Avi", newUser.getName());
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex("1234cB57");
        Assert.assertEquals(hashedPassword, newUser.getPassword());


        //userName already exists
        EntityManager.getInstance().addUser(systemUser);
        try{
            Controller.signUp("Avi", "nir", "1234cB57","test@gmail.com", false);
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Username already exists",e.getMessage());
        }

        try {
            //password does not meet security req
            Controller.signUp("Yosi", "yos", "12a34567","test@gmail.com", false);
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Password does not meet the requirements",e.getMessage());
        }

        try {
            //password does not meet security req
            Controller.signUp("Yossi", "yos1", "55bB","test@gmail.com", false);
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

        //"rosengal" Not a team owner
        //assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 0)));

        // "rosengal" role type not TeamOwner
        //assertFalse(Controller.addTeamOwner(new SystemUserStub("rosengal", "gal", 1)));

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

    /**
     * Success Check, a user who never owned a team before
     */
    @Test
    public void addTeamOwner2ITest() {
        UIController.setIsTest(true);
//        TeamOwnerStub.setSelector(0);
        new SystemUserStub("newTOUsername", "newTO", 3);
        UIController.setSelector(2);

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
        assertTrue(to.addTeamToOwn(team,test));
        UIController.setSelector(61114);
        assertTrue(Controller.addAsset(test));
    }

    @Test
    public void addAssetSystemUserNoStubITest() throws Exception
    {
        SystemUser test = new SystemUser("test","test User");
        SystemUser anotherUser =new SystemUser("anotherUser","another test User");
        TeamOwner to = new TeamOwner(test);
        Team team = new Team();
        team.getTeamOwners().add(to);
        assertTrue(to.addTeamToOwn(team,test));
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
        teamOwner.addTeamToOwn(team,systemUser);
        Stadium stadium = new Stadium("AESEAL" , "New York");
        team.addStadium(stadium);
        UIController.setSelector(6139);
        assertTrue(Controller.modifyTeamAssetDetails(systemUser));
        System.out.println(stadium.getName());
    }

    /**
     * Failure test, check that the user name owned a team
     */
    @Test
    public void removeTeamOwner1UTest(){
        UIController.setIsTest(true);
        UIController.setSelector(0);
        //false because username not owned a team
        assertFalse(Controller.removeTeamOwner(new SystemUser("rosengal", "gal")));
    }

    /**
     * Failure test, check that the team owner owned a team
     */
    @Test
    public void removeTeamOwner2UTest(){
        UIController.setIsTest(true);
        UIController.setSelector(1);
        SystemUser user = new SystemUser("rosengal", "gal");
        TeamOwnerStub teamOwner = new TeamOwnerStub(user);
        user.addNewRole(teamOwner);
        teamOwner.setSelector(6111);

        assertFalse(Controller.removeTeamOwner(user));
    }


    /**
     * Failure test, check that user name input is a not a real user name
     */
    @Test
    public void removeTeamOwner1ITest(){
        UIController.setIsTest(true);
        UIController.setSelector(0);
        //false because of wrong username from user
        assertFalse(Controller.removeTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
    }

//    /**
//     * Success test, check user name input is a real user name
//     */
//    @Test
//    public void removeTeamOwner2ITest(){
//        UIController.setIsTest(true);
//        UIController.setSelector(1);
//
//        assertTrue(Controller.removeTeamOwner(new SystemUserStub("rosengal", "gal", 2)));
//    }

    /**
     * check only the owner of this team can remove owner
     */
    @Test
    public void removeTeamOwner3ITest(){
        UIController.setIsTest(true);
        UIController.setSelector(1);

        SystemUser owner = new SystemUser("rosengal", "gal");
        owner.addNewRole(new TeamOwner(owner));
        assertFalse(Controller.removeTeamOwner(owner));

       // owner.getRole(RoleTypes.TEAM_OWNER).
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