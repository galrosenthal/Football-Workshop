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

import java.sql.Ref;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
//    private List<SystemAdmin> sstemAdmins;

    private List<PointsPolicy> pointsPolicies;
    private List<SchedulingPolicy> schedulingPolicies;
//    private HashMap<SystemUser, Boolean> loggedInMap;

    private boolean isSystemBooted = false;





    private EntityManager() {
        allUsers = new ArrayList<>();
        allLeagues = new HashSet<>();
        allTeams = new ArrayList<>();
        allStadiums = new ArrayList<>();
//        loggedInMap = new HashMap<>();
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
            DBManager.getInstance().startConnection();
            SystemUser admin = new SystemUser("Administrator", org.apache.commons.codec.digest.DigestUtils.sha256Hex("Aa123456"), "admin", "test@gmail.com", false, true);
            SystemUser arnav = new SystemUser("arnav", org.apache.commons.codec.digest.DigestUtils.sha256Hex("Aa123456"), "arnav", "test@gmail.com", false, true);
            admin.addNewRole(new SystemAdmin(admin, true));
            admin.addNewRole(new AssociationRepresentative(admin, true));
            admin.addNewRole(new Referee(admin, RefereeQualification.VAR_REFEREE, true));
            admin.addNewRole(new TeamOwner(admin, true));
            createObjectsForGuiTests();
        }

        return entityManagerInstance;
    }

    private static void createObjectsForGuiTests() {
        //AR Controls tests
        Stadium stadium1 = new Stadium("stadium1","london" , true);
        Stadium stadium2 = new Stadium("stadium2","paris", true);
        EntityManager.getInstance().addStadium(stadium1);
        EntityManager.getInstance().addStadium(stadium2);

        SystemUser user1 = new SystemUser("user1","Aa123456","Oran","oran@gmail.com",false, true);
        EntityManager.getInstance().addUser(user1);
        SystemUser user2 = new SystemUser("user2","Aa123456","Oran","oran@gmail.com",false, true);
        EntityManager.getInstance().addUser(user2);
        TeamOwner to1 = new TeamOwner(user1, true);
        TeamOwner to2 = new TeamOwner(user2, true);
        Team team1 = new Team("team1",to1, true);
        Team team2 = new Team("team2",to2, true);

        for (int i=0; i < 12;i++){
            SystemUser user = new SystemUser(Integer.toString(i),Integer.toString(i), true);
            Player player = new Player(user,new Date(), true);
            EntityManager.getInstance().addUser(user);
            team1.addTeamPlayer(to1,player);
        }
        for (int i=12; i < 25;i++){
            SystemUser user = new SystemUser(Integer.toString(i),Integer.toString(i), true);
            Player player = new Player(user,new Date(), true);
            EntityManager.getInstance().addUser(user);
            team2.addTeamPlayer(to2,player);
        }

        team1.addStadium(stadium1);
        team2.addStadium(stadium2);

        EntityManager.getInstance().addTeam(team1);
        EntityManager.getInstance().addTeam(team2);
        SystemUser refUser = new SystemUser("referee","referee", true);

        EntityManager.getInstance().addUser(refUser);

        //To Controls Tests
        SystemUser user3 = new SystemUser("user3","user3", true);
        TeamOwner to3 = new TeamOwner(user3, true);
        Team team3 = new Team("team3",(TeamOwner)EntityManager.getInstance().getUser("Administrator").getRole(RoleTypes.TEAM_OWNER), true);
        EntityManager.getInstance().addUser(user3);
        EntityManager.getInstance().addTeam(team3);
        Stadium stadium3 = new Stadium("stadium3","london", true);
        EntityManager.getInstance().addStadium(stadium3);
        Stadium stadium4 = new Stadium("stadium4","london", true);
        EntityManager.getInstance().addStadium(stadium4);

        SystemUser systemUser = EntityManager.getInstance().getUser("Administrator");
        Referee referee = (Referee)systemUser.getRole(RoleTypes.REFEREE);
        SystemUser arSystemUser = new SystemUser("arSystemUser", "arUser", true);
        new AssociationRepresentative(arSystemUser, true);
        new TeamOwner(arSystemUser, true);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team firstTeam = new Team("Hapoel Beit Shan", toRole, true);
        EntityManager.getInstance().addTeam(firstTeam);
        Team secondTeam = new Team("Hapoel Beer Sheva", toRole, true);
        EntityManager.getInstance().addTeam(secondTeam);

        Date gameDate;
        try {
            gameDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020");
            Game game = new Game(new Stadium("staName", "staLoca", true), firstTeam, secondTeam,gameDate, new ArrayList<>(), true);
            SystemUser avi = new SystemUser("AviCohen", "Avi Cohen", true);
            Player player1 = new Player(avi, new Date(2001, 01, 01), true);
            avi.addNewRole(player1);
            EntityManager.getInstance().addUser(avi);
            firstTeam.addTeamPlayer(toRole, player1);

            game.addReferee(referee);
            referee.addGame(game);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        if (this.allLeagues.isEmpty()) {//TODO: Replace with a function that checks if additional records should be pulled from the DB
            List<String> allLeaguesList = DBManager.getInstance().getLeagues();
            List<League> leagues = new ArrayList<>();
            for (int i = 0; i < allLeaguesList.size(); i++) {
                String leagueName = allLeaguesList.get(i);
                League leagueToAdd = new League(leagueName, false);
                //get seasons
                List<HashMap<String, String>> leagueSeasons = DBManager.getInstance().getLeagueSeasons(leagueName);
                for (int j = 0; j < leagueSeasons.size(); j++) {
                    HashMap<String, String> seasonDetails = leagueSeasons.get(j);
                    boolean isUnderway = false;
                    if (seasonDetails.get("is_under_way").equals("true")) {
                        isUnderway = true;
                    }
                    PointsPolicy pointsPolicy = getPointsPolicy(Integer.parseInt(seasonDetails.get("victory_points")), Integer.parseInt(seasonDetails.get("loss_points")), Integer.parseInt(seasonDetails.get("tie_points")));
                    leagueToAdd.addSeason(seasonDetails.get("years"), pointsPolicy, isUnderway);
                }
                leagues.add(leagueToAdd);
            }
            //load leagues
            for (League leagueFromDB : leagues) {
                if (!this.allLeagues.contains(leagueFromDB)) {
                    this.allLeagues.add(leagueFromDB);
                }
            }
        }
        //return a copy
        return new ArrayList<>(this.allLeagues);
    }

    public List<Team> getTeams() {
        if (this.allTeams.isEmpty()) {//TODO: Replace with a function that checks if additional records should be pulled from the DB
            List<Team> teams = new ArrayList<>();

            List<HashMap<String, String>> allTeamsDetails = DBManager.getInstance().getTeamsDetails();

            for (int i = 0; i < allTeamsDetails.size(); i++) {
                HashMap<String, String> currentTeamDetails = allTeamsDetails.get(i);
                String teamName = currentTeamDetails.get("name");
                TeamStatus teamStatus = TeamStatus.valueOf(currentTeamDetails.get("status"));
                Team currentTeam = reCreateTeam(teamName, teamStatus);
                teams.add(currentTeam);
            }
            //load teams
            for (Team teamFromDB : teams) {
                if (!this.allTeams.contains(teamFromDB)) {
                    this.allTeams.add(teamFromDB);
                }
            }
        }
        //return a copy
        return new ArrayList<Team>(this.allTeams);
    }

    private Team reCreateTeam(String teamName, TeamStatus teamStatus) {
        return new Team(teamName, teamStatus, false);
    }

    public List<SystemUser> getAllUsers() {
        if (this.allUsers.isEmpty()) {//TODO: Replace with a function that checks if additional records should be pulled from the DB
            List<String> allUsernames = DBManager.getInstance().getUsernames();
            List<SystemUser> users = new ArrayList<>();

            for (int i = 0; i < allUsernames.size(); i++) {
                String currentUsername = allUsernames.get(i);
                SystemUser currentSystemUser = getUser(currentUsername);
                users.add(currentSystemUser);
            }

            //load systemUsers
            for (SystemUser userFromDB : users) {
                if (!this.allUsers.contains(userFromDB)) {
                    this.allUsers.add(userFromDB);
                }
            }
        }
        //return a copy
        return new ArrayList<>(this.allUsers);
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
        SystemUser systemUser = reCreateSystemUser(userDetails);
        reCreateSystemUserRole(systemUser, rolesDetails);

        for (SystemUser su : allUsers) {
            if (su.getUsername().equals(username)) {
                return su;
            }
        }
        return systemUser;
        //return null;
    }

    /**
     * Re-create a Role object based on the given arguments
     *
     * @param systemUser   - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetails - HashMap<String, List<Pair<String, String>>> - The role's details
     */
    private void reCreateSystemUserRole(SystemUser systemUser, HashMap<String, List<Pair<String, String>>> rolesDetails) {
        for (String roleType : rolesDetails.keySet()) {
            switch (roleType) {
                case "PLAYER":
                    reCreatePlayerRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "COACH":
                    reCreateCoachRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "TEAM_MANAGER":
                    reCreateTeamMangerRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "TEAM_OWNER":
                    reCreateTeamOwnerRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "SYSTEM_ADMIN":
                    reCreateSystemAdminRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "REFEREE":
                    reCreateRefereeRole(systemUser, rolesDetails.get(roleType));
                    break;
                case "ASSOCIATION_REPRESENTATIVE":
                    reCreateARRole(systemUser, rolesDetails.get(roleType));
                    break;
            }
        }
    }

    /**
     * Re-create a Player role based on the given arguments
     *
     * @param systemUser  - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetail - List<Pair<String, String>> - The role's details
     */
    private void reCreatePlayerRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        String username = "";
        Date bday = null;
        for (int i = 0; i < rolesDetail.size(); i++) {
            if (rolesDetail.get(i).getKey().equals("username")) {
                username = rolesDetail.get(i).getValue();
            } else if (rolesDetail.get(i).getKey().equals("birthday")) {
                String string = rolesDetail.get(i).getValue();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = format.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bday = date;
            }
        }
        Player player = new Player(systemUser, bday, false);
    }

    /**
     * Re-create a Coach role based on the given arguments
     *
     * @param systemUser  - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetail - List<Pair<String, String>> - The role's details
     */
    private void reCreateCoachRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        String username = "";
        CoachQualification qualification = null;
        for (int i = 0; i < rolesDetail.size(); i++) {
            if (rolesDetail.get(i).getKey().equals("username")) {
                username = rolesDetail.get(i).getValue();
            } else if (rolesDetail.get(i).getKey().equals("qualification")) {
                qualification = getCoachQualification(rolesDetail.get(i).getValue());
            }
        }
        Coach coach = new Coach(systemUser, qualification, false);
    }

    /**
     * Re-create a TeamManger role based on the given arguments
     *
     * @param systemUser  - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetail - List<Pair<String, String>> - The role's details
     */
    private void reCreateTeamMangerRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        TeamManager teamManager = new TeamManager(systemUser, false);
    }

    /**
     * Re-create a TeamOwner role based on the given arguments
     *
     * @param systemUser  - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetail - List<Pair<String, String>> - The role's details
     */
    private void reCreateTeamOwnerRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        TeamOwner teamOwner = new TeamOwner(systemUser, false);
    }

    /**
     * Re-create a SystemAdmin role based on the given arguments
     *
     * @param systemUser  - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetail - List<Pair<String, String>> - The role's details
     */
    private void reCreateSystemAdminRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {
        SystemAdmin systemAdmin = new SystemAdmin(systemUser, false);
    }

    /**
     * Re-create a Referee role based on the given arguments
     *
     * @param systemUser  - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetail - List<Pair<String, String>> - The role's details
     */
    private void reCreateRefereeRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {

        String username = "";
        RefereeQualification training = null;
        for (int i = 0; i < rolesDetail.size(); i++) {
            if (rolesDetail.get(i).getKey().equals("username")) {
                username = rolesDetail.get(i).getValue();
            } else if (rolesDetail.get(i).getKey().equals("training")) {
                training = getRefereeQualification(rolesDetail.get(i).getValue());
            }
        }

        Referee referee = new Referee(systemUser, training, false);
    }

    /**
     * Re-create a AssociationRepresentative role based on the given arguments
     *
     * @param systemUser  - SystemUser - the SystemUser to add the recreated role to
     * @param rolesDetail - List<Pair<String, String>> - The role's details
     */
    private void reCreateARRole(SystemUser systemUser, List<Pair<String, String>> rolesDetail) {

        AssociationRepresentative associationRepresentative = new AssociationRepresentative(systemUser, false);
    }

    /**
     * Return RefereeQualification based on a given string which represents the qualification type
     *
     * @param value - String - Represents the qualification type
     * @return - RefereeQualification - Based on a given string
     */
    private RefereeQualification getRefereeQualification(String value) {
        /*MAIN_REFEREE,SIDE_REFEREE,VAR_REFEREE*/
        if (value == null) {
            return null;
        }
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

    /**
     * Return CoachQualification based on a given string which represents the qualification type
     *
     * @param value - String - Represents the qualification type
     * @return - CoachQualification - Based on a given string
     */
    private CoachQualification getCoachQualification(String value) {
        /*'MAIN_COACH','SECOND_COACH','JUNIOR_COACH'*/
        if (value == null) {
            return null;
        }
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

    /**
     * Re-create SystemUser object based on the given arguments
     *
     * @param userDetails - List<Pair<String, String>> - A list of pairs of keys and values of the user details.
     * @return - SystemUser - A new SystemUser object initialized with the given parameters.
     */
    private SystemUser reCreateSystemUser(List<Pair<String, String>> userDetails) {
        String username = "";
        String name = "";
        String password = "";
        String email = "";
        boolean notifyByEmail = false;
        for (int i = 0; i < userDetails.size(); i++) {
            String key = userDetails.get(i).getKey();
            switch (key) {
                case "username":
                    username = userDetails.get(i).getValue();
                    break;
                case "name":
                    name = userDetails.get(i).getValue();
                    break;
                case "password":
                    password = userDetails.get(i).getValue();
                    break;
                case "email":
                    email = userDetails.get(i).getValue();
                    break;
                case "notify_by_email":
                    String check = userDetails.get(i).getValue();
                    if (check.equals("true")) {
                        notifyByEmail = true;
                    }
                    break;
            }
        }
        //initializing the user. Doesn't adds it again to the DB.
        SystemUser systemUser = new SystemUser(username, password, name, email, notifyByEmail, false);
        return systemUser;
    }

    /**
     * Returns a Team by its team name
     *
     * @param teamName
     * @return The team with the given team name, if exists in the system.
     */
    public Team getTeam(String teamName) {

        HashMap<String,String> team = DBManager.getInstance().getTeam(teamName);
        String name = team.get("name");
        TeamStatus teamStatus = TeamStatus.valueOf(team.get("status"));
        Team teamToGet = new Team(teamName, teamStatus,false);
        return teamToGet;
//        for (Team team : allTeams) {
//            if (team.getTeamName().toLowerCase().equals(teamName.toLowerCase())) {
//                return team;
//            }
//        }
        //return null;
    }

    /**
     * Returns a Stadium by its stadium name
     *
     * @param stadiumName
     * @return The Stadium with the given stadium name, if exists in the system.
     */
    public Stadium getStadium(String stadiumName) {
        for (Stadium std : allStadiums) {
            if (std.getName().equals(stadiumName)) {
                return std;
            }
        }
        List<String> stadiumList = DBManager.getInstance().getStadium(stadiumName);
        if (stadiumList == null) {
            return null;
        }
        Stadium stadium = new Stadium(stadiumList.get(0), stadiumList.get(1), true);
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
    public boolean doesTeamExists(String teamName){
        return DBManager.getInstance().doesTeamExists(teamName);

/*        for (Team team : allTeams) {
            if (team.getTeamName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;*/
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
        return removeUserByReference(getUser(username));
//        for (SystemUser su : allUsers) {
//            if (su.getUsername().equals(username)) {
//                this.allUsers.remove(su);
//                return true;
//            }
//        }
//        return false;
    }

    /**
     * Adds a given Team to the team's list of the system.
     * @param team Team to add
     * @return true if successfully added the Team to the system.
     */
    public boolean addTeam(Team team) {
        /*TODO - ADD TEAM TO DB*/
        return DBManager.getInstance().addTeam(team.getTeamName(), team.getStatus().toString());
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
        if (!(this.allLeagues.contains(league))) { //Verify that the league does not exists in the memory
            if (!(DBManager.getInstance().doesLeagueExists(league.getName()))) { //Verify that the league does not exists in the DB
                if (DBManager.getInstance().addLeagueRecord(league.getName())) {
                    this.allLeagues.add(league);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Adds a given Stadium to the stadium's list of the system.
     * @param stadium Stadium to add
     * @return true if successfully added the Stadium to the system.
     */
    public boolean addStadium(Stadium stadium) {
        if (!(this.allStadiums.contains(stadium))) {
            this.allStadiums.add(stadium);
            return DBManager.getInstance().addStadium(stadium.getName(),stadium.getLocation());
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
//        loggedInMap = new HashMap<>();
//        loggedInMap = new HashMap<>();
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
        List<SystemUser> allSystemUsers = this.getAllUsers();
        for (SystemUser user : allSystemUsers) {
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
    public SystemUser login(String usrNm, String pswrd) throws UsernameOrPasswordIncorrectException, AlreadyLoggedInUser {
        SystemUser userWithUsrNm = getUser(usrNm);
//        if(loggedInMap.containsKey(userWithUsrNm) && loggedInMap.get(userWithUsrNm))
//        {
//            String msg = "Error: The user " + usrNm + " is already logged in";
//            SystemLoggerManager.logError(EntityManager.class, msg);
//            throw new AlreadyLoggedInUser(msg);
//        }
        if(userWithUsrNm == null) { //User name does not exists.
            String msg = "Username or Password was incorrect!";
            SystemLoggerManager.logError(EntityManager.class, msg);
            throw new UsernameOrPasswordIncorrectException(msg);
        }

        //User name exists, checking password.
        if(authenticate(userWithUsrNm, pswrd)){
//            loggedInMap.put(userWithUsrNm,true);
            //Log the action
            SystemLoggerManager.logInfo(this.getClass(), new LoginLogMsg(userWithUsrNm.getUsername()));
            /*TODO SHOW ALERT!  - in case he doesnt get alert in email*/
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
    public SystemUser signUp(String name, String usrNm, String pswrd,String email, boolean emailAlert) throws UsernameAlreadyExistsException, WeakPasswordException, InvalidEmailException, InvalidEventException {
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

        throw new InvalidEventException("something went wrong");

    }


    public League getLeagueByName(String leagueName) {
        for (League lg :
                allLeagues) {
            if (lg.getName().equals(leagueName)) {
                return lg;
            }
        }

        return null;
    }

//    public void logout(SystemUser logoutUser) {
////        loggedInMap.put(logoutUser,false);
//    }



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
        return DBManager.getInstance().doesPointsPolicyExists(victoryPoints, lossPoints, tiePoints);
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
        //FIXME:!!!PULL FROM DB AND INIT!!!
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
            DBManager.getInstance().addPointsPolicy(newPointsPolicy.getVictoryPoints(), newPointsPolicy.getLossPoints(), newPointsPolicy.getTiePoints());
        }
    }

    public List<PointsPolicy> getPointsPolicies() {
        if (this.pointsPolicies.isEmpty()) { //TODO: Replace with a function that checks if additional records should be pulled from the DB
            List<HashMap<String, String>> pointsPoliciesFromDB = DBManager.getInstance().getPointsPolicies();

            for (HashMap<String, String> pointsPolicyDetails : pointsPoliciesFromDB) {
                int victoryPoints = Integer.parseInt(pointsPolicyDetails.get("victory_points"));
                int lossPoints = Integer.parseInt(pointsPolicyDetails.get("loss_points"));
                int tiePoints = Integer.parseInt(pointsPolicyDetails.get("tie_points"));
                PointsPolicy currentPointsPolicy = new PointsPolicy(victoryPoints, lossPoints, tiePoints);
                if (!this.pointsPolicies.contains(currentPointsPolicy)) {
                    this.pointsPolicies.add(currentPointsPolicy);
                }
            }
        }
        return new ArrayList<>(this.pointsPolicies);
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
        return DBManager.getInstance().doesSchedulingPolicyExists(gamesPerSeason, gamesPerDay, minRest);
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

        HashMap<String , String> schedulingPolicyDetails = DBManager.getInstance().getSchedulingPolicy(gamesPerSeason, gamesPerDay,  minRest);
        gamesPerSeason = Integer.parseInt(schedulingPolicyDetails.get("games_Per_Season"));
        gamesPerDay = Integer.parseInt(schedulingPolicyDetails.get("games_Per_Day"));
        minRest = Integer.parseInt(schedulingPolicyDetails.get("minimum_Rest_Days"));
        return new SchedulingPolicy(gamesPerSeason,gamesPerDay,minRest);

//        for (SchedulingPolicy schedulingPolicy : this.schedulingPolicies) {
//            if (schedulingPolicy.equals(gamesPerSeason, gamesPerDay, minRest)) {
//                return schedulingPolicy;
//            }
//        }
//        return null;
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
            DBManager.getInstance().addSchedulingPolicy(newSchedulingPolicy.getGamesPerSeason(), newSchedulingPolicy.getGamesPerDay(), newSchedulingPolicy.getMinimumRestDays());
        }
    }

    public List<SchedulingPolicy> getSchedulingPolicies() {
        List<HashMap<String , String> >schedulingPolicyDetails = DBManager.getInstance().getSchedulingPolicies();
        List<SchedulingPolicy>  schedulingPolicies = new ArrayList<>();
        for (int i = 0; i < schedulingPolicyDetails.size(); i++) {
            int gamesPerSeason = Integer.parseInt(schedulingPolicyDetails.get(i).get("games_Per_Season"));
            int gamesPerDay = Integer.parseInt(schedulingPolicyDetails.get(i).get("games_Per_Day"));
            int minRest = Integer.parseInt(schedulingPolicyDetails.get(i).get("minimum_Rest_Days"));
            schedulingPolicies.add(new SchedulingPolicy(gamesPerSeason,gamesPerDay,minRest));
        }
        return schedulingPolicies;
    }

    /**
     * check is season exist
     *
     * @param leagueName  - String
     * @param seasonYears - String
     * @return true - season exist
     * false - otherwise
     */
    public boolean doesSeasonExist(String leagueName, String seasonYears) {
        return DBManager.getInstance().doesSeasonExists(leagueName, seasonYears);
    }

    /**
     * add season to league - in db
     *
     * @param leagueName - String
     * @param season     - Season
     * @return true - add season successfully
     * false - otherwise
     */
    public boolean addSeason(String leagueName, Season season) {
        PointsPolicy pointsPolicy = season.getPointsPolicy();
        int pointsPolicyID = DBManager.getInstance().getPointsPolicyID(pointsPolicy.getVictoryPoints(), pointsPolicy.getLossPoints(), pointsPolicy.getTiePoints());

        return DBManager.getInstance().addSeasonToLeague(leagueName, season.getYears(), season.getIsUnderway(), pointsPolicyID);
    }

    /**
     * validate String to Email REGEX
     *
     * @param emailStr
     * @return true - valid Email
     * false - otherwise
     */
    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * All teams owned by teamOwner
     *
     * @param teamOwner - TeamOwner
     * @return - List<Team> - owned by teamOwner
     */
    public List<Team> getOwnedTeams(TeamOwner teamOwner) {
        List<HashMap<String, String>> ownedTeams = DBManager.getInstance().getTeams(teamOwner.getSystemUser().getUsername());
        List<Team> teamsManaged = new ArrayList<>();
        for (int i = 0; i < ownedTeams.size(); i++) {
            String teamName = ownedTeams.get(i).get("name");
            TeamStatus teamStatus = TeamStatus.valueOf(ownedTeams.get(i).get("status"));
            teamsManaged.add(new Team(teamName, teamStatus, false));
        }
        return teamsManaged;
    }

    /**
     * check which status the team
     *
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
                return TeamStatus.PERMANENTLY_CLOSED;
        }
        return null;

    }

    /**
     * addRole to role in DBManger
     *
     * @param role
     */
    public void addRole(Role role) {
        RoleTypes type = role.getType();
        switch (type) {
            case PLAYER:
                Player player = (Player) role;
                DBManager.getInstance().addPlayer(player.getSystemUser().getUsername(), player.getBday());
                break;
            case COACH:
                Coach coach = (Coach) role;
                if (coach.getQualification() != null) {
                    DBManager.getInstance().addCoach(coach.getSystemUser().getUsername(), coach.getQualification().name());
                }
                DBManager.getInstance().addCoach(coach.getSystemUser().getUsername(), null);
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
                if (referee.getTraining() != null) {
                    DBManager.getInstance().addReferee(referee.getSystemUser().getUsername(), referee.getTraining().name());
                } else {
                    DBManager.getInstance().addReferee(referee.getSystemUser().getUsername(), null);
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
     *
     * @param team
     * @param teamManager
     * @param teamOwner
     * @return true - added successfully
     * false - otherwise
     */
    public boolean addTeamManger(Team team, TeamManager teamManager, TeamOwner teamOwner) {
        return DBManager.getInstance().addTeamMangerToTeam(team.getTeamName(), teamManager.getSystemUser().getUsername(), teamOwner.getSystemUser().getUsername());
    }

    public boolean isSystemBooted() {
        return isSystemBooted;
    }

    public void setIsBooted(boolean isSystemBoot) {
        isSystemBooted = isSystemBoot;
    }

    /**
     * Check if teamOwner is  Team Owner in team
     *
     * @param teamOwner - TeamOwner
     * @param team      - Team
     * @return true -teamOwner is Team owner of  team
     * false - otherwise
     */
    public boolean isTeamOwner(TeamOwner teamOwner, Team team) {
        return DBManager.getInstance().isTeamOwner(teamOwner.getSystemUser().getUsername(), team.getTeamName());
    }

    /**
     * add stadium to db
     *
     * @param stadium - Stadium
     * @param team    - Team
     * @return - true - added  Stadium to team successfully
     * false - otherwise
     */
    public boolean addStadium(Stadium stadium, Team team) {
        return DBManager.getInstance().addStadiumToTeam(stadium.getName(), stadium.getLocation(), team.getTeamName());
    }


    /**
     * add connection between team to partOfTeam
     *
     * @param teamBelongsTo - PartOfTeam
     * @param partOfTeam    - teamBelongsTo
     * @return true - add connection successfully
     * false - otherwise
     */
    public boolean addConnection(Team teamBelongsTo, PartOfTeam partOfTeam) {
        return DBManager.getInstance().addTeamConnection(teamBelongsTo.getTeamName(), partOfTeam.getType().toString(), partOfTeam.getSystemUser().getUsername());
    }

    public boolean updateTeamName(String teamName, String testName) {
        return DBManager.getInstance().updateTeamName(teamName, testName);
    }

    public boolean setTeamOwnerAppointed(TeamOwner teamOwner, Team team, String appointed) {
        return DBManager.getInstance().setTeamOwnerAppointed(teamOwner.getSystemUser().getUsername(), team.getTeamName(), appointed);

    }

    public boolean addTeamOwnerToTeam(TeamOwner teamOwner, Team team) {
        return DBManager.getInstance().addTeamOwnerToTeam(teamOwner.getSystemUser().getUsername(), team.getTeamName());

    }

    public List<Game> getRefereeGames(Referee referee) {
        List<HashMap<String, String>> refereeGamesDetails = DBManager.getInstance().getRefereeGames(referee.getSystemUser().getUsername());
        List<Game> allRefereeGames = new ArrayList<>();
        for (HashMap<String, String> gameRecord :
                refereeGamesDetails) {
            Team homeTeam = new Team(gameRecord.get(DB.Tables.tables.Game.GAME.HOME_TEAM.getName()),false);
            Team awayTeam = new Team(gameRecord.get(DB.Tables.tables.Game.GAME.AWAY_TEAM.getName()),false);
            Stadium gameStadium = new Stadium(gameRecord.get(DB.Tables.tables.Stadium.STADIUM.NAME.getName()),
                    gameRecord.get(DB.Tables.tables.Stadium.STADIUM.LOCATION.getName()),false);
            Date gameDate = getDateFromLocalDate(gameRecord.get(DB.Tables.tables.Game.GAME.START_DATE.getName()));

            String endGameDateString = gameRecord.get(DB.Tables.tables.Game.GAME.END_DATE.getName());
            Date endGameDate = null;
            if(!(endGameDateString == null))
            {
               endGameDate = getDateFromLocalDate(endGameDateString);
            }
            Game g = new Game(gameStadium,homeTeam,awayTeam,gameDate,null,false);
            g.setEndDate(endGameDate);
            allRefereeGames.add(g);
        }
        return allRefereeGames;
    }

    private Date getDateFromLocalDate(String gameDate) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(gameDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void addGame(Game game) {
        Stadium gameStad = game.getStadium();
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();
        Date gameDate = game.getGameDate();
        boolean isFinished = game.hasFinished();
        DBManager.getInstance().addGame(gameStad.getAssetName(),gameStad.getLocation() ,homeTeam.getTeamName(), awayTeam.getTeamName(), gameDate, isFinished);
    }

    public HashMap<String, Boolean> getRefereeGamesStatus(Referee referee) {
        List<HashMap<String, String>> refereeGamesDetails = DBManager.getInstance().getRefereeGames(referee.getSystemUser().getUsername());
        HashMap<String, Boolean> gamesStatus = new HashMap<>();
        for (int i = 0; i < refereeGamesDetails.size(); i++) {
            if (refereeGamesDetails.get(i).get("finished").equals("true")) {
                gamesStatus.put(refereeGamesDetails.get(i).get("game_id"), new Boolean(true));
            } else {
                gamesStatus.put(refereeGamesDetails.get(i).get("game_id"), new Boolean(false));
            }
        }
        return gamesStatus;
    }

    public void unAssignRefereeFromAllSeasons(Referee referee) {
        DBManager.getInstance().unAssignRefereeFromAllSeasons(referee.getSystemUser().getUsername());
    }

    public boolean setPointsPolicy(Season season, PointsPolicy pointsPolicy) {
        return DBManager.getInstance().setPointsPolicy(season.getLeague().getName(), season.getYears(), pointsPolicy.getVictoryPoints(), pointsPolicy.getLossPoints(), pointsPolicy.getTiePoints());
    }

    public boolean addSeasonToTeam(Season season, Team team) {
        int seasonID = DBManager.getInstance().getSeasonId(season.getLeague().getName(), season.getYears());
        return DBManager.getInstance().addSeasonToTeam(seasonID, team.getTeamName());
    }

    public boolean seasonInTeam(Season season, Team team) {
        int seasonID = DBManager.getInstance().getSeasonId(season.getLeague().getName(), season.getYears());
        return DBManager.getInstance().isSeasonInTeam(seasonID, team.getTeamName());

    }

    public boolean removeSeasonFromTeam(Season season, Team team) {
        int seasonID = DBManager.getInstance().getSeasonId(season.getLeague().getName(), season.getYears());
        return DBManager.getInstance().removeSeasonInTeam(seasonID, team.getTeamName());
    }

    /*todo: check*/

    public List<Season> getAllSeasonInTeam(Team team) {
        List<Season> seasons = new ArrayList<>();
        List<HashMap<String, String>> seasonsDetails = DBManager.getInstance().getAllSeasonInTeam(team.getTeamName());
        for (int i = 0; i < seasonsDetails.size(); i++) {
            HashMap<String, String> seasonDetails = seasonsDetails.get(i);
            boolean isUnderWay = false;
            if (seasonDetails.get("is_under_way").equals("true")) {
                isUnderWay = true;
            }
            seasons.add(new Season(new League(seasonDetails.get("league_name"), false), seasonDetails.get("years"), new PointsPolicy(Integer.parseInt(seasonDetails.get("victory_points")), Integer.parseInt(seasonDetails.get("loss_points")), Integer.parseInt(seasonDetails.get("tie_points"))), isUnderWay));
        }
        return seasons;

    }

    public boolean isTeamManager(TeamManager teamManager, Team team) {
        return DBManager.getInstance().isTeamManager(teamManager.getSystemUser().getUsername(), team.getTeamName());
    }
/*
    public boolean removeTeamManager(TeamManager teamManager, Team team) {
        return DBManager.getInstance().removeTeamManager(teamManager.getSystemUser().getUsername() , team.getTeamName());

    }

 */

    public void updateTeamMangerPermission(TeamManager teamManager, List<TeamManagerPermissions> permissions, Team team) {
        List<String> permissionsToUpdate = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            permissionsToUpdate.add(permissions.get(i).name());
        }
        DBManager.getInstance().updateTeamMangerPermission(teamManager.getSystemUser().getUsername(), team.getTeamName(), permissionsToUpdate);
    }

    public List<TeamOwner> getTeamsOwners(Team team) {
        List<String> teamOwners = DBManager.getInstance().getTeamsOwners(team.getTeamName());
        List<SystemUser> systemUsers = new ArrayList<>();
        for (int i = 0; i < teamOwners.size(); i++) {
            SystemUser systemUser = this.getUser(teamOwners.get(i));
            systemUsers.add(systemUser);
        }
        List<TeamOwner> teamOwnerList = new ArrayList<>();
        for (int i = 0; i < teamOwners.size(); i++) {
            teamOwnerList.add((TeamOwner) systemUsers.get(i).getRole(RoleTypes.TEAM_OWNER));
        }
        return teamOwnerList;

    }

    public boolean removeTeamOwner(TeamOwner teamOwner, Team team) {
        return DBManager.getInstance().removeTeamOwner(teamOwner.getSystemUser().getUsername(), team.getTeamName());
    }


    public void updateTeamStatus(String teamName, TeamStatus status) {
        DBManager.getInstance().updateTeamStatus(teamName, status.name());
    }

    public List<Team> getTeamsPerSeason(Season season) {
        List<HashMap<String, String>> teamsInSeasonDetails = DBManager.getInstance().getTeamsPerSeason(season.getYears(), season.getLeague().getName());
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < teamsInSeasonDetails.size(); i++) {
            String teamName = teamsInSeasonDetails.get(i).get("name");
            TeamStatus teamStatus = TeamStatus.valueOf(teamsInSeasonDetails.get(i).get("status"));
            teams.add(new Team(teamName, teamStatus, false));
        }
        return teams;
    }

    public boolean removeRole(SystemUser systemUser, Role role) {
        RoleTypes roleType = role.getType();
        return DBManager.getInstance().removeRole(systemUser.getUsername(), roleType.name());
    }

    public boolean assignRefereeToSeason(Referee refereeRole, Season season) {
        return DBManager.getInstance().addRefereeToSeason(refereeRole.getSystemUser().getUsername(), season.getLeague().getName(), season.getYears());
    }


    public void cleaAll() {
        allUsers = new ArrayList<>();
        allLeagues = new HashSet<>();
        allTeams = new ArrayList<>();
        allStadiums = new ArrayList<>();
        //loggedInMap = new HashMap<>();
       // systemAdmins = new ArrayList<>();
        pointsPolicies = new ArrayList<>();
        schedulingPolicies = new ArrayList<>();
    }

    public List<Team> getTeamsManaged(TeamManager teamManager) {
        List<HashMap<String, String>> teams = DBManager.getInstance().getTeamManaged(teamManager.getSystemUser().getUsername());
        List<Team> teamsManaged = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            String teamName = teams.get(i).get("name");
            TeamStatus teamStatus = TeamStatus.valueOf(teams.get(i).get("status"));
            teamsManaged.add(new Team(teamName, teamStatus, false));
        }
        return teamsManaged;

    }

    public List<TeamManager> getTeamsManager(Team team) {
        List<String> getTeamsManager = DBManager.getInstance().getTeamManagers(team.getTeamName());
        List<TeamManager> teamManagers = new ArrayList<>();
        for (int i = 0; i < getTeamsManager.size(); i++) {
            SystemUser systemUser = getUser(getTeamsManager.get(i));
            TeamManager teamManager = (TeamManager) systemUser.getRole(RoleTypes.TEAM_MANAGER);
            teamManagers.add(teamManager);

        }
        return teamManagers;

    }

    public List<Stadium> getStadiumsInTeam(Team team) {
        List<HashMap<String, String>> stadiumsDetails = DBManager.getInstance().getStadiumsInTeam(team.getTeamName());
        List<Stadium> stadiums = new ArrayList<>();
        for (int i = 0; i < stadiumsDetails.size(); i++) {
            String stadiumName = stadiumsDetails.get(i).get("name");
            String stadiumLocation = stadiumsDetails.get(i).get("location");
            stadiums.add(new Stadium(stadiumName, stadiumLocation, false));
        }
        return stadiums;
    }

    public void removeStadiumFromTeam(Stadium stadium, Team team) {
        int stadiumID = DBManager.getInstance().getStadiumId(stadium.getName(), stadium.getLocation());
        DBManager.getInstance().removeStadiumFromTeam(stadiumID, team.getTeamName());
    }

    public List<Player> getAllPlayersInTeam(Team team) {
        List<String> getPlayers = DBManager.getInstance().getAllPlayersInTeam(team.getTeamName());
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < getPlayers.size(); i++) {
            SystemUser systemUser = getUser(getPlayers.get(i));
            Player player = (Player) systemUser.getRole(RoleTypes.PLAYER);
            players.add(player);

        }
        return players;
    }

    public List<Coach> getAllCoachesInTeam(Team team) {
        List<String> getTeamsManager = DBManager.getInstance().getAllCoachesInTeam(team.getTeamName());
        List<Coach> coaches = new ArrayList<>();
        for (int i = 0; i < getTeamsManager.size(); i++) {
            SystemUser systemUser = getUser(getTeamsManager.get(i));
            Coach coach = (Coach) systemUser.getRole(RoleTypes.COACH);
            coaches.add(coach);

        }
        return coaches;
    }

    public boolean updateStadiumName(Stadium stadium, String toChange) {
        return DBManager.getInstance().updateStadiumName(stadium.getName(), stadium.getLocation(), toChange);
    }

    public List<Team> getAllStadiumTeams(Stadium stadium) {
        List<HashMap<String, String>> teamsInStadium = DBManager.getInstance().getAllStadiumTeams(stadium.getName(), stadium.getLocation());
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < teamsInStadium.size(); i++) {
            String teamName = teamsInStadium.get(i).get("name");
            TeamStatus teamStatus = TeamStatus.valueOf(teamsInStadium.get(i).get("status"));
            teams.add(new Team(teamName, teamStatus, false));
        }
        return teams;
    }

    public void removeGameFromReferee(String refereeUsername, Game game) throws GameNotFoundException{
        Stadium gameStad = game.getStadium();
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();
        Date gameDate = game.getGameDate();
        boolean isFinished = game.hasFinished();
        DBManager.getInstance().removeGameFromReferee(refereeUsername,gameStad.getName(),gameStad.getLocation(),homeTeam.getTeamName(),awayTeam.getTeamName(),gameDate,isFinished);
    }

    public void addGameToReferee(String refereeUsername, Game game) {
        Stadium gameStad = game.getStadium();
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();
        Date gameDate = game.getGameDate();
        boolean isFinished = game.hasFinished();
        DBManager.getInstance().addGameToReferee(refereeUsername,gameStad.getAssetName(), gameStad.getLocation(), homeTeam.getTeamName(),awayTeam.getTeamName(), gameDate, isFinished);

    }


    /*TODO CHECK*/

    public List<TeamManagerPermissions> getAllPermissionsPerTeam(Team team, TeamManager teamManager) {
        List<String> permissions = DBManager.getInstance().getAllPermissionsPerTeam(team.getTeamName(), teamManager.getSystemUser().getUsername());
        List<TeamManagerPermissions> teamManagerPermissions = new ArrayList<>();
        if (permissions.contains(TeamManagerPermissions.ADD_COACH.name())) {
            teamManagerPermissions.add(TeamManagerPermissions.ADD_COACH);
        }
        if (permissions.contains(TeamManagerPermissions.ADD_PLAYER.name())) {
            teamManagerPermissions.add(TeamManagerPermissions.ADD_PLAYER);
        }
        if (permissions.contains(TeamManagerPermissions.REMOVE_COACH.name())) {
            teamManagerPermissions.add(TeamManagerPermissions.REMOVE_COACH);
        }
        if (permissions.contains(TeamManagerPermissions.REMOVE_PLAYER.name())) {
            teamManagerPermissions.add(TeamManagerPermissions.REMOVE_PLAYER);
        }
        if (permissions.contains(TeamManagerPermissions.CHANGE_POSITION_PLAYER.name())) {
            teamManagerPermissions.add(TeamManagerPermissions.CHANGE_POSITION_PLAYER);
        }
        if (permissions.contains(TeamManagerPermissions.CHANGE_TEAM_JOB_COACH.name())) {
            teamManagerPermissions.add(TeamManagerPermissions.CHANGE_TEAM_JOB_COACH);
        }
        return teamManagerPermissions;
    }

    public SystemUser getAppointedOwner(Team ownedTeam, TeamOwner teamOwner) {
        String systemUser = DBManager.getInstance().getAppointedOwner(ownedTeam.getTeamName(), teamOwner.getSystemUser().getUsername());
        return getUser(systemUser);
    }

    public void saveAlert(String username, String alert) {
        DBManager.getInstance().saveAlert(username,alert);
    }

    public List<Game> getSeasonGames(Season season) {
        List<Game> seasonGames = new ArrayList<>();

        List<HashMap<String, String>> seasonGamesDetails = DBManager.getInstance().getSeasonGamesDetails(season.getLeague().getName(), season.getYears());

        for (int i = 0; i < seasonGamesDetails.size(); i++) {
            HashMap<String, String> currentGameDetails = seasonGamesDetails.get(i);
            seasonGames.add(reCreateGame(currentGameDetails));
        }
        //return a copy
        return seasonGames;
    }

    private Game reCreateGame(HashMap<String, String> gameDetails) {
        Stadium stadium = getStadium(gameDetails.get("stadium_name"));
        Team homeTeam = this.getTeam(gameDetails.get("home_team"));
        Team awayTeam = this.getTeam(gameDetails.get("away_team"));

        Date startDate = convertStringToDate(gameDetails.get("start_date"));
        Date endDate = convertStringToDate(gameDetails.get("end_date"));

        boolean finishedString = Boolean.parseBoolean(gameDetails.get("away_team"));


        Game game = new Game(stadium, homeTeam, awayTeam, startDate,/*Check*/new ArrayList<>(), false);
        if (endDate != null) {
            game.setEndDate(endDate);
        }
        return game;
    }

    private Date convertStringToDate(String dateString) {
        if (dateString != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = null;
            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    public boolean removeGamesFromSeason(Season season) {
        return DBManager.getInstance().removeGamesFromSeason(season.getLeague().getName(), season.getYears());
    }

    public void addRefereeToGame(Referee referee, Game game) {
        DBManager.getInstance().addRefereeToGame(referee.getSystemUser().getUsername(), game.getStadium().getName(), game.getStadium().getLocation(), game.getHomeTeam().getTeamName(), game.getAwayTeam().getTeamName());
        //referee_in_game
    }

    public void addGameToSeason(Season season, Game game) {
        DBManager.getInstance().addGameToSeason(season.getLeague().getName(), season.getYears(), game.getStadium().getName(), game.getStadium().getLocation(), game.getHomeTeam().getTeamName(), game.getAwayTeam().getTeamName());
        //game_in_season
    }

    public List<Referee> getSeasonReferees(Season season) {
        List<Referee> seasonReferees = new ArrayList<>();

        List<String> refereesUsernames = DBManager.getInstance().getRefereesUsernamesOfSeason(season.getLeague().getName(), season.getYears());
        for (String refereeUsername : refereesUsernames) {
            seasonReferees.add((Referee) getUser(refereeUsername).getRole(RoleTypes.REFEREE));
        }
        return seasonReferees;
    }

    public List<Season> getRefereeSeasons(Referee referee) {
        List<Season> refereeSeasons = new ArrayList<>();
        List<HashMap<String, String>> refereeSeasonsDetails = DBManager.getInstance().getRefereeSeasonsDetails(referee.getSystemUser().getUsername());
        List<League> leagues = getLeagues();
        for (HashMap<String, String> seasonsDetails : refereeSeasonsDetails) {
            for (League league : leagues) {
                if (league.equals(seasonsDetails.get("league_name"))) {
                    for (Season season : league.getSeasons()) {
                        if (season.getYears().equals(seasonsDetails.get("years"))) {
                            refereeSeasons.add(season);
                            break;
                        }
                    }
                }
            }
        }
        return refereeSeasons;

    }

    /**
     * Get all referees in a game
     * @param game the specific game to retrieve its referees
     * @return List of all the referees in the game
     */
    public List<Referee> getAllRefereesInGame(Game game) {
        List<String> refereesUsernames = DBManager.getInstance().getAllRefereesInGame(game.getStadium().getName(),game.getStadium().getLocation(),
                                                                                        game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),
                                                                                        game.getGameDate());
        List<Referee> referees = new ArrayList<>();
        for (String username:
                refereesUsernames)
        {
            SystemUser systemUser = this.getUser(username);
            Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);
            referees.add(referee);
        }

        return referees;
    }

    public boolean updateEndGame(Game game, Date endDate) {
        return DBManager.getInstance().updateEndGame(game.getStadium().getName(),game.getStadium().getLocation(),
                game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),endDate);
    }

    public boolean updateCardEvent(Game game, String cardType, String username, int minute) {
        return DBManager.getInstance().updateCardEvent(game.getStadium().getName(),game.getStadium().getLocation(),
                game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),cardType, username,minute);
    }

    public boolean updateGoalEvent(Game game, Team scoringTeam, Team scoredOnTeam, String scorrerUsername, int minute) {
        return DBManager.getInstance().updateGoalEvent(game.getStadium().getName(),game.getStadium().getLocation(),
                game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),scoringTeam.getTeamName(),scoredOnTeam.getTeamName(),scorrerUsername,minute);
    }

    public boolean updateOffsideEvent(Game game, Team teamWhoCommitted, int minute) {
        return DBManager.getInstance().updateOffsideEvent(game.getStadium().getName(),game.getStadium().getLocation(),
                game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),teamWhoCommitted.getTeamName(),minute);
    }

    public boolean updatePenaltyEvent(Game game, Team teamWhoCommitted, int minute) {
        return DBManager.getInstance().updatePenaltyEvent(game.getStadium().getName(),game.getStadium().getLocation(),
                game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),teamWhoCommitted.getTeamName(),minute);
    }

    public boolean updateSwitchEvent(Game game, Team teamWhoCommitted, Player enteringPlayer, Player exitingPlayer, int minute) {
        return DBManager.getInstance().updateSwitchPlayerEvent(game.getStadium().getName(),game.getStadium().getLocation(),
                game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),teamWhoCommitted.getTeamName(),
                enteringPlayer.getSystemUser().getUsername(),exitingPlayer.getSystemUser().getUsername(),minute);
    }

    public boolean addInjuryEvent(Game game, Player player, int minute) {
        return DBManager.getInstance().addInjuryEvent(game.getStadium().getName(),game.getStadium().getLocation(),
                game.getHomeTeam().getTeamName(),game.getAwayTeam().getTeamName(),player.getSystemUser().getUsername(),minute);
    }
}