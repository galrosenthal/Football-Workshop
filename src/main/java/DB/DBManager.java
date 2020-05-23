package DB;

import static DB.Tables.Tables.*;

import org.jooq.DSLContext;
import org.jooq.Result;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static DBManager dbManagerInstance = null;

    /**
     * Constructor
     */
    private DBManager() {
    }


    /**
     * Returns an instance of dbManager. part of the Singleton design
     * @return - DBManager - an instance of dbManager
     */
    public static DBManager getInstance() {
        if (dbManagerInstance == null)
            dbManagerInstance = new DBManager();
        return dbManagerInstance;
    }

    /**
     * clear all records from DB
     * @throws Exception
     */
    public static void deleteData() throws Exception {
        DBHandler.getInstance().deleteData();
    }


    /**
     * start connection to DB
     * @param url - to DB
     */
    public void startConnection(String url) {

        DBHandler.startConnection(url);
    }

    /**
     * close connection to DB
     *
     */
    public void closeConnection() {

        DBHandler.closeConnection();
    }

    /**
     * Saves all the tables to their original files
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
     *
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
     *Add league record
     * @param name - league name to add
     * @return true - added successfully
     *         false - otherwise
     */

    public boolean addLeagueRecord(String name) {
        DSLContext dslContext = DBHandler.getContext();
        //todo: check!!!!
        int succeed = dslContext.insertInto(LEAGUE, LEAGUE.NAME).values(name).execute();
        if(succeed == 0)
        {
               return false;
        }
        return true;
    }

    /**
     *  Get all leagues name from DB
     * @return - List<String> - Leagues name
     */
    public List<String> getLeagues() {
        List<String> leaguesName = new ArrayList<>();
        DSLContext dslContext = DBHandler.getContext();
        Result<?> result = dslContext.select().
                from(LEAGUE).fetch();

        leaguesName = result.getValues(LEAGUE.NAME);
        return  leaguesName;
    }

    /**
     * Check if season Exists in DB
     * @param leagueName
     * @param seasonYears
     * @return true - if  season Exists
     *         false - otherwise
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
     *get Points Policy ID fom DB
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
     * @param leagueName
     * @param years
     * @param isUnderway
     * @param pointsPolicyID
     * @return true - added successfully
     *         false - otherwise
     */
    public boolean addSeasonToLeague(String leagueName, String years, boolean isUnderway, int pointsPolicyID) {
        DSLContext dslContext = DBHandler.getContext();
        int succeed = dslContext.insertInto(SEASON, SEASON.LEAGUE_NAME,  SEASON.YEARS,
               SEASON.IS_UNDER_WAY, SEASON.POINTS_POLICY_ID)
                .values(leagueName,years, isUnderway, pointsPolicyID).execute();
        if(succeed == 0)
        {
            return false;
        }
        return true;
    }
}
