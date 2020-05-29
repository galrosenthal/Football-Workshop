/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables;


import DB.Tables.Fwdb;
import DB.Tables.Keys;
import DB.Tables.tables.records.PlayerRecord;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
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
public class Player extends TableImpl<PlayerRecord> {

    private static final long serialVersionUID = 519374778;

    /**
     * The reference instance of <code>fwdb.player</code>
     */
    public static final Player PLAYER = new Player();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlayerRecord> getRecordType() {
        return PlayerRecord.class;
    }

    /**
     * The column <code>fwdb.player.username</code>.
     */
    public final TableField<PlayerRecord, String> USERNAME = createField(DSL.name("username"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>fwdb.player.birthday</code>.
     */
    public final TableField<PlayerRecord, LocalDate> BIRTHDAY = createField(DSL.name("birthday"), org.jooq.impl.SQLDataType.LOCALDATE.nullable(false), this, "");

    /**
     * Create a <code>fwdb.player</code> table reference
     */
    public Player() {
        this(DSL.name("player"), null);
    }

    /**
     * Create an aliased <code>fwdb.player</code> table reference
     */
    public Player(String alias) {
        this(DSL.name(alias), PLAYER);
    }

    /**
     * Create an aliased <code>fwdb.player</code> table reference
     */
    public Player(Name alias) {
        this(alias, PLAYER);
    }

    private Player(Name alias, Table<PlayerRecord> aliased) {
        this(alias, aliased, null);
    }

    private Player(Name alias, Table<PlayerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Player(Table<O> child, ForeignKey<O, PlayerRecord> key) {
        super(child, key, PLAYER);
    }

    @Override
    public Schema getSchema() {
        return Fwdb.FWDB;
    }

    @Override
    public UniqueKey<PlayerRecord> getPrimaryKey() {
        return Keys.KEY_PLAYER_PRIMARY;
    }

    @Override
    public List<UniqueKey<PlayerRecord>> getKeys() {
        return Arrays.<UniqueKey<PlayerRecord>>asList(Keys.KEY_PLAYER_PRIMARY);
    }

    @Override
    public List<ForeignKey<PlayerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PlayerRecord, ?>>asList(Keys.FK_PLAYER_USER_ROLES);
    }

    public UserRoles userRoles() {
        return new UserRoles(this, Keys.FK_PLAYER_USER_ROLES);
    }

    @Override
    public Player as(String alias) {
        return new Player(DSL.name(alias), this);
    }

    @Override
    public Player as(Name alias) {
        return new Player(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Player rename(String name) {
        return new Player(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Player rename(Name name) {
        return new Player(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, LocalDate> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
