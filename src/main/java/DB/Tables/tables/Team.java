/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables;


import DB.Tables.Fwdb;
import DB.Tables.Keys;
import DB.Tables.enums.TeamStatus;
import DB.Tables.tables.records.TeamRecord;

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
public class Team extends TableImpl<TeamRecord> {

    private static final long serialVersionUID = -672316430;

    /**
     * The reference instance of <code>fwdb.team</code>
     */
    public static final Team TEAM = new Team();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TeamRecord> getRecordType() {
        return TeamRecord.class;
    }

    /**
     * The column <code>fwdb.team.name</code>.
     */
    public final TableField<TeamRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>fwdb.team.status</code>.
     */
    public final TableField<TeamRecord, TeamStatus> STATUS = createField(DSL.name("status"), org.jooq.impl.SQLDataType.VARCHAR(18).nullable(false).defaultValue(org.jooq.impl.DSL.field("'OPEN'", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(DB.Tables.enums.TeamStatus.class), this, "");

    /**
     * Create a <code>fwdb.team</code> table reference
     */
    public Team() {
        this(DSL.name("team"), null);
    }

    /**
     * Create an aliased <code>fwdb.team</code> table reference
     */
    public Team(String alias) {
        this(DSL.name(alias), TEAM);
    }

    /**
     * Create an aliased <code>fwdb.team</code> table reference
     */
    public Team(Name alias) {
        this(alias, TEAM);
    }

    private Team(Name alias, Table<TeamRecord> aliased) {
        this(alias, aliased, null);
    }

    private Team(Name alias, Table<TeamRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Team(Table<O> child, ForeignKey<O, TeamRecord> key) {
        super(child, key, TEAM);
    }

    @Override
    public Schema getSchema() {
        return Fwdb.FWDB;
    }

    @Override
    public UniqueKey<TeamRecord> getPrimaryKey() {
        return Keys.KEY_TEAM_PRIMARY;
    }

    @Override
    public List<UniqueKey<TeamRecord>> getKeys() {
        return Arrays.<UniqueKey<TeamRecord>>asList(Keys.KEY_TEAM_PRIMARY);
    }

    @Override
    public Team as(String alias) {
        return new Team(DSL.name(alias), this);
    }

    @Override
    public Team as(Name alias) {
        return new Team(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Team rename(String name) {
        return new Team(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Team rename(Name name) {
        return new Team(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, TeamStatus> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
