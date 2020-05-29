/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables;


import DB.Tables_Test.FwdbTest;
import DB.Tables_Test.Keys;
import DB.Tables_Test.tables.records.EventInjuryRecord;

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
public class EventInjury extends TableImpl<EventInjuryRecord> {

    private static final long serialVersionUID = 191683167;

    /**
     * The reference instance of <code>fwdb_test.event_injury</code>
     */
    public static final EventInjury EVENT_INJURY = new EventInjury();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EventInjuryRecord> getRecordType() {
        return EventInjuryRecord.class;
    }

    /**
     * The column <code>fwdb_test.event_injury.event_id</code>.
     */
    public final TableField<EventInjuryRecord, Integer> EVENT_ID = createField(DSL.name("event_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>fwdb_test.event_injury.game_id</code>.
     */
    public final TableField<EventInjuryRecord, Integer> GAME_ID = createField(DSL.name("game_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>fwdb_test.event_injury.injured_name</code>.
     */
    public final TableField<EventInjuryRecord, String> INJURED_NAME = createField(DSL.name("injured_name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.field("''", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>fwdb_test.event_injury.minute</code>.
     */
    public final TableField<EventInjuryRecord, Integer> MINUTE = createField(DSL.name("minute"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * Create a <code>fwdb_test.event_injury</code> table reference
     */
    public EventInjury() {
        this(DSL.name("event_injury"), null);
    }

    /**
     * Create an aliased <code>fwdb_test.event_injury</code> table reference
     */
    public EventInjury(String alias) {
        this(DSL.name(alias), EVENT_INJURY);
    }

    /**
     * Create an aliased <code>fwdb_test.event_injury</code> table reference
     */
    public EventInjury(Name alias) {
        this(alias, EVENT_INJURY);
    }

    private EventInjury(Name alias, Table<EventInjuryRecord> aliased) {
        this(alias, aliased, null);
    }

    private EventInjury(Name alias, Table<EventInjuryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> EventInjury(Table<O> child, ForeignKey<O, EventInjuryRecord> key) {
        super(child, key, EVENT_INJURY);
    }

    @Override
    public Schema getSchema() {
        return FwdbTest.FWDB_TEST;
    }

    @Override
    public Identity<EventInjuryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_EVENT_INJURY;
    }

    @Override
    public UniqueKey<EventInjuryRecord> getPrimaryKey() {
        return Keys.KEY_EVENT_INJURY_PRIMARY;
    }

    @Override
    public List<UniqueKey<EventInjuryRecord>> getKeys() {
        return Arrays.<UniqueKey<EventInjuryRecord>>asList(Keys.KEY_EVENT_INJURY_PRIMARY);
    }

    @Override
    public List<ForeignKey<EventInjuryRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<EventInjuryRecord, ?>>asList(Keys.FK_EVENT_INJURY_GAME, Keys.FK__PLAYER_INJURED);
    }

    public Game game() {
        return new Game(this, Keys.FK_EVENT_INJURY_GAME);
    }

    public Player player() {
        return new Player(this, Keys.FK__PLAYER_INJURED);
    }

    @Override
    public EventInjury as(String alias) {
        return new EventInjury(DSL.name(alias), this);
    }

    @Override
    public EventInjury as(Name alias) {
        return new EventInjury(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public EventInjury rename(String name) {
        return new EventInjury(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EventInjury rename(Name name) {
        return new EventInjury(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
