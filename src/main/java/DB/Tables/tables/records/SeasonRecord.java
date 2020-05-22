/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.Season;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SeasonRecord extends UpdatableRecordImpl<SeasonRecord> implements Record5<Integer, String, String, Boolean, Integer> {

    private static final long serialVersionUID = -273201904;

    /**
     * Setter for <code>fwdb.season.season_id</code>.
     */
    public void setSeasonId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.season.season_id</code>.
     */
    public Integer getSeasonId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>fwdb.season.league_name</code>.
     */
    public void setLeagueName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.season.league_name</code>.
     */
    public String getLeagueName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>fwdb.season.years</code>.
     */
    public void setYears(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb.season.years</code>.
     */
    public String getYears() {
        return (String) get(2);
    }

    /**
     * Setter for <code>fwdb.season.is_under_way</code>.
     */
    public void setIsUnderWay(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>fwdb.season.is_under_way</code>.
     */
    public Boolean getIsUnderWay() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>fwdb.season.points_policy_id</code>.
     */
    public void setPointsPolicyId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>fwdb.season.points_policy_id</code>.
     */
    public Integer getPointsPolicyId() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Integer, String, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, String, Boolean, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, String, String, Boolean, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Season.SEASON.SEASON_ID;
    }

    @Override
    public Field<String> field2() {
        return Season.SEASON.LEAGUE_NAME;
    }

    @Override
    public Field<String> field3() {
        return Season.SEASON.YEARS;
    }

    @Override
    public Field<Boolean> field4() {
        return Season.SEASON.IS_UNDER_WAY;
    }

    @Override
    public Field<Integer> field5() {
        return Season.SEASON.POINTS_POLICY_ID;
    }

    @Override
    public Integer component1() {
        return getSeasonId();
    }

    @Override
    public String component2() {
        return getLeagueName();
    }

    @Override
    public String component3() {
        return getYears();
    }

    @Override
    public Boolean component4() {
        return getIsUnderWay();
    }

    @Override
    public Integer component5() {
        return getPointsPolicyId();
    }

    @Override
    public Integer value1() {
        return getSeasonId();
    }

    @Override
    public String value2() {
        return getLeagueName();
    }

    @Override
    public String value3() {
        return getYears();
    }

    @Override
    public Boolean value4() {
        return getIsUnderWay();
    }

    @Override
    public Integer value5() {
        return getPointsPolicyId();
    }

    @Override
    public SeasonRecord value1(Integer value) {
        setSeasonId(value);
        return this;
    }

    @Override
    public SeasonRecord value2(String value) {
        setLeagueName(value);
        return this;
    }

    @Override
    public SeasonRecord value3(String value) {
        setYears(value);
        return this;
    }

    @Override
    public SeasonRecord value4(Boolean value) {
        setIsUnderWay(value);
        return this;
    }

    @Override
    public SeasonRecord value5(Integer value) {
        setPointsPolicyId(value);
        return this;
    }

    @Override
    public SeasonRecord values(Integer value1, String value2, String value3, Boolean value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SeasonRecord
     */
    public SeasonRecord() {
        super(Season.SEASON);
    }

    /**
     * Create a detached, initialised SeasonRecord
     */
    public SeasonRecord(Integer seasonId, String leagueName, String years, Boolean isUnderWay, Integer pointsPolicyId) {
        super(Season.SEASON);

        set(0, seasonId);
        set(1, leagueName);
        set(2, years);
        set(3, isUnderWay);
        set(4, pointsPolicyId);
    }
}