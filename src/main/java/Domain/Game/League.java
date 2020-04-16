package Domain.Game;

import java.util.ArrayList;
import java.util.List;

public class League {
    private String name;

    /**
     * Constructor
     *
     * @param leagueName - String - League name
     */
    public League(String leagueName) {
        this.name = leagueName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
