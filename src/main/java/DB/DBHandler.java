package DB;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.jooq.impl.DSL.name;

public class DBHandler implements CRUD {
    private static String username = "root";
    private static String password = "foot123Ball!";
    private static String myDriver = "org.mariadb.jdbc.Driver";
    private static String myUrl = "jdbc:mysql://132.72.65.105:3306/fwdb_test";
    private static Connection connection = null;

    private static DBHandler DBHandlerInstance = null;

    /**
     * Constructor
     */
    private DBHandler() {
    }

    /**
     * Returns an instance of DBHandler. part of the Singleton design
     * @return - DBHandler - an instance of DBHandler
     */
    public static DBHandler getInstance() {
        if (DBHandlerInstance == null)
            DBHandlerInstance = new DBHandler();
        return DBHandlerInstance;
    }

    /**
     * start connection to DB
     * @param url - for DB connection
     */
    public static void startConnection(String url) {
        //connect to DB and save to field in class.
        try {
            Class.forName(myDriver);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Successful connection to server db ");
        } catch (SQLException e) {
            System.out.println("error connecting to server. connection is now null");
        } catch (ClassNotFoundException e) {
            System.out.println("error connecting to driver");
        }
    }

    /**
     * close connection
     * @return - true if closing connection successfully
     *         - false otherwise
     */
    public static boolean closeConnection()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }

    /**
     * create context to JOOQ
     * @return DSLContext - create
     */
    public static DSLContext getContext() {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        return create;
    }

    /**
     * delete all records from DB
     * @throws Exception
     */
    public void deleteData() throws Exception {
        File url  = new File(
                getClass().getClassLoader().getResource("backup-file.sql").getFile()
        );
        this.scriptRunner(url);

    }

    /**
     * run sql script
     * @param url- to script file
     * @throws Exception - wrong input url FILE
     */
    private void scriptRunner(File url) throws Exception {
      //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(connection);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader(url));
        //Running the script
        sr.runScript(reader);
    }



    @Override
    public boolean create() {
        return false;
    }

    @Override
    public boolean read() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
