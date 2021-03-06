/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables.records;


import DB.Tables_Test.tables.UserComplaint;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserComplaintRecord extends UpdatableRecordImpl<UserComplaintRecord> implements Record6<Integer, String, String, String, LocalDate, Boolean> {

    private static final long serialVersionUID = -263901347;

    /**
     * Setter for <code>fwdb_test.user_complaint.complaint_id</code>.
     */
    public void setComplaintId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb_test.user_complaint.complaint_id</code>.
     */
    public Integer getComplaintId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>fwdb_test.user_complaint.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb_test.user_complaint.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>fwdb_test.user_complaint.title</code>.
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb_test.user_complaint.title</code>.
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>fwdb_test.user_complaint.content</code>.
     */
    public void setContent(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>fwdb_test.user_complaint.content</code>.
     */
    public String getContent() {
        return (String) get(3);
    }

    /**
     * Setter for <code>fwdb_test.user_complaint.date</code>.
     */
    public void setDate(LocalDate value) {
        set(4, value);
    }

    /**
     * Getter for <code>fwdb_test.user_complaint.date</code>.
     */
    public LocalDate getDate() {
        return (LocalDate) get(4);
    }

    /**
     * Setter for <code>fwdb_test.user_complaint.active</code>.
     */
    public void setActive(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>fwdb_test.user_complaint.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, String, LocalDate, Boolean> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, String, LocalDate, Boolean> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return UserComplaint.USER_COMPLAINT.COMPLAINT_ID;
    }

    @Override
    public Field<String> field2() {
        return UserComplaint.USER_COMPLAINT.USERNAME;
    }

    @Override
    public Field<String> field3() {
        return UserComplaint.USER_COMPLAINT.TITLE;
    }

    @Override
    public Field<String> field4() {
        return UserComplaint.USER_COMPLAINT.CONTENT;
    }

    @Override
    public Field<LocalDate> field5() {
        return UserComplaint.USER_COMPLAINT.DATE;
    }

    @Override
    public Field<Boolean> field6() {
        return UserComplaint.USER_COMPLAINT.ACTIVE;
    }

    @Override
    public Integer component1() {
        return getComplaintId();
    }

    @Override
    public String component2() {
        return getUsername();
    }

    @Override
    public String component3() {
        return getTitle();
    }

    @Override
    public String component4() {
        return getContent();
    }

    @Override
    public LocalDate component5() {
        return getDate();
    }

    @Override
    public Boolean component6() {
        return getActive();
    }

    @Override
    public Integer value1() {
        return getComplaintId();
    }

    @Override
    public String value2() {
        return getUsername();
    }

    @Override
    public String value3() {
        return getTitle();
    }

    @Override
    public String value4() {
        return getContent();
    }

    @Override
    public LocalDate value5() {
        return getDate();
    }

    @Override
    public Boolean value6() {
        return getActive();
    }

    @Override
    public UserComplaintRecord value1(Integer value) {
        setComplaintId(value);
        return this;
    }

    @Override
    public UserComplaintRecord value2(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public UserComplaintRecord value3(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public UserComplaintRecord value4(String value) {
        setContent(value);
        return this;
    }

    @Override
    public UserComplaintRecord value5(LocalDate value) {
        setDate(value);
        return this;
    }

    @Override
    public UserComplaintRecord value6(Boolean value) {
        setActive(value);
        return this;
    }

    @Override
    public UserComplaintRecord values(Integer value1, String value2, String value3, String value4, LocalDate value5, Boolean value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserComplaintRecord
     */
    public UserComplaintRecord() {
        super(UserComplaint.USER_COMPLAINT);
    }

    /**
     * Create a detached, initialised UserComplaintRecord
     */
    public UserComplaintRecord(Integer complaintId, String username, String title, String content, LocalDate date, Boolean active) {
        super(UserComplaint.USER_COMPLAINT);

        set(0, complaintId);
        set(1, username);
        set(2, title);
        set(3, content);
        set(4, date);
        set(5, active);
    }
}
