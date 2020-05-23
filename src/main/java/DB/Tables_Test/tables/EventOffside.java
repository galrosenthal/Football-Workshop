/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables;


import DB.Tables_Test.FwdbTest;
import DB.Tables_Test.Keys;
import DB.Tables_Test.tables.records.EventOffsideRecord;

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
public class EventOffside extends TableImpl<EventOffsideRecord> {

    private static final long serialVersionUID = -1428901894;

    /**
     * The reference instance of <code>fwdb_test.event_offside</code>
     */
    public static final EventOffside EVENT_OFFSIDE = new EventOffside();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EventOffsideRecord> getRecordType() {
        return EventOffsideRecord.class;
    }

    /**
     * The column <code>fwdb_test.event_offside.event_id</code>.
     */
    public final TableField<EventOffsideRecord, Integer> EVENT_ID = createField(DSL.name("event_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>fwdb_test.event_offside.game_id</code>.
     */
    public final TableField<EventOffsideRecord, Integer> GAME_ID = createField(DSL.name("game_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>fwdb_test.event_offside.team_name</code>.
     */
    public final TableField<EventOffsideRecord, String> TEAM_NAME = createField(DSL.name("team_name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>fwdb_test.event_offside.minute</code>.
     */
    public final TableField<EventOffsideRecord, Integer> MINUTE = createField(DSL.name("minute"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>fwdb_test.event_offside</code> table reference
     */
    public EventOffside() {
        this(DSL.name("event_offside"), null);
    }

    /**
     * Create an aliased <code>fwdb_test.event_offside</code> table reference
     */
    public EventOffside(String alias) {
        this(DSL.name(alias), EVENT_OFFSIDE);
    }

    /**
     * Create an aliased <code>fwdb_test.event_offside</code> table reference
     */
    public EventOffside(Name alias) {
        this(alias, EVENT_OFFSIDE);
    }

    private EventOffside(Name alias, Table<EventOffsideRecord> aliased) {
        this(alias, aliased, null);
    }

    private EventOffside(Name alias, Table<EventOffsideRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> EventOffside(Table<O> child, ForeignKey<O, EventOffsideRecord> key) {
        super(child, key, EVENT_OFFSIDE);
    }

    @Override
    public Schema getSchema() {
        return FwdbTest.FWDB_TEST;
    }

    @Override
    public Identity<EventOffsideRecord, Integer> getIdentity() {
        return Keys.IDENTITY_EVENT_OFFSIDE;
    }

    @Override
    public UniqueKey<EventOffsideRecord> getPrimaryKey() {
        return Keys.KEY_EVENT_OFFSIDE_PRIMARY;
    }

    @Override
    public List<UniqueKey<EventOffsideRecord>> getKeys() {
        return Arrays.<UniqueKey<EventOffsideRecord>>asList(Keys.KEY_EVENT_OFFSIDE_PRIMARY);
    }

    @Override
    public List<ForeignKey<EventOffsideRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<EventOffsideRecord, ?>>asList(Keys.FK_EVENT_OFFSIDE_GAME, Keys.FK__TEAM_OFFSIDE);
    }

    public Game game() {
        return new Game(this, Keys.FK_EVENT_OFFSIDE_GAME);
    }

    public Team team() {
        return new Team(this, Keys.FK__TEAM_OFFSIDE);
    }

    @Override
    public EventOffside as(String alias) {
        return new EventOffside(DSL.name(alias), this);
    }

    @Override
    public EventOffside as(Name alias) {
        return new EventOffside(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public EventOffside rename(String name) {
        return new EventOffside(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EventOffside rename(Name name) {
        return new EventOffside(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
