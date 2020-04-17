package Domain.Game;

import Domain.EntityManager;
import Domain.Exceptions.StadiumNotFoundException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Users.*;
import Service.UIController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TeamTest {

    Team team;
    TeamOwnerStub localTeamOwner;
    SystemUser testUser;

    @Before
    public void setUp() {
        team = new Team();
        team.setTeamName("testTeam");
        testUser = new SystemUserStub("test", "testUser", 0);
        localTeamOwner = new TeamOwnerStub(new SystemUserStub("username", "name",0));
    }

    @Test
    public void copyTeamToAnotherUTest() throws Exception
    {
//        Player p2 = new PlayerStub(anotherUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        Team copyTeam = new Team(team);
        assertEquals(0,copyTeam.getTeamPlayers().size());
        assertEquals(0,copyTeam.getTeamOwners().size());
        assertEquals(0,copyTeam.getTeamManagers().size());
        assertEquals(0,copyTeam.getTeamCoaches().size());
        assertEquals(0,copyTeam.getStadiums().size());

        copyTeam = null;
        SystemUser anotherUser = new SystemUserStub("test2","Test User2",0);
        Player p1 = new PlayerStub(anotherUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        team.getTeamPlayers().add(p1);
        assertEquals(1,team.getTeamPlayers().size());
        copyTeam = new Team(team);
        assertEquals(1,copyTeam.getTeamPlayers().size());
        team.getTeamPlayers().remove(p1);
        assertEquals(1,copyTeam.getTeamPlayers().size());

        copyTeam = null;
        anotherUser = new SystemUserStub("test3","Test User3",0);
        Coach c = new CoachStub(anotherUser);
        team.getTeamCoaches().add(c);
        assertEquals(1,team.getTeamCoaches().size());
        copyTeam = new Team(team);
        assertEquals(1,copyTeam.getTeamCoaches().size());
        team.getTeamCoaches().remove(c);
        assertEquals(1,copyTeam.getTeamCoaches().size());

        copyTeam = null;
        anotherUser = new SystemUserStub("test4","Test User4",0);
        TeamManager tm = new TeamManagerStub(anotherUser);
        team.getTeamManagers().add(tm);
        assertEquals(1,team.getTeamManagers().size());
        copyTeam = new Team(team);
        assertEquals(1,copyTeam.getTeamManagers().size());
        team.getTeamManagers().remove(tm);
        assertEquals(1,copyTeam.getTeamManagers().size());

        copyTeam = null;
        anotherUser = new SystemUserStub("test4","Test User4",0);
        TeamOwner to = new TeamOwnerStub(anotherUser);
        team.getTeamOwners().add(to);
        assertEquals(1,team.getTeamOwners().size());
        copyTeam = new Team(team);
        assertEquals(1,copyTeam.getTeamOwners().size());
        team.getTeamOwners().remove(to);
        assertEquals(1,copyTeam.getTeamOwners().size());

        copyTeam = null;
        Stadium s = new StadiumStub("BS","beer");
        team.getStadiums().add(s);
        assertEquals(1,team.getStadiums().size());
        copyTeam = new Team(team);
        assertEquals(1,copyTeam.getStadiums().size());
        team.getStadiums().remove(s);
        assertEquals(1,copyTeam.getStadiums().size());


    }

    @Test
    public void testAddTeamCoachUTest() throws Exception {
        Coach c1 = new CoachStub(testUser);
        boolean result;
        //No Team Owners
        result = team.addTeamCoach(localTeamOwner,c1);
        assertFalse(result);

        team.getTeamOwners().add(localTeamOwner);
        result = team.addTeamCoach(localTeamOwner,c1);
        assertTrue(result);

        SystemUser anotherUser = new SystemUserStub("test2","userTest2", 0);
        TeamManager differentUser = new TeamManagerStub(anotherUser);
        assertFalse(team.addTeamCoach(localTeamOwner,differentUser));
    }

    @Test
    public void testAddTeamManagerUTest() throws Exception {
        TeamManager tm = new TeamManagerStub(testUser);
        boolean result;
        //No Team Owners
        result = team.addTeamManager(localTeamOwner,tm);
        assertFalse(result);

        team.getTeamOwners().add(localTeamOwner);
        result = team.addTeamManager(localTeamOwner,tm);
        assertTrue(result);

        SystemUser anotherUser = new SystemUserStub("test2","userTest2", 0);
        Coach differentUser = new Coach(anotherUser);
        assertFalse(team.addTeamManager(localTeamOwner,differentUser));
    }

    @Test
    public void testPlayersListEqualsUTest() throws Exception
    {
        SystemUser anotherUser = new SystemUserStub("test2","UserTest2",0);
        Team copyTeam = new Team(team);
        Player p1 = new PlayerStub(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        copyTeam.getTeamPlayers().add(p1);
        assertNotEquals(team, copyTeam);
        Player p2 = new PlayerStub(anotherUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        team.getTeamPlayers().add(p2);
        assertNotEquals(team, copyTeam);
        copyTeam.getTeamPlayers().remove(p1);
    }


    @Test
    public void testCoachesListEqualsUTest() throws Exception
    {
        Team copyTeam = new Team(team);
        SystemUser anotherUser = new SystemUserStub("test2","UserTest2",0);
        Coach c1 = new CoachStub(testUser);
        copyTeam.getTeamCoaches().add(c1);
        assertNotEquals(team,copyTeam);
        UIController.setIsTest(true);
        UIController.setSelector(8);
        Coach c2 = new Coach(anotherUser);
        c2.addAllProperties();
        team.getTeamCoaches().add(c2);
        assertNotEquals(team,copyTeam);
        copyTeam.getTeamCoaches().remove(c1);
    }

    @Test
    public void testManagersListEqualsUTest()
    {
        SystemUser anotherUser = new SystemUserStub("test2","UserTest2",0);
        Team copyTeam = new Team(team);
        TeamManager tm = new TeamManagerStub(testUser);
        copyTeam.getTeamManagers().add(tm);
        assertNotEquals(team,copyTeam);
        TeamManager tm2 = new TeamManagerStub(anotherUser);
        team.getTeamManagers().add(tm2);
        assertNotEquals(team,copyTeam);
        copyTeam.getTeamManagers().remove(tm);
        copyTeam.getTeamManagers().remove(tm2);

    }

    @Test
    public void testStadiumsListEqualsUTest() throws Exception
    {
        Team copyTeam = new Team(team);
        StadiumStub st = new StadiumStub("testStadium","bs");
        st.setSelector(1);
        copyTeam.getStadiums().add(st);
        assertNotEquals(team,copyTeam);
        StadiumStub st2 = new StadiumStub("test2Stadium", "bs2");
        st2.setSelector(1);
        team.getStadiums().add(st2);
        assertNotEquals(team,copyTeam);
    }

    @Test
    public void testOwnersListEqualsUTest() throws Exception
    {
        SystemUser anotherUser = new SystemUserStub("test2","UserTest2",0);
        Team copyTeam = new Team(team);
        TeamOwnerStub to = new TeamOwnerStub(testUser);
        to.setSelector(1);
        copyTeam.getTeamOwners().add(to);
        assertNotEquals(team,copyTeam);
        TeamOwnerStub to2 = new TeamOwnerStub(anotherUser);
        to2.setSelector(1);
        team.getTeamOwners().add(to2);
        assertNotEquals(team,copyTeam);
        copyTeam.getTeamOwners().remove(to);
        team.getTeamOwners().remove(to2);
    }


    @Test
    public void testEqualsUTest() throws Exception {
        boolean result = team.equals("o");
        assertFalse(result);

        assertEquals(team, team);

        SystemUser anotherUser = new SystemUserStub("test2","UserTest2",0);
        Team copyTeam = new Team(team);
        assertEquals(team,copyTeam);
    }

    @Test
    public void testGetTeamNameUTest()
    {
        team.setTeamName("testTeamName");
        assertEquals("testTeamName",team.getTeamName());

        assertNotEquals("notName",team.getTeamName());
    }

    @Test
    public void testToStringUTest() throws Exception {
        String result = team.toString();
        Assert.assertEquals("Team {\n" +
                " teamPlayers=Players: \n," +
                " teamCoaches=Coaches: \n," +
                " teamManagers=Managers: \n," +
                " teamOwners=Owners: \n," +
                " teamStadiums=Stadiums: \n" +
                "}", result);

        TeamOwner to2 = new TeamOwnerStub(testUser);
        team.getTeamOwners().add(to2);
        Stadium st2 = new StadiumStub("test2Stadium", "bs2");
        team.getStadiums().add(st2);
        TeamManager tm2 = new TeamManagerStub(testUser);
        team.getTeamManagers().add(tm2);
        UIController.setIsTest(true);
        UIController.setSelector(8);
        Coach c2 = new Coach(testUser);
        c2.addAllProperties();
        team.getTeamCoaches().add(c2);
        Player p2 = new PlayerStub(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        team.getTeamPlayers().add(p2);

        String toString = team.toString();
        Assert.assertEquals("Team {\n" +
                " teamPlayers=Players: \n" +
                "1. Stub\n" +
                ", teamCoaches=Coaches: \n" +
                "1. Stub\n" +
                ", teamManagers=Managers: \n" +
                "1. Stub\n" +
                ", teamOwners=Owners: \n" +
                "1. Stub\n" +
                ", teamStadiums=Stadiums: \n" +
                "1. test2Stadium\n" +
                "}", toString);
    }


    @Test (expected = UserNotFoundException.class)
    public void testAddAssetUserNotFoundUTest() throws Exception {
        team.addAsset("NotAnExistingUserName", localTeamOwner,TeamAsset.PLAYER);
    }

    @Test
    public void testAddStadiumsUTest() {
        boolean result = team.addStadium(new Stadium("stadName", "stadLocation"));
        assertTrue(result);
        result = team.addStadium(new Stadium("stadName", "stadLocation"));
        assertTrue(result);
    }

    @Test
    public void testRemoveStadiumsUTest(){
        team.addStadium(new Stadium("stadName", "stadLocation"));
        boolean result = team.removeStadium(new Stadium("stadName", "stadLocation"));
        assertTrue(result);


        result = team.removeStadium(new Stadium("stadName", "stadLocation"));
        Assert.assertFalse(result);
    }

    @Test
    public void testGetAllAssetsUTest() throws Exception {
        List<Asset> result = team.getAllAssets();
        assertEquals(0,result.size());

        Player p1 = new PlayerStub(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        ((PlayerStub)p1).setSelector(0);
        team.getTeamPlayers().add(p1);
        result = team.getAllAssets();
        assertEquals(1,result.size());
        team.getTeamCoaches().add(new CoachStub(testUser));
        result = team.getAllAssets();
        assertEquals(2,result.size());
        team.getTeamManagers().add(new TeamManagerStub(testUser));
        result = team.getAllAssets();
        assertEquals(3,result.size());
    }

    @Test
    public void testIsTeamOwnerUTest() throws Exception {

        boolean result = team.isTeamOwner(null);
        Assert.assertFalse(result);

        team.getTeamOwners().add(localTeamOwner);
        result = team.isTeamOwner(localTeamOwner);
        assertTrue(result);
    }

    @Test
    public void testAddTeamPlayerUTest() throws Exception {
        Player p1 = new PlayerStub(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"));
        ((PlayerStub)p1).setSelector(0);

        boolean result;
        //No Team Owners
        result = team.addTeamPlayer(localTeamOwner,p1);
        assertFalse(result);

        team.getTeamOwners().add(localTeamOwner);
        result = team.addTeamPlayer(localTeamOwner,p1);
        assertTrue(result);

        SystemUser anotherUser = new SystemUserStub("test2","userTest2", 0);
        Coach differentThenPlayer = new Coach(anotherUser);
        assertFalse(team.addTeamPlayer(localTeamOwner,differentThenPlayer));
    }


    @Test
    public void testAddAssetNotStadiumTrivialUTest() throws Exception
    {
        UIController.setIsTest(true);
        UIController.printMessage("Started Testing AddAsset");
        //TeamOwner is not an Owner
        ((SystemUserStub)testUser).setSelector(6);
        UIController.setSelector(7);
        UIController.printMessage("Started Testing Player False");
        assertFalse(team.addAsset("test",localTeamOwner,TeamAsset.PLAYER));
        team.getTeamOwners().add(localTeamOwner);
        UIController.printMessage("Started Testing Player True");
        assertTrue(team.addAsset("test",localTeamOwner,TeamAsset.PLAYER));

        team.getTeamOwners().remove(localTeamOwner);
        ((SystemUserStub)testUser).setSelector(7);
        UIController.printMessage("Started Testing Coach False");
        assertFalse(team.addAsset("test",localTeamOwner,TeamAsset.COACH));
        team.getTeamOwners().add(localTeamOwner);
        UIController.printMessage("Started Testing Coach True");
        assertTrue(team.addAsset("test",localTeamOwner,TeamAsset.COACH));

        team.getTeamOwners().remove(localTeamOwner);
        ((SystemUserStub)testUser).setSelector(8);
        UIController.printMessage("Started Testing Manager False");
        assertFalse(team.addAsset("test",localTeamOwner,TeamAsset.TEAM_MANAGER));
        team.getTeamOwners().add(localTeamOwner);
        UIController.printMessage("Started Testing Manager True");
        assertTrue(team.addAsset("test",localTeamOwner,TeamAsset.TEAM_MANAGER));


    }

    @Test
    public void testAddAssetNotStadiumNotTrivialUTest() throws Exception
    {
        ((SystemUserStub)testUser).setSelector(9);
        UIController.setIsTest(true);
        UIController.setSelector(7);
        assertFalse(team.addAsset("test",localTeamOwner,TeamAsset.PLAYER));
        UIController.setSelector(7);
        team.getTeamOwners().add(localTeamOwner);
        assertTrue(team.addAsset("test",localTeamOwner,TeamAsset.PLAYER));

        team.getTeamOwners().remove(localTeamOwner);
        UIController.setSelector(8);
        assertFalse(team.addAsset("test",localTeamOwner,TeamAsset.COACH));
        UIController.setSelector(8);
        team.getTeamOwners().add(localTeamOwner);
        assertTrue(team.addAsset("test",localTeamOwner,TeamAsset.COACH));

        team.getTeamOwners().remove(localTeamOwner);
        assertFalse(team.addAsset("test",localTeamOwner,TeamAsset.TEAM_MANAGER));
        team.getTeamOwners().add(localTeamOwner);
        assertTrue(team.addAsset("test",localTeamOwner,TeamAsset.TEAM_MANAGER));

    }


    @Test (expected = StadiumNotFoundException.class)
    public void testAddAssetStadiumNotFoundUTest() throws Exception
    {
        team.addAsset("NotAnExistingStadiumName", localTeamOwner,TeamAsset.STADIUM);
    }

    @Test
    public void testAddAssetStadiumUTest() throws Exception
    {
        StadiumStub stadiumStub = new StadiumStub("vas","BS");
        assertTrue(team.addAsset("vas", localTeamOwner,TeamAsset.STADIUM));
    }

    @Test
    public void testAddAssetStadiumNotTrivialUTest() throws Exception
    {
        Stadium stadiumStub = new Stadium("vas","BS");
        UIController.setIsTest(true);
        UIController.setSelector(9);
        //No Team Owner
        assertFalse(team.addAsset("vas", localTeamOwner,TeamAsset.STADIUM));

        team.getTeamOwners().add(localTeamOwner);
        stadiumStub.changeProperty("name","vas");
        assertTrue(team.addAsset("vas", localTeamOwner,TeamAsset.STADIUM));
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
