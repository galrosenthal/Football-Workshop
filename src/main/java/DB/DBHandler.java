package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
