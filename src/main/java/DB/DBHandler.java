package DB;

import DB.Tables.Fwdb;
import Domain.SystemLogger.SystemLoggerManager;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.jooq.*;
import org.jooq.Table;
import org.jooq.impl.*;

import java.io.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
            System.out.println("url: "+url+" username "+username+" password: "+password);
            Class.forName(myDriver);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("URL: " + url);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Successful connection to server db ");
        } catch (SQLException e) {
            String msg = "error connecting to server. connection is now null";
            System.out.println(msg);
            SystemLoggerManager.logFatal(DBHandler.class , msg);
        } catch (ClassNotFoundException e) {
            String msg = "error connecting to driver";
            System.out.println(msg);
            SystemLoggerManager.logFatal(DBHandler.class , msg);
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
            String msg = "error closing connection of DB";
            System.out.println(msg);
            SystemLoggerManager.logFatal(DBHandler.class , msg);
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
    public void deleteData(String dbName) {
        List<Table<?>> fwdbTables = Fwdb.FWDB.getTables();
        DSLContext create = DBHandler.getContext();
        create.fetch("SET FOREIGN_KEY_CHECKS = 0;");
        for (int i = 0; i < fwdbTables.size(); i++) {
            String tableName = "`"+dbName+"`.`"+fwdbTables.get(i).getName()+"`";
            List<? extends ForeignKey<?, ?>> indexes = fwdbTables.get(i).getReferences();
            create.truncate(DSL.table(tableName)).execute();
        }
        create.fetch("SET FOREIGN_KEY_CHECKS = 1;");


        /*
        File url  = new File(
                getClass().getClassLoader().getResource("backup-file.sql").getFile()
        );
        this.scriptRunner(url);

         */


    }

    /**
     * run sql script
     * @param urlPath- to script file
     * @throws Exception - wrong input url FILE
     */
    public void scriptRunner(String urlPath) throws Exception {
      //Initialize the script runner
        File urlFile  = new File(urlPath);
        if(!urlFile.exists())
            return;
        ScriptRunner sr = new ScriptRunner(connection);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader(urlFile));
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
