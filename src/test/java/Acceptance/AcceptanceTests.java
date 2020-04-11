package Acceptance;

import Domain.Users.SystemUser;
import Service.Controller;
import Service.UIController;
import org.junit.Test;

public class AcceptanceTests {

    @Test
    public void addTeamOwnerATest() {
        UIController.setIsTest(true);
        Controller.addTeamOwner(new SystemUser("rosengal","Gal"));
    }
}
