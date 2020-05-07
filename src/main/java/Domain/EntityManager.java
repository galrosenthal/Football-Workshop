package Domain;

import DB.DBManager;
import DB.Table;
import Domain.Game.League;
import Domain.Game.Stadium;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Game.Stadium;
import Domain.Game.Team;
import Domain.Users.*;
import Domain.Game.Stadium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EntityManager {
    private static EntityManager entityManagerInstance = null;

    private List<SystemUser> allUsers;
    private List<Team> allTeams;
    private List<Stadium> allStadiums;
    private HashSet<League> allLeagues;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    private boolean loggedIn = false;

    private EntityManager() {
        allUsers = new ArrayList<>();
        allLeagues = new HashSet<>();
        allTeams = new ArrayList<>();
        allStadiums = new ArrayList<>();
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
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.FAN));
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
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.TEAM_OWNER));
                        break;
                    case "system admin":
                        newUser.addNewRole(recreateRoleFromDB(username, RoleTypes.SYSTEM_ADMIN));
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

    public List<League> getLeagues() {
        return new ArrayList<League>(allLeagues);
    }

    public SystemUser getUser(String username) {
        for (SystemUser su : allUsers) {
            if (su.getUsername().equals(username)) {
                return su;
            }
        }
        return null;
    }

    public Stadium getStadium(String stadiumName) {
        for (Stadium std: allStadiums) {
            if (std.getName().equals(stadiumName)) {
                return std;
            }
        }
        return null;
    }

    /**
     * Checks if a league with a name that matches the given name already exists.
     * @param name - String - name
     * @return - boolean - True if a league with a name that matches the given name already exists, else false
     */
    public boolean doesLeagueExists(String name){
        for (League league : allLeagues) {
            if (league.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean addUser(SystemUser systemUser) {
        if (!(this.allUsers.contains(systemUser))) {
            this.allUsers.add(systemUser);
            return true;
        }
        return false;
    }

    public boolean removeUserByReference(SystemUser systemUser) {
        return this.allUsers.remove(systemUser);
    }

    public boolean removeUserByName(String username) {
        for (SystemUser su : allUsers) {
            if (su.getUsername().equals(username)) {
                this.allUsers.remove(su);
                return true;
            }
        }
        return false;
    }

    public boolean addTeam(Team team) {
        if (!(this.allTeams.contains(team))) {
            this.allTeams.add(team);
            return true;
        }
        return false;
    }

    public boolean addStadium(Stadium stadium) {
        if (!(this.allStadiums.contains(stadium))) {
            this.allStadiums.add(stadium);
            return true;
        }
        return false;
    }

    public boolean isStadiumExists(Stadium stadium){
        return allStadiums.contains(stadium);
    }

    public boolean removeTeamByReference(Team team) {
        return this.allTeams.remove(team);
    }

    /**
     * Removes a league by a given name
     * @param leagueName - String - a name of the league to be removed
     * @return - boolean - true if the league removed successfully, else false
     */
    public boolean removeLeagueByName(String leagueName) {
        League leagueToRemove = null;
        for (League league : allLeagues) {
            if (league.getName().equals(leagueName)) {
                leagueToRemove = league;
            }
        }
        if(leagueToRemove==null){
            return false;
        }
        return this.allLeagues.remove(leagueToRemove);
    }

    /**
     * Adds a new league. Responsible only for creating and adding a new league, doesn't do any farther checks.
     * @param leagueName - String - A unique leagueName
     */
    public void addLeague(String leagueName) {
        League league = new League(leagueName);
        allLeagues.add(league);
    }

    public void clearAll() {
        allStadiums = new ArrayList<>();
        allLeagues = new HashSet<>();
        allUsers = new ArrayList<>();
        allTeams = new ArrayList<>();
    }

    public boolean removeStadiumByReference(Stadium st) {
        return allStadiums.remove(st);
    }
}
