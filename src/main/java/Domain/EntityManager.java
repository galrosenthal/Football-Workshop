package Domain;

import DB.DBManager;
import Domain.Exceptions.*;
import Domain.Game.*;
import Domain.SystemLogger.*;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Game.Stadium;
import Domain.Users.*;
import javafx.util.Pair;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityManager{
    private static EntityManager entityManagerInstance = null;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private List<SystemUser> allUsers;
    private List<Team> allTeams;
    private List<Stadium> allStadiums;
    private HashSet<League> allLeagues;
    private List<SystemAdmin> systemAdmins;

    private List<PointsPolicy> pointsPolicies;
    private List<SchedulingPolicy> schedulingPolicies;
    private HashMap<SystemUser, Boolean> loggedInMap;

    private boolean isSystemBooted = false;

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
        loggedInMap = new HashMap<>();
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

            SystemUser admin = new SystemUser("Administrator",org.apache.commons.codec.digest.DigestUtils.sha256Hex("Aa123456"),"admin" , "test@gmail.com" , false, true);
            SystemUser arnav = new SystemUser("arnav",org.apache.commons.codec.digest.DigestUtils.sha256Hex("Aa123456"),"arnav" , "test@gmail.com" , false, true);
            admin.addNewRole(new SystemAdmin(admin, true));
            admin.addNewRole(new AssociationRepresentative(admin, true));
            admin.addNewRole(new Referee(admin,RefereeQualification.VAR_REFEREE, true));
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
        List<String> allLeaguesList = DBManager.getInstance().getLeagues();
        List<League> leagues = new ArrayList<>();
        for (int i = 0; i < allLeaguesList.size(); i++) {
            leagues.add(new League((allLeaguesList.get(i))));
        }

        if (leagues.isEmpty()) {
            return new ArrayList<>(this.allLeagues);
        } else {
            return leagues;
        }
        //
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
        List<Pair<String, String>> userDetails = null;
        try {
            userDetails = DBManager.getInstance().getUser(username);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        HashMap<String/*RoleType*/, List<Pair<String, String>>> rolesDetails = DBManager.getInstance().getUserRoles(username);
        SystemUser systemUser = createSystemUser(userDetails);
        createSystemUserRole(systemUser, rolesDetails);
        for (SystemUser su : allUsers) {
            if (su.getUsername().equals(username)) {
                return su;
            }
        }
        return systemUser;
        //return null;
    }

    private void createSystemUserRole(SystemUser systemUser, HashMap<String, List<Pair<String, String>>> rolesDetails) {
        for (String roleType : rolesDetails.keySet()) {
            switch (roleType) {
                case "PLAYER":
                    addPlayerRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "COACH":
                    addCoachRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "TEAM_MANAGER":
                    addTeamMangerRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "TEAM_OWNER":
                    addTeamOwnerRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "SYSTEM_ADMIN":
                    addSystemAdminRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "REFEREE":
                    addRefereeRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "ASSOCIATION_REPRESENTATIVE":
                    addARRole(systemUser, rolesDetails.get(roleType));
                    break;
            }
        }
    }

    private void addARRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {

        AssociationRepresentative associationRepresentative = new AssociationRepresentative(systemUser, true);
    }

    private void addRefereeRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {

        String username = "";
        RefereeQualification training = null;
        for (int i = 0; i < rolesDetail.size(); i++) {
            if (rolesDetail.get(i).getKey().equals("username")) {
                username = rolesDetail.get(i).getValue();
            } else if (rolesDetail.get(i).getKey().equals("training")) {
                training = getRefereeQualification(rolesDetail.get(i).getValue());
            }
        }

        Referee referee = new Referee(systemUser, training, true);
    }

    private RefereeQualification getRefereeQualification(String value) {
        /*MAIN_REFEREE,SIDE_REFEREE,VAR_REFEREE*/
        switch (value) {
            case "MAIN_REFEREE":
                return RefereeQualification.MAIN_REFEREE;
            case "SIDE_REFEREE":
                return RefereeQualification.SIDE_REFEREE;
            case "VAR_REFEREE":
                return RefereeQualification.VAR_REFEREE;
        }
        return null;
    }

    private void addSystemAdminRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        SystemAdmin systemAdmin = new SystemAdmin(systemUser, true);
    }

    private void addTeamOwnerRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        TeamOwner teamOwner = new TeamOwner(systemUser, true);
    }

    private void addTeamMangerRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        TeamManager teamManager = new TeamManager(systemUser, true);
    }

    private void addCoachRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        String username = "";
        CoachQualification qualification = null;
        for (int i = 0; i < rolesDetail.size(); i++) {
            if (rolesDetail.get(i).getKey().equals("username")) {
                username = rolesDetail.get(i).getValue();
            } else if (rolesDetail.get(i).getKey().equals("qualification")) {
                qualification = getCoachQualification(rolesDetail.get(i).getValue());
            }
        }
        Coach coach = new Coach(systemUser, qualification);
    }

    private CoachQualification getCoachQualification(String value) {
        /*'MAIN_COACH','SECOND_COACH','JUNIOR_COACH'*/
        switch (value) {
            case "MAIN_COACH":
                return CoachQualification.MAIN_COACH;
            case "SECOND_COACH":
                return CoachQualification.SECOND_COACH;
            case "JUNIOR_COACH":
                return CoachQualification.JUNIOR_COACH;
        }
        return null;
    }

    private void addPlayerRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        String username = "";
        Date bday = null;
        for (int i = 0; i < rolesDetail.size(); i++) {
            if (rolesDetail.get(i).getKey().equals("username")) {
                username = rolesDetail.get(i).getValue();
            } else if (rolesDetail.get(i).getKey().equals("birthday")) {
                bday = new Date(rolesDetail.get(i).getValue());
            }
        }
        Player player = new Player(systemUser, bday);
    }


    private SystemUser createSystemUser(List<Pair<String, String>> userDetails) {
        String username = "";
        String name = "";
        String password = "";
        String email = "";
        boolean notifyByEmail = false;
        for (int i = 0; i < userDetails.size(); i++) {
            if (userDetails.get(i).getKey().equals("username")) {
                username = userDetails.get(i).getValue();
            } else if (userDetails.get(i).getKey().equals("name")) {
                name = userDetails.get(i).getValue();
            } else if (userDetails.get(i).getKey().equals("password")) {
                password = userDetails.get(i).getValue();
            } else if (userDetails.get(i).getKey().equals("email")) {
                email = userDetails.get(i).getValue();
            } else if (userDetails.get(i).getKey().equals("notify_by_email")) {
                String check = userDetails.get(i).getValue();
                if (check.equals("true")) {
                    notifyByEmail = true;
                }

            }
        }
        SystemUser systemUser = new SystemUser(username, password, name, email, notifyByEmail , false);
        return systemUser;
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
        List<String> stadiumList = DBManager.getInstance().getStadium(stadiumName);
        if (stadiumList == null) {
            return null;
        }
        Stadium stadium = new Stadium(stadiumList.get(0), stadiumList.get(1));
        return stadium;
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
            if (userAdmin != null && userAdmin.getType() == RoleTypes.SYSTEM_ADMIN) {
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

        //return false;
    }

    /**
     * Adds a given SystemUser to the user's list of the system.
     * @param systemUser User to add
     * @return true if successfully added the SystemUser to the system.
     */
    public boolean addUser(SystemUser systemUser) {
        boolean succeeded = false;
        if (!(this.allUsers.contains(systemUser))) {
            succeeded = DBManager.getInstance().addUser(systemUser.getUsername(), systemUser.getName(), systemUser.getPassword(), systemUser.getEmail(), systemUser.isAlertEmail());
            if (succeeded) {
                this.allUsers.add(systemUser);
                return true;
            }
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
        /*TODO - ADD TEAM TO DB*/
        return DBManager.getInstance().addTeam(team.getTeamName(),team.getStatus().toString());
//        if (!(this.allTeams.contains(team))) {
//            this.allTeams.add(team);
//            return true;
//        }
//        return false;
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
        loggedInMap = new HashMap<>();
        try {
            DBManager.deleteData("fwdb_test");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public SystemUser login(String usrNm, String pswrd) throws UsernameOrPasswordIncorrectException,AlreadyLoggedInUser {
        SystemUser userWithUsrNm = getUser(usrNm);
        if(loggedInMap.containsKey(userWithUsrNm) && loggedInMap.get(userWithUsrNm))
        {
            String msg = "Error: The user " + usrNm + " is already logged in";
            SystemLoggerManager.logError(EntityManager.class, msg);
            throw new AlreadyLoggedInUser(msg);
        }
        if(userWithUsrNm == null) { //User name does not exists.
            String msg = "Username or Password was incorrect!";
            SystemLoggerManager.logError(EntityManager.class, msg);
            throw new UsernameOrPasswordIncorrectException(msg);
        }

        //User name exists, checking password.
        if(authenticate(userWithUsrNm, pswrd)){
            loggedInMap.put(userWithUsrNm,true);
            //Log the action
            SystemLoggerManager.logInfo(this.getClass(), new LoginLogMsg(userWithUsrNm.getUsername()));
            return userWithUsrNm;
        }

        String msg = "Username or Password was incorrect!";
        SystemLoggerManager.logError(EntityManager.class, msg);
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
     * @param email  email address
     * @param emailAlert - boolean  - if send via email - true, otherwise false
     * @return New user with those credentials.
     * @throws Exception If user name is already belongs to a user in the system, or
     * the password does not meet the security requirements.
     */
    public SystemUser signUp(String name, String usrNm, String pswrd,String email, boolean emailAlert) throws UsernameAlreadyExistsException, WeakPasswordException, InvalidEmailException, Invalid {
        //Checking if user name is already exists
        if(getUser(usrNm) != null){
            String msg = "Username already exists";
            SystemLoggerManager.logError(EntityManager.class, msg);
            throw new UsernameAlreadyExistsException(msg);
        }

        //Checking if the password meets the security requirements
        // at least 8 characters
        // at least 1 number
        // at least 1 upper case letter
        // at least 1 lower case letter
        // must not contain any spaces
        String pswrdRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        if(!pswrd.matches(pswrdRegEx)){
            String msg = "Password does not meet the requirements";
            SystemLoggerManager.logError(EntityManager.class, msg);
            throw new WeakPasswordException(msg);
        }
        if(!validate(email))
        {
            String msg = "Invalid Email";
            SystemLoggerManager.logError(EntityManager.class, msg);
            throw new InvalidEmailException(msg);
        }

        //hash the password
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pswrd);


        /*add user to db*/
        if (DBManager.getInstance().addUser(usrNm, name, hashedPassword, email, emailAlert)) {
            SystemUser newUser = new SystemUser(usrNm, hashedPassword, name, email, emailAlert, true);
            addUser(newUser);
            //Log the action
            SystemLoggerManager.logInfo(this.getClass(), new SignUpLogMsg(newUser.getUsername()));

            return newUser;

        }

        throw new Invalid("something went wrong");

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

    public void logout(SystemUser logoutUser) {
        loggedInMap.put(logoutUser,false);

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
     * assumes a points policy with the same values doesn't exist yet.
     *
     * @param newPointsPolicy - PointsPolicy - a new policy
     */
    public void addPointsPolicy(PointsPolicy newPointsPolicy) {
        if (newPointsPolicy != null) {
            this.pointsPolicies.add(newPointsPolicy);
            DBManager dbManager = DBManager.getInstance();
            DBManager.getInstance().addPointsPolicy(newPointsPolicy.getVictoryPoints(), newPointsPolicy.getLossPoints(), newPointsPolicy.getTiePoints());
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
     * Receives a new scheduling policy and adds it.
     * assumes a scheduling policy with the same values doesn't exist yet.
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

    /**
     * check is season exist
     * @param leagueName - String
     * @param seasonYears - String
     * @return true - season exist
     *         false - otherwise
     */
    public boolean doesSeasonExist(String leagueName, String seasonYears) {
        return DBManager.getInstance().doesSeasonExists(leagueName, seasonYears);
    }

    /**
     * add season to league - in db
     * @param leagueName - String
     * @param season - Season
     * @return true - add season successfully
     *         false - otherwise
     */
    public boolean addSeason(String leagueName, Season season) {
        PointsPolicy pointsPolicy = season.getPointsPolicy();
        int pointsPolicyID = DBManager.getInstance().getPointsPolicyID(pointsPolicy.getVictoryPoints(), pointsPolicy.getLossPoints(), pointsPolicy.getTiePoints());

        return DBManager.getInstance().addSeasonToLeague(leagueName, season.getYears(), season.getIsUnderway(), pointsPolicyID);
    }

    /**
     * validate String to Email REGEX
     * @param emailStr
     * @return true - valid Email
     *         false - otherwise
     */
    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * All teams owned by teamOwner
     * @param teamOwner - TeamOwner
     * @return - List<Team> - owned by teamOwner
     */
    public List<Team> getOwnedTeams(TeamOwner teamOwner) {
        List<Pair<String, String>> ownedTeams = DBManager.getInstance().getTeams(teamOwner.getSystemUser().getName());
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < ownedTeams.size(); i++) {
            Team team = new Team(ownedTeams.get(i).getKey(), teamOwner);
            team.setStatus(getTeamStatus(ownedTeams.get(i).getValue()));
            teams.add(team);
        }
        return teams;
    }

    /**
     * check which status the team
     * @param value - String team status - return from DBmanger
     * @return - TeamStatus - Enum
     */
    private TeamStatus getTeamStatus(String value) {
        /*'OPEN','CLOSED','PERMENENTLY_CLOSED'*/
        switch (value) {
            case "OPEN":
                return TeamStatus.OPEN;
            case "CLOSED":
                return TeamStatus.CLOSED;
            case "PERMENENTLY_CLOSED":
                return TeamStatus.PERMENENTLY_CLOSED;
        }
        return null;

    }

    /**
     * addRole to role in DBManger
     * @param role
     */
    public void addRole(Role role) {
        RoleTypes type = role.getType();
        switch (type) {
            case PLAYER:
                Player player = (Player) role;
                DBManager.getInstance().addPlayer(player.getSystemUser().getUsername(),player.getBday());
                break;
            case COACH:
                Coach coach = (Coach) role;
                if(coach.getQualification() != null)
                {
                    DBManager.getInstance().addCoach(coach.getSystemUser().getUsername(),coach.getQualification().name());
                }
                DBManager.getInstance().addCoach(coach.getSystemUser().getUsername(),null);
                break;
            case TEAM_MANAGER:
                TeamManager teamManager = (TeamManager) role;
                DBManager.getInstance().addTeamManager(teamManager.getSystemUser().getUsername());
                break;
            case TEAM_OWNER:
                TeamOwner teamOwner = (TeamOwner) role;
                DBManager.getInstance().addTeamOwner(teamOwner.getSystemUser().getUsername());
                break;
            case SYSTEM_ADMIN:
                SystemAdmin systemAdmin = (SystemAdmin) role;
                DBManager.getInstance().addSystemAdmin(systemAdmin.getSystemUser().getUsername());
                break;
            case REFEREE:
                Referee referee = (Referee) role;
                if(referee.getTraining()!= null)
                {
                    DBManager.getInstance().addReferee(referee.getSystemUser().getUsername(),referee.getTraining().name());
                }
                else
                {
                    DBManager.getInstance().addReferee(referee.getSystemUser().getUsername(),null);
                }
                break;
            case ASSOCIATION_REPRESENTATIVE:
                AssociationRepresentative associationRepresentative = (AssociationRepresentative) role;
                DBManager.getInstance().addAssociationRepresentative(associationRepresentative.getSystemUser().getUsername());
                break;
        }
    }


    /**
     * add team manger to team - with tha appointee
     * @param team
     * @param teamManager
     * @param teamOwner
     * @return true - added successfully
     *         false - otherwise
     */
    public boolean addTeamManger(Team team, TeamManager teamManager, TeamOwner teamOwner) {
        return DBManager.getInstance().addTeamMangerToTeam(team.getTeamName(),teamManager.getSystemUser().getUsername(),teamOwner.getSystemUser().getUsername());
    }

    public boolean isSystemBooted() {
        return isSystemBooted;
    }

    public void setIsBooted(boolean isSystemBoot) {
        isSystemBooted = isSystemBoot;
    }

    /**
     * Check if teamOwner is  Team Owner in team
     * @param teamOwner - TeamOwner
     * @param team - Team
     * @return true -teamOwner is Team owner of  team
     *         false - otherwise
     */
    public boolean isTeamOwner(TeamOwner teamOwner, Team team) {
        return DBManager.getInstance().isTeamOwner(teamOwner.getSystemUser().getUsername(),team.getTeamName());
    }

    /**
     * add stadium to db
     * @param stadium - Stadium
     * @param team - Team
     * @return - true - added  Stadium to team successfully
     *           false - otherwise
     */
    public boolean addStadium(Stadium stadium, Team team) {
        return DBManager.getInstance().addStadiumToTeam(stadium.getName(),stadium.getLocation(),team.getTeamName());
    }


    /**
     * add connection between team to partOfTeam
     * @param teamBelongsTo - PartOfTeam
     * @param partOfTeam - teamBelongsTo
     * @return true - add connection successfully
     *         false - otherwise
     */
    public boolean addConnection(Team teamBelongsTo, PartOfTeam partOfTeam) {
        return DBManager.getInstance().addTeamConnection(teamBelongsTo.getTeamName(),partOfTeam.getType().toString(),partOfTeam.getSystemUser().getUsername());
    }

    public boolean updateTeamName(String teamName, String testName) {
        return DBManager.getInstance().updateTeamName(teamName ,testName );
    }

    public boolean setTeamOwnerAppointed(TeamOwner teamOwner, Team team, String appointed) {
        return DBManager.getInstance().setTeamOwnerAppointed(teamOwner.getSystemUser().getUsername() ,team.getTeamName(),appointed );

    }

    public boolean addTeamOwnerToTeam(TeamOwner teamOwner, Team team) {
        return DBManager.getInstance().addTeamOwnerToTeam(teamOwner.getSystemUser().getUsername() ,team.getTeamName() );

    }
}
