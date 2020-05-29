package Domain;

import Domain.SystemLogger.SystemLoggerManager;
import Generic.GenericTestAbstract;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertTest extends GenericTestAbstract {



    @Test
    public void sendEmail()
    {
            assertTrue(Alert.getInstance().sendEmail("shaked9121@gmail.com" , "notifyfootballworkshop@gmail.com" , "JavaMailTest" , "Sent message successfully....\nHi there,we are just experimenting with JavaMail here"));
    }

}