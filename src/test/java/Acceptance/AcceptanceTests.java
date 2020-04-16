package Acceptance;

import Domain.EntityManager;
import Domain.Users.AssociationRepresentative;
import Domain.Users.SystemAdmin;
import Domain.Users.SystemUser;
import Domain.Users.Unregistered;
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
    public void addTeamOwnerATest() {
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
}
