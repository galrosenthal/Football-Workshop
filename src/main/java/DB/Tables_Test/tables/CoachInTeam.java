/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables;


import DB.Tables_Test.FwdbTest;
import DB.Tables_Test.Keys;
import DB.Tables_Test.tables.records.CoachInTeamRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class CoachInTeam extends TableImpl<CoachInTeamRecord> {

    private static final long serialVersionUID = -344385972;

    /**
     * The reference instance of <code>fwdb_test.coach_in_team</code>
     */
    public static final CoachInTeam COACH_IN_TEAM = new CoachInTeam();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CoachInTeamRecord> getRecordType() {
        return CoachInTeamRecord.class;
    }

    /**
     * The column <code>fwdb_test.coach_in_team.username</code>.
     */
    public final TableField<CoachInTeamRecord, String> USERNAME = createField(DSL.name("username"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>fwdb_test.coach_in_team.team_name</code>.
     */
    public final TableField<CoachInTeamRecord, String> TEAM_NAME = createField(DSL.name("team_name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>fwdb_test.coach_in_team.team_job</code>.
     */
    public final TableField<CoachInTeamRecord, String> TEAM_JOB = createField(DSL.name("team_job"), org.jooq.impl.SQLDataType.VARCHAR(50).defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>fwdb_test.coach_in_team</code> table reference
     */
    public CoachInTeam() {
        this(DSL.name("coach_in_team"), null);
    }

    /**
     * Create an aliased <code>fwdb_test.coach_in_team</code> table reference
     */
    public CoachInTeam(String alias) {
        this(DSL.name(alias), COACH_IN_TEAM);
    }

    /**
     * Create an aliased <code>fwdb_test.coach_in_team</code> table reference
     */
    public CoachInTeam(Name alias) {
        this(alias, COACH_IN_TEAM);
    }

    private CoachInTeam(Name alias, Table<CoachInTeamRecord> aliased) {
        this(alias, aliased, null);
    }

    private CoachInTeam(Name alias, Table<CoachInTeamRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> CoachInTeam(Table<O> child, ForeignKey<O, CoachInTeamRecord> key) {
        super(child, key, COACH_IN_TEAM);
    }

    @Override
    public Schema getSchema() {
        return FwdbTest.FWDB_TEST;
    }

    @Override
    public UniqueKey<CoachInTeamRecord> getPrimaryKey() {
        return Keys.KEY_COACH_IN_TEAM_PRIMARY;
    }

    @Override
    public List<UniqueKey<CoachInTeamRecord>> getKeys() {
        return Arrays.<UniqueKey<CoachInTeamRecord>>asList(Keys.KEY_COACH_IN_TEAM_PRIMARY);
    }

    @Override
    public List<ForeignKey<CoachInTeamRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CoachInTeamRecord, ?>>asList(Keys.FK_COACH_IN_TEAM_COACH, Keys.FK_COACH_IN_TEAM_TEAM);
    }

    public Coach coach() {
        return new Coach(this, Keys.FK_COACH_IN_TEAM_COACH);
    }

    public Team team() {
        return new Team(this, Keys.FK_COACH_IN_TEAM_TEAM);
    }

    @Override
    public CoachInTeam as(String alias) {
        return new CoachInTeam(DSL.name(alias), this);
    }

    @Override
    public CoachInTeam as(Name alias) {
        return new CoachInTeam(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CoachInTeam rename(String name) {
        return new CoachInTeam(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CoachInTeam rename(Name name) {
        return new CoachInTeam(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
