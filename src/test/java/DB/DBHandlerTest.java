package DB;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBHandlerTest {

    DBHandler db;

    @Before
    @Test
    public void DBHandler()
    {
         db = new DBHandler();
         DBHandler.startConnection();

    }

}