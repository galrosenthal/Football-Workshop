package Domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlertTest {

    @Test
    public void sendEmail()
    {
        Alert alert = new Alert();
        assertTrue(alert.sendEmail("shaked94.212@gmail.com" , "notifyfootballworkshop@gmail.com" , "JavaMailTest" , "Sent message successfully....\nHi there,we are just experimenting with JavaMail here"));
    }

}