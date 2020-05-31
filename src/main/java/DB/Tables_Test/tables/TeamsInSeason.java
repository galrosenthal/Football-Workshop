/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables;


import DB.Tables_Test.FwdbTest;
import DB.Tables_Test.Keys;
import DB.Tables_Test.tables.records.TeamsInSeasonRecord;

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
public class TeamsInSeason extends TableImpl<TeamsInSeasonRecord> {

    private static final long serialVersionUID = 1326039343;

    /**
     * The reference instance of <code>fwdb_test.teams_in_season</code>
     */
    public static final TeamsInSeason TEAMS_IN_SEASON = new TeamsInSeason();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TeamsInSeasonRecord> getRecordType() {
        return TeamsInSeasonRecord.class;
    }

    /**
     * The column <code>fwdb_test.teams_in_season.season_id</code>.
     */
    public final TableField<TeamsInSeasonRecord, Integer> SEASON_ID = createField(DSL.name("season_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>fwdb_test.teams_in_season.team_name</code>.
     */
    public final TableField<TeamsInSeasonRecord, String> TEAM_NAME = createField(DSL.name("team_name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * Create a <code>fwdb_test.teams_in_season</code> table reference
     */
    public TeamsInSeason() {
        this(DSL.name("teams_in_season"), null);
    }

    /**
     * Create an aliased <code>fwdb_test.teams_in_season</code> table reference
     */
    public TeamsInSeason(String alias) {
        this(DSL.name(alias), TEAMS_IN_SEASON);
    }

    /**
     * Create an aliased <code>fwdb_test.teams_in_season</code> table reference
     */
    public TeamsInSeason(Name alias) {
        this(alias, TEAMS_IN_SEASON);
    }

    private TeamsInSeason(Name alias, Table<TeamsInSeasonRecord> aliased) {
        this(alias, aliased, null);
    }

    private TeamsInSeason(Name alias, Table<TeamsInSeasonRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> TeamsInSeason(Table<O> child, ForeignKey<O, TeamsInSeasonRecord> key) {
        super(child, key, TEAMS_IN_SEASON);
    }

    @Override
    public Schema getSchema() {
        return FwdbTest.FWDB_TEST;
    }

    @Override
    public UniqueKey<TeamsInSeasonRecord> getPrimaryKey() {
        return Keys.KEY_TEAMS_IN_SEASON_PRIMARY;
    }

    @Override
    public List<UniqueKey<TeamsInSeasonRecord>> getKeys() {
        return Arrays.<UniqueKey<TeamsInSeasonRecord>>asList(Keys.KEY_TEAMS_IN_SEASON_PRIMARY);
    }

    @Override
    public List<ForeignKey<TeamsInSeasonRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TeamsInSeasonRecord, ?>>asList(Keys.FK_TEAMS_IN_SEASON_SEASON, Keys.FK__TEAM_NAME);
    }

    public Season season() {
        return new Season(this, Keys.FK_TEAMS_IN_SEASON_SEASON);
    }

    public Team team() {
        return new Team(this, Keys.FK__TEAM_NAME);
    }

    @Override
    public TeamsInSeason as(String alias) {
        return new TeamsInSeason(DSL.name(alias), this);
    }

    @Override
    public TeamsInSeason as(Name alias) {
        return new TeamsInSeason(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamsInSeason rename(String name) {
        return new TeamsInSeason(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamsInSeason rename(Name name) {
        return new TeamsInSeason(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
