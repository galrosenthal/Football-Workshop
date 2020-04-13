package Domain;

import DB.DBManager;
import DB.Table;
import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private static EntityManager entityManagerInstance = null;

    List<SystemUser> allUsers;

    private EntityManager() {
        allUsers = new ArrayList<>();
    }

    /**
     * Returns an instance of dbManager. part of the Singleton design
     *
     * @return - DBManager - an instance of dbManager
     */
    public static EntityManager getInstance() {
        if (entityManagerInstance == null) {
            entityManagerInstance = new EntityManager();
        }
        return entityManagerInstance;
    }


    //login function moved to Unregistered

   /* public SystemUser login(String usrNm, String pswrd) throws Exception {
        for (SystemUser su : allUsers) {
            if (su.getUsername().equalsIgnoreCase(usrNm)) {
                List<String> userDetails = DBManager.getInstance().getSystemUsers().getRecord(new String[]{"username"}, new String[]{usrNm});
                if (userDetails.get(2).equals(pswrd)) {
                    return su;
                }

            }
        }
        throw new Exception("Username or Password was incorrect!!!!!");

    }*/

    public void initSystem() throws Exception {
        Table systemUsersTable = DBManager.getInstance().getSystemUsers();
        for (int i = 0; i < systemUsersTable.size(); i++) {
            String username = systemUsersTable.getRecordValue(i, "username");
            String name = systemUsersTable.getRecordValue(i, "name");
            String[] roles = systemUsersTable.getRecordValue(i, "role").split(";");

            SystemUser newUser = new SystemUser(username, name);
            for (String role : roles) {
                switch (role) {
                    case "fan":
                       // newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.FAN));
                        new Fan(newUser);
                        break;
                    case "player":
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.PLAYER));
                        break;
                    case "coach":
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.COACH));
                        break;
                    case "team manager":
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.TEAM_MANAGER));
                        break;
                    case "team owner":
                       // newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.TEAM_OWNER));
                        new TeamOwner(newUser);
                        break;
                    case "system admin":
                      //  newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.SYSTEM_ADMIN));
                        new SystemAdmin(newUser);
                        break;
                    case "referee":
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.REFEREE));
                        break;
                    case "association representative":
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.ASSOCIATION_REPRESENTATIVE));
                        break;
                    default:
                        throw new Exception("Error in Role type in the DB");
                }

            }
            allUsers.add(newUser);

        }
    }

    private Role recreateRoleFromDB(String username, RoleTypes roleType) {
        Role newRole;

        try {
            Table roleRecords = DBManager.getInstance().getRelevantRecords(username, roleType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public SystemUser getUser(String username) {
        for (SystemUser su : allUsers) {
            if (su.getUsername().equals(username)) {
                return su;
            }
        }
        return null;
    }

    public boolean addUser(SystemUser systemUser) {
        if (!(this.allUsers.contains(systemUser))) {
            this.allUsers.add(systemUser);
        }
        return true;
    }
}
