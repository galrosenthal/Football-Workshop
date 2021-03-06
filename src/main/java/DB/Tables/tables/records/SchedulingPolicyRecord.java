/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.SchedulingPolicy;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchedulingPolicyRecord extends UpdatableRecordImpl<SchedulingPolicyRecord> implements Record4<Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 357540687;

    /**
     * Setter for <code>fwdb.scheduling_policy.scheduling_id</code>.
     */
    public void setSchedulingId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.scheduling_policy.scheduling_id</code>.
     */
    public Integer getSchedulingId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>fwdb.scheduling_policy.games_Per_Season</code>.
     */
    public void setGamesPerSeason(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.scheduling_policy.games_Per_Season</code>.
     */
    public Integer getGamesPerSeason() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>fwdb.scheduling_policy.games_Per_Day</code>.
     */
    public void setGamesPerDay(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb.scheduling_policy.games_Per_Day</code>.
     */
    public Integer getGamesPerDay() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>fwdb.scheduling_policy.minimum_Rest_Days</code>.
     */
    public void setMinimumRestDays(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>fwdb.scheduling_policy.minimum_Rest_Days</code>.
     */
    public Integer getMinimumRestDays() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, Integer, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return SchedulingPolicy.SCHEDULING_POLICY.SCHEDULING_ID;
    }

    @Override
    public Field<Integer> field2() {
        return SchedulingPolicy.SCHEDULING_POLICY.GAMES_PER_SEASON;
    }

    @Override
    public Field<Integer> field3() {
        return SchedulingPolicy.SCHEDULING_POLICY.GAMES_PER_DAY;
    }

    @Override
    public Field<Integer> field4() {
        return SchedulingPolicy.SCHEDULING_POLICY.MINIMUM_REST_DAYS;
    }

    @Override
    public Integer component1() {
        return getSchedulingId();
    }

    @Override
    public Integer component2() {
        return getGamesPerSeason();
    }

    @Override
    public Integer component3() {
        return getGamesPerDay();
    }

    @Override
    public Integer component4() {
        return getMinimumRestDays();
    }

    @Override
    public Integer value1() {
        return getSchedulingId();
    }

    @Override
    public Integer value2() {
        return getGamesPerSeason();
    }

    @Override
    public Integer value3() {
        return getGamesPerDay();
    }

    @Override
    public Integer value4() {
        return getMinimumRestDays();
    }

    @Override
    public SchedulingPolicyRecord value1(Integer value) {
        setSchedulingId(value);
        return this;
    }

    @Override
    public SchedulingPolicyRecord value2(Integer value) {
        setGamesPerSeason(value);
        return this;
    }

    @Override
    public SchedulingPolicyRecord value3(Integer value) {
        setGamesPerDay(value);
        return this;
    }

    @Override
    public SchedulingPolicyRecord value4(Integer value) {
        setMinimumRestDays(value);
        return this;
    }

    @Override
    public SchedulingPolicyRecord values(Integer value1, Integer value2, Integer value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SchedulingPolicyRecord
     */
    public SchedulingPolicyRecord() {
        super(SchedulingPolicy.SCHEDULING_POLICY);
    }

    /**
     * Create a detached, initialised SchedulingPolicyRecord
     */
    public SchedulingPolicyRecord(Integer schedulingId, Integer gamesPerSeason, Integer gamesPerDay, Integer minimumRestDays) {
        super(SchedulingPolicy.SCHEDULING_POLICY);

        set(0, schedulingId);
        set(1, gamesPerSeason);
        set(2, gamesPerDay);
        set(3, minimumRestDays);
    }
}
