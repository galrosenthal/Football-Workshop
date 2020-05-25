/*
 * This file is generated by jOOQ.
 */
package DB.Tables.tables.records;


import DB.Tables.tables.ManagerInTeams;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ManagerInTeamsRecord extends UpdatableRecordImpl<ManagerInTeamsRecord> implements Record8<String, String, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> {

    private static final long serialVersionUID = -1915811805;

    /**
     * Setter for <code>fwdb.manager_in_teams.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>fwdb.manager_in_teams.team_name</code>.
     */
    public void setTeamName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.team_name</code>.
     */
    public String getTeamName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>fwdb.manager_in_teams.REMOVE_PLAYER</code>.
     */
    public void setRemovePlayer(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.REMOVE_PLAYER</code>.
     */
    public Boolean getRemovePlayer() {
        return (Boolean) get(2);
    }

    /**
     * Setter for <code>fwdb.manager_in_teams.ADD_PLAYER</code>.
     */
    public void setAddPlayer(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.ADD_PLAYER</code>.
     */
    public Boolean getAddPlayer() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>fwdb.manager_in_teams.CHANGE_POSITION_PLAYER</code>.
     */
    public void setChangePositionPlayer(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.CHANGE_POSITION_PLAYER</code>.
     */
    public Boolean getChangePositionPlayer() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>fwdb.manager_in_teams.REMOVE_COACH</code>.
     */
    public void setRemoveCoach(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.REMOVE_COACH</code>.
     */
    public Boolean getRemoveCoach() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>fwdb.manager_in_teams.ADD_COACH</code>.
     */
    public void setAddCoach(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.ADD_COACH</code>.
     */
    public Boolean getAddCoach() {
        return (Boolean) get(6);
    }

    /**
     * Setter for <code>fwdb.manager_in_teams.CHANGE_TEAM_JOB_COACH</code>.
     */
    public void setChangeTeamJobCoach(Boolean value) {
        set(7, value);
    }

    /**
     * Getter for <code>fwdb.manager_in_teams.CHANGE_TEAM_JOB_COACH</code>.
     */
    public Boolean getChangeTeamJobCoach() {
        return (Boolean) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<String, String, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<String, String, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return ManagerInTeams.MANAGER_IN_TEAMS.USERNAME;
    }

    @Override
    public Field<String> field2() {
        return ManagerInTeams.MANAGER_IN_TEAMS.TEAM_NAME;
    }

    @Override
    public Field<Boolean> field3() {
        return ManagerInTeams.MANAGER_IN_TEAMS.REMOVE_PLAYER;
    }

    @Override
    public Field<Boolean> field4() {
        return ManagerInTeams.MANAGER_IN_TEAMS.ADD_PLAYER;
    }

    @Override
    public Field<Boolean> field5() {
        return ManagerInTeams.MANAGER_IN_TEAMS.CHANGE_POSITION_PLAYER;
    }

    @Override
    public Field<Boolean> field6() {
        return ManagerInTeams.MANAGER_IN_TEAMS.REMOVE_COACH;
    }

    @Override
    public Field<Boolean> field7() {
        return ManagerInTeams.MANAGER_IN_TEAMS.ADD_COACH;
    }

    @Override
    public Field<Boolean> field8() {
        return ManagerInTeams.MANAGER_IN_TEAMS.CHANGE_TEAM_JOB_COACH;
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
    public Boolean component3() {
        return getRemovePlayer();
    }

    @Override
    public Boolean component4() {
        return getAddPlayer();
    }

    @Override
    public Boolean component5() {
        return getChangePositionPlayer();
    }

    @Override
    public Boolean component6() {
        return getRemoveCoach();
    }

    @Override
    public Boolean component7() {
        return getAddCoach();
    }

    @Override
    public Boolean component8() {
        return getChangeTeamJobCoach();
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
    public Boolean value3() {
        return getRemovePlayer();
    }

    @Override
    public Boolean value4() {
        return getAddPlayer();
    }

    @Override
    public Boolean value5() {
        return getChangePositionPlayer();
    }

    @Override
    public Boolean value6() {
        return getRemoveCoach();
    }

    @Override
    public Boolean value7() {
        return getAddCoach();
    }

    @Override
    public Boolean value8() {
        return getChangeTeamJobCoach();
    }

    @Override
    public ManagerInTeamsRecord value1(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord value2(String value) {
        setTeamName(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord value3(Boolean value) {
        setRemovePlayer(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord value4(Boolean value) {
        setAddPlayer(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord value5(Boolean value) {
        setChangePositionPlayer(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord value6(Boolean value) {
        setRemoveCoach(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord value7(Boolean value) {
        setAddCoach(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord value8(Boolean value) {
        setChangeTeamJobCoach(value);
        return this;
    }

    @Override
    public ManagerInTeamsRecord values(String value1, String value2, Boolean value3, Boolean value4, Boolean value5, Boolean value6, Boolean value7, Boolean value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ManagerInTeamsRecord
     */
    public ManagerInTeamsRecord() {
        super(ManagerInTeams.MANAGER_IN_TEAMS);
    }

    /**
     * Create a detached, initialised ManagerInTeamsRecord
     */
    public ManagerInTeamsRecord(String username, String teamName, Boolean removePlayer, Boolean addPlayer, Boolean changePositionPlayer, Boolean removeCoach, Boolean addCoach, Boolean changeTeamJobCoach) {
        super(ManagerInTeams.MANAGER_IN_TEAMS);

        set(0, username);
        set(1, teamName);
        set(2, removePlayer);
        set(3, addPlayer);
        set(4, changePositionPlayer);
        set(5, removeCoach);
        set(6, addCoach);
        set(7, changeTeamJobCoach);
    }
}
