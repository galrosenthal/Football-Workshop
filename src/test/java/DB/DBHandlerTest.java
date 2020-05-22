package DB;

import org.junit.Test;

public class DBHandlerTest {

    DBHandler db;

    @Test
    public void DBHandler()
    {
         DBHandler.startConnection("jdbc:mysql://132.72.65.105:3306/fwdb_test");
    }

}