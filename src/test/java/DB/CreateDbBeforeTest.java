package DB;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateDbBeforeTest {

    DBHandler db;

    @Test
    public void DBHandlerConnectionTesting()
    {
         DBHandler.startConnection("jdbc:mysql://localhost:3306/fwdb_test");
//         DBHandler.getInstance().create();
    }


}