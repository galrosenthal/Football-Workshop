package Acceptance;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Exceptions.AssetsNotExistsException;
import Domain.Exceptions.TeamAlreadyExistsException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Game.*;
import Domain.GameLogger.Event;
import Domain.GameLogger.Injury;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.*;
import Domain.Users.AssociationRepresentative;
import Domain.Users.SystemAdmin;
import Domain.Users.SystemUser;
import Generic.GenericTestAbstract;
import Service.*;
import org.junit.*;
import org.junit.experimental.categories.Category;


import java.io.File;
import java.util.*;
import java.util.Date;

import static Domain.Game.SchedulingPolicy.getDefaultSchedulingPolicy;
import static org.junit.Assert.*;

@Category(RegressionTests.class)
public class AcceptanceTests extends GenericTestAbstract {
    private static SystemUser existingUser;
    private static SystemUser aviCohenSu;
    private static SystemUser yosiManagerSu;


    @Before
    public void start()
    {
        String hashedPasswordForEu = org.apache.commons.codec.digest.DigestUtils.sha256Hex("aBc12345");
        String hashedPasswordForAviYosi = org.apache.commons.codec.digest.DigestUtils.sha256Hex("123Ab456");
        existingUser = new SystemUser("abc", hashedPasswordForEu, "abc", "test@gmail.com", false, true);
        existingUser.addNewRole(new TeamOwner(existingUser, true));
        aviCohenSu = new SystemUser("avicohen", hashedPasswordForAviYosi, "Avi Cohen", "test@gmail.com", false, true);
        aviCohenSu.addNewRole(new TeamOwner(aviCohenSu, true));
        yosiManagerSu = new SystemUser("yosilevi", hashedPasswordForAviYosi, "Yosi Levi", "test@gmail.com", false, true);
        yosiManagerSu.addNewRole(new TeamManager(yosiManagerSu, true));
    }


    @Test
    public void systemBootATest() {
        initEntities();
        UIController.setIsTest(true);
        UIController.setSelector(3);
        assertTrue(Controller.systemBoot());
    }

    private void initEntities() {
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex("12345678");
        SystemUser adminUser = new SystemUser("admin", hashedPassword, "administrator", "test@gmail.com", false, true);
        adminUser.addNewRole(new SystemAdmin(adminUser, true));
        EntityManager.getInstance().addUser(adminUser);
    }

    @Test
    public void addTeamOwnerATest() throws Exception {
        UIController.setIsTest(true);
        Controller.addTeamOwner(new SystemUser("rosengal", "Gal", true));
    }


    //***** login() *****

    @Test
    public void loginATest() throws Exception {
        EntityManager.getInstance().addUser(existingUser);
        //success
        SystemUser user = Controller.login("abc", "aBc12345");


    }

    @Test
    public void login2ATest() throws Exception {
        //not a user
        try {
            SystemUser user2 = Controller.login("notAUser", "aBc12345");
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void login3ATest() throws Exception {
        EntityManager.getInstance().addUser(existingUser);
        //wrong password
        try {
            SystemUser user3 = Controller.login("abc", "pass12345");
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //cleanup
        EntityManager.getInstance().removeUserByReference(existingUser);

    }

    //***** signUp() *****

    @Test
    public void signUpATest() throws Exception {
        //success
        SystemUser user = Controller.signUp("abc", "abc1", "aBc12345","test@gmail.com", false);
    }

    @Test
    public void signUp2ATest() throws Exception {
        //username already exists
        try {
            SystemUser user2 = Controller.signUp("abc", "abc", "aBc12345","test@gmail.com", false);
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //cleanup
        EntityManager.getInstance().removeUserByReference(existingUser);
    }

    @Test
    public void signUp3ATest() throws Exception {
        //password not strong
        try {
            SystemUser user3 = Controller.signUp("abc", "abc1", "123","test@gmail.com", false);
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 9.1.a
     */
    @Test
    public void addLeagueATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        systemUser.addNewRole(new AssociationRepresentative(systemUser, true));
        EntityManager.getInstance().addUser(systemUser);
        UIController.setIsTest(true);
        UIController.setSelector(6);
        //First league creation
        assertTrue(ARController.addLeague(systemUser));

    }

    /**
     * 9.1.b
     */
    @Test
    public void addLeague2ATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        systemUser.addNewRole(new AssociationRepresentative(systemUser, true));
        EntityManager.getInstance().addUser(systemUser);
        EntityManager.getInstance().addLeague(new League("Premier League", true));
        UIController.setIsTest(true);
        UIController.setSelector(6);
        //Duplicated league creation
        assertFalse(ARController.addLeague(systemUser));

    }


    /**
     * 6.6.1.a
     */
    @Test
    public void closeTeamATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner, true);
        teamOwner.addTeamToOwn(team,existingUser);
        //add team owner
        TeamOwner aviTo = (TeamOwner)aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team,aviCohenSu);
        //add team manager
        TeamManager tmanager = (TeamManager)yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);
        tmanager.addTeam(team,teamOwner);

        //success
        UIController.setIsTest(true);
        UIController.setSelector(6611);
        Assert.assertTrue(TOController.closeTeam(existingUser));


    }

    /**
     * 6.6.1.b
     */
    @Test
    public void closeTeam2ATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner, true);
        teamOwner.addTeamToOwn(team,existingUser);
        //add team owner
        TeamOwner aviTo = (TeamOwner)aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team, aviCohenSu);
        //add team manager
        TeamManager tmanager = (TeamManager) yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);
        tmanager.addTeam(team, teamOwner);

        //operation cancelled
        UIController.setIsTest(true);
        UIController.setSelector(6612);
        Assert.assertFalse(TOController.closeTeam(existingUser));

    }

    /**
     * 6.6.2.a
     */
    @Test
    public void reopenTeamATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner)existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner, true);
        teamOwner.addTeamToOwn(team, existingUser);
        //add team owner
        TeamOwner aviTo = (TeamOwner) aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team, aviCohenSu);
        //add team manager, BUT NOT the team TO the manager
        TeamManager tmanager = (TeamManager) yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);

        //success. Yosi the team manager got his team back.
        team.setStatus(TeamStatus.CLOSED); // set status to closed
        UIController.setIsTest(true);
        UIController.setSelector(6621);
        Assert.assertTrue(TOController.reopenTeam(existingUser));

    }

    /**
     * 6.6.2.b
     */
    @Test
    public void reopenTeam2ATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner) existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner, true);
        teamOwner.addTeamToOwn(team, existingUser);
        //add team owner
        TeamOwner aviTo = (TeamOwner) aviCohenSu.getRole(RoleTypes.TEAM_OWNER);
        team.addTeamOwner(aviTo);
        aviTo.addTeamToOwn(team, aviCohenSu);
        //add team manager, BUT NOT the team TO the manager
        TeamManager tmanager = (TeamManager) yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);


        //operation cancelled
        team.setStatus(TeamStatus.CLOSED); // set status to closed
        UIController.setIsTest(true);
        UIController.setSelector(6622);
        Assert.assertFalse(TOController.reopenTeam(existingUser));

    }

    /**
     * 6.6.2.c
     */
    @Test
    public void reopenTeam3ATest() throws Exception {
        //setups
        TeamOwner teamOwner = (TeamOwner) existingUser.getRole(RoleTypes.TEAM_OWNER);
        Team team = new Team("Hapoel Beit Shan", teamOwner, true);
        teamOwner.addTeamToOwn(team, existingUser);
        //add team manager, BUT NOT the team TO the manager
        TeamManager tmanager = (TeamManager) yosiManagerSu.getRole(RoleTypes.TEAM_MANAGER);
        team.addTeamManager(teamOwner, tmanager);

        //success, but could not retrieve avi cohen's permissions
        team.setStatus(TeamStatus.CLOSED); // set status to closed
        yosiManagerSu.removeRole(tmanager); //Yosi lost his role as Team Manger in the system
        UIController.setIsTest(true);
        UIController.setSelector(6623);
        Assert.assertTrue(TOController.reopenTeam(existingUser));

    }


    /**
     * 9.2.a
     */
    @Test
    public void addSeasonToLeagueATest(){
        SystemUser systemUser = new SystemUser("username", "name", true);
        systemUser.addNewRole(new AssociationRepresentative(systemUser, true));
        EntityManager.getInstance().addUser(systemUser);
        EntityManager.getInstance().addLeague(new League("Premier League", true));
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
        SystemUser systemUser = new SystemUser("username", "name", true);
        systemUser.addNewRole(new AssociationRepresentative(systemUser, true));
        EntityManager.getInstance().addUser(systemUser);
        EntityManager.getInstance().addLeague(new League("Premier League", true));
        UIController.setIsTest(true);
        UIController.setSelector(921); //0 , "2020/21", "2020/21", "2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));
        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2020/21"));

        assertTrue(ARController.addSeasonToLeague(systemUser));

    }


    /**
     * 6.1.1.a
     * User for Elisha Levy exists
     */
    @Test
    public void addAsset1ATest() throws Exception {
        Team beitShean = new Team("beitShean", true);

        beitShean.setTeamName("Beit Shean");
        SystemUser abcCreate = Controller.signUp("abc12", "abc1", "aBc12345", "test@gmail.com", false);
        TeamOwner abcOwner = new TeamOwner(abcCreate, true);
        abcOwner.addTeamToOwn(beitShean, abcCreate);
        beitShean.addTeamOwner(abcOwner);
        abcOwner.setAppointedOwner(beitShean , abcCreate);
        beitShean.getTeamOwners().add(abcOwner);
        SystemUser elisha = new SystemUser("elevy","Elisha Levy", true);

        SystemUser abc = Controller.login("abc1", "aBc12345");
        assertEquals(abc, abcCreate);

        UIController.setSelector(61118);
        assertTrue(Controller.addAsset(abc));



    }

    /**
     * 6.1.1.b
     * User for Elisha Levy does not exists
     */
    @Test
    public void addAsset2ATest() throws Exception {
        Team beitShean = new Team("beitShean", true);

        beitShean.setTeamName("Beit Shean");

        SystemUser abcCreate = Controller.signUp("abc12", "abc1", "aBc12345", "test@gmail.com", false);
        TeamOwner abcOwner = new TeamOwner(abcCreate, true);
        abcOwner.addTeamToOwn(beitShean, abcCreate);
        beitShean.addTeamOwner(abcOwner);
        abcOwner.setAppointedOwner(beitShean , abcCreate);

        SystemUser abc = Controller.login("abc1", "aBc12345");
        assertEquals(abc, abcCreate);

        UIController.setSelector(61118);
        try {
            Controller.addAsset(abc);
            EntityManager.getInstance().removeUserByReference(abcCreate);
            fail();
        } catch (UserNotFoundException e) {
            UIController.showNotification(e.getMessage());
            assertEquals("Could not find user elevy", e.getMessage());
        }
    }


    /**
     * 6.1.3.a
     * preconditions:
     * beitShean - team
     * abcCreate - team owner
     * playerElisha - player
     * <p>
     * edit elisha levy position to DEFENCE
     * success!
     */
    @Test
    public void modifyTeamAssetDetails1ATest() throws Exception {
        Team beitShean = new Team("beitShean", true);
        beitShean.setTeamName("Beit Shean");
        SystemUser abcCreate = Controller.signUp("abc12", "abc1", "aBc12345", "test@gmail.com", false);
        TeamOwner abcOwner = new TeamOwner(abcCreate, true);
        abcOwner.addTeamToOwn(beitShean, abcCreate);
        beitShean.addTeamOwner(abcOwner);
        abcOwner.setAppointedOwner(beitShean , abcCreate);

        SystemUser abc = Controller.login("abc1", "aBc12345");
        assertEquals(abc, abcCreate);

        SystemUser elivyCreate = new SystemUser("elivy", "abc12345", "elisha levy", "test@gmail.com", false, true);
        Player playerElisha = new Player(elivyCreate, new Date(), true);
        beitShean.addTeamPlayer(abcOwner, playerElisha);
        UIController.setIsTest(true);
        UIController.setSelector(6139);
        assertTrue(Controller.modifyTeamAssetDetails(abc));


    }

    /**
     * 6.1.3.b
     * preconditions:
     * beitShean - team
     * abcCreate - team owner
     * <p>
     * <p>
     * No assets to to team
     * failure!
     */
    @Test(expected = AssetsNotExistsException.class)
    public void modifyTeamAssetDetails2ATest() throws Exception {
        Team beitShean = new Team("beitShean", true);
        beitShean.setTeamName("Beit Shean");
        SystemUser abcCreate = Controller.signUp("abc12", "abc1", "aBc12345", "test@gmail.com", false);
        TeamOwner abcOwner = new TeamOwner(abcCreate, true);
        abcOwner.addTeamToOwn(beitShean, abcCreate);
        beitShean.addTeamOwner(abcOwner);
        abcOwner.setAppointedOwner(beitShean , abcCreate);

        SystemUser abc = Controller.login("abc1", "aBc12345");
        assertEquals(abc, abcCreate);

        SystemUser elivyCreate = new SystemUser("elivy", "abc12345", "elisha levy", "test@gmail.com", false, true);
        Player playerElisha = new Player(elivyCreate, new Date(), true);

        UIController.setIsTest(true);
        UIController.setSelector(6139);
        Controller.modifyTeamAssetDetails(abc);


    }

    /**
     * 9.3.1.a
     * Main success scenario - a user exists with no Referee role
     */
//    @Test
//    public void addRefereeATest() {
//        UIController.setSelector(9311);
//        addRefereeSuccessTest();
//    }

    /**
     * 9.3.1.b
     * first username entered wasn't found, second username exists with no Referee role.
     */
//    @Test
//    public void addReferee2ATest() {
//        UIController.setSelector(9312);
//        addRefereeSuccessTest();
//    }


    private void addRefereeSuccessTest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);
        SystemUser refereeUser = new SystemUser("AviCohen", "Avi Cohen", true);

        assertTrue(ARController.addReferee(systemUser));

        assertNotNull(refereeUser);
        assertTrue(refereeUser.isType(RoleTypes.REFEREE));
        Referee refRole = (Referee) refereeUser.getRole(RoleTypes.REFEREE);
        assertTrue(refRole.getTraining().equals(RefereeQualification.VAR_REFEREE));
    }

    /**
     * 9.3.1.c
     * failure scenario - a user exists with the given username but he is already a referee.
     */
    @Test
    public void addReferee3ATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);
        SystemUser refereeUser = new SystemUser("AviCohen", "Avi Cohen", true);
        new Referee(refereeUser, RefereeQualification.VAR_REFEREE, true);

        UIController.setSelector(9311);
        //The user is already a referee
        assertFalse(ARController.addReferee(systemUser));
    }

    /**
     * 9.3.2.a
     * Main success scenario - a Referee role was removed successfully
     */
//    @Test
//    public void removeRefereeATest() {
//        SystemUser systemUser = new SystemUser("username", "name", true);
//        new AssociationRepresentative(systemUser, true);
//
//        SystemUser refereeUser = new SystemUser("AviCohen", "Avi Cohen", true);
//        new Referee(refereeUser, RefereeQualification.VAR_REFEREE, true);
//        UIController.setSelector(9321);
//        //There are no referees
//        assertTrue(ARController.removeReferee(systemUser));
//        assertFalse(refereeUser.isType(RoleTypes.REFEREE));
//    }

    /**
     * 9.3.2.b
     * failure scenario - There are no referees
     */
    @Test
    public void removeReferee2ATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);

        SystemUser refereeUser = new SystemUser("AviCohen", "Avi Cohen", true);

        UIController.setSelector(9321);
        //There are no referees
        assertFalse(ARController.removeReferee(systemUser));
    }

    /**
     * 9.4.a
     * Main success scenario - a Referee role was assigned to a season successfully
     */
//    @Test
//    public void assignRefereeATest() {
//        SystemUser systemUser = new SystemUser("username", "name", true);
//        new AssociationRepresentative(systemUser, true);
//        UIController.setSelector(0);
//        EntityManager.getInstance().addLeague(new League("Premier League", true));
//        League league = EntityManager.getInstance().getLeagues().get(0);
//        league.addSeason("2019/20");
//        SystemUser refereeUser = new SystemUser("AviCohen", "Avi Cohen", true);
//        new Referee(refereeUser, RefereeQualification.VAR_REFEREE, true);
//        /*
//        Expected: The referee has been assigned to the season successfully
//         */
//        assertTrue(ARController.assignReferee(systemUser));
//    }

    /**
     * 9.4.b
     * failure scenario - The chosen referee is already assigned to the chosen season
     */
//    @Test
//    public void assignReferee2ATest() {
//        SystemUser systemUser = new SystemUser("username", "name", true);
//        new AssociationRepresentative(systemUser, true);
//        UIController.setSelector(0);
//        EntityManager.getInstance().addLeague(new League("Premier League", true));
//        League league = EntityManager.getInstance().getLeagues().get(0);
//        league.addSeason("2019/20");
//        SystemUser refereeUser = new SystemUser("AviCohen", "Avi Cohen", true);
//        new Referee(refereeUser, RefereeQualification.VAR_REFEREE, true);
//        /*
//        Expected: The referee has been assigned to the season successfully
//         */
//        assertTrue(ARController.assignReferee(systemUser));
//        /*
//        Expected: This referee is already assigned to the chosen season
//         */
//        assertFalse(ARController.assignReferee(systemUser));
//    }

    /**
     * Removes one team owner
     *
     * @throws TeamAlreadyExistsException
     * @throws UserNotFoundException
     */
    @Test
    public void removeTeamOwner1ATest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser arSystemUser = new SystemUser("oran", "oran", true);
        SystemUser toSystemUser = new SystemUser("gal", "gal", true);
        SystemUser to2SystemUser = new SystemUser("merav", "merav", true);
        new AssociationRepresentative(arSystemUser, true);
        UIController.setSelector(631);
        ARController.registerNewTeam(arSystemUser);
        UIController.setSelector(633);
        Controller.addTeamOwner(toSystemUser);
        assertTrue(Controller.removeTeamOwner(to2SystemUser));
        assertEquals(EntityManager.getInstance().getTeam("Hapoel Ta").getTeamOwners().size(), 1);
    }


    /**
     * Removes recursively two team owners
     *
     * @throws TeamAlreadyExistsException
     * @throws UserNotFoundException
     */
    @Test
    public void removeTeamOwner2ATest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser arSystemUser = new SystemUser("oran", "oran", true);
        SystemUser toSystemUser = new SystemUser("gal", "gal", true);
        SystemUser to2SystemUser = new SystemUser("merav", "merav", true);
        SystemUser to3SystemUser = new SystemUser("nir", "nir", true);
        new AssociationRepresentative(arSystemUser, true);
        UIController.setSelector(631);
        ARController.registerNewTeam(arSystemUser);
        UIController.setSelector(633);
        Controller.addTeamOwner(toSystemUser);
        UIController.setSelector(634);
        Controller.addTeamOwner(to2SystemUser);
        UIController.setSelector(633);
        assertTrue(Controller.removeTeamOwner(toSystemUser));
        assertEquals(EntityManager.getInstance().getTeam("Hapoel Ta").getTeamOwners().size(), 1);
    }

    /**
     * Exception by given a user doesnt exist
     *
     * @throws TeamAlreadyExistsException
     * @throws UserNotFoundException
     */
    @Test
    public void removeTeamOwner3ATest() throws TeamAlreadyExistsException, UserNotFoundException {
        try {
            SystemUser arSystemUser = new SystemUser("oran", "oran", true);
            SystemUser toSystemUser = new SystemUser("gal", "gal", true);
            SystemUser to2SystemUser = new SystemUser("merav", "merav", true);
            new AssociationRepresentative(arSystemUser, true);
            UIController.setSelector(631);
            ARController.registerNewTeam(arSystemUser);
            UIController.setSelector(635);
            Controller.addTeamOwner(toSystemUser);
            Controller.removeTeamOwner(to2SystemUser);
        } catch (Exception e) {
            assertEquals("Could not find a user by the given username", e.getMessage());
        }
    }

    /**
     * 9.10.a
     */
//    @Test
//    public void registerNewTeamATest() throws TeamAlreadyExistsException, UserNotFoundException {
//        SystemUser arSystemUser = new SystemUser("username", "name", true);
//        new AssociationRepresentative(arSystemUser, true);
//        SystemUser userToBeOwner = new SystemUser("AviCohen", "name", true);
//        UIController.setSelector(91011); // Hapoel Beit Shan
//
//        assertTrue(ARController.registerNewTeam(arSystemUser));
//        assertNotNull(userToBeOwner);
//        assertTrue(userToBeOwner.isType(RoleTypes.TEAM_OWNER));
//        TeamOwner toRole = (TeamOwner) userToBeOwner.getRole(RoleTypes.TEAM_OWNER);
//        assertEquals(1, toRole.getOwnedTeams().size());
//
//    }

    /**
     * 9.10.b
     */
    @Test
    public void registerNewTeam2ATest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser arSystemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(arSystemUser, true);
        SystemUser userToBeOwner = new SystemUser("AviCohen", "name", true);

        SystemUser teamOwnerUser = new SystemUser("to", "name", true);
        new TeamOwner(teamOwnerUser, true);
        TeamOwner toRole = (TeamOwner) teamOwnerUser.getRole(RoleTypes.TEAM_OWNER);
        Team existingTeam = new Team("Hapoel Beit Shan", toRole, true);
        EntityManager.getInstance().addTeam(existingTeam);
        //Team already exists
        UIController.setSelector(91021); // Hapoel Beit Shan
        try {
            ARController.registerNewTeam(arSystemUser);
            Assert.fail();
        } catch (TeamAlreadyExistsException e) {
            e.printStackTrace();
        }
        assertNotNull(userToBeOwner);
        assertFalse(userToBeOwner.isType(RoleTypes.TEAM_OWNER));
    }

    /**
     * 9.10.c
     */
    @Test
    public void registerNewTeam3ATest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser arSystemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(arSystemUser, true);
        UIController.setSelector(91031); // Hapoel Beit Shan, then NOTaUSERNAME
        try {
            ARController.registerNewTeam(arSystemUser);
            Assert.fail();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 9.5.1.a
     * Main success scenario - A new points policy is created.
     */
    @Test
    public void addPointsPolicyATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);
        UIController.setSelector(9511);//1,-1,0
        assertTrue(ARController.addPointsPolicy(systemUser));
        assertTrue(EntityManager.getInstance().doesPointsPolicyExists(1, -1, 0));
        assertNotNull(EntityManager.getInstance().getPointsPolicy(1, -1, 0));
        /*
        Expected: The new points policy has been added successfully
         */
    }

    /**
     * 9.5.1.b
     * failure scenario - A points policy
     */
    @Test
    public void addPointsPolicy2ATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);
        UIController.setSelector(9511); //1,-1,0
        assertTrue(ARController.addPointsPolicy(systemUser));
        assertFalse(ARController.addPointsPolicy(systemUser));
        /*
        Expected: This points policy already exists
         */
    }

    /**
     * 9.5.2.a
     * Main success scenario - A points policy was changed in a season.
     */
    @Test
    public void setPointsPolicyATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);
        EntityManager.getInstance().addLeague(new League("Premier League", true));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2019/20");

        AssociationRepresentative aR = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);
        try {
            aR.addPointsPolicy(1, -1, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(league.getSeasons().get(0).getPointsPolicy().equals(3, 0, 1));
        UIController.setSelector(95211);
        assertTrue(ARController.setPointsPolicy(systemUser));
        assertTrue(league.getSeasons().get(0).getPointsPolicy().equals(1, -1, 0));
        /*
        Expected: The chosen points policy was set successfully
         */
    }

    /**
     * 9.11.a
     */
    @Test
    public void addTeamsToSeasonATest(){
        SystemUser arSystemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(arSystemUser, true);
        new TeamOwner(arSystemUser, true);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team team1 = new Team("Hapoel Beit Shan", toRole, true);
        Team team2 = new Team("Hapoel Beer Sheva", toRole, true);
        League league = new League("Ligat ul", true);
        league.addSeason("2020/21");
        Season season = league.getLatestSeason();
        assertNotNull(season);
        assertEquals("2020/21", season.getYears());
        EntityManager.getInstance().addTeam(team1);
        EntityManager.getInstance().addTeam(team2);
        EntityManager.getInstance().addLeague(league);
        UIController.setSelector(9111);
        assertTrue(ARController.addTeamsToSeason(arSystemUser));
        assertEquals(2, season.getTeams().size());
        assertEquals(1, team1.getSeasons().size());
        assertEquals(1, team2.getSeasons().size());
    }

    /**
     * 9.12.a
     */
    @Test
    public void removeTeamsFromSeasonATest() {
        SystemUser arSystemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(arSystemUser, true);
        new TeamOwner(arSystemUser, true);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team team1 = new Team("Hapoel Beit Shan", toRole, true);
        Team team2 = new Team("Hapoel Beer Sheva", toRole, true);
        League league = new League("Ligat ul", true);
        league.addSeason("2020/21");
        Season season = league.getLatestSeason();
        assertNotNull(season);
        assertEquals("2020/21", season.getYears());
        season.addTeam(team1);
        season.addTeam(team2);
        team1.addSeason(season);
        team2.addSeason(season);
        EntityManager.getInstance().addTeam(team1);
        EntityManager.getInstance().addTeam(team2);
        EntityManager.getInstance().addLeague(league);
        assertEquals(2, season.getTeams().size());
        assertEquals(1, team1.getSeasons().size());
        assertEquals(1, team2.getSeasons().size());
        UIController.setSelector(9121);
        assertTrue(ARController.removeTeamsFromSeason(arSystemUser));
        assertEquals(0, season.getTeams().size());
        assertEquals(0, team1.getSeasons().size());
        assertEquals(0, team2.getSeasons().size());
    }

    /**
     * 9.6.a
     * Main success scenario - A new scheduling policy is created.
     */
    @Test
    public void addSchedulingPolicyATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);
        UIController.setSelector(962);//1,1,1,..
        assertTrue(ARController.addSchedulingPolicy(systemUser));
        assertTrue(EntityManager.getInstance().doesSchedulingPolicyExists(1, 1, 1));
        assertNotNull(EntityManager.getInstance().getSchedulingPolicy(1, 1, 1));
        /*
        Expected: The new scheduling policy has been added successfully
         */
    }
    /**
     * 9.6.b
     * failure scenario - A scheduling policy with the same values already exists
     */
    @Test
    public void addSchedulingPolicy2ATest() {
        SystemUser systemUser = new SystemUser("username", "name", true);
        new AssociationRepresentative(systemUser, true);
        UIController.setSelector(962); //1,1,1,...
        assertTrue(ARController.addSchedulingPolicy(systemUser));
        assertFalse(ARController.addSchedulingPolicy(systemUser));
        /*
        Expected: This scheduling policy already exists
         */
    }

    /**
     * 9.7.a
     * Main success scenario - activating scheduling policy.
     * Season has no prior schedule.
     */
//    @Test
//    public void activateSchedulingPolicyATest(){
//        SystemUser arSystemUser = new SystemUser("username", "name", true);
//        SystemUser to1SystemUser = new SystemUser("teamowner1", "Team Owner", true);
//        SystemUser to2SystemUser = new SystemUser("teamowner2", "Team Owner", true);
//        new AssociationRepresentative(arSystemUser, true);
//        new TeamOwner(arSystemUser, true);
//        new TeamOwner(to1SystemUser, true);
//        new TeamOwner(to2SystemUser, true);
//        AssociationRepresentative aR = (AssociationRepresentative) arSystemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);
//        TeamOwner to1 = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
//        TeamOwner to2 = (TeamOwner) to1SystemUser.getRole(RoleTypes.TEAM_OWNER);
//        TeamOwner to3 = (TeamOwner) to2SystemUser.getRole(RoleTypes.TEAM_OWNER);
//
//        EntityManager.getInstance().addLeague(new League("Premier League", true));
//        League league = EntityManager.getInstance().getLeagues().get(0);
//        league.addSeason("2019/20");
//        Season season = league.getLatestSeason();
//        Team team1 = createFullTeam("team1", to1);
//        Team team2 = createFullTeam("team2", to2);
//        Team team3 = createFullTeam("team3", to3);
//        season.addTeam(team1);
//        season.addTeam(team2);
//        season.addTeam(team3);
//        team1.addSeason(season);
//        team2.addSeason(season);
//        team3.addSeason(season);
//
//        assertFalse(season.scheduled());
//        assertEquals(0, season.getGames().size());
//        UIController.setSelector(9711); //0,"10/12/2019",0
//        assertTrue(ARController.activateSchedulingPolicy(arSystemUser));
//        assertTrue(season.scheduled());
//        assertTrue(season.getGames().size() > 0);
//
//    }

    /**
     * 9.7.b
     * Alternative scenario 1 - activating scheduling policy.
     * Season has prior schedule, the AR agrees to override it.
     */
//    @Test
//    public void activateSchedulingPolicy2ATest() throws Exception {
//        SystemUser arSystemUser = new SystemUser("username", "name", true);
//        SystemUser to1SystemUser = new SystemUser("teamowner1", "Team Owner", true);
//        SystemUser to2SystemUser = new SystemUser("teamowner2", "Team Owner", true);
//        new AssociationRepresentative(arSystemUser, true);
//        new TeamOwner(arSystemUser, true);
//        new TeamOwner(to1SystemUser, true);
//        new TeamOwner(to2SystemUser, true);
//        AssociationRepresentative aR = (AssociationRepresentative) arSystemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);
//        TeamOwner to1 = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
//        TeamOwner to2 = (TeamOwner) to1SystemUser.getRole(RoleTypes.TEAM_OWNER);
//        TeamOwner to3 = (TeamOwner) to2SystemUser.getRole(RoleTypes.TEAM_OWNER);
//
//        EntityManager.getInstance().addLeague(new League("Premier League", true));
//        League league = EntityManager.getInstance().getLeagues().get(0);
//        league.addSeason("2019/20");
//        Season season = league.getLatestSeason();
//        Team team1 = createFullTeam("team1", to1);
//        Team team2 = createFullTeam("team2", to2);
//        Team team3 = createFullTeam("team3", to3);
//        season.addTeam(team1);
//        season.addTeam(team2);
//        season.addTeam(team3);
//        team1.addSeason(season);
//        team2.addSeason(season);
//        team3.addSeason(season);
//        //Scheduling the season
//        season.scheduleGames(getDefaultSchedulingPolicy(), new Date());
//        assertTrue(season.scheduled());
//        assertTrue(season.getGames().size() > 0);
//
//        //AR overrides scheduling
//        UIController.setSelector(9721); //0,true,"10/12/2019",0
//        assertTrue(ARController.activateSchedulingPolicy(arSystemUser));
//        assertTrue(season.scheduled());
//        assertTrue(season.getGames().size() > 0);
//    }

    /**
     * 9.7.c
     * Alternative scenario 2 - activating scheduling policy.
     * Season has prior schedule, the AR decided to cancel the operation.
     */
//    @Test
//    public void activateSchedulingPolicy3ATest() throws Exception {
//        SystemUser arSystemUser = new SystemUser("username", "name", true);
//        SystemUser to1SystemUser = new SystemUser("teamowner1", "Team Owner", true);
//        SystemUser to2SystemUser = new SystemUser("teamowner2", "Team Owner", true);
//        new AssociationRepresentative(arSystemUser, true);
//        new TeamOwner(arSystemUser, true);
//        new TeamOwner(to1SystemUser, true);
//        new TeamOwner(to2SystemUser, true);
//        AssociationRepresentative aR = (AssociationRepresentative) arSystemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);
//        TeamOwner to1 = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
//        TeamOwner to2 = (TeamOwner) to1SystemUser.getRole(RoleTypes.TEAM_OWNER);
//        TeamOwner to3 = (TeamOwner) to2SystemUser.getRole(RoleTypes.TEAM_OWNER);
//
//        EntityManager.getInstance().addLeague(new League("Premier League", true));
//        League league = EntityManager.getInstance().getLeagues().get(0);
//        league.addSeason("2019/20");
//        Season season = league.getLatestSeason();
//        Team team1 = createFullTeam("team1", to1);
//        Team team2 = createFullTeam("team2", to2);
//        Team team3 = createFullTeam("team3", to3);
//        season.addTeam(team1);
//        season.addTeam(team2);
//        season.addTeam(team3);
//        team1.addSeason(season);
//        team2.addSeason(season);
//        team3.addSeason(season);
//        //Scheduling the season
//        season.scheduleGames(getDefaultSchedulingPolicy(), new Date());
//        assertTrue(season.scheduled());
//        assertTrue(season.getGames().size() > 0);
//
//        //AR canceled re-scheduling
//        UIController.setSelector(9731); //0,false
//        assertFalse(ARController.activateSchedulingPolicy(arSystemUser));
//        assertTrue(season.scheduled());
//        assertTrue(season.getGames().size() > 0);
//    }

    private Team createFullTeam(String teamName, TeamOwner teamOwner) {
        Team team = new Team(teamName, teamOwner, true);
        team.addStadium(new Stadium("stadium1"," location1", true));
        for(int i = 1; i<= 11; i++){ //add 11 players
            SystemUser pSystemUser = new SystemUser("player"+teamName+""+i, "name"+i, true);
            new Player(pSystemUser, new Date(), true);
            Player player = (Player) pSystemUser.getRole(RoleTypes.PLAYER);
            team.addTeamPlayer(teamOwner, player);
        }
        return team;
    }

    /**
     * 10.3.a
     * Main success scenario - A new Injury event is created.
     */
//    @Test
//    public void updateGameEventsATest() {
//        SystemUser systemUser = new SystemUser("username", "name", true);
//        systemUser.addNewRole(new Referee(systemUser,RefereeQualification.VAR_REFEREE, true));
//        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);
//
//        SystemUser arSystemUser = new SystemUser("arSystemUser", "arUser", true);
//        new AssociationRepresentative(arSystemUser, true);
//        new TeamOwner(arSystemUser, true);
//        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
//        Team firstTeam = new Team("Hapoel Beit Shan", toRole, true);
//        Team secondTeam = new Team("Hapoel Beer Sheva", toRole, true);
//
//        Game game = new Game(new Stadium("staName", "staLoca", true), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>(), true);
//        Player player1 = new Player(new SystemUser("AviCohen","Avi Cohen", true),new Date(2001, 01, 01), true);
//        firstTeam.addTeamPlayer(toRole,player1);
//
//        game.addReferee(referee);
//        referee.addGame(game);
//
//        UIController.setSelector(10314); //0,6,0,1
//        assertTrue(RefereeController.updateGameEvents(systemUser));
//        //The new Penalty has been added successfully
//        Event event = game.getEventsLogger().getGameEvents().get(0);
//        assertTrue(event instanceof Injury);
//        assertTrue(((Injury) event).getMinute() == 1);
//        assertTrue(((Injury) event).getInjuredPlayer().equals(player1));
//        /*
//        Expected: The new Injury has been added successfully
//         */
//    }

    /**
     * 10.4.a
     * Main success scenario - A Game  Report is produced
     */
//    @Test
//    public void produceGameReportATest() {
//        SystemUser systemUser = new SystemUser("username", "name", true);
//        systemUser.addNewRole(new Referee(systemUser,RefereeQualification.VAR_REFEREE, true));
//        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);
//
//        SystemUser arSystemUser = new SystemUser("arSystemUser", "arUser", true);
//        new AssociationRepresentative(arSystemUser, true);
//        new TeamOwner(arSystemUser, true);
//        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
//        Team firstTeam = new Team("Hapoel Beit Shan", toRole, true);
//        Team secondTeam = new Team("Hapoel Beer Sheva", toRole, true);
//
//        Game game = new Game(new Stadium("staName", "staLoca", true), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>(), true);
//        Player player1 = new Player(new SystemUser("AviCohen","Avi Cohen", true),new Date(2001, 01, 01), true);
//        firstTeam.addTeamPlayer(toRole,player1);
//
//        game.addReferee(referee);
//        referee.addGame(game);
//
//        try {
//            game.addGoal(game.getHomeTeam(), game.getAwayTeam(),
//                    player1, 2);
//        } catch (Exception e) {
//        }
//        game.addEndGame(new Date(), 90); // end the game
//
//        UIController.setSelector(10411);//0,"."
//        boolean succeeded = RefereeController.produceGameReport(systemUser);
//        assertTrue(succeeded);
//        if (succeeded) {
//            //delete the created report
//            File dir = new File(".");
//            File[] directoryListing = dir.listFiles();
//            for (File file : directoryListing) {
//                if (file.getName().startsWith("GameReport_Hapoel")) {
//                    assertTrue(file.delete());
//                }
//            }
//        }
//    }

    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
    }

}
