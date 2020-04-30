package Domain.Users;

import Domain.EntityManager;
import Service.Controller;
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
            Controller.login(unregistered,"usrNmNotExists", "pswrd");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Username or Password was incorrect!",e.getMessage());
        }

        //working good
        EntityManager.getInstance().addUser(systemUser);

        SystemUser result2 = Controller.login(unregistered,"nir", "12aA34567");
        Assert.assertEquals("nir", result2.getUsername());
        Assert.assertEquals("Nir", result2.getName());
        Assert.assertEquals("12aA34567", result2.getPassword());
        Assert.assertTrue(unregistered.getSystemUser()!= null);

        //password incorrect
        try{
            Controller.login(unregistered,"nir", "pswrdNotCorrect");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Username or Password was incorrect!",e.getMessage());
        }
        EntityManager.getInstance().removeUserByReference(systemUser);
    }

    @Test
    public void signUpUTest() throws Exception {
        //success
        SystemUser newUser = Controller.signUp(unregistered,"Avi", "avi", "1234cB57");
        Assert.assertEquals("avi", newUser.getUsername());
        Assert.assertEquals("Avi", newUser.getName());
        Assert.assertEquals("1234cB57", newUser.getPassword());
        Assert.assertTrue(unregistered.getSystemUser()!= null);

        //userName already exists
        EntityManager.getInstance().addUser(systemUser);
        try{
            Controller.signUp(unregistered,"Avi", "nir", "1234cB57");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Username already exists",e.getMessage());
        }

        try {
            //password does not meet security req
            Controller.signUp(unregistered,"Yosi", "yos", "12a34567");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Password does not meet the requirements",e.getMessage());
        }

        try {
            //password does not meet security req
            Controller.signUp(unregistered,"Yossi", "yos1", "55bB");
            Assert.fail();
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Password does not meet the requirements",e.getMessage());
        }

        EntityManager.getInstance().removeUserByReference(systemUser);

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme