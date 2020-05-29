package Domain.Users;

import Domain.Game.Team;
import org.junit.Test;
import org.junit.*;
public class TeamOwnerTest {

    /**
     * Check if teamOwner is added successfully
     */
    @Test
    public void addTeamToOwnUTest() {
        Team hapoelBash = new Team();
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh", "Oran", 62);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO);
        Assert.assertTrue(newTeamOwner.addTeamToOwn(hapoelBash,sysUserTO));
        Assert.assertFalse(newTeamOwner.addTeamToOwn(hapoelBash,sysUserTO));
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
        newTeamOwner.addTeamToOwn(hapoelBash,sysUserTO);
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
        Assert.assertFalse(newTeamOwner.setAppointedOwner(hapoelBash,null));
        newTeamOwner.setAppointedOwner(hapoelBash,newTeamOwner.getSystemUser());
        Assert.assertEquals(newTeamOwner.getSystemUser(),newTeamOwner.getAppointedOwner(hapoelBash));

    }
}
