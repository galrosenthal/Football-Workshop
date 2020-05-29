package Generic;

import DB.DBHandler;
import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.SystemLogger.SystemLoggerManager;
import Service.UIController;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.fail;

public abstract class GenericTestAbstract {

    @BeforeClass
    public static void beforeClass(){
        DBManagerForTest dbMngr = (DBManagerForTest) DBManager.startTest();
        dbMngr.startConnection();
        creatingDB();
        SystemLoggerManager.disableLoggers(); // disable loggers in tests
        UIController.setIsTest(true);
    }

    public static void creatingDB(){
        try{

            DBHandler.getInstance().scriptRunner("src/main/resources/backup-file.sql");
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @AfterClass
    public static void afterClass()
    {

        try {
            DBManager.getInstance().deleteData("fwdb_test");
            DBManager.getInstance().closeConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
}
