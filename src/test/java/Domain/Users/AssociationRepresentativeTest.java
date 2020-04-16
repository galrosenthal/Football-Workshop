package Domain.Users;

import Domain.EntityManager;
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
}

