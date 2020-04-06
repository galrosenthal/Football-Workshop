package DB;


import java.util.List;

public class DBManager {

    private static DBManager dbManagerInstance = null;

    private final String tablesDetailsPath = "resources/Tables/tablesDetails.csv";

    private Table systemUsers;
    //...

    private DBManager() {
        //read tables details from csv, init
        Table tablesDetails = CSVEditor.readTableFromCSV(tablesDetailsPath);
        //init lists
        for (int i = 0; i < tablesDetails.size(); i++) {
            switch (tablesDetails.getRecordValue(i, "name")) {
                case "SystemUsers":
                    this.initSystemUsers(tablesDetails.getRecordValue(i, "path"));
                    break;
                case "players":
                    this.initPlayers(tablesDetails.getRecordValue(i, "path"));
                    break;
                case "coaches":
                    this.initCoaches(tablesDetails.getRecordValue(i, "path"));
                    break;
            }
        }
    }

    private void initSystemUsers(String path) {
        //init systemUsers
        this.systemUsers = CSVEditor.readTableFromCSV(path);
        /**
         * Usage Example
         */
        for (int i = 0; i < systemUsers.size(); i++) {
            String username = systemUsers.getRecordValue(i,"username");
            String password = systemUsers.getRecordValue(i,"password");
            String name = systemUsers.getRecordValue(i,"name");
            //TODO: Decide if holding a list of objects or just a table.
        }
    }

    private void initCoaches(String path) {
        //initCoaches
    }

    private void initPlayers(String path) {
        //initPlayers
    }

    public static DBManager getInstance() {
        if (dbManagerInstance == null)
            dbManagerInstance = new DBManager();
        return dbManagerInstance;
    }

    public static String close() {
        boolean finished = false;
        //save all the changes to files.
        if (finished) {
            return "Saved successfully";
        }
        return "Save failed";
    }


    public List<String> getUser(String username){
        for (int i = 0; i < this.systemUsers.size(); i++) {
            if((this.systemUsers.getRecordValue(i,"username")).equals(username)){
                return this.systemUsers.getRecord(i);
            }
        }
        return null;
    }
}
