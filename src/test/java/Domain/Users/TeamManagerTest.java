package Domain.Users;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Game.TeamStub;
import Generic.GenericTestAbstract;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class TeamManagerTest extends GenericTestAbstract {


    @Test
    public void getPropertiesUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        List<String> allProperties = teamManager.getProperties();
        Assert.assertTrue(allProperties.size() == 1);
        Assert.assertTrue(allProperties.contains(teamManager.permissionsString));
    }

    @Test
    public void changePropertyUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        Assert.assertFalse(teamManager.changeProperty(null, "test", teamManager.permissionsString));

    }

    @Test
    public void isListPropertyUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        Assert.assertTrue(teamManager.isListProperty(teamManager.permissionsString));
        Assert.assertFalse(teamManager.isListProperty("Test"));
    }

    @Test
    public void isStringPropertyUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        Assert.assertFalse(teamManager.isStringProperty(teamManager.permissionsString));
    }

    @Test
    public void isEnumPropertyUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        Assert.assertFalse(teamManager.isEnumProperty(teamManager.permissionsString));
    }

    @Test
    public void getAllValuesUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        List<Enum> enumList = teamManager.getAllValues(teamManager.permissionsString);
        Assert.assertTrue(enumList.size() == TeamManagerPermissions.values().length);
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.ADD_COACH));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.ADD_PLAYER));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.CHANGE_POSITION_PLAYER));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.CHANGE_TEAM_JOB_COACH));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.REMOVE_COACH));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.REMOVE_PLAYER));
        enumList = teamManager.getAllValues("Test");
        Assert.assertNull(enumList);
    }

    @Test
    public void getAllPropertyListUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        List<Enum> enumList = teamManager.getAllPropertyList(new TeamStub(6131), "Test");
        assertNull(enumList);
        Team team = new TeamStub(6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUserStub("teamManagerTestUS", "gal", 6131));
        teamManager.addTeam(team, teamOwner);
        enumList = teamManager.getAllPropertyList(team, teamManager.permissionsString);
        assertTrue(enumList.size() == 0);
    }

    @Test
    public void addPropertyUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        Team team = new TeamStub(6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUserStub("teamManagerTestUS", "gal", 6131));
        teamManager.addTeam(team, teamOwner);
        assertTrue(teamManager.addProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));
        assertFalse(teamManager.addProperty("Test", TeamManagerPermissions.ADD_COACH, team));
        assertFalse(teamManager.addProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));


    }

    @Test
    public void removePropertyUTest() {
        TeamManager teamManager = new TeamManager(new SystemUserStub("teamManagerTest", "gal", 6131), true);
        Team team = new TeamStub(6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUserStub("teamManagerTestUS", "gal", 6131));
        teamManager.addTeam(team, teamOwner);
        assertFalse(teamManager.removeProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));
        teamManager.addProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team);
        assertTrue(teamManager.removeProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));

    }


    @Test
    public void getPropertiesITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        List<String> allProperties = teamManager.getProperties();
        Assert.assertTrue(allProperties.size() == 1);
        Assert.assertTrue(allProperties.contains(teamManager.permissionsString));
    }

    @Test
    public void changePropertyITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        Assert.assertFalse(teamManager.changeProperty(null, "test", teamManager.permissionsString));

    }

    @Test
    public void isListPropertyITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        Assert.assertTrue(teamManager.isListProperty(teamManager.permissionsString));
        Assert.assertFalse(teamManager.isListProperty("Test"));
    }

    @Test
    public void isStringPropertyITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        Assert.assertFalse(teamManager.isStringProperty(teamManager.permissionsString));
    }

    @Test
    public void isEnumPropertyITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        Assert.assertFalse(teamManager.isEnumProperty(teamManager.permissionsString));
    }

    @Test
    public void getAllValuesITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        List<Enum> enumList = teamManager.getAllValues(teamManager.permissionsString);
        Assert.assertTrue(enumList.size() == TeamManagerPermissions.values().length);
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.ADD_COACH));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.ADD_PLAYER));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.CHANGE_POSITION_PLAYER));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.CHANGE_TEAM_JOB_COACH));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.REMOVE_COACH));
        Assert.assertTrue(enumList.contains(TeamManagerPermissions.REMOVE_PLAYER));
        enumList = teamManager.getAllValues("Test");
        Assert.assertNull(enumList);
    }

    @Test
    public void getAllPropertyListITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        List<Enum> enumList = teamManager.getAllPropertyList(new TeamStub(6131), "Test");
        assertNull(enumList);
        Team team = new TeamStub(6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUserStub("teamManagerTestUS", "gal", 6131));
        teamManager.addTeam(team, teamOwner);
        enumList = teamManager.getAllPropertyList(team, teamManager.permissionsString);
        assertTrue(enumList.size() == 0);
    }

    @Test
    public void addPropertyITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        Team team = new TeamStub(6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUserStub("teamManagerTestUS", "gal", 6131));
        teamManager.addTeam(team, teamOwner);
        assertTrue(teamManager.addProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));
        assertFalse(teamManager.addProperty("Test", TeamManagerPermissions.ADD_COACH, team));
        assertFalse(teamManager.addProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));


    }

    @Test
    public void removePropertyITest() {
        TeamManager teamManager = new TeamManager(new SystemUser("teamManagerTest", "gal"), true);
        Team team = new TeamStub(6131);
        TeamOwner teamOwner = new TeamOwnerStub(new SystemUserStub("teamManagerTestUS", "gal", 6131));
        teamManager.addTeam(team, teamOwner);
        assertFalse(teamManager.removeProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));
        teamManager.addProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team);
        assertTrue(teamManager.removeProperty(teamManager.permissionsString, TeamManagerPermissions.ADD_COACH, team));

    }

    @After
    public void tearDown() {
        EntityManager.getInstance().clearAll();
    }

}