/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables.records;


import DB.Tables_Test.enums.RefereeTraining;
import DB.Tables_Test.tables.Referee;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RefereeRecord extends UpdatableRecordImpl<RefereeRecord> implements Record2<String, RefereeTraining> {

    private static final long serialVersionUID = -197269359;

    /**
     * Setter for <code>fwdb_test.referee.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb_test.referee.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>fwdb_test.referee.training</code>.
     */
    public void setTraining(RefereeTraining value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb_test.referee.training</code>.
     */
    public RefereeTraining getTraining() {
        return (RefereeTraining) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, RefereeTraining> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, RefereeTraining> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Referee.REFEREE.USERNAME;
    }

    @Override
    public Field<RefereeTraining> field2() {
        return Referee.REFEREE.TRAINING;
    }

    @Override
    public String component1() {
        return getUsername();
    }

    @Override
    public RefereeTraining component2() {
        return getTraining();
    }

    @Override
    public String value1() {
        return getUsername();
    }

    @Override
    public RefereeTraining value2() {
        return getTraining();
    }

    @Override
    public RefereeRecord value1(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public RefereeRecord value2(RefereeTraining value) {
        setTraining(value);
        return this;
    }

    @Override
    public RefereeRecord values(String value1, RefereeTraining value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RefereeRecord
     */
    public RefereeRecord() {
        super(Referee.REFEREE);
    }

    /**
     * Create a detached, initialised RefereeRecord
     */
    public RefereeRecord(String username, RefereeTraining training) {
        super(Referee.REFEREE);

        set(0, username);
        set(1, training);
    }
}
