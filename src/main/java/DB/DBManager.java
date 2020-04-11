package DB;


import Domain.Users.RoleTypes;

import java.util.List;

public class DBManager {

    private static DBManager dbManagerInstance = null;

    private final String tablesDetailsPath = "resources/Tables/tablesDetails.csv";
    private Table tablesDetails;

    private Table systemUsers;
    private Table team;
    private Table teamManager;
    private Table teamOwner;
    private Table fan;
    private Table player;
    private Table coach;
    private Table associationRepresentative;
    private Table referee;
    private Table systemAdmin;
    //TODO: add fields for different tables

    /**
     * Constructor
     */
    private DBManager() {
        //read tables details from csv, init
        //TODO: Maybe we should change this, and implemet Strategey Design Pattern here?
        
        tablesDetails = CSVEditor.readTableFromCSV(tablesDetailsPath);
        //init lists
        String path = "";
        for (int i = 0; i < tablesDetails.size(); i++) {
            switch (tablesDetails.getRecordValue(i, "name")) {
                case "SystemUsers":
                     path = tablesDetails.getRecordValue(i, "path");
                    this.systemUsers = CSVEditor.readTableFromCSV(path);
                    //this.initSystemUsers(path);
                    break;
                case "Team":
                    path = tablesDetails.getRecordValue(i, "path");
                    this.team = CSVEditor.readTableFromCSV(path);
                    break;
                case "coaches":

                    break;
                //TODO: add cases for different tables
            }
        }
    }




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

    public Table getRelevantRecords(String username, RoleTypes roleType) throws Exception {
        switch (roleType) {
            case FAN:
                return fan.getRecords(new String[]{"username"}, new String[]{username});
            case PLAYER:
                return player.getRecords(new String[]{"username"}, new String[]{username});
            case COACH:
                return coach.getRecords(new String[]{"username"}, new String[]{username});
            case TEAM_MANAGER:
                return teamManager.getRecords(new String[]{"username"}, new String[]{username});
            case TEAM_OWNER:
                return teamOwner.getRecords(new String[]{"username"}, new String[]{username});
            case SYSTEM_ADMIN:
                return systemAdmin.getRecords(new String[]{"username"}, new String[]{username});
            case REFEREE:
                return referee.getRecords(new String[]{"username"}, new String[]{username});
            case ASSOCIATION_REPRESENTATIVE:
                return associationRepresentative.getRecords(new String[]{"username"}, new String[]{username});
            default:
                throw new Exception("Error in Role type in the DB");
        }

    }
}
