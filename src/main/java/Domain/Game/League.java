package Domain.Game;

import java.util.List;

public class League {
    private String name;
    private List<Season> seasons;

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
