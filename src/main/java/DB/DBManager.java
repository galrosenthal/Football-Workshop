package DB;

import static DB.Tables.Tables.*;
import static org.jooq.impl.DSL.row;

import DB.Tables.enums.TeamStatus;
import DB.Tables.enums.UserRolesRoleType;

import DB.Tables.enums.CoachQualification;
import DB.Tables.enums.RefereeTraining;
import DB.Tables.tables.RefereeInGame;
import Domain.Exceptions.UserNotFoundException;
import Domain.Pair;
import Domain.Users.SystemUser;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;

import java.time.LocalDate;
import java.time.ZoneId;
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

    /**
     * @param teamOwner
     * @return
     */
    public List<Pair<String, String>> getTeams(String teamOwner) {
        List<String> teamsName;
        List<TeamStatus> statues;
        List<Pair<String, String>> teams = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select()
                .from(OWNED_TEAMS.where(OWNED_TEAMS.USERNAME.eq(teamOwner)).join(TEAM)
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
                .where(SEASON.LEAGUE_NAME.eq(leagueName)).and(SEASON.YEARS.eq(seasonYears)).fetch();
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
        return result.get(0).getValue(POINTS_POLICY.POLICY_ID);

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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap<String, List<Pair<String, String>>> getUserRoles(String username) {
        List<String> userRolesTypes = getUserRolesTypes(username);
        if (userRolesTypes == null) {
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
            rolesTypes.add(result.getValues(i).get(0).toString());
        }
        return rolesTypes;
    }


    private boolean hasRole(String username, String role) {
        List<String> userRoles = getUserRolesTypes(username);
        if (userRoles == null) {
            return false;
        }
        if (userRoles.contains(role)) {
            return true;
        }
        return false;
    }

    public void addPlayer(String username, Date bday) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "PLAYER"))) {

            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username,UserRolesRoleType.PLAYER).execute();
            create.insertInto(PLAYER, PLAYER.USERNAME, PLAYER.BIRTHDAY).values(username,this.convertToLocalDateViaInstant(bday)).execute();
        }
    }

    public void addCoach(String username, String qualification) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "COACH"))) {
            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.COACH).execute();
            if (qualification != null) {
                create.insertInto(COACH, COACH.USERNAME, COACH.QUALIFICATION).values(username, CoachQualification.valueOf(qualification)).execute();
            } else {
                create.insertInto(COACH, COACH.USERNAME, COACH.QUALIFICATION).values(username, null).execute();
            }
        }
    }

    public void addTeamManager(String username) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "TEAM_MANAGER"))) {
            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_MANAGER).execute();
            create.insertInto(TEAM_MANAGER, TEAM_MANAGER.USERNAME).values(username).execute();
        }
    }

    public void addTeamOwner(String username) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "TEAM_OWNER"))) {
            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_OWNER).execute();
            create.insertInto(TEAM_OWNER, TEAM_OWNER.USERNAME).values(username).execute();
        }
    }

    public void addSystemAdmin(String username) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "SYSTEM_ADMIN"))) {
            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.SYSTEM_ADMIN).execute();
            create.insertInto(SYSTEM_ADMIN, SYSTEM_ADMIN.USERNAME).values(username).execute();
        }
    }

    public void addReferee(String username, String training) {
        DSLContext create = DBHandler.getContext();
        //todo: check!!!!
        if (!(hasRole(username, "REFEREE"))) {
            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.REFEREE).execute();
            if (training != null) {
                create.insertInto(REFEREE, REFEREE.USERNAME, REFEREE.TRAINING).values(username, RefereeTraining.valueOf(training)).execute();
            } else {
                create.insertInto(REFEREE, REFEREE.USERNAME, REFEREE.TRAINING).values(username, null).execute();
            }
        }
    }

    public void addAssociationRepresentative(String username) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.ASSOCIATION_REPRESENTATIVE).execute();

        create.insertInto(ASSOCIATION_REPRESENTATIVE, ASSOCIATION_REPRESENTATIVE.USERNAME).values(username).execute();


    }

    public boolean addTeam(String teamName, String teamStatus) {
        try {
            DSLContext create = DBHandler.getContext();
            create.insertInto(TEAM, TEAM.NAME, TEAM.STATUS).values(teamName, TeamStatus.valueOf(teamStatus)).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isTeamOwner(String name, String teamName) {
        DSLContext create = DBHandler.getContext();
        List<String> stadium;
        Result<?> result = create.select()
                .from(OWNED_TEAMS.where(OWNED_TEAMS.USERNAME.eq(name).and(OWNED_TEAMS.TEAM_NAME.eq(teamName)))).fetch();
        if (result.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean addTeamMangerToTeam(String teamName, String username, String usernameAppointed) {
        try {
            DSLContext create = DBHandler.getContext();
            create.insertInto(MANAGER_IN_TEAMS, MANAGER_IN_TEAMS.TEAM_NAME, MANAGER_IN_TEAMS.USERNAME, MANAGER_IN_TEAMS.APPOINTER).values(teamName, username, usernameAppointed).execute();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addStadiumToTeam(String name, String location, String teamName) {
        int stadiumId = getStadiumId(name, location);
        try {
            DSLContext create = DBHandler.getContext();
            create.insertInto(STADIUM_HOME_TEAMS, STADIUM_HOME_TEAMS.STADIUM_ID, STADIUM_HOME_TEAMS.TEAM_NAME).values(stadiumId, teamName).execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private int getStadiumId(String name, String location) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(STADIUM).where(STADIUM.LOCATION.eq(location)).and(STADIUM.NAME.eq(name)).fetch();
        return records.getValues(STADIUM.STADIUM_ID).get(0);
    }

    public boolean addTeamConnection(String teamName, String type, String username) {
        try {
            DSLContext create = DBHandler.getContext();
            if (type.equals("PLAYER")) {
                create.insertInto(PLAYER_IN_TEAM, PLAYER_IN_TEAM.TEAM_NAME, PLAYER_IN_TEAM.USERNAME).values(teamName, username).execute();
                return true;
            } else if (type.equals("COACH")) {
                create.insertInto(COACH_IN_TEAM, COACH_IN_TEAM.TEAM_NAME, COACH_IN_TEAM.USERNAME).values(teamName, username).execute();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean updateTeamName(String teamName, String testName) {
        try {
            DSLContext create = DBHandler.getContext();
            create.update(TEAM).set(TEAM.NAME, testName).where(TEAM.NAME.eq(teamName)).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setTeamOwnerAppointed(String username, String teamName, String appointed) {
        try {
            DSLContext create = DBHandler.getContext();
            create.update(OWNED_TEAMS).set(OWNED_TEAMS.APPOINTER, appointed).where(OWNED_TEAMS.USERNAME.eq(username).and(OWNED_TEAMS.TEAM_NAME.eq(teamName))).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addTeamOwnerToTeam(String username, String teamName) {
        try {
            DSLContext create = DBHandler.getContext();
            create.insertInto(OWNED_TEAMS, OWNED_TEAMS.TEAM_NAME, OWNED_TEAMS.USERNAME, OWNED_TEAMS.APPOINTER).values(teamName, username, null).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HashMap<String, String>> getLeagueSeasons(String leagueName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(SEASON).where(SEASON.LEAGUE_NAME.eq(leagueName)).fetch();

        List<HashMap<String, String>> seasonsDetails = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            HashMap<String, String> currentSeasonDetails = new HashMap<>();
            for (int j = 0; j < records.fields().length; j++) {
                String fieldName = records.get(i).fields()[j].getName();
                String fieldValue = records.get(i).getValue(fieldName).toString();
                if (fieldName.equals("points_policy_id")) {
                    HashMap<String, String> pointsPolicyDetails = getPointsPolicyByID(Integer.parseInt(fieldValue));
                    currentSeasonDetails.putAll(pointsPolicyDetails);
                } else {
                    currentSeasonDetails.put(fieldName, fieldValue);
                }
            }
            seasonsDetails.add(currentSeasonDetails);
        }
        return seasonsDetails;
    }

    public HashMap<String, String> getPointsPolicyByID(int policyID) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(POINTS_POLICY).where(POINTS_POLICY.POLICY_ID.eq(policyID)).fetch();
        HashMap<String, String> pointsPolicyDetails = new HashMap<>();
        for (int i = 0; i < records.fields().length; i++) {
            String fieldName = records.fields()[i].getName();
            String fieldValue = records.getValues(i).get(0).toString();
            pointsPolicyDetails.put(fieldName, fieldValue + "");
        }
        return pointsPolicyDetails;
    }

    public List<HashMap<String, String>> getRefereeGames(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(REFEREE_IN_GAME.join(GAME).on(GAME.GAME_ID.eq(REFEREE_IN_GAME.GAME_ID))).where(REFEREE_IN_GAME.USERNAME.eq(username)).fetch();

        return getDetailsFromResult(records);
    }

    public void unAssignRefereeFromAllSeasons(String username) {
        DSLContext create = DBHandler.getContext();
        create.delete(REFEREE_IN_SEASON)
                .where(REFEREE_IN_SEASON.USERNAME.eq(username))
                .execute();
    }

    public boolean doesPointsPolicyExists(int victoryPoints, int lossPoints, int tiePoints) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(POINTS_POLICY)
                .where(POINTS_POLICY.VICTORY_POINTS.eq(victoryPoints))
                .and(POINTS_POLICY.LOSS_POINTS.eq(lossPoints)
                        .and(POINTS_POLICY.TIE_POINTS.eq(tiePoints))).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<HashMap<String, String>> getPointsPolicies() {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(POINTS_POLICY).fetch();

        return getDetailsFromResult(records);
    }

    private List<HashMap<String, String>> getDetailsFromResult(Result<?> records) {
        List<HashMap<String, String>> pointsPoliciesDetails = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            HashMap<String, String> currentPointsPoliciesDetails = new HashMap<>();
            for (int j = 0; j < records.fields().length; j++) {
                String fieldName = records.get(i).fields()[j].getName();
                String fieldValue = records.get(i).getValue(fieldName).toString();
                currentPointsPoliciesDetails.put(fieldName, fieldValue);
            }
            pointsPoliciesDetails.add(currentPointsPoliciesDetails);
        }
        return pointsPoliciesDetails;
    }

    public boolean setPointsPolicy(String leagueName, String years, int victoryPoints, int lossPoints, int tiePoints) {
        DSLContext dslContext = DBHandler.getContext();
        int pointsPolicyID = getPointsPolicyID(victoryPoints, lossPoints, tiePoints);

        int succeed = dslContext.update(SEASON)
                .set(SEASON.POINTS_POLICY_ID, pointsPolicyID)
                .where(SEASON.LEAGUE_NAME.eq(leagueName)).and(SEASON.YEARS.eq(years)).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    public boolean doesSchedulingPolicyExists(int gamesPerSeason, int gamesPerDay, int minRest) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(SCHEDULING_POLICY)
                .where(SCHEDULING_POLICY.GAMES_PER_SEASON.eq(gamesPerSeason))
                .and(SCHEDULING_POLICY.GAMES_PER_DAY.eq(gamesPerDay)
                        .and(SCHEDULING_POLICY.MINIMUM_REST_DAYS.eq(minRest))).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean addSchedulingPolicy(int gamesPerSeason, int gamesPerDay, int minimumRestDays) {
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(SCHEDULING_POLICY,
                SCHEDULING_POLICY.GAMES_PER_SEASON, SCHEDULING_POLICY.GAMES_PER_DAY,
                SCHEDULING_POLICY.MINIMUM_REST_DAYS)
                .values(gamesPerSeason, gamesPerDay, minimumRestDays).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    public int getSeasonId(String name, String years) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(SEASON)
                .where(SEASON.LEAGUE_NAME.eq(name)).and(SEASON.YEARS.eq(years)).fetch();
        if (result.isEmpty()) {
            return -1;
        }
        return result.get(0).getValue(SEASON.SEASON_ID);
    }

    public boolean addSeasonToTeam(int seasonID, String teamName) {
        DSLContext create = DBHandler.getContext();
        try {
            create.insertInto(TEAMS_IN_SEASON, TEAMS_IN_SEASON.SEASON_ID, TEAMS_IN_SEASON.TEAM_NAME).values(seasonID, teamName).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSeasonInTeam(int seasonID, String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(TEAMS_IN_SEASON).where(TEAMS_IN_SEASON.SEASON_ID.eq(seasonID).and(TEAMS_IN_SEASON.TEAM_NAME.eq(teamName))).fetch();
        if (records.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean removeSeasonInTeam(int seasonID, String teamName) {
        DSLContext create = DBHandler.getContext();
        try {
            create.deleteFrom(TEAMS_IN_SEASON).where(TEAMS_IN_SEASON.SEASON_ID.eq(seasonID).and(TEAMS_IN_SEASON.TEAM_NAME.eq(teamName)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HashMap<String, String>> getAllSeasonInTeam(String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(TEAMS_IN_SEASON).where(TEAMS_IN_SEASON.TEAM_NAME.eq(teamName)).fetch();
        List<HashMap<String, String>> seasonsDetails = new ArrayList<>();
        //Add loop
        for (int i = 0; i < records.size(); i++) {
            for (int j = 0; j < records.fields().length; j++) {
                HashMap<String, String> currentSeasonDetails = new HashMap<>();
                String fieldName = records.get(i).fields()[j].getName();
                String fieldValue = records.get(i).getValue(fieldName).toString();
                if (fieldName.equals("points_policy_id")) {
                    HashMap<String, String> pointsPolicyDetails = getPointsPolicyByID(Integer.parseInt(fieldValue));
                    currentSeasonDetails.putAll(pointsPolicyDetails);
                } else {
                    currentSeasonDetails.put(fieldName, fieldValue);
                }
            }
        }
        return seasonsDetails;
    }

    public boolean isTeamManager(String username, String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(MANAGER_IN_TEAMS).where(MANAGER_IN_TEAMS.USERNAME.eq(username).and(MANAGER_IN_TEAMS.TEAM_NAME.eq(teamName))).fetch();
        if (records.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean removeTeamManager(String username, String teamName) {
        DSLContext create = DBHandler.getContext();
        try {
            create.deleteFrom(MANAGER_IN_TEAMS).where(MANAGER_IN_TEAMS.USERNAME.eq(username).and(MANAGER_IN_TEAMS.TEAM_NAME.eq(teamName))).execute();
            if (this.isTeamMangerInOtherTeam(username)) {
                create.deleteFrom(USER_ROLES).where(USER_ROLES.USERNAME.eq(username).and(USER_ROLES.ROLE_TYPE.eq(UserRolesRoleType.TEAM_MANAGER))).execute();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean isTeamMangerInOtherTeam(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(MANAGER_IN_TEAMS).where(MANAGER_IN_TEAMS.USERNAME.eq(username)).fetch();
        if (records.size() != 0) {
            return true;
        } else {
            return false;
        }
    }


    public void updateTeamMangerPermission(String username, String teamName, List<String> permissions) {
        /*    REMOVE_PLAYER,ADD_PLAYER,CHANGE_POSITION_PLAYER,REMOVE_COACH,ADD_COACH,CHANGE_TEAM_JOB_COACH; */
        boolean remove_player = permissions.contains("REMOVE_PLAYER");
        boolean add_player = permissions.contains("ADD_PLAYER");
        boolean change_position_player = permissions.contains("CHANGE_POSITION_PLAYER");
        boolean remove_coach = permissions.contains("REMOVE_COACH");
        boolean add_coach = permissions.contains("ADD_COACH");
        boolean change_team_job_coach = permissions.contains("CHANGE_TEAM_JOB_COACH");
        try {
            DSLContext create = DBHandler.getContext();
            create.update(MANAGER_IN_TEAMS).set(row(MANAGER_IN_TEAMS.REMOVE_PLAYER, MANAGER_IN_TEAMS.ADD_PLAYER,
                    MANAGER_IN_TEAMS.CHANGE_POSITION_PLAYER, MANAGER_IN_TEAMS.REMOVE_COACH, MANAGER_IN_TEAMS.ADD_COACH,
                    MANAGER_IN_TEAMS.CHANGE_TEAM_JOB_COACH), row(remove_player, add_player, change_position_player,
                    remove_coach, add_coach ,change_team_job_coach )).where(MANAGER_IN_TEAMS.TEAM_NAME.eq(teamName)
                    .and(MANAGER_IN_TEAMS.USERNAME.eq(username))).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public List<String> getTeamsOwners(String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(OWNED_TEAMS).where(OWNED_TEAMS.TEAM_NAME.eq(teamName)).fetch();
        List<String> teamOwners;
        teamOwners = records.getValues(OWNED_TEAMS.USERNAME);
        return teamOwners;
    }

    public boolean removeTeamOwner(String username, String teamName) {
        DSLContext create = DBHandler.getContext();
        try {
            create.deleteFrom(OWNED_TEAMS).where(OWNED_TEAMS.USERNAME.eq(username).and(OWNED_TEAMS.TEAM_NAME.eq(teamName))).execute();
            if (this.isTeamOwnedOtherTeam(username)) {
                create.deleteFrom(USER_ROLES).where(USER_ROLES.USERNAME.eq(username).and(USER_ROLES.ROLE_TYPE.eq(UserRolesRoleType.TEAM_OWNER))).execute();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isTeamOwnedOtherTeam(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(OWNED_TEAMS).where(OWNED_TEAMS.USERNAME.eq(username)).fetch();
        if (records.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void updateTeamStatus(String teamName, String status) {
        try{
            DSLContext create = DBHandler.getContext();
            create.update(TEAM).set(TEAM.STATUS, TeamStatus.valueOf(status)).execute();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public List<HashMap<String, String>> getTeamsPerSeason(String years, String league) {
        int seasonID = this.getSeasonId(league,years);
        List<HashMap<String, String>> teamsDetails = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(TEAMS_IN_SEASON).where(TEAMS_IN_SEASON.SEASON_ID.eq(seasonID)).fetch();
        List<String> teams = records.getValues(TEAMS_IN_SEASON.TEAM_NAME);
        for (int i = 0; i < records.size() ; i++) {
            Result<?> result = create.select().from(TEAM).where(TEAM.NAME.eq(teams.get(i))).fetch();
            List<String> teamName = result.getValues(TEAM.NAME);
            List<TeamStatus> teamStatus = result.getValues(TEAM.STATUS);
            HashMap<String,String> details = new HashMap<>();
            details.put("name" , teamName.get(0));
            details.put("status" , teamStatus.get(0).name());
            teamsDetails.add(details);
        }
        return  teamsDetails;
    }


    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void addStadium(String name, String location) {
        DSLContext create = DBHandler.getContext();
        try{
            create.insertInto(STADIUM , STADIUM.NAME , STADIUM.LOCATION).values(name , location).execute();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<HashMap<String, String>> getTeamManaged(String systemUser) {
        List<HashMap<String, String>> teamsDetails = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(MANAGER_IN_TEAMS).where(MANAGER_IN_TEAMS.USERNAME.eq(systemUser)).fetch();
        List<String> teams = records.getValues(MANAGER_IN_TEAMS.TEAM_NAME);
        for (int i = 0; i < records.size() ; i++) {
            Result<?> result = create.select().from(TEAM).where(TEAM.NAME.eq(teams.get(i))).fetch();
            List<String> teamName = result.getValues(TEAM.NAME);
            List<TeamStatus> teamStatus = result.getValues(TEAM.STATUS);
            HashMap<String,String> details = new HashMap<>();
            details.put("name" , teamName.get(0));
            details.put("status" , teamStatus.get(0).name());
            teamsDetails.add(details);
        }
        return  teamsDetails;
    }
}

