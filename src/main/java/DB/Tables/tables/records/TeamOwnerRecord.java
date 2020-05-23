/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.TeamOwner;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TeamOwnerRecord extends UpdatableRecordImpl<TeamOwnerRecord> implements Record2<String, String> {

    private static final long serialVersionUID = -1416498067;

    /**
     * Setter for <code>fwdb.team_owner.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.team_owner.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>fwdb.team_owner.appointer</code>.
     */
    public void setAppointer(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.team_owner.appointer</code>.
     */
    public String getAppointer() {
        return (String) get(1);
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
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return TeamOwner.TEAM_OWNER.USERNAME;
    }

    @Override
    public Field<String> field2() {
        return TeamOwner.TEAM_OWNER.APPOINTER;
    }

    @Override
    public String component1() {
        return getUsername();
    }

    @Override
    public String component2() {
        return getAppointer();
    }

    @Override
    public String value1() {
        return getUsername();
    }

    @Override
    public String value2() {
        return getAppointer();
    }

    @Override
    public TeamOwnerRecord value1(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public TeamOwnerRecord value2(String value) {
        setAppointer(value);
        return this;
    }

    @Override
    public TeamOwnerRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TeamOwnerRecord
     */
    public TeamOwnerRecord() {
        super(TeamOwner.TEAM_OWNER);
    }

    /**
     * Create a detached, initialised TeamOwnerRecord
     */
    public TeamOwnerRecord(String username, String appointer) {
        super(TeamOwner.TEAM_OWNER);

        set(0, username);
        set(1, appointer);
    }
}
