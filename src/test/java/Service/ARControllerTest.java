package Service;

import Domain.Users.AssociationRepresentative;
import Domain.Users.AssociationRepresentativeStub;
import Domain.Users.SystemUser;
import Domain.Users.SystemUserStub;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ARControllerTest {
    @BeforeClass
    public static void beforeClass() throws Exception {
        UIController.setIsTest(true);
    }

    @Test
    public void addLeagueUTest() {
        assertFalse(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 4)));
    }

    @Test
    public void addLeague2UTest() {
        AssociationRepresentativeStub.setSelector(0);
        assertFalse(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }
    @Test
    public void addLeague3UTest() {
        AssociationRepresentativeStub.setSelector(1);
        assertTrue(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }

    @Test
    public void addLeagueITest() {
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new AssociationRepresentative(systemUser));
        UIController.setSelector(5);
        //First league creation
        assertTrue(ARController.addLeague(systemUser));
        //Duplicated league creation
        assertFalse(ARController.addLeague(systemUser));
    }
}