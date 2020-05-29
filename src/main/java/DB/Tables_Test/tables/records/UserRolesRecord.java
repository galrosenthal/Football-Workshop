/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables.records;


import DB.Tables_Test.enums.UserRolesRoleType;
import DB.Tables_Test.tables.UserRoles;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRolesRecord extends UpdatableRecordImpl<UserRolesRecord> implements Record2<String, UserRolesRoleType> {

    private static final long serialVersionUID = 2064190621;

    /**
     * Setter for <code>fwdb_test.user_roles.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb_test.user_roles.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>fwdb_test.user_roles.role_type</code>.
     */
    public void setRoleType(UserRolesRoleType value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb_test.user_roles.role_type</code>.
     */
    public UserRolesRoleType getRoleType() {
        return (UserRolesRoleType) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, UserRolesRoleType> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, UserRolesRoleType> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, UserRolesRoleType> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return UserRoles.USER_ROLES.USERNAME;
    }

    @Override
    public Field<UserRolesRoleType> field2() {
        return UserRoles.USER_ROLES.ROLE_TYPE;
    }

    @Override
    public String component1() {
        return getUsername();
    }

    @Override
    public UserRolesRoleType component2() {
        return getRoleType();
    }

    @Override
    public String value1() {
        return getUsername();
    }

    @Override
    public UserRolesRoleType value2() {
        return getRoleType();
    }

    @Override
    public UserRolesRecord value1(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public UserRolesRecord value2(UserRolesRoleType value) {
        setRoleType(value);
        return this;
    }

    @Override
    public UserRolesRecord values(String value1, UserRolesRoleType value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRolesRecord
     */
    public UserRolesRecord() {
        super(UserRoles.USER_ROLES);
    }

    /**
     * Create a detached, initialised UserRolesRecord
     */
    public UserRolesRecord(String username, UserRolesRoleType roleType) {
        super(UserRoles.USER_ROLES);

        set(0, username);
        set(1, roleType);
    }
}
