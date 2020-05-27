package DB;

import DB.Tables_Test.enums.UserRolesRoleType;
import DB.Tables_Test.enums.CoachQualification;
import DB.Tables_Test.enums.RefereeTraining;
import DB.Tables_Test.enums.TeamStatus;
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

import static DB.Tables_Test.Tables.*;


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


    public static void startConnection() {
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

        leaguesName = result.getValues(LEAGUE.NAME);
        return leaguesName;
    }

    @Override
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

    @Override
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

    @Override
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
        int succeed = dslContext.insertInto(SYSTEMUSER,
                SYSTEMUSER.USERNAME, SYSTEMUSER.NAME, SYSTEMUSER.PASSWORD,
                SYSTEMUSER.EMAIL, SYSTEMUSER.NOTIFY_BY_EMAIL)
                .values(username, name, password, email, alertEmail).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    @Override
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

    private List<Pair<String, String>> getDetails(Result<?> user)
    {
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

    @Override
    public List<Pair<String, String>> getTeams(String name) {
        List<String> teamsName = new ArrayList<>();
        List<TeamStatus> statues = new ArrayList<>();
        List<Pair<String,String>> teams = new ArrayList<>();
        DSLContext create = DBHandler.getContext();
        Result<?> result = create.select()
                .from(OWNED_TEAMS.where(OWNED_TEAMS.USERNAME.eq(name)).join(TEAM)
                        .on(TEAM.NAME.eq(OWNED_TEAMS.TEAM_NAME)))
                .fetch();
        teamsName = result.getValues(OWNED_TEAMS.TEAM_NAME);
        statues = result.getValues(TEAM.STATUS);
        for (int i = 0; i < teamsName.size(); i++) {
            Pair<String,String> pair = new Pair(teamsName.get(i),statues.get(i));
            teams.add(pair);
        }
        return teams;
    }

    @Override
    public List<String> getStadium(String stadiumName) {
        DSLContext create = DBHandler.getContext();
        List<String> stadium;
        Result<?> result = create.select()
                .from(STADIUM.where(STADIUM.NAME.eq(stadiumName))).fetch();
        if (result.size() == 0)
        {
            return null;
        }
        stadium = result.getValues(STADIUM.NAME);
        stadium.addAll(result.getValues(STADIUM.LOCATION));
        return stadium;
    }
    @Override
    public void addPlayer(String username, Date bday) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.PLAYER).execute();

        create.insertInto(PLAYER, PLAYER.USERNAME , PLAYER.BIRTHDAY).values(username, LocalDate.ofEpochDay(bday.getTime())).execute();


    }
    @Override
    public void addCoach(String username, String qualification) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.COACH).execute();

        create.insertInto(COACH, COACH.USERNAME , COACH.QUALIFICATION).values(username, CoachQualification.valueOf(qualification)).execute();


    }
    @Override
    public void addTeamManager(String username) {
        DSLContext create = DBHandler.getContext();
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_MANAGER).execute();

        create.insertInto(TEAM_MANAGER, TEAM_MANAGER.USERNAME).values(username).execute();


    }
    @Override
    public void addTeamOwner(String username) {
        DSLContext create = DBHandler.getContext();
        //todo: check!!!!
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.TEAM_OWNER).execute();

        create.insertInto(TEAM_OWNER, TEAM_OWNER.USERNAME).values(username).execute();


    }
    @Override
    public void addSystemAdmin(String username) {
        DSLContext create = DBHandler.getContext();
        //todo: check!!!!
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.SYSTEM_ADMIN).execute();

        create.insertInto(SYSTEM_ADMIN, SYSTEM_ADMIN.USERNAME).values(username).execute();




    }
    @Override
    public void addReferee(String username, String training) {
        DSLContext create = DBHandler.getContext();
        //todo: check!!!!
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.REFEREE).execute();

        create.insertInto(REFEREE, REFEREE.USERNAME , REFEREE.TRAINING).values(username, RefereeTraining.valueOf(training)).execute();


    }
    @Override
    public void addAssociationRepresentative(String username) {
        DSLContext create = DBHandler.getContext();
        //todo: check!!!!
        create.insertInto(USER_ROLES, USER_ROLES.USERNAME , USER_ROLES.ROLE_TYPE).values(username, UserRolesRoleType.ASSOCIATION_REPRESENTATIVE).execute();

        create.insertInto(ASSOCIATION_REPRESENTATIVE, ASSOCIATION_REPRESENTATIVE.USERNAME).values(username).execute();


    }
}
