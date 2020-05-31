package Domain.Game;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Exceptions.StadiumNotFoundException;
import Domain.Exceptions.UserNotFoundException;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.*;
import Service.UIController;
import org.junit.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class TeamTest {

    private Team team;
    private TeamOwnerStub localTeamOwner;
    private SystemUser testUser;



    @Before
    public void setUp() {
        team = new Team("Test", true);
        team.setTeamName("testTeam");
        testUser = new SystemUserStub("test", "testUser", 0, true);
        localTeamOwner = new TeamOwnerStub(new SystemUserStub("username", "name", 0, true));
    }

    //Irrelevant with db
    /*
    @Test
    public void copyTeamToAnotherUTest() throws Exception {
//        Player p2 = new PlayerStub(anotherUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        Team copyTeam = new Team(team);
        assertEquals(0, copyTeam.getTeamPlayers().size());
        assertEquals(0, copyTeam.getTeamOwners().size());
        assertEquals(0, copyTeam.getTeamManagers().size());
        assertEquals(0, copyTeam.getTeamCoaches().size());
        assertEquals(0, copyTeam.getStadiums().size());

        //Test copy of players list
        copyTeam = null;
       // team.getTeamOwners().add(localTeamOwner);
        team.addTeamOwner(localTeamOwner);
        SystemUser anotherUser = new SystemUserStub("test2", "Test User2", 0, true);
        Player p1 = new PlayerStub(anotherUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        team.addTeamPlayer(localTeamOwner,p1);
        assertEquals(1, team.getTeamPlayers().size());
        copyTeam = new Team(team);
        assertEquals(1, copyTeam.getTeamPlayers().size());
        team.removeTeamPlayer(p1);
        assertEquals(1, copyTeam.getTeamPlayers().size());

        //Test copy of Coaches list
        copyTeam = null;
        anotherUser = new SystemUserStub("test3", "Test User3", 0, true);
        Coach c = new CoachStub(anotherUser);
        team.addTeamCoach(localTeamOwner,c);
        assertEquals(1, team.getTeamCoaches().size());
        copyTeam = new Team(team);
        assertEquals(1, copyTeam.getTeamCoaches().size());
        team.removeTeamCoach(c);
        assertEquals(1, copyTeam.getTeamCoaches().size());
        team.removeTeamOwner(localTeamOwner);

        //Test copy of Managers list
        copyTeam = null;
        anotherUser = new SystemUserStub("test4", "Test User4", 0, true);
        TeamManager tm = new TeamManagerStub(anotherUser);
        team.getTeamManagers().add(tm);
        assertEquals(1, team.getTeamManagers().size());
        copyTeam = new Team(team);
        assertEquals(1, copyTeam.getTeamManagers().size());
        team.getTeamManagers().remove(tm);
        assertEquals(1, copyTeam.getTeamManagers().size());

        //Test copy of Owners list
        copyTeam = null;
        anotherUser = new SystemUserStub("test4", "Test User4", 0, true);
        TeamOwner to = new TeamOwnerStub(anotherUser);
        team.getTeamOwners().add(to);
        assertEquals(1, team.getTeamOwners().size());
        copyTeam = new Team(team);
        assertEquals(1, copyTeam.getTeamOwners().size());
        team.getTeamOwners().remove(to);
        assertEquals(1, copyTeam.getTeamOwners().size());

        //Test copy of Stadiums list
        copyTeam = null;
        Stadium s = new StadiumStub("BS", "beer");
        team.getStadiums().add(s);
        assertEquals(1, team.getStadiums().size());
        copyTeam = new Team(team);
        assertEquals(1, copyTeam.getStadiums().size());
        team.getStadiums().remove(s);
        assertEquals(1, copyTeam.getStadiums().size());


    }

     */

    @Test
    public void testAddTeamCoachUTest() throws Exception {
        Coach c1 = new CoachStub(testUser);
        boolean result;
        //No Team Owners
        result = team.addTeamCoach(localTeamOwner, c1);
        assertFalse(result);

//        team.getTeamOwners().add(localTeamOwner);
        team.addTeamOwner(localTeamOwner);

        result = team.addTeamCoach(localTeamOwner, c1);
        assertTrue(result);

        SystemUser anotherUser = new SystemUserStub("test2", "userTest2", 0, true);
        TeamManager differentUser = new TeamManagerStub(anotherUser);
        assertFalse(team.addTeamCoach(localTeamOwner, differentUser));
    }

    @Test
    public void testAddTeamManagerUTest() throws Exception {
        TeamManager tm = new TeamManager(testUser , true);
        boolean result;
        //No Team Owners
        result = team.addTeamManager(localTeamOwner, tm);
        assertFalse(result);

        //team.getTeamOwners().add(localTeamOwner);
        team.addTeamOwner(localTeamOwner);
        result = team.addTeamManager(localTeamOwner, tm);
        assertTrue(result);

        SystemUser anotherUser = new SystemUserStub("test2", "userTest2", 0, true);
        Coach differentUser = new Coach(anotherUser, true);
        assertFalse(team.addTeamManager(localTeamOwner, differentUser));
    }

    //Irrelevant  with db
    /**
     * Testing team.equals() only on Players list
     *
     * @throws Exception
     */
/*    @Test
    public void testPlayersListEqualsUTest() throws Exception {
        SystemUser anotherUser = new SystemUserStub("test2", "UserTest2", 0, true);
        Team copyTeam = new Team(team);
        assertTrue(copyTeam.addTeamOwner(localTeamOwner));
        Player p1 = new PlayerStub(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        assertTrue(copyTeam.addTeamPlayer(localTeamOwner,p1));
        assertNotEquals(team, copyTeam);
        Player p2 = new PlayerStub(anotherUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        team.addTeamPlayer(localTeamOwner,p2);
        assertNotEquals(team, copyTeam);
    }*/


    //Irrelevant  with db
    /**
     * Testing team.equals() only on Coaches list
     *
     * @throws Exception
     */
/*    @Test
    public void testCoachesListEqualsUTest() throws Exception {
        Team copyTeam = new Team(team);
        SystemUser anotherUser = new SystemUserStub("test2", "UserTest2", 0, true);
        Coach c1 = new CoachStub(testUser);
        copyTeam.addTeamOwner(localTeamOwner);
        copyTeam.addTeamCoach(localTeamOwner,c1);
        assertNotEquals(team, copyTeam);
        UIController.setIsTest(true);
        UIController.setSelector(6118);
        Coach c2 = new Coach(anotherUser, true);
        c2.addAllProperties(team);
        team.addTeamOwner(localTeamOwner);
        team.addTeamCoach(localTeamOwner,c2);
        assertNotEquals(team, copyTeam);
    }*/


    //Irrelevant  with db
    /**
     * Testing team.equals() only on Managers list
     *
     * @throws Exception
     */
/*
    @Test
    public void testManagersListEqualsUTest() {
        SystemUser anotherUser = new SystemUserStub("test2", "UserTest2", 0, true);
        Team copyTeam = new Team(team);
        TeamManager tm = new TeamManagerStub(testUser);
        copyTeam.getTeamManagers().add(tm);
        assertNotEquals(team, copyTeam);
        TeamManager tm2 = new TeamManagerStub(anotherUser);
        team.getTeamManagers().add(tm2);
        assertNotEquals(team, copyTeam);
        copyTeam.getTeamManagers().remove(tm);
        copyTeam.getTeamManagers().remove(tm2);

    }
*/

    //Irrelevant with db
    /**
     * Testing team.equals() only on Stadiums list
     *
     * @throws Exception
     */
/*    @Test
    public void testStadiumsListEqualsUTest() throws Exception {
        Team copyTeam = new Team(team);
        StadiumStub st = new Stadium("testStadium", "bs" , true);
        st.setSelector(1);
        copyTeam.getStadiums().add(st);
        assertNotEquals(team, copyTeam);
        StadiumStub st2 = new StadiumStub("test2Stadium", "bs2");
        st2.setSelector(1);
        team.getStadiums().add(st2);
        assertNotEquals(team, copyTeam);
    }*/

    /*Irrelevant with db*/
    /**
     * Testing team.equals() only on Owners list
     *
     * @throws Exception
     */
//    @Test
//    public void testOwnersListEqualsUTest() throws Exception {
//        SystemUser anotherUser = new SystemUserStub("test2", "UserTest2", 0, true);
//        Team copyTeam = new Team(team);
//        TeamOwnerStub to = new TeamOwnerStub(testUser);
//        to.setSelector(1);
//        copyTeam.getTeamOwners().add(to);
//        assertNotEquals(team, copyTeam);
//        TeamOwnerStub to2 = new TeamOwnerStub(anotherUser);
//        to2.setSelector(1);
//        team.getTeamOwners().add(to2);
//        assertNotEquals(team, copyTeam);
//        copyTeam.getTeamOwners().remove(to);
//        team.getTeamOwners().remove(to2);
//    }


    @Test
    public void testEqualsUTest() throws Exception {
        boolean result = team.equals("o");
        assertFalse(result);

        assertEquals(team, team);

        SystemUser anotherUser = new SystemUserStub("test2", "UserTest2", 0, true);
        Team copyTeam = new Team(team);
        assertEquals(team, copyTeam);
    }

//    @Test
//    public void getBirthDateUTest() throws Exception
//    {
//        @Jailbreak Team anotherTeam = new Team();
//        UIController.setIsTest(true);
//        UIController.setSelector(61110);
//        Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1199");
//        assertEquals(testDate, anotherTeam.getPlayerBirthDate());
//
//        UIController.setSelector(6117);
//        testDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1199");
//        assertEquals(testDate, anotherTeam.getPlayerBirthDate());
//
//    }

    @Test
    public void testGetTeamNameUTest() {
        team.setTeamName("testTeamName");
        assertEquals("testTeamName", team.getTeamName());

        assertNotEquals("notName", team.getTeamName());
    }

   /*Irrelevant when connected to db */
//    @Test
//    public void testToStringUTest() throws Exception {
//        String result = team.toString();
//        Assert.assertEquals("Team {\n" +
//                " teamPlayers=Players: \n," +
//                " teamCoaches=Coaches: \n," +
//                " teamManagers=Managers: \n," +
//                " teamOwners=Owners: \n," +
//                " teamStadiums=Stadiums: \n" +
//                "}", result);
//
//        // Add 1 asset of each kind
//        TeamOwner to2 = new TeamOwnerStub(testUser);
//        team.getTeamOwners().add(to2);
//        Stadium st2 = new StadiumStub("test2Stadium", "bs2");
//        team.getStadiums().add(st2);
//        TeamManager tm2 = new TeamManagerStub(testUser);
//        team.getTeamManagers().add(tm2);
//        UIController.setIsTest(true);
//        UIController.setSelector(6118);
//        Coach c2 = new Coach(testUser, true);
//        c2.addAllProperties(team);
//        team.addTeamCoach(localTeamOwner,c2);
//        Player p2 = new PlayerStub(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
//        team.addTeamPlayer(localTeamOwner,p2);
//
//        String toString = team.toString();
//        Assert.assertEquals("Team {\n" +
//                " teamPlayers=Players: \n" +
//                "1. Stub\n" +
//                ", teamCoaches=Coaches: \n" +
//                "1. Stub\n" +
//                ", teamManagers=Managers: \n" +
//                "1. Stub\n" +
//                ", teamOwners=Owners: \n" +
//                "1. Stub\n" +
//                ", teamStadiums=Stadiums: \n" +
//                "1. test2Stadium\n" +
//                "}", toString);
//    }


    @Test(expected = UserNotFoundException.class)
    public void testAddAssetUserNotFoundUTest() throws Exception {
        team.addAsset("NotAnExistingUserName", localTeamOwner, TeamAsset.PLAYER);
    }

    @Test
    public void testAddStadiumsUTest() {
        boolean result = team.addStadium(new Stadium("stadName", "stadLocation", true));
        assertTrue(result);
        result = team.addStadium(new Stadium("stadNametest", "stadLocation", true));
        assertTrue(result);
    }


    @Test
    public void testRemoveStadiumsUTest() {
        team.addStadium(new Stadium("stadName", "stadLocation", true));
        boolean result = team.removeStadium(new Stadium("stadName", "stadLocation", true));
        assertTrue(result);


        result = team.removeStadium(new Stadium("stadName", "stadLocation", true));
        Assert.assertFalse(result);
    }

    /**
     * This Test is testing getAllAssets()
     * using assets stubs
     * <p>{@link PlayerStub}</p>
     * <p>{@link TeamManagerStub}</p>
     * <p>{@link CoachStub}</p>
     *
     * @throws Exception
     * @see TeamAsset
     */
    @Test
    public void testGetAllAssetsUTest() throws Exception {
        List<Asset> result = team.getAllAssets();
        assertEquals(0, result.size());

        Player p1 = new Player(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993") , true);
        team.addTeamOwner(localTeamOwner);
        team.addTeamPlayer(localTeamOwner,p1);
        result = team.getAllAssets();
        assertEquals(1, result.size());
        team.addTeamCoach(localTeamOwner,new Coach(testUser , true));
        result = team.getAllAssets();
        assertEquals(2, result.size());
        TeamManager teamManager =  new TeamManager(testUser , true);
        team.addTeamManager( localTeamOwner ,teamManager);
        teamManager.addTeam(team , localTeamOwner);
        result = team.getAllAssets();
        assertEquals(3, result.size());
    }

    /**
     * This Test is testing the isTeamOwner() function
     * using stubs
     *
     * @throws Exception
     */
    @Test
    public void testIsTeamOwnerUTest() throws Exception {

        boolean result = team.isTeamOwner(null);
        Assert.assertFalse(result);

        team.addTeamOwner(localTeamOwner);
        result = team.isTeamOwner(localTeamOwner);
        assertTrue(result);
    }

    /**
     * This Test is testing the addTeamPlayer() function
     * using stubs
     *
     * @throws Exception
     */
    @Test
    public void testAddTeamPlayerUTest() throws Exception {
        Player p1 = new Player(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"),true);

        boolean result;
        //No Team Owners
        result = team.addTeamPlayer(localTeamOwner, p1);
        assertFalse(result);

        team.addTeamOwner(localTeamOwner);
        result = team.addTeamPlayer(localTeamOwner, p1);
        assertTrue(result);

        SystemUser anotherUser = new SystemUserStub("test2", "userTest2", 0, true);
        Coach differentThenPlayer = new Coach(anotherUser, true);
        assertFalse(team.addTeamPlayer(localTeamOwner, differentThenPlayer));
    }



    @Test(expected = StadiumNotFoundException.class)
    public void testAddAssetStadiumNotFoundUTest() throws Exception {
        team.addAsset("NotAnExistingStadiumName", localTeamOwner, TeamAsset.STADIUM);
    }

    @Test
    public void testAddAssetStadiumUTest() throws Exception {
        StadiumStub stadiumStub = new StadiumStub("vas", "BS");
        assertTrue(team.addAsset("vas", localTeamOwner, TeamAsset.STADIUM));
    }

    @Test
    public void testAddAssetStadiumNotTrivialUTest() throws Exception {
        Stadium stadiumStub = new Stadium("vas", "BS", true);
        UIController.setIsTest(true);
        UIController.setSelector(6119);
        //No Team Owner
        assertFalse(team.addAsset("vas", localTeamOwner, TeamAsset.STADIUM));

        team.getTeamOwners().add(localTeamOwner);
        stadiumStub.changeProperty(team,"name", "vas");
        assertTrue(team.addAsset("vas", localTeamOwner, TeamAsset.STADIUM));
    }


    @Test
    public void getAllAssetsUTest() {
        Team team = new Team("Test", true);
        assertTrue(team.getAllAssets().size() == 0);
        Stadium stadium = new Stadium("vas", "BS" , true);
        team.addStadium(stadium);
        assertTrue(team.getAllAssets().size() == 1);
        Player player = new Player(new SystemUser("teamTest1", "gal", true), new Date(), true);
        TeamOwner teamOwner = new TeamOwner(new SystemUser("teamTest2", "gal", true), true);
        team.addTeamOwner(teamOwner);
        team.addTeamPlayer(teamOwner, player);
        assertTrue(team.getAllAssets().size() == 2);
        Coach coach = new Coach(new SystemUser("teamTest3", "gal", true), true);
        team.addTeamCoach(teamOwner, coach);
        assertTrue(team.getAllAssets().size() == 3);
        Coach secondCoach = new Coach(new SystemUser("teamTest4", "gal", true) , true);
        team.addTeamCoach(teamOwner, secondCoach);
        assertTrue(team.getAllAssets().size() == 4);
        TeamManager teamManager = new TeamManager(new SystemUser("teamTest5", "gal", true),true);
        team.addTeamManager(teamOwner, teamManager);
        teamManager.addTeam(team,teamOwner);
        assertTrue(team.getAllAssets().size() == 5);


    }

    @Test
    public void getAllPropertyUTest() {
        Team team = new Team("Test", true);
        Asset asset = new AssetStub(6131);
        List<Enum> enumList = asset.getAllPropertyList(team, "Test");
        assertNull(enumList);
        asset = new AssetStub(6132);
        enumList = team.getAllProperty(asset, "Test");
        assertEquals( 0  , enumList.size());
    }


    /*User System*/
    @Test
    public void getAllAssets0ITest() {
        Team team = new Team("Test", true);
        assertEquals(0, team.getAllAssets().size());
        Stadium stadium = new Stadium("vas", "BS", true);
        assertTrue(team.addStadium(stadium));
        assertEquals(1, team.getAllAssets().size());
        Player player = new PlayerStub(new SystemUser("teamTest1", "gal", true), new Date(), 6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUser("teamTest2", "gal", true));
        assertTrue(team.addTeamOwner(teamOwner));
        assertTrue(team.addTeamPlayer(teamOwner, player));
        assertEquals(2, team.getAllAssets().size());
        Coach coach = new CoachStub(new SystemUser("teamTest3", "gal", true));
        assertTrue(team.addTeamCoach(teamOwner, coach));
        assertEquals(3, team.getAllAssets().size());
        Coach secondCoach = new CoachStub(new SystemUser("teamTest4", "gal", true));
        assertTrue(team.addTeamCoach(teamOwner, secondCoach));
        assertTrue(team.getAllAssets().size() == 4);
    }
    /*Stadium*/
    @Test
    public void getAllAssets1ITest() {
        Team team = new Team("Test", true);
        assertTrue(team.getAllAssets().size() == 0);
        Stadium stadium = new Stadium("vas", "BS", true);
        team.addStadium(stadium);
        assertTrue(team.getAllAssets().size() == 1);
        Player player = new PlayerStub(new SystemUser("teamTest1", "gal", true), new Date(), 6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUser("teamTest2", "gal", true));
        team.addTeamOwner(teamOwner);
        team.addTeamPlayer(teamOwner, player);
        assertTrue(team.getAllAssets().size() == 2);
        Coach coach = new CoachStub(new SystemUser("teamTest3", "gal", true));
        team.addTeamCoach(teamOwner, coach);
        assertTrue(team.getAllAssets().size() == 3);
        Coach secondCoach = new CoachStub(new SystemUser("teamTest4", "gal", true));
        team.addTeamCoach(teamOwner, secondCoach);
        assertTrue(team.getAllAssets().size() == 4);
    }

    /*Stadium , Player*/
    @Test
    public void getAllAssets2ITest() {
        Team team = new Team("Test", true);
        assertTrue(team.getAllAssets().size() == 0);
        Stadium stadium = new Stadium("vas", "BS", true);
        team.addStadium(stadium);
        assertTrue(team.getAllAssets().size() == 1);
        Player player = new Player(new SystemUser("teamTest1", "gal", true), new Date(), true);
        TeamOwner teamOwner = new TeamOwner( new SystemUser("teamTest2", "gal", true), true);
        team.addTeamOwner(teamOwner);
        team.addTeamPlayer(teamOwner, player);
        assertTrue(team.getAllAssets().size() == 2);
        Coach coach = new Coach(new SystemUser("teamTest3", "gal", true), true);
        team.addTeamCoach(teamOwner, coach);
        assertTrue(team.getAllAssets().size() == 3);
        Coach secondCoach = new Coach(new SystemUser("teamTest4", "gal", true),true);
        team.addTeamCoach(teamOwner, secondCoach);
        assertTrue(team.getAllAssets().size() == 4);
    }

    /*Stadium , Player , Coach*/
    @Test
    public void getAllAssets3ITest() {
        Team team = new Team("Test", true);
        assertTrue(team.getAllAssets().size() == 0);
        Stadium stadium = new Stadium("vas", "BS", true);
        team.addStadium(stadium);
        assertTrue(team.getAllAssets().size() == 1);
        Player player = new Player(new SystemUser("teamTest1", "gal", true), new Date(), true);
        TeamOwner teamOwner = new TeamOwnerStub( new SystemUser("teamTest2", "gal", true));
        team.addTeamOwner(teamOwner);
        team.addTeamPlayer(teamOwner, player);
        assertTrue(team.getAllAssets().size() == 2);
        Coach coach = new Coach(new SystemUser("teamTest3", "gal", true), true);
        team.addTeamCoach(teamOwner, coach);
        assertTrue(team.getAllAssets().size() == 3);
        Coach secondCoach = new Coach(new SystemUser("teamTest4", "gal", true), true);
        team.addTeamCoach(teamOwner, secondCoach);
        assertTrue(team.getAllAssets().size() == 4);
    }

    /*Stadium , Player , Coach , TeamManager*/
    @Test
    public void getAllAssets4ITest() {
        Team team = new Team("Test", true);
        assertTrue(team.getAllAssets().size() == 0);
        Stadium stadium = new Stadium("vas", "BS", true);
        team.addStadium(stadium);
        assertTrue(team.getAllAssets().size() == 1);
        Player player = new Player(new SystemUser("teamTest1", "gal", true), new Date(), true);
        TeamOwner teamOwner = new TeamOwnerStub( new SystemUser("teamTest2", "gal", true));
        team.addTeamOwner(teamOwner);
        team.addTeamPlayer(teamOwner, player);
        assertTrue(team.getAllAssets().size() == 2);
        Coach coach = new Coach(new SystemUser("teamTest3", "gal", true), true);
        team.addTeamCoach(teamOwner, coach);
        assertTrue(team.getAllAssets().size() == 3);
        Coach secondCoach = new Coach(new SystemUser("teamTest4", "gal", true), true);
        team.addTeamCoach(teamOwner, secondCoach);
        assertTrue(team.getAllAssets().size() == 4);
        TeamManager teamManager = new TeamManager(new SystemUser("teamTest5", "gal", true), true);
        team.addTeamManager(teamOwner, teamManager);
        teamManager.addTeam(team , teamOwner);
        assertTrue(team.getAllAssets().size() == 5);
    }

    /*Stadium , Player , Coach , TeamManager , TeamOwner*/
    @Test
    public void getAllAssets5ITest() {
        Team team = new Team("Test", true);
        assertTrue(team.getAllAssets().size() == 0);
        Stadium stadium = new Stadium("vas", "BS", true);
        team.addStadium(stadium);
        assertTrue(team.getAllAssets().size() == 1);
        Player player = new Player(new SystemUser("teamTest1", "gal", true), new Date(), true);
        TeamOwner teamOwner = new TeamOwner( new SystemUser("teamTest2", "gal", true), true);
        team.addTeamOwner(teamOwner);
        team.addTeamPlayer(teamOwner, player);
        assertTrue(team.getAllAssets().size() == 2);
        Coach coach = new Coach(new SystemUser("teamTest3", "gal", true), true);
        team.addTeamCoach(teamOwner, coach);
        assertTrue(team.getAllAssets().size() == 3);
        Coach secondCoach = new Coach(new SystemUser("teamTest4", "gal", true), true);
        team.addTeamCoach(teamOwner, secondCoach);
        assertTrue(team.getAllAssets().size() == 4);
        TeamManager teamManager = new TeamManager(new SystemUser("teamTest5", "gal", true), true);
        team.addTeamManager(teamOwner, teamManager);
        teamManager.addTeam(team , teamOwner);
        assertTrue(team.getAllAssets().size() == 5);
    }


    /*Stadium*/
    @Test
    public void getAllProperty1ITest() {
        Team team = new Team("Test", true);
        Stadium stadium = new Stadium("vas", "BS", true);
        team.addStadium( stadium);
        List<Enum> enumList = team.getAllProperty(stadium , stadium.namePropertyString);
        assertNull(enumList);
    }

    /*Player*/
    @Test
    public void getAllProperty2ITest() {
        Team team = new Team("Test", true);
        Player player = new Player(new SystemUser("teamTest1", "gal", true), new Date(), true);
        TeamOwner teamOwner = new TeamOwnerStub( new SystemUser("teamTest2", "gal", true));
        team.addTeamOwner(teamOwner);
        team.addTeamPlayer( teamOwner , player);
        List<Enum> enumList = team.getAllProperty(player , player.fieldJobString);
        assertNull(enumList);
    }

    /*Coach*/
    @Test
    public void getAllProperty3ITest() {
        Team team = new Team("Test", true);
        Coach coach = new Coach(new SystemUser("teamTest3", "gal", true), true);
        TeamOwner teamOwner = new TeamOwnerStub( new SystemUser("teamTest2", "gal", true));
        team.addTeamOwner(teamOwner);
        team.addTeamCoach(teamOwner,coach);
        List<Enum> enumList = team.getAllProperty(coach , coach.qualificationString);
        assertNull(enumList);
    }

    /*TeamManger*/
    @Test
    public void getAllProperty4ITest() {
        Team team = new Team("Test", true);
        TeamManager teamManager = new TeamManager(new SystemUser("teamTest3", "gal", true), true);
        TeamOwner teamOwner = new TeamOwnerStub( new SystemUser("teamTest2", "gal", true));
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner , teamManager);
        List<Enum> enumList = team.getAllProperty(teamManager , "Test");
        assertNull(enumList);
        enumList = team.getAllProperty(teamManager , teamManager.permissionsString);
        assertEquals(0 , enumList.size());
        assertTrue(teamManager.addProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        assertFalse(teamManager.addProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        enumList = team.getAllProperty(teamManager , teamManager.permissionsString);
        assertEquals(1 , enumList.size());
        assertTrue(teamManager.removeProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        assertFalse(teamManager.removeProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        enumList = team.getAllProperty(teamManager , teamManager.permissionsString);
        assertEquals(0 , enumList.size());
    }

    /*TeamOwner*/
    @Test
    public void getAllProperty5ITest() {
        Team team = new Team("Test", true);
        TeamManager teamManager = new TeamManager(new SystemUser("teamTest1", "gal", true), true);
        TeamOwner teamOwner = new TeamOwner( new SystemUser("teamTest2", "gal", true), true);
        team.addTeamOwner(teamOwner);
        team.addTeamManager(teamOwner , teamManager);
        List<Enum> enumList = team.getAllProperty(teamManager , "Test");
        assertNull(enumList);
        enumList = team.getAllProperty(teamManager , teamManager.permissionsString);
        assertEquals(0 , enumList.size());
        assertTrue(teamManager.addProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        assertFalse(teamManager.addProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        enumList = team.getAllProperty(teamManager , teamManager.permissionsString);
        assertEquals(1 , enumList.size());
        assertTrue(teamManager.removeProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        assertFalse(teamManager.removeProperty(teamManager.permissionsString , TeamManagerPermissions.ADD_COACH , team));
        enumList = team.getAllProperty(teamManager , teamManager.permissionsString);
        assertEquals(0 , enumList.size());
        Coach coach = new Coach(new SystemUser("teamTest3", "gal", true), true);
        enumList = team.getAllProperty(coach , coach.qualificationString);
        assertNull(enumList);

    }


    @After
    public void tearDown() throws Exception {
        team = null;
        localTeamOwner = null;
        testUser = null;
        UIController.setIsTest(false);
        EntityManager.getInstance().clearAll();
    }


}
