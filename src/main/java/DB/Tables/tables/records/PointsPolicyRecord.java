/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.PointsPolicy;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PointsPolicyRecord extends UpdatableRecordImpl<PointsPolicyRecord> implements Record4<Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 112369976;

    /**
     * Setter for <code>fwdb.points_policy.policy_id</code>.
     */
    public void setPolicyId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.points_policy.policy_id</code>.
     */
    public Integer getPolicyId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>fwdb.points_policy.victory_points</code>.
     */
    public void setVictoryPoints(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.points_policy.victory_points</code>.
     */
    public Integer getVictoryPoints() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>fwdb.points_policy.loss_points</code>.
     */
    public void setLossPoints(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb.points_policy.loss_points</code>.
     */
    public Integer getLossPoints() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>fwdb.points_policy.tie_points</code>.
     */
    public void setTiePoints(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>fwdb.points_policy.tie_points</code>.
     */
    public Integer getTiePoints() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, Integer, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return PointsPolicy.POINTS_POLICY.POLICY_ID;
    }

    @Override
    public Field<Integer> field2() {
        return PointsPolicy.POINTS_POLICY.VICTORY_POINTS;
    }

    @Override
    public Field<Integer> field3() {
        return PointsPolicy.POINTS_POLICY.LOSS_POINTS;
    }

    @Override
    public Field<Integer> field4() {
        return PointsPolicy.POINTS_POLICY.TIE_POINTS;
    }

    @Override
    public Integer component1() {
        return getPolicyId();
    }

    @Override
    public Integer component2() {
        return getVictoryPoints();
    }

    @Override
    public Integer component3() {
        return getLossPoints();
    }

    @Override
    public Integer component4() {
        return getTiePoints();
    }

    @Override
    public Integer value1() {
        return getPolicyId();
    }

    @Override
    public Integer value2() {
        return getVictoryPoints();
    }

    @Override
    public Integer value3() {
        return getLossPoints();
    }

    @Override
    public Integer value4() {
        return getTiePoints();
    }

    @Override
    public PointsPolicyRecord value1(Integer value) {
        setPolicyId(value);
        return this;
    }

    @Override
    public PointsPolicyRecord value2(Integer value) {
        setVictoryPoints(value);
        return this;
    }

    @Override
    public PointsPolicyRecord value3(Integer value) {
        setLossPoints(value);
        return this;
    }

    @Override
    public PointsPolicyRecord value4(Integer value) {
        setTiePoints(value);
        return this;
    }

    @Override
    public PointsPolicyRecord values(Integer value1, Integer value2, Integer value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PointsPolicyRecord
     */
    public PointsPolicyRecord() {
        super(PointsPolicy.POINTS_POLICY);
    }

    /**
     * Create a detached, initialised PointsPolicyRecord
     */
    public PointsPolicyRecord(Integer policyId, Integer victoryPoints, Integer lossPoints, Integer tiePoints) {
        super(PointsPolicy.POINTS_POLICY);

        set(0, policyId);
        set(1, victoryPoints);
        set(2, lossPoints);
        set(3, tiePoints);
    }
}
