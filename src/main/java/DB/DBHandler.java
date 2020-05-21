package DB;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.jooq.impl.DSL.name;

public class DBHandler implements CRUD {
    private static String username = "root";
    private static String password = "foot123Ball!";
    private static String myDriver = "org.mariadb.jdbc.Driver";
    private static String myUrl = "jdbc:mysql://132.72.65.105:3306/fwdb";
    private static Connection connection = null;


    public DBHandler() {
    }

    public static void startConnection() {
        //connect to DB and save to field in class.
        try {
            Class.forName(myDriver);
            connection = DriverManager.getConnection(myUrl, username, password);
            System.out.println("Successful connection to server db ");
        } catch (SQLException e) {
            System.out.println("error connecting to server. connection is now null");
        } catch (ClassNotFoundException e) {
            System.out.println("error connecting to driver");
        }
    }

    public static DSLContext getContext(){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        return create;
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
