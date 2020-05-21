/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.EventInjury;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventInjuryRecord extends UpdatableRecordImpl<EventInjuryRecord> implements Record4<Integer, Integer, String, Integer> {

    private static final long serialVersionUID = 430611227;

    /**
     * Setter for <code>fwdb.event_injury.event_id</code>.
     */
    public void setEventId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.event_injury.event_id</code>.
     */
    public Integer getEventId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>fwdb.event_injury.game_id</code>.
     */
    public void setGameId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.event_injury.game_id</code>.
     */
    public Integer getGameId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>fwdb.event_injury.injured_name</code>.
     */
    public void setInjuredName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb.event_injury.injured_name</code>.
     */
    public String getInjuredName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>fwdb.event_injury.minute</code>.
     */
    public void setMinute(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>fwdb.event_injury.minute</code>.
     */
    public Integer getMinute() {
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
    public Row4<Integer, Integer, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, String, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return EventInjury.EVENT_INJURY.EVENT_ID;
    }

    @Override
    public Field<Integer> field2() {
        return EventInjury.EVENT_INJURY.GAME_ID;
    }

    @Override
    public Field<String> field3() {
        return EventInjury.EVENT_INJURY.INJURED_NAME;
    }

    @Override
    public Field<Integer> field4() {
        return EventInjury.EVENT_INJURY.MINUTE;
    }

    @Override
    public Integer component1() {
        return getEventId();
    }

    @Override
    public Integer component2() {
        return getGameId();
    }

    @Override
    public String component3() {
        return getInjuredName();
    }

    @Override
    public Integer component4() {
        return getMinute();
    }

    @Override
    public Integer value1() {
        return getEventId();
    }

    @Override
    public Integer value2() {
        return getGameId();
    }

    @Override
    public String value3() {
        return getInjuredName();
    }

    @Override
    public Integer value4() {
        return getMinute();
    }

    @Override
    public EventInjuryRecord value1(Integer value) {
        setEventId(value);
        return this;
    }

    @Override
    public EventInjuryRecord value2(Integer value) {
        setGameId(value);
        return this;
    }

    @Override
    public EventInjuryRecord value3(String value) {
        setInjuredName(value);
        return this;
    }

    @Override
    public EventInjuryRecord value4(Integer value) {
        setMinute(value);
        return this;
    }

    @Override
    public EventInjuryRecord values(Integer value1, Integer value2, String value3, Integer value4) {
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
     * Create a detached EventInjuryRecord
     */
    public EventInjuryRecord() {
        super(EventInjury.EVENT_INJURY);
    }

    /**
     * Create a detached, initialised EventInjuryRecord
     */
    public EventInjuryRecord(Integer eventId, Integer gameId, String injuredName, Integer minute) {
        super(EventInjury.EVENT_INJURY);

        set(0, eventId);
        set(1, gameId);
        set(2, injuredName);
        set(3, minute);
    }
}
