package Domain.Users;

import Domain.EntityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UnregisteredTest {
    @Mock
    SystemUser systemUser;
    @InjectMocks
    private Unregistered unregistered;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(systemUser.getPassword()).thenReturn("12aA34567");
        when(systemUser.getName()).thenReturn("Nir");
        when(systemUser.getUsername()).thenReturn("nir");
    }

    @Test
    public void loginUTest() throws Exception {
        //userName not exists
        try{
            unregistered.login("usrNmNotExists", "pswrd");
            Assert.fail();
        }
        catch(Exception e) {

        }

        //working good
        EntityManager.getInstance().addUser(systemUser);

        SystemUser result2 = unregistered.login("nir", "12aA34567");
        Assert.assertEquals("nir", result2.getUsername());
        Assert.assertEquals("Nir", result2.getName());
        Assert.assertEquals("12aA34567", result2.getPassword());
        Assert.assertTrue(unregistered.getSystemUser()!= null);

        //password incorrect
        try{
            unregistered.login("nir", "pswrdNotCorrect");
            Assert.fail();
        }
        catch(Exception e) {
        }
        EntityManager.getInstance().removeUserByReference(systemUser);
    }

    @Test
    public void signUpUTest() throws Exception {
       // SystemUser result = unregistered.signUp("name", "usrNm", "pswrd");
       // Assert.assertEquals(new SystemUser("username", null, "name"), result);

        //working good
        SystemUser newUser = unregistered.signUp("Avi", "avi", "1234cB57");
        Assert.assertEquals("avi", newUser.getUsername());
        Assert.assertEquals("Avi", newUser.getName());
        Assert.assertEquals("1234cB57", newUser.getPassword());
        Assert.assertTrue(unregistered.getSystemUser()!= null);

        //userName already exists
        EntityManager.getInstance().addUser(systemUser);
        try{
            unregistered.signUp("Avi", "nir", "1234cB57");
            Assert.fail();

        }
        catch(Exception e) {

        }

        try {
            //password does not meet security req
            unregistered.signUp("Yosi", "yos", "12a34567");
            Assert.fail();
        }
        catch(Exception e) {

        }

        try {
            //password does not meet security req
            unregistered.signUp("Yossi", "yos1", "55bB");
            Assert.fail();
        }
        catch(Exception e) {
        }

        EntityManager.getInstance().removeUserByReference(systemUser);

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme