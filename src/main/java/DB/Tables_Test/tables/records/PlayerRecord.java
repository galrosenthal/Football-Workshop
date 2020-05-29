/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables.records;


import DB.Tables_Test.tables.Player;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlayerRecord extends UpdatableRecordImpl<PlayerRecord> implements Record2<String, LocalDate> {

    private static final long serialVersionUID = 303581421;

    /**
     * Setter for <code>fwdb_test.player.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb_test.player.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>fwdb_test.player.birthday</code>.
     */
    public void setBirthday(LocalDate value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb_test.player.birthday</code>.
     */
    public LocalDate getBirthday() {
        return (LocalDate) get(1);
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
    public Row2<String, LocalDate> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, LocalDate> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Player.PLAYER.USERNAME;
    }

    @Override
    public Field<LocalDate> field2() {
        return Player.PLAYER.BIRTHDAY;
    }

    @Override
    public String component1() {
        return getUsername();
    }

    @Override
    public LocalDate component2() {
        return getBirthday();
    }

    @Override
    public String value1() {
        return getUsername();
    }

    @Override
    public LocalDate value2() {
        return getBirthday();
    }

    @Override
    public PlayerRecord value1(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public PlayerRecord value2(LocalDate value) {
        setBirthday(value);
        return this;
    }

    @Override
    public PlayerRecord values(String value1, LocalDate value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlayerRecord
     */
    public PlayerRecord() {
        super(Player.PLAYER);
    }

    /**
     * Create a detached, initialised PlayerRecord
     */
    public PlayerRecord(String username, LocalDate birthday) {
        super(Player.PLAYER);

        set(0, username);
        set(1, birthday);
    }
}
