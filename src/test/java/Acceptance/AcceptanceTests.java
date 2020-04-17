package Acceptance;

import Domain.EntityManager;
import Domain.Exceptions.UserNotFoundException;
import Domain.Game.Team;
import Domain.Users.*;
import Service.ARController;
import Service.Controller;
import Service.UIController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AcceptanceTests {
    private static SystemUser existingUser;
    @BeforeClass
    public static void setUp() { //Will be called only once
        existingUser = new SystemUser("abc", "aBc12345", "abc");
        UIController.setIsTest(true);
    }

    @Test
    public void systemBootATest(){
        initEntities();
        UIController.setIsTest(true);
        UIController.setSelector(3);
        assertTrue(Controller.systemBoot());
    }

    private void initEntities() {
        SystemUser adminUser = new SystemUser("admin","12345678","administrator");
        adminUser.addNewRole(new SystemAdmin(adminUser));
        EntityManager.getInstance().addUser(adminUser);
    }

    @Test
    public void addTeamOwnerATest() throws Exception{
        UIController.setIsTest(true);
        Controller.addTeamOwner(new SystemUser("rosengal","Gal"));
    }


    //***** login() *****

    @Test
    public void loginATest() throws Exception {
        EntityManager.getInstance().addUser(existingUser);
        //success
        Unregistered unregUser = new Unregistered();
        SystemUser user = unregUser.login("abc", "aBc12345");

    }

    @Test
    public void login2ATest() throws Exception {
        //not a user
        Unregistered unregUser2 = new Unregistered();
        try {
        SystemUser user2 = unregUser2.login("notAUser", "aBc12345");
            Assert.fail();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void login3ATest() throws Exception {
        EntityManager.getInstance().addUser(existingUser);
        //wrong password
        Unregistered unregUser3 = new Unregistered();
        try {
            SystemUser user3 = unregUser3.login("abc", "pass12345");
            Assert.fail();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //***** signUp() *****

    @Test
    public void signUpATest() throws Exception {
        EntityManager.getInstance().removeUserByReference(existingUser);
        //success
        Unregistered unregUser = new Unregistered();
        SystemUser user = unregUser.signUp("abc", "abc", "aBc12345");
    }

    @Test
    public void signUp2ATest() throws Exception {
        //username already exists
        EntityManager.getInstance().addUser(existingUser);
        Unregistered unregUser2 = new Unregistered();
        try {
            SystemUser user2 = unregUser2.signUp("abc", "abc", "aBc12345");
            Assert.fail();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void signUp3ATest() throws Exception {
        //password not strong
        EntityManager.getInstance().removeUserByReference(existingUser);
        Unregistered unregUser3 = new Unregistered();
        try {
            SystemUser user3 = unregUser3.signUp("abc", "abc", "123");
            Assert.fail();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 9.1.a
     */
    @Test
    public void addLeagueATest(){
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new AssociationRepresentative(systemUser));
        EntityManager.getInstance().addUser(systemUser);
        UIController.setIsTest(true);
        UIController.setSelector(6);
        //First league creation
        assertTrue(ARController.addLeague(systemUser));

        //CleanUp
        EntityManager.getInstance().removeLeagueByName("Premier League");
        assertFalse(EntityManager.getInstance().doesLeagueExists("Premier League"));
    }
    /**
     * 9.1.b
     */
    @Test
    public void addLeague2ATest(){
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new AssociationRepresentative(systemUser));
        EntityManager.getInstance().addUser(systemUser);
        EntityManager.getInstance().addLeague("Premier League");
        UIController.setIsTest(true);
        UIController.setSelector(6);
        //Duplicated league creation
        assertFalse(ARController.addLeague(systemUser));

        //CleanUp
        EntityManager.getInstance().removeLeagueByName("Premier League");
        assertFalse(EntityManager.getInstance().doesLeagueExists("Premier League"));
    }

    /**
     * 6.1.1.a
     * User for Elisha Levy exists
     */
    @Test
    public void addAsset1ATest() throws Exception
    {
        Team beitShean = new Team();

        beitShean.setTeamName("Beit Shean");
        SystemUser abcCreate = new SystemUser("abc1","abc12345","abc");;
        TeamOwner abcOwner = new TeamOwner(abcCreate);
        abcOwner.addTeamToOwn(beitShean);
        beitShean.getTeamOwners().add(abcOwner);
        SystemUser elisha = new SystemUser("elevy","Elisha Levy");

        Unregistered abcUnreg = new Unregistered();
        SystemUser abc = abcUnreg.login("abc1","abc12345");
        assertEquals(abc,abcCreate);

        UIController.setSelector(61118);
        assertTrue(Controller.addAsset(abc));


    }

    /**
     * 6.1.1.b
     * User for Elisha Levy does not exists
     */
    @Test
    public void addAsset2ATest() throws Exception
    {
        Team beitShean = new Team();

        beitShean.setTeamName("Beit Shean");
        SystemUser abcCreate = new SystemUser("abc1","abc12345","abc");;
        TeamOwner abcOwner = new TeamOwner(abcCreate);
        abcOwner.addTeamToOwn(beitShean);
        beitShean.getTeamOwners().add(abcOwner);

        Unregistered abcUnreg = new Unregistered();
        SystemUser abc = abcUnreg.login("abc1","abc12345");
        assertEquals(abc,abcCreate);

        UIController.setSelector(61118);
        try{
            Controller.addAsset(abc);
            fail();
        }
        catch (UserNotFoundException e)
        {
            UIController.printMessage(e.getMessage());
            assertEquals("Could not find user elevy",e.getMessage());
        }


    }

}
