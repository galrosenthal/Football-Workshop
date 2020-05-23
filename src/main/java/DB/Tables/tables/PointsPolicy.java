/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables;


import DB.Tables.Fwdb;
import DB.Tables.Keys;
import DB.Tables.tables.records.PointsPolicyRecord;

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
public class PointsPolicy extends TableImpl<PointsPolicyRecord> {

    private static final long serialVersionUID = 375724829;

    /**
     * The reference instance of <code>fwdb.points_policy</code>
     */
    public static final PointsPolicy POINTS_POLICY = new PointsPolicy();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PointsPolicyRecord> getRecordType() {
        return PointsPolicyRecord.class;
    }

    /**
     * The column <code>fwdb.points_policy.policy_id</code>.
     */
    public final TableField<PointsPolicyRecord, Integer> POLICY_ID = createField(DSL.name("policy_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>fwdb.points_policy.victory_points</code>.
     */
    public final TableField<PointsPolicyRecord, Integer> VICTORY_POINTS = createField(DSL.name("victory_points"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>fwdb.points_policy.loss_points</code>.
     */
    public final TableField<PointsPolicyRecord, Integer> LOSS_POINTS = createField(DSL.name("loss_points"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>fwdb.points_policy.tie_points</code>.
     */
    public final TableField<PointsPolicyRecord, Integer> TIE_POINTS = createField(DSL.name("tie_points"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>fwdb.points_policy</code> table reference
     */
    public PointsPolicy() {
        this(DSL.name("points_policy"), null);
    }

    /**
     * Create an aliased <code>fwdb.points_policy</code> table reference
     */
    public PointsPolicy(String alias) {
        this(DSL.name(alias), POINTS_POLICY);
    }

    /**
     * Create an aliased <code>fwdb.points_policy</code> table reference
     */
    public PointsPolicy(Name alias) {
        this(alias, POINTS_POLICY);
    }

    private PointsPolicy(Name alias, Table<PointsPolicyRecord> aliased) {
        this(alias, aliased, null);
    }

    private PointsPolicy(Name alias, Table<PointsPolicyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> PointsPolicy(Table<O> child, ForeignKey<O, PointsPolicyRecord> key) {
        super(child, key, POINTS_POLICY);
    }

    @Override
    public Schema getSchema() {
        return Fwdb.FWDB;
    }

    @Override
    public Identity<PointsPolicyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_POINTS_POLICY;
    }

    @Override
    public UniqueKey<PointsPolicyRecord> getPrimaryKey() {
        return Keys.KEY_POINTS_POLICY_PRIMARY;
    }

    @Override
    public List<UniqueKey<PointsPolicyRecord>> getKeys() {
        return Arrays.<UniqueKey<PointsPolicyRecord>>asList(Keys.KEY_POINTS_POLICY_PRIMARY);
    }

    @Override
    public PointsPolicy as(String alias) {
        return new PointsPolicy(DSL.name(alias), this);
    }

    @Override
    public PointsPolicy as(Name alias) {
        return new PointsPolicy(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PointsPolicy rename(String name) {
        return new PointsPolicy(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PointsPolicy rename(Name name) {
        return new PointsPolicy(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
