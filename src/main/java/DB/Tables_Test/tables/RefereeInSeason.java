/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables;


import DB.Tables_Test.FwdbTest;
import DB.Tables_Test.Keys;
import DB.Tables_Test.tables.records.RefereeInSeasonRecord;

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
public class RefereeInSeason extends TableImpl<RefereeInSeasonRecord> {

    private static final long serialVersionUID = -1184221365;

    /**
     * The reference instance of <code>fwdb_test.referee_in_season</code>
     */
    public static final RefereeInSeason REFEREE_IN_SEASON = new RefereeInSeason();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RefereeInSeasonRecord> getRecordType() {
        return RefereeInSeasonRecord.class;
    }

    /**
     * The column <code>fwdb_test.referee_in_season.username</code>.
     */
    public final TableField<RefereeInSeasonRecord, String> USERNAME = createField(DSL.name("username"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>fwdb_test.referee_in_season.season_id</code>.
     */
    public final TableField<RefereeInSeasonRecord, Integer> SEASON_ID = createField(DSL.name("season_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>fwdb_test.referee_in_season</code> table reference
     */
    public RefereeInSeason() {
        this(DSL.name("referee_in_season"), null);
    }

    /**
     * Create an aliased <code>fwdb_test.referee_in_season</code> table reference
     */
    public RefereeInSeason(String alias) {
        this(DSL.name(alias), REFEREE_IN_SEASON);
    }

    /**
     * Create an aliased <code>fwdb_test.referee_in_season</code> table reference
     */
    public RefereeInSeason(Name alias) {
        this(alias, REFEREE_IN_SEASON);
    }

    private RefereeInSeason(Name alias, Table<RefereeInSeasonRecord> aliased) {
        this(alias, aliased, null);
    }

    private RefereeInSeason(Name alias, Table<RefereeInSeasonRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> RefereeInSeason(Table<O> child, ForeignKey<O, RefereeInSeasonRecord> key) {
        super(child, key, REFEREE_IN_SEASON);
    }

    @Override
    public Schema getSchema() {
        return FwdbTest.FWDB_TEST;
    }

    @Override
    public UniqueKey<RefereeInSeasonRecord> getPrimaryKey() {
        return Keys.KEY_REFEREE_IN_SEASON_PRIMARY;
    }

    @Override
    public List<UniqueKey<RefereeInSeasonRecord>> getKeys() {
        return Arrays.<UniqueKey<RefereeInSeasonRecord>>asList(Keys.KEY_REFEREE_IN_SEASON_PRIMARY);
    }

    @Override
    public List<ForeignKey<RefereeInSeasonRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RefereeInSeasonRecord, ?>>asList(Keys.FK_REFEREE_IN_SEASON_REFEREE, Keys.FK_REFEREE_IN_SEASON_SEASON);
    }

    public Referee referee() {
        return new Referee(this, Keys.FK_REFEREE_IN_SEASON_REFEREE);
    }

    public Season season() {
        return new Season(this, Keys.FK_REFEREE_IN_SEASON_SEASON);
    }

    @Override
    public RefereeInSeason as(String alias) {
        return new RefereeInSeason(DSL.name(alias), this);
    }

    @Override
    public RefereeInSeason as(Name alias) {
        return new RefereeInSeason(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RefereeInSeason rename(String name) {
        return new RefereeInSeason(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RefereeInSeason rename(Name name) {
        return new RefereeInSeason(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
