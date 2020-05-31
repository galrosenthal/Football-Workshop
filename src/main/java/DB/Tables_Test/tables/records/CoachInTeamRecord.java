/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.tables.records;


import DB.Tables_Test.tables.CoachInTeam;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CoachInTeamRecord extends UpdatableRecordImpl<CoachInTeamRecord> implements Record3<String, String, String> {

    private static final long serialVersionUID = -992336432;

    /**
     * Setter for <code>fwdb_test.coach_in_team.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb_test.coach_in_team.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>fwdb_test.coach_in_team.team_name</code>.
     */
    public void setTeamName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb_test.coach_in_team.team_name</code>.
     */
    public String getTeamName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>fwdb_test.coach_in_team.team_job</code>.
     */
    public void setTeamJob(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb_test.coach_in_team.team_job</code>.
     */
    public String getTeamJob() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<String, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return CoachInTeam.COACH_IN_TEAM.USERNAME;
    }

    @Override
    public Field<String> field2() {
        return CoachInTeam.COACH_IN_TEAM.TEAM_NAME;
    }

    @Override
    public Field<String> field3() {
        return CoachInTeam.COACH_IN_TEAM.TEAM_JOB;
    }

    @Override
    public String component1() {
        return getUsername();
    }

    @Override
    public String component2() {
        return getTeamName();
    }

    @Override
    public String component3() {
        return getTeamJob();
    }

    @Override
    public String value1() {
        return getUsername();
    }

    @Override
    public String value2() {
        return getTeamName();
    }

    @Override
    public String value3() {
        return getTeamJob();
    }

    @Override
    public CoachInTeamRecord value1(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public CoachInTeamRecord value2(String value) {
        setTeamName(value);
        return this;
    }

    @Override
    public CoachInTeamRecord value3(String value) {
        setTeamJob(value);
        return this;
    }

    @Override
    public CoachInTeamRecord values(String value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CoachInTeamRecord
     */
    public CoachInTeamRecord() {
        super(CoachInTeam.COACH_IN_TEAM);
    }

    /**
     * Create a detached, initialised CoachInTeamRecord
     */
    public CoachInTeamRecord(String username, String teamName, String teamJob) {
        super(CoachInTeam.COACH_IN_TEAM);

        set(0, username);
        set(1, teamName);
        set(2, teamJob);
    }
}
