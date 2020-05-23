package DB;

import static DB.Tables.Tables.*;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;


import java.io.IOException;
import java.util.ArrayList;
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

    public static void deleteData(String dbName) throws Exception {
        DBHandler.getInstance().deleteData(dbName);
    }

    public void closeConnection() {
        DBHandler.closeConnection();
    }

    public static void startConnection(){
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

    public boolean addLeagueRecord(String name) {
        DSLContext dslContext = DBHandler.getContext();
        //todo: check!!!!
        int succeed = dslContext.insertInto(LEAGUE, LEAGUE.NAME).values(name).execute();
        if (succeed == 0) {
            return false;
        }
        return true;
    }

    public List<String> getLeagues() {
        List<String> leaguesName = new ArrayList<>();
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(LEAGUE).fetch();

        leaguesName = result.getValues(LEAGUE.NAME);
        return leaguesName;
    }

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
}
