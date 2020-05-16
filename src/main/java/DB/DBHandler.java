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

import static DB.Tables.Tables.TABLETEST;
import static org.jooq.impl.DSL.name;

public class DBHandler implements CRUD {
    String username = "root";
    String password = "foot123Ball!";
    String myDriver = "org.mariadb.jdbc.Driver";
    String myUrl = "jdbc:mysql://132.72.65.105:3306/TestDB";
    Connection connection = null;


    public DBHandler() {
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


    public boolean dslCheck() {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(DSL.table(name("tabletest")))
                .where(DSL.field(name("A")).eq(1)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean dslCheckFalse() {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(TABLETEST)
                .where(TABLETEST.A.eq(6)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
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
