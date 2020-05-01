package Domain.Users;

import Domain.EntityManager;
import Domain.Exceptions.RoleExistsAlreadyException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sun.net.www.ParseUtil;

import static org.junit.Assert.*;

public class AssociationRepresentativeTest {

    private AssociationRepresentative aR;

    @Test
    public void testAddLeagueITest() throws Exception {
        EntityManager.getInstance().removeLeagueByName("leagueName");
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        assertTrue(aR.addLeague("leagueName"));
    }


    @Test(expected = Exception.class)
    public void testAddLeague2ITest() throws Exception {
        EntityManager.getInstance().removeLeagueByName("leagueName");
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 0));
        aR.addLeague("leagueName");
        aR.addLeague("leagueName");
    }

    @Test(expected = RoleExistsAlreadyException.class)
    public void addRefereeUTest() throws RoleExistsAlreadyException {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        aR.addReferee(new SystemUserStub("stubUsername", "stub", 9311), "VAR");
    }

    @Test
    public void addReferee2UTest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 9312);
        try {
            aR.addReferee(newRefereeUser, "VAR");
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }

    @Test
    public void addRefereeITest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 93131);
        try {
            assertTrue(aR.addReferee(newRefereeUser, "VAR"));
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }


    @Test
    public void addReferee2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        try {
            assertTrue(aR.addReferee(newRefereeUser, "VAR"));
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }

    @Test(expected = RoleExistsAlreadyException.class)
    public void addReferee3ITest() throws RoleExistsAlreadyException {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        new Referee(newRefereeUser, "refTraining");

        aR.addReferee(newRefereeUser, "VAR");
    }

    @Test
    public void removeRefereeUTest(){
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 9312);

        assertFalse(aR.removeReferee(newRefereeUser));

        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE) != null);
    }
    @Test
    public void removeReferee2UTest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 9311);

        assertTrue(aR.removeReferee(newRefereeUser));
    }

    @Test
    public void removeRefereeITest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 93132);

        assertTrue(aR.removeReferee(newRefereeUser));
    }

    @Test
    public void removeReferee2ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");

        assertFalse(aR.removeReferee(newRefereeUser));
    }

    @Test
    public void removeReferee3ITest() {
        SystemUser aRUser = new SystemUser("arUsername", "arName");
        aR = new AssociationRepresentative(aRUser);
        SystemUser newRefereeUser = new SystemUser("refUsername", "refName");
        new Referee(newRefereeUser, "refTraining");
        assertTrue(aR.removeReferee(newRefereeUser));
    }


    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
    }
}



