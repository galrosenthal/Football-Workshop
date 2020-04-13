package Acceptance;

import Domain.EntityManager;
import Domain.Users.SystemAdmin;
import Domain.Users.SystemUser;
import Service.Controller;
import Service.UIController;
import org.junit.Test;

import static org.junit.Assert.*;

public class AcceptanceTests {

    @Test
    public void systemBootATest(){
        initEntities();
        UIController.setIsTest(true);
        UIController.setSelector(3);
        assertTrue(Controller.systemBoot());
    }

    private void initEntities() {
        SystemUser adminUser = new SystemUser("admin","12345678","administrator");
        adminUser.addNewRole(new SystemAdmin(adminUser));
        EntityManager.getInstance().addUser(adminUser);
    }

    @Test
    public void addTeamOwnerATest() {
        UIController.setIsTest(true);
        Controller.addTeamOwner(new SystemUser("rosengal","Gal"));
    }
}
