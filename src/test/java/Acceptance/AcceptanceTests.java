package Acceptance;

import Domain.EntityManager;
import Domain.Exceptions.AssetsNotExistsException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Game.Team;
import Domain.Users.*;
import Domain.Game.League;
import Domain.Users.AssociationRepresentative;
import Domain.Users.SystemAdmin;
import Domain.Users.SystemUser;
import Domain.Users.Unregistered;
import Domain.Game.Team;
import Domain.Game.TeamStatus;
import Domain.Users.*;
import Domain.Exceptions.UserNotFoundException;
import Domain.Game.Team;
import Domain.Users.*;
import Service.ARController;
import Service.Controller;
import Service.TOController;
import Service.UIController;
import org.junit.*;
import org.junit.experimental.categories.Category;


import java.util.Date;

import static org.junit.Assert.*;

@Category(RegressionTests.class)
public class AcceptanceTests {
    private static SystemUser existingUser;
    private static SystemUser aviCohenSu;
    private static SystemUser yosiManagerSu;
    @BeforeClass
    public static void setUp() { //Will be called only once
        existingUser = new SystemUser("abc", "aBc12345", "abc");
        existingUser.addNewRole(new TeamOwner(existingUser));
        aviCohenSu = new SystemUser("avicohen", "123Ab456", "Avi Cohen");
        aviCohenSu.addNewRole(new TeamOwner(aviCohenSu));
        yosiManagerSu = new SystemUser("yosilevi", "123Ab456", "Yosi Levi");
        yosiManagerSu.addNewRole(new TeamManager(yosiManagerSu));
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

        //cleanup
        EntityManager.getInstance().removeUserByReference(existingUser);

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

        //cleanup
        EntityManager.getInstance().removeUserByReference(existingUser);

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

        //cleanup
        EntityManager.getInstance().removeUserByReference(existingUser);
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
     * 6.6.1.a
     */
    @Test
    public void closeTeamATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner);
        teamOwner.addTeamToOwn(team);
        //add team owner
        TeamOwner aviTo = (TeamOwner)aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team);
        //add team manager
        TeamManager tmanager = (TeamManager)yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);
        tmanager.addTeam(team);
        //add to Entity manager
        EntityManager.getInstance().addUser(existingUser);
        EntityManager.getInstance().addUser(aviCohenSu);
        EntityManager.getInstance().addUser(yosiManagerSu);
        EntityManager.getInstance().addTeam(team);

        //success
        UIController.setIsTest(true);
        UIController.setSelector(6611);
        Assert.assertTrue(TOController.closeTeam(existingUser));

        //cleanup
        teamOwner.removeTeamOwned(team);
        aviTo.removeTeamOwned(team);
        tmanager.removeTeam(team);
        EntityManager.getInstance().removeUserByName(existingUser.getUsername());
        EntityManager.getInstance().removeUserByName(aviCohenSu.getUsername());
        EntityManager.getInstance().removeUserByName(yosiManagerSu.getUsername());
        EntityManager.getInstance().removeTeamByReference(team);

    }

    /**
     * 6.6.1.b
     */
    @Test
    public void closeTeam2ATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner);
        teamOwner.addTeamToOwn(team);
        //add team owner
        TeamOwner aviTo = (TeamOwner)aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team);
        //add team manager
        TeamManager tmanager = (TeamManager)yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);
        tmanager.addTeam(team);
        //add to Entity manager
        EntityManager.getInstance().addUser(existingUser);
        EntityManager.getInstance().addUser(aviCohenSu);
        EntityManager.getInstance().addUser(yosiManagerSu);
        EntityManager.getInstance().addTeam(team);

        //operation cancelled
        UIController.setIsTest(true);
        UIController.setSelector(6612);
        Assert.assertFalse(TOController.closeTeam(existingUser));

        //cleanup
        teamOwner.removeTeamOwned(team);
        aviTo.removeTeamOwned(team);
        tmanager.removeTeam(team);
        EntityManager.getInstance().removeUserByName(existingUser.getUsername());
        EntityManager.getInstance().removeUserByName(aviCohenSu.getUsername());
        EntityManager.getInstance().removeUserByName(yosiManagerSu.getUsername());
        EntityManager.getInstance().removeTeamByReference(team);
    }

    /**
     * 6.6.2.a
     */
    @Test
    public void reopenTeamATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner);
        teamOwner.addTeamToOwn(team);
        //add team owner
        TeamOwner aviTo = (TeamOwner)aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team);
        //add team manager, BUT NOT the team TO the manager
        TeamManager tmanager = (TeamManager)yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);
        //add to Entity manager
        EntityManager.getInstance().addUser(existingUser);
        EntityManager.getInstance().addUser(aviCohenSu);
        EntityManager.getInstance().addUser(yosiManagerSu);
        EntityManager.getInstance().addTeam(team);

        //success. Yosi the team manager got his team back.
        team.setStatus(TeamStatus.CLOSED); // set status to closed
        UIController.setIsTest(true);
        UIController.setSelector(6621);
        Assert.assertTrue(TOController.reopenTeam(existingUser));

        //cleanup
        teamOwner.removeTeamOwned(team);
        aviTo.removeTeamOwned(team);
        tmanager.removeTeam(team);
        EntityManager.getInstance().removeUserByName(existingUser.getUsername());
        EntityManager.getInstance().removeUserByName(aviCohenSu.getUsername());
        EntityManager.getInstance().removeUserByName(yosiManagerSu.getUsername());
        EntityManager.getInstance().removeTeamByReference(team);
    }

    /**
     * 6.6.2.b
     */
    @Test
    public void reopenTeam2ATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner);
        teamOwner.addTeamToOwn(team);
        //add team owner
        TeamOwner aviTo = (TeamOwner)aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team);
        //add team manager, BUT NOT the team TO the manager
        TeamManager tmanager = (TeamManager)yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);
        //add to Entity manager
        EntityManager.getInstance().addUser(existingUser);
        EntityManager.getInstance().addUser(aviCohenSu);
        EntityManager.getInstance().addUser(yosiManagerSu);
        EntityManager.getInstance().addTeam(team);

        //operation cancelled
        team.setStatus(TeamStatus.CLOSED); // set status to closed
        UIController.setIsTest(true);
        UIController.setSelector(6622);
        Assert.assertFalse(TOController.reopenTeam(existingUser));

        //cleanup
        teamOwner.removeTeamOwned(team);
        aviTo.removeTeamOwned(team);
        tmanager.removeTeam(team);
        EntityManager.getInstance().removeUserByName(existingUser.getUsername());
        EntityManager.getInstance().removeUserByName(aviCohenSu.getUsername());
        EntityManager.getInstance().removeUserByName(yosiManagerSu.getUsername());
        EntityManager.getInstance().removeTeamByReference(team);
    }

    /**
     * 6.6.2.c
     */
    @Test
    public void reopenTeam3ATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner);
        teamOwner.addTeamToOwn(team);
        //add team manager, BUT NOT the team TO the manager
        TeamManager tmanager = (TeamManager)yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);
        //add to Entity manager
        EntityManager.getInstance().addUser(existingUser);
        EntityManager.getInstance().addUser(yosiManagerSu);
        EntityManager.getInstance().addTeam(team);

        //success, but could not retrieve avi cohen's permissions
        team.setStatus(TeamStatus.CLOSED); // set status to closed
        yosiManagerSu.removeRole(tmanager); //Yosi lost his role as Team Manger in the system
        UIController.setIsTest(true);
        UIController.setSelector(6623);
        Assert.assertTrue(TOController.reopenTeam(existingUser));

        //cleanup
        teamOwner.removeTeamOwned(team);
        tmanager.removeTeam(team);
        EntityManager.getInstance().removeUserByName(existingUser.getUsername());
        EntityManager.getInstance().removeUserByName(aviCohenSu.getUsername());
        EntityManager.getInstance().removeUserByName(yosiManagerSu.getUsername());
        EntityManager.getInstance().removeTeamByReference(team);
    }


    /**
     * 9.2.a
     */
    @Test
    public void addSeasonToLeagueATest(){
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new AssociationRepresentative(systemUser));
        EntityManager.getInstance().addUser(systemUser);
        EntityManager.getInstance().addLeague("Premier League");
        UIController.setIsTest(true);
        UIController.setSelector(921); //0 , "2020/21", "2020/21", "2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));
        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2020/21"));
    }

    /**
     * 9.2.b
     */
    @Test
    public void addSeasonToLeague2ATest(){
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new AssociationRepresentative(systemUser));
        EntityManager.getInstance().addUser(systemUser);
        EntityManager.getInstance().addLeague("Premier League");
        UIController.setIsTest(true);
        UIController.setSelector(921); //0 , "2020/21", "2020/21", "2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));
        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2020/21"));

        assertTrue(ARController.addSeasonToLeague(systemUser));

    }

    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
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
        SystemUser abcCreate = new SystemUser("abc1","abc12345","abc");
        TeamOwner abcOwner = new TeamOwner(abcCreate);
        abcOwner.addTeamToOwn(beitShean);
        beitShean.getTeamOwners().add(abcOwner);
        SystemUser elisha = new SystemUser("elevy","Elisha Levy");

        Unregistered abcUnreg = new Unregistered();
        SystemUser abc = abcUnreg.login("abc1","abc12345");
        assertEquals(abc,abcCreate);

        UIController.setSelector(61118);
        assertTrue(Controller.addAsset(abc));

        EntityManager.getInstance().removeUserByReference(abcCreate);


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

        SystemUser abcCreate = new SystemUser("abc1","abc12345","abc");
        TeamOwner abcOwner = new TeamOwner(abcCreate);
        abcOwner.addTeamToOwn(beitShean);
        beitShean.getTeamOwners().add(abcOwner);

        Unregistered abcUnreg = new Unregistered();
        SystemUser abc = abcUnreg.login("abc1","abc12345");
        assertEquals(abc,abcCreate);

        UIController.setSelector(61118);
        try{
            Controller.addAsset(abc);
            EntityManager.getInstance().removeUserByReference(abcCreate);
            fail();
        }
        catch (UserNotFoundException e)
        {
            UIController.printMessage(e.getMessage());
            assertEquals("Could not find user elevy",e.getMessage());
        }


        EntityManager.getInstance().removeUserByReference(abcCreate);
    }


    /**
     * 6.1.3.a
     * preconditions:
     * beitShean - team
     * abcCreate - team owner
     * playerElisha - player
     *
     * edit elisha levy position to DEFENCE
     * success!
     */
    @Test
    public void modifyTeamAssetDetails1ATest() throws Exception {
        Team beitShean = new Team();
        beitShean.setTeamName("Beit Shean");
        SystemUser abcCreate = new SystemUser("abc1", "abc12345", "abc");
        TeamOwner abcOwner = new TeamOwner(abcCreate);
        abcOwner.addTeamToOwn(beitShean);
        beitShean.getTeamOwners().add(abcOwner);

        Unregistered abcUnreg = new Unregistered();
        SystemUser abc = abcUnreg.login("abc1", "abc12345");
        assertEquals(abc, abcCreate);

        SystemUser elivyCreate = new SystemUser("elivy", "abc12345", "elisha levy");
        Player playerElisha = new Player(elivyCreate, new Date());
        beitShean.addTeamPlayer(abcOwner , playerElisha);
        UIController.setIsTest(true);
        UIController.setSelector(6139);
        assertTrue(Controller.modifyTeamAssetDetails(abc));


    }

    /**
     * 6.1.3.b
     * preconditions:
     * beitShean - team
     * abcCreate - team owner
     *
     *
     * No assets to to team
     * failure!
     */
    @Test (expected = AssetsNotExistsException.class)
    public void modifyTeamAssetDetails2ATest() throws Exception {
        Team beitShean = new Team();
        beitShean.setTeamName("Beit Shean");
        SystemUser abcCreate = new SystemUser("abc1", "abc12345", "abc");
        TeamOwner abcOwner = new TeamOwner(abcCreate);
        abcOwner.addTeamToOwn(beitShean);
        beitShean.getTeamOwners().add(abcOwner);

        Unregistered abcUnreg = new Unregistered();
        SystemUser abc = abcUnreg.login("abc1", "abc12345");
        assertEquals(abc, abcCreate);

        SystemUser elivyCreate = new SystemUser("elivy", "abc12345", "elisha levy");
        Player playerElisha = new Player(elivyCreate, new Date());

        UIController.setIsTest(true);
        UIController.setSelector(6139);
        Controller.modifyTeamAssetDetails(abc);


    }

}
