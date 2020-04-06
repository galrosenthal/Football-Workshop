package DB;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest {

    @Test
    void getInstance() {
        Table users = DBManager.getInstance().getSystemUsers();
        assertTrue(users!=null);
        assertTrue(users.getRecord(0).get(0).equals("yiftachs"));
    }

    @Test
    void close() {
        Table users = DBManager.getInstance().getSystemUsers();
        /*List<String> newUser = new ArrayList<>();
        newUser.add("testUser");
        newUser.add("testPass");
        newUser.add("testName");
        users.addRecord(newUser);*/
        assertTrue(DBManager.getInstance().close());
    }

    @Test
    void getUser() {
    }
}