package Domain.Game;

import Domain.EntityManager;

import java.util.ArrayList;
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
        this.seasons = new ArrayList<>();
    }

    /**
     * Getter for the name
     *
     * @return - String - League name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     *
     * @param name - String - League name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the seasons
     *
     * @return - List<Season>
     */
    public List<Season> getSeasons() {
        return new ArrayList<>(seasons);
    }

    /**
     * Checks if a season with the same years already exists as the given years.
     *
     * @param seasonYears - String - years in the correct format as dictated by Season
     * @return - true if a season with the same years already exists, else false
     */
    public boolean doesSeasonExists(String seasonYears) {
        for (Season currSeason : seasons) {
            String currSeasonYears = currSeason.getYears();
            if (currSeasonYears.equals(seasonYears)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a season to this league with the given season years
     *
     * @param seasonYears - String - unique season years for this league
     * @return - boolean - true if the addition completed successfully, else false
     */
    public boolean addSeason(String seasonYears) {
        Season season = new Season(this, seasonYears);
        this.seasons.add(season);
        return true;
    }

    /**
     * Get the latest season of the league.
     * @return the latest season of the league.
     */
    public Season getLatestSeason(){
        if(seasons == null || seasons.isEmpty())
            return null;
        Season latestSeason = seasons.get(0);
        for(Season s : seasons){
            if(s.getYear().isAfter(latestSeason.getYear()))
                latestSeason = s;
        }
        return latestSeason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof League)) return false;
        League that = (League) o;
        return this.getName().equals(that.getName());
    }

}
