package Domain.Users;

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
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 0));
        assertTrue(aR.addLeague("leagueName"));
    }

    @Test(expected = Exception.class)
    public void testAddLeague2ITest() throws Exception {
        aR = new AssociationRepresentative(new SystemUserStub("stubUsername", "stub", 0));
        aR.addLeague("leagueName");
        aR.addLeague("leagueName");
    }
}

