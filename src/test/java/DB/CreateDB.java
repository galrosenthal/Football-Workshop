package DB;
import Generic.GenericTestAbstract;
import org.junit.Test;

public class CreateDB extends GenericTestAbstract {

    DBHandler db;

    @Test
    public void DBHandlerConnectionTesting()
    {
         DBHandler.startConnection("jdbc:mysql://localhost:3306/fwdb_test");
//         DBHandler.getInstance().create();
    }


}