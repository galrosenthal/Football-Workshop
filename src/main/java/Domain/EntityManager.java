package Domain;

import DB.DBManager;
import DB.Table;
import Domain.Exceptions.UsernameAlreadyExistsException;
import Domain.Exceptions.UsernameOrPasswordIncorrectException;
import Domain.Exceptions.WeakPasswordException;
import Domain.Game.*;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Game.Stadium;
import Domain.Users.*;
import Domain.Game.Stadium;
import Service.AllSubscribers;
import Service.Observer;
import Service.UIController;

import java.util.*;

public class EntityManager{
    private static EntityManager entityManagerInstance = null;

    private List<SystemUser> allUsers;
    private List<Team> allTeams;
    private List<Stadium> allStadiums;
    private HashSet<League> allLeagues;
    private List<SystemAdmin> systemAdmins;

    private List<PointsPolicy> pointsPolicies;
    private List<SchedulingPolicy> schedulingPolicies;

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
        systemAdmins = new ArrayList<>();
        pointsPolicies = new ArrayList<>();
        schedulingPolicies = new ArrayList<>();
    }

    /**
     * Returns an instance of dbManager. part of the Singleton design
     *
     * @return - DBManager - an instance of dbManager
     */
    public static EntityManager getInstance() {
        if (entityManagerInstance == null) {
            entityManagerInstance = new EntityManager();
            SystemUser a = new SystemUser("Administrator","Aa123456","admin");
            a.addNewRole(new SystemAdmin(a));
            a.addNewRole(new AssociationRepresentative(a));
        }

        return entityManagerInstance;
    }

/*
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

 */
/*
    private Role recreateRoleFromDB(String username, RoleTypes roleType) {
        Role newRole;

        try {
            Table roleRecords = DBManager.getInstance().getRelevantRecords(username, roleType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

 */

    public List<League> getLeagues() {
        //fixme to list<String>
        List<String> allLeaguesList = DBManager.getInstance().getLeagues();
        List<League> leagues =  new ArrayList<>();
        for (int i = 0; i < allLeaguesList.size(); i++) {
            //fixme
            leagues.add(new League((String) allLeaguesList.get(i)));
        }
        return new ArrayList<League>(allLeagues);
    }
    public List<Team> getTeams() {
        return new ArrayList<Team>(allTeams);
    }

    /**
     * Returns a SystemUser by his username
     * @param username
     * @return The SystemUser with the username, if exists in the system.
     */
    //todo: check is username in allUsers list,
    // if yes return SystemUser,
    // otherwise ask dbManager.getUserDetails(String username) and receive all Details to create user system
    // then ask dbManager.getUserRoles(String username) - ask dbManager for each role Details.
    public SystemUser getUser(String username) {

        for (SystemUser su : allUsers) {
            if (su.getUsername().equals(username)) {
                return su;
            }
        }


        return null;
    }

    /**
     * Returns a Team by its team name
     * @param teamName
     * @return The team with the given team name, if exists in the system.
     */
    public Team getTeam(String teamName) {
        for (Team team : allTeams) {
            if (team.getTeamName().toLowerCase().equals(teamName.toLowerCase())) {
                return team;
            }
        }
        return null;
    }

    /**
     * Returns a Stadium by its stadium name
     * @param stadiumName
     * @return The Stadium with the given stadium name, if exists in the system.
     */
    public Stadium getStadium(String stadiumName) {
        for (Stadium std: allStadiums) {
            if (std.getName().equals(stadiumName)) {
                return std;
            }
        }
        return null;
    }


    /**
     * Returns all system admins
     * @return List<SystemAdmin> SystemAdmin
     */
    public List<SystemAdmin> getSystemAdmins() {
        List<SystemAdmin> sysAdmins = new ArrayList<>();
        for (SystemUser user :
                allUsers) {
            Role userAdmin = user.getRole(RoleTypes.SYSTEM_ADMIN);
            if (userAdmin != null && userAdmin.getType() == RoleTypes.SYSTEM_ADMIN )
            {
                sysAdmins.add((SystemAdmin) userAdmin);
            }
        }
        return sysAdmins;
    }


    /**
     * Checks if a team with a name that matches the given name already exists.
     * @param name - String - name
     * @return - boolean - True if a team with a name that matches the given name already exists, else false
     */
    public boolean doesTeamExists(String name){
        for (Team team : allTeams) {
            if (team.getTeamName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
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
        return DBManager.getInstance().doesLeagueExists(name);

       // return false;
    }

    /**
     * Adds a given SystemUser to the user's list of the system.
     * @param systemUser User to add
     * @return true if successfully added the SystemUser to the system.
     */
    public boolean addUser(SystemUser systemUser) {
        if (!(this.allUsers.contains(systemUser))) {
            this.allUsers.add(systemUser);
            return true;
        }
        return false;
    }

    /**
     * Removes a SystemUser by a given reference to the SystemUser to remove.
     * @param systemUser - SystemUser - the SystemUser to remove.
     * @return - boolean - true if the SystemUser removed successfully, else false
     */
    public boolean removeUserByReference(SystemUser systemUser) {
        return this.allUsers.remove(systemUser);
    }

    /**
     * Removes a SystemUser by a given username
     * @param username - String - a name of the user to be removed
     * @return - boolean - true if the SystemUser removed successfully, else false
     */
    public boolean removeUserByName(String username) {
        for (SystemUser su : allUsers) {
            if (su.getUsername().equals(username)) {
                this.allUsers.remove(su);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a given Team to the team's list of the system.
     * @param team Team to add
     * @return true if successfully added the Team to the system.
     */
    public boolean addTeam(Team team) {
        if (!(this.allTeams.contains(team))) {
            this.allTeams.add(team);
            return true;
        }
        return false;
    }

    /**
     * Adds a given League to the league's list of the system.
     * @param league league to add
     * @return true if successfully added the League to the system.
     */
    public boolean addLeague(League league) {
        if (!(this.allLeagues.contains(league))) {
            this.allLeagues.add(league);
            return true;
        }
        return DBManager.getInstance().addLeagueRecord(league.getName());
        //return false;
    }


    /**
     * Adds a given Stadium to the stadium's list of the system.
     * @param stadium Stadium to add
     * @return true if successfully added the Stadium to the system.
     */
    public boolean addStadium(Stadium stadium) {
        if (!(this.allStadiums.contains(stadium))) {
            this.allStadiums.add(stadium);
            return true;
        }
        return false;
    }

    /**
     * Checks if a given Stadium exists in the system.
     * @param stadium
     * @return true if the given Stadium exists in the system.
     */
    public boolean isStadiumExists(Stadium stadium){
        return allStadiums.contains(stadium);
    }

    /**
     * Removes a Team by a given reference to the Team to remove.
     * @param team - Team - the Team to remove.
     * @return - boolean - true if the Team removed successfully, else false
     */
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
        if (leagueToRemove == null) {
            return false;
        }
        return this.allLeagues.remove(leagueToRemove);
    }

    public void clearAll() {
        clearAllUsers();
        allStadiums = new ArrayList<>();
        allLeagues = new HashSet<>();
        allUsers = new ArrayList<>();
        allTeams = new ArrayList<>();
        pointsPolicies = new ArrayList<>();
        schedulingPolicies = new ArrayList<>();
    }

    private void clearAllUsers() {
        for (SystemUser user :
                allUsers) {
            user = null;
        }
    }

    /**
     * Removes a Stadium by a given reference to the Stadium to remove.
     * @param st - Stadium - the Stadium to remove.
     * @return - boolean - true if the Stadium removed successfully, else false
     */
    public boolean removeStadiumByReference(Stadium st) {
        return allStadiums.remove(st);
    }

    /**
     * Returns a list of all the system users that are referees.
     * @return - List<SystemUser> - A list of all the system users that are referees
     */
    public List<SystemUser> getReferees() {
        List<SystemUser> referees = new ArrayList<>();
        for (SystemUser user : this.allUsers) {
            if (user.isType(RoleTypes.REFEREE)) {
                referees.add(user);
            }
        }
        return referees;
    }

    /**
     * Receives user name and password from the unregistered user who wants to log in to the system,
     * performs validation and returns the relevant user.
     * @param usrNm User name
     * @param pswrd Password
     * @return The user in the system with those credentials.
     * @throws UsernameOrPasswordIncorrectException If user name or password are incorrect.
     */
    public SystemUser login(String usrNm, String pswrd) throws UsernameOrPasswordIncorrectException {
        SystemUser userWithUsrNm = getUser(usrNm);
        if(userWithUsrNm == null) //User name does not exists.
            throw new UsernameOrPasswordIncorrectException("Username or Password was incorrect!");

        //User name exists, checking password.
        if(authenticate(userWithUsrNm, pswrd)){
            return userWithUsrNm;
        }

        throw new UsernameOrPasswordIncorrectException("Username or Password was incorrect!");
    }

    /**
     * This Function is used to authenticate the username with its password
     * @param userWithUsrNm the SystemUser of the username from the entity manager
     * @param pswrd the password recieved from the UI
     * @return true if the password is correct and the user is able to login
     */
    private boolean authenticate(SystemUser userWithUsrNm, String pswrd) {
        //hash the given password
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pswrd);
        if (userWithUsrNm.getPassword().equals(hashedPassword)) {
            return true;
        }
        return false;
    }

    /**
     * Receives name, user name and password from the unregistered user who wants to sign up to the system,
     * performs validation - checks whether the user name is not already belongs to a user in the system,
     * and whether the given password meets the following security requirements:
     * At least 8 characters.
     * At least 1 number.
     * At least 1 upper case letter.
     * At least 1 lower case letter.
     * Must not contain any spaces.
     * Adds new user with the role fan to the system, and returns the relevant user.
     * @param name Name.
     * @param usrNm User name.
     * @param pswrd Password.
     * @return New user with those credentials.
     * @throws Exception If user name is already belongs to a user in the system, or
     * the password does not meet the security requirements.
     */
    public SystemUser signUp(String name, String usrNm, String pswrd) throws UsernameAlreadyExistsException, WeakPasswordException {
        //Checking if user name is already exists
        if(getUser(usrNm) != null){
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        //Checking if the password meets the security requirements
        // at least 8 characters
        // at least 1 number
        // at least 1 upper case letter
        // at least 1 lower case letter
        // must not contain any spaces
        String pswrdRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        if(!pswrd.matches(pswrdRegEx)){
            throw new WeakPasswordException("Password does not meet the requirements");
        }

        //hash the password
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pswrd);
        SystemUser newUser = new SystemUser(usrNm, hashedPassword, name);
        addUser(newUser);


        return newUser;

    }


    public List<SystemUser> getAllUsers() {
        return allUsers;
    }

    /**
     * Get a list of all Teams by thier name
     * @return List<String of all the teams names
     */
    public List<Team> getAllTeams() {
        return allTeams;
    }

    public League getLeagueByName(String leagueName) {
        for (League lg :
                allLeagues) {
            if(lg.getName().equals(leagueName)){
                return lg;
            }
        }

        return null;
    }

/*
    public List<Referee> getAllRefereesPerGame(Game game) {
        //need to import referees table
        List<Referee> referees = new ArrayList<>();

        for (int i = 0; i < this.allUsers.size(); i++) {
            Role role= allUsers.get(i).getRole(RoleTypes.REFEREE);
            if(role != null)
            {
                Referee referee = (Referee) role;
                if(referee.get)
            }
        }
        return referees;
    }

 */

    /**
     * Checks if a points policy already exists with the same values
     *
     * @param victoryPoints - int
     * @param lossPoints    - int
     * @param tiePoints     - int
     * @return - boolean - true if a policy with the same given values already exists, else false
     */
    public boolean doesPointsPolicyExists(int victoryPoints, int lossPoints, int tiePoints) {
        for (PointsPolicy pointsPolicy : this.pointsPolicies) {
            if (pointsPolicy.equals(victoryPoints, lossPoints, tiePoints)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a points policy matching the given parameters
     *
     * @param victoryPoints - int
     * @param lossPoints    - int
     * @param tiePoints     - int
     * @return - PointsPolicy - A points policy matching the given parameters
     */
    public PointsPolicy getPointsPolicy(int victoryPoints, int lossPoints, int tiePoints) {
        for (PointsPolicy pointsPolicy : this.pointsPolicies) {
            if (pointsPolicy.equals(victoryPoints, lossPoints, tiePoints)) {
                return pointsPolicy;
            }
        }
        return null;
    }

    /**
     * Receives a new points policy and adds it
     *
     * @param newPointsPolicy - PointsPolicy - a new policy
     */
    public void addPointsPolicy(PointsPolicy newPointsPolicy) {
        if (newPointsPolicy != null) {
            this.pointsPolicies.add(newPointsPolicy);
            //TODO: Update DB?
        }
    }

    public List<PointsPolicy> getPointsPolicies() {
        return this.pointsPolicies;
    }

    /**
     * Checks if a scheduling policy already exists with the same values
     *
     * @param gamesPerSeason - int
     * @param gamesPerDay    - int
     * @param minRest        - int
     * @return - boolean - true if a policy with the same given values already exists, else false
     */
    public boolean doesSchedulingPolicyExists(int gamesPerSeason, int gamesPerDay, int minRest) {
        for (SchedulingPolicy schedulingPolicy : this.schedulingPolicies) {
            if (schedulingPolicy.equals(gamesPerSeason, gamesPerDay, minRest)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a scheduling policy matching the given parameters
     *
     * @param gamesPerSeason - int
     * @param gamesPerDay    - int
     * @param minRest        - int
     * @return - SchedulingPolicy - A scheduling policy matching the given parameters
     */
    public SchedulingPolicy getSchedulingPolicy(int gamesPerSeason, int gamesPerDay, int minRest) {
        for (SchedulingPolicy schedulingPolicy : this.schedulingPolicies) {
            if (schedulingPolicy.equals(gamesPerSeason, gamesPerDay, minRest)) {
                return schedulingPolicy;
            }
        }
        return null;
    }

    /**
     * Receives a new scheduling policy and adds it
     *
     * @param newSchedulingPolicy - SchedulingPolicy - a scheduling policy to be added
     */
    public void addSchedulingPolicy(SchedulingPolicy newSchedulingPolicy) {
        if (newSchedulingPolicy != null) {
            this.schedulingPolicies.add(newSchedulingPolicy);
            //TODO: Update DB?
        }
    }

    public List<SchedulingPolicy> getSchedulingPolicies() {
        return schedulingPolicies;
    }

    public boolean doesSeasonExist(String leagueName, String seasonYears) {
       return  DBManager.getInstance().doesSeasonExists(leagueName , seasonYears);
    }

    public boolean addSeason(String leagueName, Season season) {
        PointsPolicy pointsPolicy = season.getPointsPolicy();
        int pointsPolicyID = DBManager.getInstance().getPointsPolicyID(pointsPolicy.getVictoryPoints(),pointsPolicy.getLossPoints(),pointsPolicy.getTiePoints());

        return  DBManager.getInstance().addSeasonToLeague(leagueName , season.getYears() , season.getIsUnderway(), pointsPolicyID);

    }
}
