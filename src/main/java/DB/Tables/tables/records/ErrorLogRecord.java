/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.ErrorLog;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ErrorLogRecord extends UpdatableRecordImpl<ErrorLogRecord> implements Record4<Integer, LocalDateTime, String, String> {

    private static final long serialVersionUID = -1821935243;

    /**
     * Setter for <code>fwdb.error_log.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.error_log.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>fwdb.error_log.DATE</code>.
     */
    public void setDate(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.error_log.DATE</code>.
     */
    public LocalDateTime getDate() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>fwdb.error_log.level</code>.
     */
    public void setLevel(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb.error_log.level</code>.
     */
    public String getLevel() {
        return (String) get(2);
    }

    /**
     * Setter for <code>fwdb.error_log.message</code>.
     */
    public void setMessage(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>fwdb.error_log.message</code>.
     */
    public String getMessage() {
        return (String) get(3);
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
    public Row4<Integer, LocalDateTime, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, LocalDateTime, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return ErrorLog.ERROR_LOG.ID;
    }

    @Override
    public Field<LocalDateTime> field2() {
        return ErrorLog.ERROR_LOG.DATE;
    }

    @Override
    public Field<String> field3() {
        return ErrorLog.ERROR_LOG.LEVEL;
    }

    @Override
    public Field<String> field4() {
        return ErrorLog.ERROR_LOG.MESSAGE;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public LocalDateTime component2() {
        return getDate();
    }

    @Override
    public String component3() {
        return getLevel();
    }

    @Override
    public String component4() {
        return getMessage();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public LocalDateTime value2() {
        return getDate();
    }

    @Override
    public String value3() {
        return getLevel();
    }

    @Override
    public String value4() {
        return getMessage();
    }

    @Override
    public ErrorLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public ErrorLogRecord value2(LocalDateTime value) {
        setDate(value);
        return this;
    }

    @Override
    public ErrorLogRecord value3(String value) {
        setLevel(value);
        return this;
    }

    @Override
    public ErrorLogRecord value4(String value) {
        setMessage(value);
        return this;
    }

    @Override
    public ErrorLogRecord values(Integer value1, LocalDateTime value2, String value3, String value4) {
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
     * Create a detached ErrorLogRecord
     */
    public ErrorLogRecord() {
        super(ErrorLog.ERROR_LOG);
    }

    /**
     * Create a detached, initialised ErrorLogRecord
     */
    public ErrorLogRecord(Integer id, LocalDateTime date, String level, String message) {
        super(ErrorLog.ERROR_LOG);

        set(0, id);
        set(1, date);
        set(2, level);
        set(3, message);
    }
}