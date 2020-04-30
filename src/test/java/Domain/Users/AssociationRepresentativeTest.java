package Domain.Users;

import Domain.EntityManager;
import Domain.Exceptions.RoleExistsAlreadyException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        aR.addReferee(new SystemUserStub("stubUsername", "stub", 9311),"VAR");
    }
    @Test
    public void addReferee2UTest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 9312);
        try {
            aR.addReferee(newRefereeUser,"VAR");
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE)!=null);
    }

    @Test
    public void addRefereeITest() {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 5));
        SystemUser newRefereeUser = new SystemUserStub("stubUsername", "stub", 93131);
        try {
            aR.addReferee(newRefereeUser,"VAR");
        } catch (RoleExistsAlreadyException e) {
            e.printStackTrace();
        }
        assertTrue(newRefereeUser.getRole(RoleTypes.REFEREE)!=null);
    }

}

