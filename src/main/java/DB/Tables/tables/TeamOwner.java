/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables;


import DB.Tables.Fwdb;
import DB.Tables.Keys;
import DB.Tables.tables.records.TeamOwnerRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row1;
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
public class TeamOwner extends TableImpl<TeamOwnerRecord> {

    private static final long serialVersionUID = -1757288799;

    /**
     * The reference instance of <code>fwdb.team_owner</code>
     */
    public static final TeamOwner TEAM_OWNER = new TeamOwner();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TeamOwnerRecord> getRecordType() {
        return TeamOwnerRecord.class;
    }

    /**
     * The column <code>fwdb.team_owner.username</code>.
     */
    public final TableField<TeamOwnerRecord, String> USERNAME = createField(DSL.name("username"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * Create a <code>fwdb.team_owner</code> table reference
     */
    public TeamOwner() {
        this(DSL.name("team_owner"), null);
    }

    /**
     * Create an aliased <code>fwdb.team_owner</code> table reference
     */
    public TeamOwner(String alias) {
        this(DSL.name(alias), TEAM_OWNER);
    }

    /**
     * Create an aliased <code>fwdb.team_owner</code> table reference
     */
    public TeamOwner(Name alias) {
        this(alias, TEAM_OWNER);
    }

    private TeamOwner(Name alias, Table<TeamOwnerRecord> aliased) {
        this(alias, aliased, null);
    }

    private TeamOwner(Name alias, Table<TeamOwnerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> TeamOwner(Table<O> child, ForeignKey<O, TeamOwnerRecord> key) {
        super(child, key, TEAM_OWNER);
    }

    @Override
    public Schema getSchema() {
        return Fwdb.FWDB;
    }

    @Override
    public UniqueKey<TeamOwnerRecord> getPrimaryKey() {
        return Keys.KEY_TEAM_OWNER_PRIMARY;
    }

    @Override
    public List<UniqueKey<TeamOwnerRecord>> getKeys() {
        return Arrays.<UniqueKey<TeamOwnerRecord>>asList(Keys.KEY_TEAM_OWNER_PRIMARY);
    }

    @Override
    public List<ForeignKey<TeamOwnerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TeamOwnerRecord, ?>>asList(Keys.FK__USER_ROLES);
    }

    public UserRoles userRoles() {
        return new UserRoles(this, Keys.FK__USER_ROLES);
    }

    @Override
    public TeamOwner as(String alias) {
        return new TeamOwner(DSL.name(alias), this);
    }

    @Override
    public TeamOwner as(Name alias) {
        return new TeamOwner(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamOwner rename(String name) {
        return new TeamOwner(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamOwner rename(Name name) {
        return new TeamOwner(name, null);
    }

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row1<String> fieldsRow() {
        return (Row1) super.fieldsRow();
    }
}
