/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.TeamsInSeason;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TeamsInSeasonRecord extends UpdatableRecordImpl<TeamsInSeasonRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = 1043766038;

    /**
     * Setter for <code>fwdb.teams_in_season.season_id</code>.
     */
    public void setSeasonId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.teams_in_season.season_id</code>.
     */
    public Integer getSeasonId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>fwdb.teams_in_season.team_name</code>.
     */
    public void setTeamName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.teams_in_season.team_name</code>.
     */
    public String getTeamName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return TeamsInSeason.TEAMS_IN_SEASON.SEASON_ID;
    }

    @Override
    public Field<String> field2() {
        return TeamsInSeason.TEAMS_IN_SEASON.TEAM_NAME;
    }

    @Override
    public Integer component1() {
        return getSeasonId();
    }

    @Override
    public String component2() {
        return getTeamName();
    }

    @Override
    public Integer value1() {
        return getSeasonId();
    }

    @Override
    public String value2() {
        return getTeamName();
    }

    @Override
    public TeamsInSeasonRecord value1(Integer value) {
        setSeasonId(value);
        return this;
    }

    @Override
    public TeamsInSeasonRecord value2(String value) {
        setTeamName(value);
        return this;
    }

    @Override
    public TeamsInSeasonRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TeamsInSeasonRecord
     */
    public TeamsInSeasonRecord() {
        super(TeamsInSeason.TEAMS_IN_SEASON);
    }

    /**
     * Create a detached, initialised TeamsInSeasonRecord
     */
    public TeamsInSeasonRecord(Integer seasonId, String teamName) {
        super(TeamsInSeason.TEAMS_IN_SEASON);

        set(0, seasonId);
        set(1, teamName);
    }
}