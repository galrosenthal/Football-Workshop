package Domain;

import Domain.SystemLogger.SystemLoggerManager;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertTest {

    @BeforeClass
    public static void setUpBeforeAll() { //Will be called only once
        SystemLoggerManager.disableLoggers(); // disable loggers in tests
    }

    @Test
    public void sendEmail()
    {
            assertTrue(Alert.getInstance().sendEmail("shaked9121@gmail.com" , "notifyfootballworkshop@gmail.com" , "JavaMailTest" , "Sent message successfully....\nHi there,we are just experimenting with JavaMail here"));
    }

}