package DB;


import java.util.List;

public class DBManager {

    private static DBManager dbManagerInstance = null;

    private final String tablesDetailsPath = "resources/Tables/tablesDetails.csv";
    private Table tablesDetails;

    private Table systemUsers;
    //TODO: add fields for different tables

    /**
     * Constructor
     */
    private DBManager() {
        //read tables details from csv, init
        tablesDetails = CSVEditor.readTableFromCSV(tablesDetailsPath);
        //init lists
        for (int i = 0; i < tablesDetails.size(); i++) {
            switch (tablesDetails.getRecordValue(i, "name")) {
                case "SystemUsers":
                    String path = tablesDetails.getRecordValue(i, "path");
                    this.systemUsers = CSVEditor.readTableFromCSV(path);
                    //this.initSystemUsers(path);
                    break;
                case "players":

                    break;
                case "coaches":

                    break;
                //TODO: add cases for different tables
            }
        }
    }

   /*
    private void initSystemUsers(String path) {
        for (int i = 0; i < systemUsers.size(); i++) {
            String username = systemUsers.getRecordValue(i,"username");
            String password = systemUsers.getRecordValue(i,"password");
            String name = systemUsers.getRecordValue(i,"name");
            //TODO: Decide if holding a list of objects or just a table.
        }
    }
*/


    /**
     * Returns an instance of dbManager. part of the Singleton design
     * @return - DBManager - an instance of dbManager
     */
    public static DBManager getInstance() {
        if (dbManagerInstance == null)
            dbManagerInstance = new DBManager();
        return dbManagerInstance;
    }

    /**
     * Saves all the tables to their original files
     * @return - boolean - true if all the tables have been saved successfully, else false
     */
    public boolean close() {
        boolean finished = true;
        //save all the changes to files.
        finished = CSVEditor.writeTableToCSV(systemUsers,tablesDetails.getRecord(new String[]{"name"},new String[]{"SystemUsers"}).get(1));


        if (finished) {
            return true;
        }
        return false;
    }

    /**
     * Returns the system users table
     * @return - Table - The system users table
     */
    public Table getSystemUsers() {
        return systemUsers;
    }

    //Might be redundant. Use Table.getRecord() for the same purpose.
    public List<String> getUser(String username){
        for (int i = 0; i < this.systemUsers.size(); i++) {
            if((this.systemUsers.getRecordValue(i,"username")).equals(username)){
                return this.systemUsers.getRecord(i);
            }
        }
        return null;
    }
}
