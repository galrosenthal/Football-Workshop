package DB;

import static DB.Tables.Tables.*;

import DB.Tables.enums.TeamStatus;
import DB.Tables.enums.CoachQualification;
import DB.Tables.enums.RefereeTraining;
import DB.Tables.enums.UserRolesRoleType;

import Domain.Exceptions.UserNotFoundException;
import javafx.util.Pair;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DBManager {

    private static DBManager dbManagerInstance = null;

    /**
     * Constructor
     */
    protected DBManager() {
    }


    /**
     * Returns an instance of dbManager. part of the Singleton design
     *
     * @return - DBManager - an instance of dbManager
     */
    public static DBManager getInstance() {
        if (dbManagerInstance == null)
            dbManagerInstance = new DBManager();
        return dbManagerInstance;
    }

    /**
     * Returns an instance of dbManagerForTest. part of the Singleton design
     *
     * @return - DBManager - an instance of dbManager
     */
    public static DBManager startTest() {
        if (dbManagerInstance == null)
            dbManagerInstance = DBManagerForTest.getInstance();
        return dbManagerInstance;
    }

    /**
     * clear all records from DB
     *
     * @throws Exception
     */
    public static void deleteData(String dbName) throws Exception {
        DBHandler.getInstance().deleteData(dbName);

    }

    public List<Pair<String, String>> getTeams(String name) {
        List<String> teamsName = new ArrayList<>();
        List<TeamStatus> statues = new ArrayList<>();
        List<Pair<String, String>> teams = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select()
                .from(OWNED_TEAMS.where(OWNED_TEAMS.USERNAME.eq(name)).join(TEAM)
                        .on(TEAM.NAME.eq(OWNED_TEAMS.TEAM_NAME)))
                .fetch();
        teamsName = result.getValues(OWNED_TEAMS.TEAM_NAME);
        statues = result.getValues(TEAM.STATUS);
        for (int i = 0; i < teamsName.size(); i++) {
            Pair<String, String> pair = new Pair(teamsName.get(i), statues.get(i));
            teams.add(pair);
        }
        return teams;
    }

    public List<String> getStadium(String stadiumName) {
        DSLContext create = DBHandler.getContext();
        List<String> stadium;
        Result<?> result = create.select()
                .from(STADIUM.where(STADIUM.NAME.eq(stadiumName))).fetch();
        if (result.size() == 0) {
            return null;
        }
        stadium = result.getValues(STADIUM.NAME);
        stadium.addAll(result.getValues(STADIUM.LOCATION));
        return stadium;
    }


    /**
     * start connection to DB
     *
     * @param url - to DB
     */
    public void startConnection(String url) {

        DBHandler.startConnection(url);
    }

    /**
     * close connection to DB
     */
    public void closeConnection() {
        DBHandler.closeConnection();
    }

    public static void startConnection() {
        DBHandler.startConnection("jdbc:mysql://132.72.65.105:3306/fwdb");
    }

    /**
     * Saves all the tables to their original files
     *
     * @return - boolean - true if all the tables have been saved successfully, else false
     */
    public boolean close() {
        boolean finished = true;
        //save all the changes to files.


        if (finished) {
            return true;
        }
        return false;
    }

    /**
     * @param name - league name
     * @return true if league name exists in table -league, false otherwise
     */
    public boolean doesLeagueExists(String name) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(LEAGUE)
                .where(LEAGUE.NAME.eq(name)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Add league record
     *
     * @param name - league name to add
     * @return true - added successfully
     * false - otherwise
     */

    public boolean addLeagueRecord(String name) {
        DSLContext dslContext = DBHandler.getContext();
        //todo: check!!!!
        int succeed = dslContext.insertInto(LEAGUE, LEAGUE.NAME).values(name).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    /**
     * Get all leagues name from DB
     *
     * @return - List<String> - Leagues name
     */
    public List<String> getLeagues() {
        List<String> leaguesName = new ArrayList<>();
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(LEAGUE).fetch();

        leaguesName = result.getValues(LEAGUE.NAME);
        return leaguesName;
    }

    /**
     * Check if season Exists in DB
     *
     * @param leagueName
     * @param seasonYears
     * @return true - if  season Exists
     * false - otherwise
     */
    public boolean doesSeasonExists(String leagueName, String seasonYears) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(SEASON)
                .where(SEASON.LEAGUE_NAME.eq(leagueName)).and(SEASON.YEARS.eq(leagueName)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }


    /**
     * get Points Policy ID fom DB
     *
     * @param victoryPoints
     * @param lossPoints
     * @param tiePoints
     * @return int  - Points Policy ID
     */
    public int getPointsPolicyID(int victoryPoints, int lossPoints, int tiePoints) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(POINTS_POLICY)
                .where(POINTS_POLICY.VICTORY_POINTS.eq(victoryPoints)).and(POINTS_POLICY.LOSS_POINTS.eq(lossPoints).and(POINTS_POLICY.TIE_POINTS.eq(tiePoints))).fetch();
        if (result.isEmpty()) {
            return -1;
        }
        return result.get(0).indexOf(POINTS_POLICY.POLICY_ID);
    }

    /**
     * add Season To League
     *
     * @param leagueName
     * @param years
     * @param isUnderway
     * @param pointsPolicyID
     * @return true - added successfully
     * false - otherwise
     */
    public boolean addSeasonToLeague(String leagueName, String years, boolean isUnderway, int pointsPolicyID) {
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(SEASON, SEASON.LEAGUE_NAME, SEASON.YEARS,
                SEASON.IS_UNDER_WAY, SEASON.POINTS_POLICY_ID)
                .values(leagueName, years, isUnderway, pointsPolicyID).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    /**
     * Adds a new record to the Points_Policy table.
     *
     * @param victoryPoints - int - the value to be inserted into the VICTORY_POINTS field
     * @param lossPoints    - int - the value to be inserted into the VICTORY_POINTS field
     * @param tiePoints     - int - the value to be inserted into the VICTORY_POINTS field
     * @return - boolean - true if the new record was added successfully, else false
     */
    public boolean addPointsPolicy(int victoryPoints, int lossPoints, int tiePoints) {
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(POINTS_POLICY,
                POINTS_POLICY.VICTORY_POINTS, POINTS_POLICY.LOSS_POINTS,
                POINTS_POLICY.TIE_POINTS)
                .values(victoryPoints, lossPoints, tiePoints).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    public List<Pair<String, String>> getUser(String username) throws UserNotFoundException {
        Result<?> user = getSystemUser(username);
        if (user == null) {
            throw new UserNotFoundException("A user with the given username dose't exists");
        }

        List<Pair<String, String>> userDetails = new ArrayList<>();

        for (int i = 0; i < user.fields().length; i++) {
            String fieldName = ((Field) (user.fields()[i])).getName();
            String fieldValue = user.getValues(i).get(0).toString();
            Pair<String, String> pair = new Pair<>(fieldName, fieldValue);
            userDetails.add(pair);
        }

        return userDetails;
    }

    private Result<?> getSystemUser(String username) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(SYSTEMUSER)
                .where(SYSTEMUSER.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public boolean addUser(String username, String name, String password, String email, boolean alertEmail) {
        DSLContext dslContext = DBHandler.getContext();
        try {
             dslContext.insertInto(SYSTEMUSER,
                    SYSTEMUSER.USERNAME, SYSTEMUSER.NAME, SYSTEMUSER.PASSWORD,
                    SYSTEMUSER.EMAIL, SYSTEMUSER.NOTIFY_BY_EMAIL)
                    .values(username, name, password, email, alertEmail).execute();
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap<String, List<Pair<String, String>>> getUserRoles(String username) {
        List<String> userRolesTypes = getUserRolesTypes(username);
        if(userRolesTypes  == null)
        {
            return new HashMap<>();
        }
        HashMap<String, List<Pair<String, String>>> userRoles = new HashMap<>();
        for (String roleType : userRolesTypes) {
            List<Pair<String, String>> roleDetails = null;
            switch (roleType) {
                case "PLAYER":
                    roleDetails = getPlayerDetails(username);
                    break;
                case "COACH":
                    roleDetails = getCoachDetails(username);
                    break;
                case "TEAM_MANAGER":
                    roleDetails = getTeamMangerDetails(username);
                    break;
                case "TEAM_OWNER":
                    roleDetails = getTeamOwnerDetails(username);
                    break;
                case "SYSTEM_ADMIN":
                    roleDetails = getSystemAdminDetails(username);
                    break;
                case "REFEREE":
                    roleDetails = getRefereeDetails(username);
                    break;
                case "ASSOCIATION_REPRESENTATIVE":
                    roleDetails = getARRoleDetails(username);
                    break;
            }
            userRoles.put(roleType, roleDetails);
        }

        return userRoles;
    }

    private List<Pair<String, String>> getARRoleDetails(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select().
                from(ASSOCIATION_REPRESENTATIVE)
                .where(ASSOCIATION_REPRESENTATIVE.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return getDetails(result);
    }

    private List<Pair<String, String>> getRefereeDetails(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select().
                from(REFEREE)
                .where(REFEREE.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return getDetails(result);
    }

    private List<Pair<String, String>> getSystemAdminDetails(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select().
                from(SYSTEM_ADMIN)
                .where(SYSTEM_ADMIN.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return getDetails(result);
    }

    private List<Pair<String, String>> getTeamOwnerDetails(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select().
                from(TEAM_OWNER)
                .where(TEAM_OWNER.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return getDetails(result);
    }

    private List<Pair<String, String>> getTeamMangerDetails(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select().
                from(TEAM_MANAGER)
                .where(TEAM_MANAGER.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return getDetails(result);
    }


    private List<Pair<String, String>> getCoachDetails(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select().
                from(COACH)
                .where(COACH.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return getDetails(result);
    }

    private List<Pair<String, String>> getPlayerDetails(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select().
                from(PLAYER)
                .where(PLAYER.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        return getDetails(result);
    }

    private List<Pair<String, String>> getDetails(Result<?> user) {
        List<Pair<String, String>> details = new ArrayList<>();
        for (int i = 0; i < user.fields().length; i++) {
            String fieldName = user.fields()[i].getName();
            String fieldValue = user.getValues(i).get(0).toString();
            Pair<String, String> pair = new Pair<>(fieldName, fieldValue);
            details.add(pair);
        }

        return details;

    }


    private List<String> getUserRolesTypes(String username) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(USER_ROLES)
                .where(USER_ROLES.USERNAME.eq(username)).fetch();
        if (result.isEmpty()) {
            return null;
        }
        List<String> rolesTypes = new ArrayList<>();
        for (int i = 0; i < result.fields().length; i++) {
            rolesTypes.add(result.getValues(i).get(1).toString());
        }
        return rolesTypes;
    }


    public void addPlayer(String username, Date bday) {
        DSLContext create = DBHandler.getContext();
        //todo: check!!!!
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.PLAYER).execute();

        create.insertInto(PLAYER, PLAYER.USERNAME , PLAYER.BIRTHDAY).values(username, LocalDate.ofEpochDay(bday.getTime())).execute();

    }

    public void addCoach(String username, String qualification) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.COACH).execute();

        create.insertInto(COACH, COACH.USERNAME , COACH.QUALIFICATION).values(username, CoachQualification.valueOf(qualification)).execute();

    }

    public void addTeamManager(String username) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_MANAGER).execute();

        create.insertInto(TEAM_MANAGER, TEAM_MANAGER.USERNAME).values(username).execute();


    }

    public void addTeamOwner(String username) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_OWNER).execute();

        create.insertInto(TEAM_OWNER, TEAM_OWNER.USERNAME).values(username).execute();


    }

    public void addSystemAdmin(String username) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.SYSTEM_ADMIN).execute();

        create.insertInto(SYSTEM_ADMIN, SYSTEM_ADMIN.USERNAME).values(username).execute();


    }

    public void addReferee(String username, String training) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.REFEREE).execute();

        create.insertInto(REFEREE, REFEREE.USERNAME , REFEREE.TRAINING).values(username, RefereeTraining.valueOf(training)).execute();


    }

    public void addAssociationRepresentative(String username) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.ASSOCIATION_REPRESENTATIVE).execute();

        create.insertInto(ASSOCIATION_REPRESENTATIVE, ASSOCIATION_REPRESENTATIVE.USERNAME).values(username).execute();


    }

    public boolean addTeam(String teamName, String teamStatus) {
        try{
            DSLContext create = DBHandler.getContext();
            create.insertInto(TEAM, TEAM.NAME,TEAM.STATUS).values(teamName,TeamStatus.valueOf(teamStatus));
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

