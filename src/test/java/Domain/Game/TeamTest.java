package Domain.Game;

import Domain.Exceptions.UserNotFoundException;
import Domain.Users.*;
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
        testUser = new SystemUserStub("test", "testUser", 0);
        localTeamOwner = new TeamOwnerStub(new SystemUserStub("username", "name",0));
    }


    @Test
    public void testAddTeamPlayerUTest() throws Exception {
//        boolean result = team.addTeamPlayer(new TeamOwner(RoleTypes.FAN, null), null);
//        Assert.assertEquals(true, result);
    }

    @Test
    public void testAddTeamCoachUTest() throws Exception {
//        boolean result = team.addTeamCoach(new TeamOwner(RoleTypes.FAN, null), null);
//        Assert.assertEquals(true, result);
    }

    @Test
    public void testAddTeamManagerUTest() throws Exception {
//        boolean result = team.addTeamManager(new TeamOwner(RoleTypes.FAN, null), null);
//        Assert.assertEquals(true, result);
    }

    @Test
    public void testAddTeamOwnerUTest() throws Exception {
//        boolean result = team.addTeamOwner(new TeamOwner(RoleTypes.FAN, new SystemUser("username", null, "name")), new TeamOwner(RoleTypes.FAN, new SystemUser("username", null, "name")));
//        Assert.assertEquals(true, result);
    }

    @Test
    public void testEqualsUTest() throws Exception {
        boolean result = team.equals("o");
        assertFalse(result);
    }

    @Test
    public void testToStringUTest() throws Exception {
        String result = team.toString();
        Assert.assertEquals("Team{" +
                " teamPlayers=Players: \n," +
                " teamCoaches=Coaches: \n," +
                " teamManagers=Managers: \n," +
                " teamOwners=Owners: \n," +
                " teamStadiums=Stadiums: \n" +
                "}", result);
    }


    @Test (expected = UserNotFoundException.class)
    public void testAddAssetUserNotFoundUTest() throws Exception {
        team.addAsset("shtut", localTeamOwner,TeamAsset.PLAYER);
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
//        Assert.assertEquals(Arrays.<Asset>asList(new Coach(RoleTypes.FAN, new SystemUser("username", null, "name"))), result);
    }

    @Test
    public void testIsTeamOwnerUTest() throws Exception {

        boolean result = team.isTeamOwner(null);
        Assert.assertFalse(result);

        result = team.isTeamOwner(localTeamOwner);
        assertTrue(result);
    }

    @Test
    public void testAddPlayerUTest() throws Exception {
        Player p1 = new PlayerStub(testUser, new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993"),0);
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


}
