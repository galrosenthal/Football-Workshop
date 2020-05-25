/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables;


import DB.Tables_Test.FwdbTest;
import DB.Tables_Test.Keys;
import DB.Tables_Test.tables.records.ErrorLogRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ErrorLog extends TableImpl<ErrorLogRecord> {

    private static final long serialVersionUID = 1975074707;

    /**
     * The reference instance of <code>fwdb_test.error_log</code>
     */
    public static final ErrorLog ERROR_LOG = new ErrorLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ErrorLogRecord> getRecordType() {
        return ErrorLogRecord.class;
    }

    /**
     * The column <code>fwdb_test.error_log.id</code>.
     */
    public final TableField<ErrorLogRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>fwdb_test.error_log.DATE</code>.
     */
    public final TableField<ErrorLogRecord, LocalDateTime> DATE = createField(DSL.name("DATE"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>fwdb_test.error_log.level</code>.
     */
    public final TableField<ErrorLogRecord, String> LEVEL = createField(DSL.name("level"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>fwdb_test.error_log.message</code>.
     */
    public final TableField<ErrorLogRecord, String> MESSAGE = createField(DSL.name("message"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>fwdb_test.error_log</code> table reference
     */
    public ErrorLog() {
        this(DSL.name("error_log"), null);
    }

    /**
     * Create an aliased <code>fwdb_test.error_log</code> table reference
     */
    public ErrorLog(String alias) {
        this(DSL.name(alias), ERROR_LOG);
    }

    /**
     * Create an aliased <code>fwdb_test.error_log</code> table reference
     */
    public ErrorLog(Name alias) {
        this(alias, ERROR_LOG);
    }

    private ErrorLog(Name alias, Table<ErrorLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private ErrorLog(Name alias, Table<ErrorLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> ErrorLog(Table<O> child, ForeignKey<O, ErrorLogRecord> key) {
        super(child, key, ERROR_LOG);
    }

    @Override
    public Schema getSchema() {
        return FwdbTest.FWDB_TEST;
    }

    @Override
    public Identity<ErrorLogRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ERROR_LOG;
    }

    @Override
    public UniqueKey<ErrorLogRecord> getPrimaryKey() {
        return Keys.KEY_ERROR_LOG_PRIMARY;
    }

    @Override
    public List<UniqueKey<ErrorLogRecord>> getKeys() {
        return Arrays.<UniqueKey<ErrorLogRecord>>asList(Keys.KEY_ERROR_LOG_PRIMARY);
    }

    @Override
    public ErrorLog as(String alias) {
        return new ErrorLog(DSL.name(alias), this);
    }

    @Override
    public ErrorLog as(Name alias) {
        return new ErrorLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ErrorLog rename(String name) {
        return new ErrorLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ErrorLog rename(Name name) {
        return new ErrorLog(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, LocalDateTime, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
