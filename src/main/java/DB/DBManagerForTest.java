package DB;

import DB.Tables_Test.enums.UserRolesRoleType;
import DB.Tables_Test.enums.CoachQualification;
import DB.Tables_Test.enums.RefereeTraining;
import DB.Tables_Test.enums.TeamStatus;
import Domain.Exceptions.GameNotFoundException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Pair;
import Domain.Pair;  //FIXME: This is a Domain Object and should not be here
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static DB.Tables_Test.Tables.*;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;


public class DBManagerForTest extends DBManager {

    private static DBManagerForTest dbManagerForTestInstance = null;


    private DBManagerForTest() {
    }


    /**
     * Returns an instance of dbManager. part of the Singleton design
     *
     * @return - DBManager - an instance of dbManager
     */
    public static DBManagerForTest getInstance() {
        if (dbManagerForTestInstance == null)
            dbManagerForTestInstance = new DBManagerForTest();
        return dbManagerForTestInstance;
    }

    @Override
    public void startConnection() {
        DBHandler.startConnection("jdbc:mysql://localhost:3306/fwdb_test");
    }

    @Override
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

    @Override
    public boolean addLeagueRecord(String name) {
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(LEAGUE, LEAGUE.NAME).values(name).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getLeagues() {
        List<String> leaguesName = new ArrayList<>();
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(LEAGUE).fetch();
        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        leaguesName = result.getValues(LEAGUE.NAME);
        return leaguesName;
    }

    @Override
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

    @Override
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

    @Override
    public boolean addSeasonToLeague(String leagueName, String years, boolean isUnderway, int pointsPolicyID) {
        DSLContext dslContext = DBHandler.getContext();
        try {
            dslContext.insertInto(SEASON, SEASON.LEAGUE_NAME, SEASON.YEARS,
                    SEASON.IS_UNDER_WAY, SEASON.POINTS_POLICY_ID)
                    .values(leagueName, years, isUnderway, pointsPolicyID).execute();
            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    @Override
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


    @Override
    public List<Pair<String, String>> getUser(String username) throws UserNotFoundException {
        Result<?> user = getSystemUser(username);
        if (user == null) {
            throw new UserNotFoundException("A user with the given username dose't exists");
        }
        if (user.isEmpty()) {
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

    @Override
    public boolean addUser(String username, String name, String password, String email, boolean alertEmail) {
        DSLContext dslContext = DBHandler.getContext();
        /*todo: check duplicate key*/
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

    @Override
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
            if(user.getValues(i).get(0)!= null) {
                String fieldValue = user.getValues(i).get(0).toString();
                Pair<String, String> pair = new Pair<>(fieldName, fieldValue);
                details.add(pair);
            }
            else{
                Pair<String, String> pair = new Pair<>(fieldName, null);
                details.add(pair);
            }
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
        List<UserRolesRoleType> rolesTypes = result.getValues(USER_ROLES.ROLE_TYPE);
        List<String> answer = new ArrayList<>();
        for (int i = 0; i < rolesTypes.size(); i++) {
            /*FIXME - CHANGE GETNAME() TO NAME()*/
            answer.add(rolesTypes.get(i).name());
        }
        return answer;
    }

    @Override
    public List<HashMap<String, String>> getTeams(String teamOwner) {
        List<HashMap<String, String>> teamsDetails = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(OWNED_TEAMS).where(OWNED_TEAMS.USERNAME.eq(teamOwner)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> teams = records.getValues(OWNED_TEAMS.TEAM_NAME);
        for (int i = 0; i < records.size(); i++) {
            Result<?> result = create.select().from(TEAM).where(TEAM.NAME.eq(teams.get(i))).fetch();
            List<String> teamName = result.getValues(TEAM.NAME);
            List<TeamStatus> teamStatus = result.getValues(TEAM.STATUS);
            HashMap<String, String> details = new HashMap<>();
            details.put("name", teamName.get(0));
            details.put("status", teamStatus.get(0).name());
            teamsDetails.add(details);
        }
        return teamsDetails;
    }

    @Override
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


    @Override
    public void addPlayer(String username, Date bday) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "PLAYER"))) {

            try {
                create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.PLAYER).execute();
                create.insertInto(PLAYER, PLAYER.USERNAME, PLAYER.BIRTHDAY).values(username, this.convertToLocalDateViaInstant(bday)).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addCoach(String username, String qualification) {
        DSLContext create = DBHandler.getContext();
        try {
            if (!(hasRole(username, "COACH"))) {
                create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.COACH).execute();
                if (qualification != null) {
                    create.insertInto(COACH, COACH.USERNAME, COACH.QUALIFICATION).values(username, CoachQualification.valueOf(qualification)).execute();
                } else {
                    create.insertInto(COACH, COACH.USERNAME, COACH.QUALIFICATION).values(username, null).execute();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addTeamManager(String username) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "TEAM_MANAGER"))) {
            try {
                create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_MANAGER).execute();
                create.insertInto(TEAM_MANAGER, TEAM_MANAGER.USERNAME).values(username).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addTeamOwner(String username) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "TEAM_OWNER"))) {
            try {
                create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_OWNER).execute();
                create.insertInto(TEAM_OWNER, TEAM_OWNER.USERNAME).values(username).execute();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void addSystemAdmin(String username) {
        DSLContext create = DBHandler.getContext();
        if (!(hasRole(username, "SYSTEM_ADMIN"))) {
            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.SYSTEM_ADMIN).execute();
            create.insertInto(SYSTEM_ADMIN, SYSTEM_ADMIN.USERNAME).values(username).execute();
        }
    }

    @Override
    public void addReferee(String username, String training) {
        DSLContext create = DBHandler.getContext();
        try {
            //todo: check!!!!
            if (!(hasRole(username, "REFEREE"))) {
                create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.REFEREE).execute();
                if (training != null) {
                    create.insertInto(REFEREE, REFEREE.USERNAME, REFEREE.TRAINING).values(username, RefereeTraining.valueOf(training)).execute();
                } else {
                    create.insertInto(REFEREE, REFEREE.USERNAME, REFEREE.TRAINING).values(username, null).execute();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addAssociationRepresentative(String username) {
        DSLContext create = DBHandler.getContext();
        //todo: check!!!!
        try {
            create.insertInto(USER_ROLES, USER_ROLES.USERNAME, USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.ASSOCIATION_REPRESENTATIVE).execute();

            create.insertInto(ASSOCIATION_REPRESENTATIVE, ASSOCIATION_REPRESENTATIVE.USERNAME).values(username).execute();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public int getStadiumId(String name, String location) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(STADIUM).where(STADIUM.LOCATION.eq(location)).and(STADIUM.NAME.eq(name)).fetch();
        return records.getValues(STADIUM.STADIUM_ID).get(0);
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<HashMap<String, String>> getLeagueSeasons(String leagueName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(SEASON).where(SEASON.LEAGUE_NAME.eq(leagueName)).fetch();
        if (records.size() == 0) {
            return new ArrayList<>();
        }
        List<HashMap<String, String>> seasonsDetails = new ArrayList<>();
        //Add loop
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

    @Override
    public HashMap<String, String> getPointsPolicyByID(int policyID) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(POINTS_POLICY).where(POINTS_POLICY.POLICY_ID.eq(policyID)).fetch();
        if (records.size() == 0) {
            return new HashMap<>();
        }
        HashMap<String, String> pointsPolicyDetails = new HashMap<>();
        for (int i = 0; i < records.fields().length; i++) {
            String fieldName = records.fields()[i].getName();
            String fieldValue = records.getValues(i).get(0).toString();
            pointsPolicyDetails.put(fieldName, fieldValue + "");
        }
        return pointsPolicyDetails;
    }

    @Override
    public List<HashMap<String, String>> getRefereeGames(String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(REFEREE_IN_GAME.join(GAME).on(GAME.GAME_ID.eq(REFEREE_IN_GAME.GAME_ID))
                .join(STADIUM).on(STADIUM.STADIUM_ID.eq(GAME.STADIUM_ID))).where(REFEREE_IN_GAME.USERNAME.eq(username)).fetch();

        return getDetailsFromResult(records);
    }

    @Override
    public void unAssignRefereeFromAllSeasons(String username) {
        DSLContext create = DBHandler.getContext();
        create.delete(REFEREE_IN_SEASON)
                .where(REFEREE_IN_SEASON.USERNAME.eq(username))
                .execute();
    }

    @Override
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

    @Override
    public List<HashMap<String, String>> getPointsPolicies() {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(POINTS_POLICY).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public boolean isSeasonInTeam(int seasonID, String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(TEAMS_IN_SEASON).where(TEAMS_IN_SEASON.SEASON_ID.eq(seasonID).and(TEAMS_IN_SEASON.TEAM_NAME.eq(teamName))).fetch();
        if (records.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
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

    @Override
    public List<HashMap<String, String>> getAllSeasonInTeam(String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(TEAMS_IN_SEASON).where(TEAMS_IN_SEASON.TEAM_NAME.eq(teamName)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> seasonsID = records.getValues(TEAMS_IN_SEASON.SEASON_ID);
        List<HashMap<String, String>> seasonsDetails = new ArrayList<>();
        for (int k = 0; k < seasonsID.size(); k++) {
            Result<?> result = create.select().from(SEASON).where(SEASON.SEASON_ID.eq(seasonsID.get(k))).fetch();
            //Add loop
            for (int i = 0; i < result.size(); i++) {
                HashMap<String, String> currentSeasonDetails = new HashMap<>();
                for (int j = 0; j < result.fields().length; j++) {
                    String fieldName = result.get(i).fields()[j].getName();
                    String fieldValue = result.get(i).getValue(fieldName).toString();
                    if (fieldName.equals("points_policy_id")) {
                        HashMap<String, String> pointsPolicyDetails = getPointsPolicyByID(Integer.parseInt(fieldValue));
                        currentSeasonDetails.putAll(pointsPolicyDetails);
                    } else {
                        currentSeasonDetails.put(fieldName, fieldValue);
                    }
                }
                seasonsDetails.add(currentSeasonDetails);
            }
        }
        return seasonsDetails;
    }

    @Override
    public boolean isTeamManager(String username, String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(MANAGER_IN_TEAMS).where(MANAGER_IN_TEAMS.USERNAME.eq(username).and(MANAGER_IN_TEAMS.TEAM_NAME.eq(teamName))).fetch();
        if (records.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
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


    @Override
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
                    remove_coach, add_coach, change_team_job_coach)).where(MANAGER_IN_TEAMS.TEAM_NAME.eq(teamName)
                    .and(MANAGER_IN_TEAMS.USERNAME.eq(username))).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<String> getTeamsOwners(String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(OWNED_TEAMS).where(OWNED_TEAMS.TEAM_NAME.eq(teamName)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> teamOwners;
        teamOwners = records.getValues(OWNED_TEAMS.USERNAME);
        return teamOwners;
    }

    @Override
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

    @Override
    public void updateTeamStatus(String teamName, String status) {
        try {
            DSLContext create = DBHandler.getContext();
            create.update(TEAM).set(TEAM.STATUS, TeamStatus.valueOf(status)).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<HashMap<String, String>> getTeamsPerSeason(String years, String league) {
        int seasonID = this.getSeasonId(league, years);
        List<HashMap<String, String>> teamsDetails = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(TEAMS_IN_SEASON).where(TEAMS_IN_SEASON.SEASON_ID.eq(seasonID)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> teams = records.getValues(TEAMS_IN_SEASON.TEAM_NAME);
        for (int i = 0; i < records.size(); i++) {
            Result<?> result = create.select().from(TEAM).where(TEAM.NAME.eq(teams.get(i))).fetch();
            List<String> teamName = result.getValues(TEAM.NAME);
            List<TeamStatus> teamStatus = result.getValues(TEAM.STATUS);
            HashMap<String, String> details = new HashMap<>();
            details.put("name", teamName.get(0));
            details.put("status", teamStatus.get(0).name());
            teamsDetails.add(details);
        }
        return teamsDetails;
    }


    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public boolean addStadium(String name, String location) {
        DSLContext create = DBHandler.getContext();
        try {
            create.insertInto(STADIUM, STADIUM.NAME, STADIUM.LOCATION).values(name, location).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<HashMap<String, String>> getTeamManaged(String systemUser) {
        List<HashMap<String, String>> teamsDetails = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(MANAGER_IN_TEAMS).where(MANAGER_IN_TEAMS.USERNAME.eq(systemUser)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> teams = records.getValues(MANAGER_IN_TEAMS.TEAM_NAME);
        for (int i = 0; i < records.size(); i++) {
            Result<?> result = create.select().from(TEAM).where(TEAM.NAME.eq(teams.get(i))).fetch();
            List<String> teamName = result.getValues(TEAM.NAME);
            List<TeamStatus> teamStatus = result.getValues(TEAM.STATUS);
            HashMap<String, String> details = new HashMap<>();
            details.put("name", teamName.get(0));
            details.put("status", teamStatus.get(0).name());
            teamsDetails.add(details);
        }
        return teamsDetails;
    }

    @Override
    public List<String> getTeamManagers(String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(MANAGER_IN_TEAMS).where(MANAGER_IN_TEAMS.TEAM_NAME.eq(teamName)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> teamMangerNames = records.getValues(MANAGER_IN_TEAMS.USERNAME);
        return teamMangerNames;
    }


    @Override
    public List<HashMap<String, String>> getStadiumsInTeam(String teamName) {

        List<HashMap<String, String>> stadiums = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(STADIUM_HOME_TEAMS).where(STADIUM_HOME_TEAMS.TEAM_NAME.eq(teamName)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> stadiumsIDs = records.getValues(STADIUM_HOME_TEAMS.STADIUM_ID);
        for (int i = 0; i < stadiumsIDs.size(); i++) {
            Result<?> result = create.select().from(STADIUM).where(STADIUM.STADIUM_ID.eq(stadiumsIDs.get(i))).fetch();
            List<String> stadiumName = result.getValues(STADIUM.NAME);
            List<String> stadiumLocation = result.getValues(STADIUM.LOCATION);
            HashMap<String, String> details = new HashMap<>();
            details.put("name", stadiumName.get(0));
            details.put("location", stadiumLocation.get(0));
            stadiums.add(details);
        }


        return stadiums;
    }

    @Override
    public void removeStadiumFromTeam(int stadiumID, String teamName) {
        try {
            DSLContext create = DBHandler.getContext();
            create.deleteFrom(STADIUM_HOME_TEAMS).where(STADIUM_HOME_TEAMS.TEAM_NAME.eq(teamName).and(STADIUM_HOME_TEAMS.STADIUM_ID.eq(stadiumID)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getAllPlayersInTeam(String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(PLAYER_IN_TEAM).where(PLAYER_IN_TEAM.TEAM_NAME.eq(teamName)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> playerNames = records.getValues(PLAYER_IN_TEAM.USERNAME);
        return playerNames;

    }

    @Override
    public List<String> getAllCoachesInTeam(String teamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(COACH_IN_TEAM).where(COACH_IN_TEAM.TEAM_NAME.eq(teamName)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> playerNames = records.getValues(COACH_IN_TEAM.USERNAME);
        return playerNames;
    }

    @Override
    public boolean updateStadiumName(String name, String location, String toChange) {
        try {
            int stadiumID = getStadiumId(name, location);
            DSLContext create = DBHandler.getContext();
            create.update(STADIUM).set(STADIUM.NAME, toChange);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<HashMap<String, String>> getAllStadiumTeams(String name, String location) {
        int stadiumID = this.getStadiumId(name, location);
        List<HashMap<String, String>> teamsDetails = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(STADIUM_HOME_TEAMS).where(STADIUM_HOME_TEAMS.STADIUM_ID.eq(stadiumID)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> teams = records.getValues(STADIUM_HOME_TEAMS.TEAM_NAME);
        for (int i = 0; i < records.size(); i++) {
            Result<?> result = create.select().from(TEAM).where(TEAM.NAME.eq(teams.get(i))).fetch();
            if (records.isEmpty()) {
                return new ArrayList<>();
            }
            List<String> teamName = result.getValues(TEAM.NAME);
            List<TeamStatus> teamStatus = result.getValues(TEAM.STATUS);
            HashMap<String, String> details = new HashMap<>();
            details.put("name", teamName.get(0));
            details.put("status", teamStatus.get(0).name());
            teamsDetails.add(details);
        }
        return teamsDetails;

    }

    @Override
    public List<String> getAllPermissionsPerTeam(String teamName, String username) {
        try {
            DSLContext create = DBHandler.getContext();
            Result<?> records = create.select().from(MANAGER_IN_TEAMS).
                    where(MANAGER_IN_TEAMS.TEAM_NAME.eq(teamName).and(MANAGER_IN_TEAMS.USERNAME.eq(username))).fetch();

            if (records.isEmpty()) {
                return new ArrayList<>();
            }

            List<String> allPermissions = new ArrayList<>();
            if (records.get(0).fields(MANAGER_IN_TEAMS.ADD_COACH).equals(true)) {
                allPermissions.add("ADD_COACH");
            }
            if (records.get(0).fields(MANAGER_IN_TEAMS.ADD_PLAYER).equals(true)) {
                allPermissions.add("ADD_PLAYER");
            }
            if (records.get(0).fields(MANAGER_IN_TEAMS.REMOVE_COACH).equals(true)) {
                allPermissions.add("REMOVE_COACH");
            }
            if (records.get(0).fields(MANAGER_IN_TEAMS.REMOVE_PLAYER).equals(true)) {
                allPermissions.add("REMOVE_PLAYER");
            }
            if (records.get(0).fields(MANAGER_IN_TEAMS.CHANGE_POSITION_PLAYER).equals(true)) {
                allPermissions.add("CHANGE_POSITION_PLAYER");
            }
            if (records.get(0).fields(MANAGER_IN_TEAMS.CHANGE_TEAM_JOB_COACH).equals(true)) {
                allPermissions.add("CHANGE_TEAM_JOB_COACH");
            }
            return allPermissions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAppointedOwner(String teamName, String username) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(OWNED_TEAMS).where(OWNED_TEAMS.TEAM_NAME.
                eq(teamName).and(OWNED_TEAMS.USERNAME.eq(username))).fetch();
        if (records.isEmpty()) {
            return null;
        }
        return records.getValues(OWNED_TEAMS.APPOINTER).get(0);
    }

    @Override
    public boolean removeRole(String username, String roleType) {
        DSLContext create = DBHandler.getContext();
        try {
            create.delete(USER_ROLES).where(USER_ROLES.USERNAME.eq(username).and(USER_ROLES.ROLE_TYPE.eq(UserRolesRoleType.valueOf(roleType)))).execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<String> getUsernames() {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select(SYSTEMUSER.USERNAME).
                from(SYSTEMUSER).fetch();

        List<String> usernames = result.getValues(SYSTEMUSER.USERNAME);
        return usernames;
    }

    @Override
    public boolean addRefereeToSeason(String username, String leagueName, String seasonYears) {
        int seasonID = this.getSeasonId(leagueName, seasonYears);
        DSLContext create = DBHandler.getContext();

        try {
            create.insertInto(REFEREE_IN_SEASON, REFEREE_IN_SEASON.USERNAME,
                    REFEREE_IN_SEASON.SEASON_ID).values(username, seasonID).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<HashMap<String, String>> getSeasonGamesDetails(String name, String years) {
        DSLContext create = DBHandler.getContext();
        int seasonId = getSeasonId(name, years);

        Result<?> records = create.select().from(GAMES_IN_SEASON.join(GAME).on(GAME.GAME_ID.eq(GAMES_IN_SEASON.GAME_ID))).where(GAMES_IN_SEASON.SEASON_ID.eq(seasonId)).fetch();
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<HashMap<String, String>> resultList = getDetailsFromResult(records);
        for (int i = 0; i < resultList.size(); i++) {
            int stadiumID = Integer.parseInt(resultList.get(i).get("stadium_id"));
            resultList.get(i).put("stadium_name", getStadiumNameByID(stadiumID));
        }
        return resultList;
    }


    private String getStadiumNameByID(int stadiumID) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select(STADIUM.NAME).from(STADIUM).where(STADIUM.STADIUM_ID.eq(stadiumID)).fetch();
        HashMap<String, String> pointsPolicyDetails = new HashMap<>();
        for (int i = 0; i < records.fields().length; i++) {
            String fieldName = records.fields()[i].getName();
            String fieldValue = records.getValues(i).get(0).toString();
            pointsPolicyDetails.put(fieldName, fieldValue + "");
        }
        return pointsPolicyDetails.get("name");
    }

    @Override
    public boolean removeGamesFromSeason(String name, String years) {
        //TODO:Check
        DSLContext create = DBHandler.getContext();
        int seasonID = getSeasonId(name, years);
        try {
            create.delete(GAME)
                    .where(GAME.GAME_ID.in(select(GAME.GAME_ID)
                            .from(GAME.join(GAMES_IN_SEASON).on(GAMES_IN_SEASON.GAME_ID.eq(GAME.GAME_ID))).
                                    where(GAMES_IN_SEASON.SEASON_ID.eq(seasonID))))
                    .execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addGame(String stadiumName, String stadiumLocation, String homeTeamName, String awayTeamName, Date startDate, Date endDate, boolean finished) {
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(GAME, GAME.STADIUM_ID, GAME.HOME_TEAM,
                GAME.AWAY_TEAM, GAME.START_DATE, GAME.END_DATE, GAME.FINISHED)
                .values(getStadiumId(stadiumName, stadiumLocation), homeTeamName,
                        awayTeamName, convertToLocalDateViaInstant(startDate), convertToLocalDateViaInstant(endDate), finished).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addRefereeToGame(String username, String stadiumName, String stadiumLocation, String homeTeamName, String awayTeamName) {
        int gameID = getGameID(stadiumName, stadiumLocation, homeTeamName, awayTeamName);
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(REFEREE_IN_GAME, REFEREE_IN_GAME.USERNAME, REFEREE_IN_GAME.GAME_ID)
                .values(username, gameID).execute();
        if (succeed == 0) {
            return false;
        }
        return true;

    }

    private int getGameID(String stadiumName, String stadiumLocation, String homeTeamName, String awayTeamName) {
        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(GAME).where(GAME.STADIUM_ID.eq(getStadiumId(stadiumName, stadiumLocation)))
                .and(GAME.HOME_TEAM.eq(homeTeamName)).and(GAME.AWAY_TEAM.eq(awayTeamName))
                .fetch();
        HashMap<String, String> gameDetails = new HashMap<>();
        for (int i = 0; i < records.fields().length; i++) {
            String fieldName = records.fields()[i].getName();
            String fieldValue = records.getValues(i).get(0).toString();
            gameDetails.put(fieldName, fieldValue + "");
        }
        return Integer.parseInt(gameDetails.get("game_id"));
    }

    @Override
    public boolean addGameToSeason(String leagueName, String years, String stadiumName, String stadiumLocation, String homeTeamName, String awayTeamName) {
        int gameID = getGameID(stadiumName, stadiumLocation, homeTeamName, awayTeamName);
        int seasonID = getSeasonId(leagueName, years);
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(GAMES_IN_SEASON, GAMES_IN_SEASON.SEASON_ID, GAMES_IN_SEASON.GAME_ID)
                .values(seasonID, gameID).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getRefereesUsernamesOfSeason(String name, String years) {
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(REFEREE_IN_SEASON).where(REFEREE_IN_SEASON.SEASON_ID.eq(getSeasonId(name, years)))
                .fetch();
        List<String> refereesUsernames = result.getValues(REFEREE_IN_SEASON.USERNAME);
        return refereesUsernames;
    }

    @Override
    public List<HashMap<String, String>> getRefereeSeasonsDetails(String username) {
        List<HashMap<String, String>> seasonsDetails = new ArrayList<>();

        DSLContext create = DBHandler.getContext();
        Result<?> records = create.select().from(REFEREE_IN_SEASON.join(SEASON).on(REFEREE_IN_SEASON.SEASON_ID.eq(SEASON.SEASON_ID))).where(REFEREE_IN_SEASON.USERNAME.eq(username)).fetch();
        if (records.isEmpty()) {
            return seasonsDetails;
        }
        return getDetailsFromResult(records);
    }

    @Override
    public HashMap<String, String> getTeam(String teamName) {
        try{
            DSLContext create = DBHandler.getContext();
            Result<?> records = create.select().from(TEAM).where(TEAM.NAME.eq(teamName)).fetch();
            HashMap<String, String> teamDetails = new HashMap<>();
            if(records.isEmpty())
            {
                return teamDetails;
            }
            List<String> name = records.getValues(TEAM.NAME);
            List<TeamStatus> teamStatus = records.getValues(TEAM.STATUS);
            teamDetails.put("name" , name.get(0));
            teamDetails.put("status" , teamStatus.get(0).name());
            return  teamDetails;

        }catch (Exception e)
        {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Override
    public void saveAlert(String username, String alert) {

        try{
            DSLContext create = DBHandler.getContext();
            create.insertInto(ALERT, ALERT.USERNAME,ALERT.NOTIFICATION).values(username,alert);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void addGame(String stadiumName,String stadiumLocation ,String homeTeamName, String awayTeamName, Date gameDate, boolean isFinished) {
        int stadId = getStadiumId(stadiumName,stadiumLocation);
        LocalDate gameLocalDate = convertToLocalDateViaInstant(gameDate);
        DSLContext create = DBHandler.getContext();
        try{
            create.insertInto(GAME , GAME.STADIUM_ID , GAME.HOME_TEAM, GAME.AWAY_TEAM, GAME.DATE, GAME.FINISHED)
                    .values(stadId, homeTeamName, awayTeamName, gameLocalDate, isFinished).execute();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addGameToReferee(String refereeUsername,String stadiumName, String stadiumLocation, String homeTeamName, String awayTeamName, Date gameDate, boolean isFinished) {
        int stadId = getStadiumId(stadiumName,stadiumLocation);
        LocalDate gameLocalDate = convertToLocalDateViaInstant(gameDate);
        DSLContext create = DBHandler.getContext();
        Result<?> records = getGameRecordsByGameParams(homeTeamName, awayTeamName, stadId, gameLocalDate, create);

        if(records.isEmpty())
        {
            try{
                create.insertInto(GAME , GAME.STADIUM_ID , GAME.HOME_TEAM, GAME.AWAY_TEAM, GAME.DATE, GAME.FINISHED)
                        .values(stadId, homeTeamName, awayTeamName, gameLocalDate, isFinished).execute();
                records = getGameRecordsByGameParams(homeTeamName, awayTeamName, stadId, gameLocalDate, create);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        int gameId = records.getValues(GAME.GAME_ID).get(0);
        try{
            create.insertInto(REFEREE_IN_GAME , REFEREE_IN_GAME.USERNAME , REFEREE_IN_GAME.GAME_ID)
                    .values(refereeUsername, gameId).execute();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    private Result<Record> getGameRecordsByGameParams(String homeTeamName, String awayTeamName, int stadId, LocalDate gameLocalDate, DSLContext create) {
        return create.select().from(GAME).where(GAME.DATE.eq(gameLocalDate)
                .and(GAME.HOME_TEAM.eq(homeTeamName).and(GAME.AWAY_TEAM.eq(awayTeamName)
                        .and(GAME.STADIUM_ID.eq(stadId))))).fetch();
    }

    @Override
    public void removeGameFromReferee(String refereeUsername, String stadiumName, String stadiumLocation, String homeTeamName, String awayTeamName, Date gameDate, boolean isFinished) throws GameNotFoundException {
        int stadId = getStadiumId(stadiumName,stadiumLocation);
        LocalDate gameLocalDate = convertToLocalDateViaInstant(gameDate);
        DSLContext create = DBHandler.getContext();
        Result<?> records = getGameRecordsByGameParams(homeTeamName, awayTeamName, stadId, gameLocalDate, create);

        if(records.size() != 1)
        {
            throw new GameNotFoundException("Could not find the game by the given params: " + homeTeamName + "," + awayTeamName + "," +  gameLocalDate + "," + stadiumName + "," + stadiumLocation);
        }
        int gameId = records.getValues(GAME.GAME_ID).get(0);

        try {
            create.deleteFrom(REFEREE_IN_GAME).where(REFEREE_IN_GAME.USERNAME.eq(refereeUsername).and(REFEREE_IN_GAME.GAME_ID.eq(gameId)));
//            return true;
        } catch (Exception e) {
            e.printStackTrace();
//            return false;
        }

    }
}
