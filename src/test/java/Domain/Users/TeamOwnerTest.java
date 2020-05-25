package Domain.Users;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Game.Team;
import org.junit.Test;
import org.junit.*;

public class TeamOwnerTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();    }

    /**
     * Check if teamOwner is added successfully
     */
    @Test
    public void addTeamToOwnUTest() {
        Team hapoelBash = new Team();
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh", "Oran", 62);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO);
        Assert.assertTrue(newTeamOwner.addTeamToOwn(hapoelBash));
        Assert.assertFalse(newTeamOwner.addTeamToOwn(hapoelBash));
    }

    /**
     * Check if teamOwner is removed successfully
     */
    @Test
    public void removeTeamToOwnUTest() {
        Team hapoelBash = new Team();
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh", "Oran", 62);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO);
        Assert.assertFalse(newTeamOwner.removeTeamOwned(hapoelBash));
        newTeamOwner.addTeamToOwn(hapoelBash);
        Assert.assertTrue(newTeamOwner.removeTeamOwned(hapoelBash));
    }

    /**
     * Check if set appointed is set successfully
     */
    @Test
    public void setAppointedOwnerUTest() {
        Team hapoelBash = new Team();
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh", "Oran", 62);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO);
        Assert.assertFalse(newTeamOwner.setAppointedOwner(null));
        newTeamOwner.setAppointedOwner(newTeamOwner.getSystemUser());
        Assert.assertEquals(newTeamOwner.getSystemUser(), newTeamOwner.getAppointedOwner());

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
