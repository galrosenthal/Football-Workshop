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
    /*TODO FIX!!!!!!!!!*/
    @Test
    public void addTeamToOwnUTest() {
        Team hapoelBash = new Team("Test", true);
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh", "Oran", 62, true);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO, true);
        Assert.assertTrue(newTeamOwner.addTeamToOwn(hapoelBash,sysUserTO));
        Assert.assertFalse(newTeamOwner.addTeamToOwn(hapoelBash,sysUserTO));
    }

    /**
     * Check if teamOwner is removed successfully
     */
    @Test
    public void removeTeamToOwnUTest() {
        Team hapoelBash = new Team("Test", true);
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh", "Oran", 62, true);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO, true);
        Assert.assertFalse(newTeamOwner.removeTeamOwned(hapoelBash));
        newTeamOwner.addTeamToOwn(hapoelBash,sysUserTO);
        Assert.assertTrue(newTeamOwner.removeTeamOwned(hapoelBash));
    }

    /**
     * Check if set appointed is set successfully
     */
    @Test
    public void setAppointedOwnerUTest() {
        Team hapoelBash = new Team("Test", true);
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh", "Oran", 62, true);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO, true);
        Assert.assertFalse(newTeamOwner.setAppointedOwner(hapoelBash,null));
        newTeamOwner.setAppointedOwner(hapoelBash,newTeamOwner.getSystemUser());
        Assert.assertEquals(newTeamOwner.getSystemUser(),newTeamOwner.getAppointedOwner(hapoelBash));

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
