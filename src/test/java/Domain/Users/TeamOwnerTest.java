package Domain.Users;

import Domain.Game.Team;
import org.junit.Test;
import org.junit.*;
public class TeamOwnerTest {

    /**
     * Check if teamOwner is added successfully
     */
    @Test
    public void addTeamToOwnUTest(){
        Team hapoelBash = new Team();
        hapoelBash.setTeamName("Hapoel Beer Sheeva");

        SystemUser sysUserTO = new SystemUserStub("OranSh","Oran",62);
        TeamOwner newTeamOwner = new TeamOwner(sysUserTO);
        Assert.assertTrue(newTeamOwner.addTeamToOwn(hapoelBash));
        Assert.assertFalse(newTeamOwner.addTeamToOwn(hapoelBash));
    }


}
