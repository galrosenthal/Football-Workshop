/*
 * This file is generated by jOOQ.
 */
package DB.Tables_Test.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum TeamStatus implements EnumType {

    OPEN("OPEN"),

    CLOSED("CLOSED"),

    PERMANENTLY_CLOSED("PERMANENTLY_CLOSED");

    private final String literal;

    private TeamStatus(String literal) {
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
        return "team_status";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
