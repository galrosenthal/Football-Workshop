/*
 * This file is generated by jOOQ.
 */
package DB.Tables.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum PlayerInTeamFieldJob implements EnumType {

    DEFENSE("DEFENSE"),

    GOAL_KEEPER("GOAL_KEEPER"),

    FRONT("FRONT");

    private final String literal;

    private PlayerInTeamFieldJob(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return null;
    }

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public String getName() {
        return "player_in_team_field_job";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
