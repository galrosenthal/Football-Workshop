package Domain.Controllers;

import Domain.Game.Team;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;
import org.junit.Assert;
import org.junit.Test;

public class TeamControllerTest {

    @Test
    public void testAddTeamOwner() throws Exception {
        boolean result = TeamController.addTeamOwner("username", new Team(), new TeamOwner(new SystemUser("username", null, "name")));
        Assert.assertEquals(false, result);

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme