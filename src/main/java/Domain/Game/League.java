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

    
    public boolean doesSeasonExists(String seasonYears) {
        for (Season currSeason : seasons) {
            String currSeasonYears = currSeason.getYears();
            if (currSeasonYears.equals(seasonYears)) {
                return true;
            }
        }
        return false;
    }

    public boolean addSeason(String seasonYears) {
        Season season = new Season(this, seasonYears);
        return this.seasons.add(season);
    }
}
