package Domain.Game;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Season {

    private League league;
    private String years; //name
    List<Team> teams;

    private GamePolicy gamePolicy; //todo: initialize with default
    private PointsPolicy pointsPolicy; //todo: initialize with default

    /**
     * Constructor
     * @param league - League - the league that the season belongs to
     * @param years - String - the season's years in the format of "yyyy/yy"
     */
    public Season(League league, String years) {
        this.league = league;
        this.teams = new ArrayList<>();
        this.years = years;
    }

    /**
     * Getter for the main year of the season - the later year.
     * @return - Year - the later year of the season
     */
    public Year getYear() {
        int[] yearsInt = splitYearsInt(years);
        return Year.parse("" + yearsInt[0] + yearsInt[2]);
    }

    /**
     * Getter for the years field.
     * @return - String - the season's years in the format of "yyyy/yy"
     */
    public String getYears() {
        return years;
    }

    public boolean addTeam(Team team) {
        if (!teams.contains(team)) {
            teams.add(team);
            return true;
        }

        return false;
    }

    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Verifies that a given string is a correct season format.
     * yyyy/yy where the first year is one year prior to the second.
     * ex.: 2020/21 represents the season of 2020-2021.
     * @param years - String - the season's years in the format of "yyyy/yy"
     * @return - boolean - true if the given string is in the correct format, else false
     */
    public static boolean isGoodYearsFormat(String years) {
        if (years.matches("^\\d\\d\\d\\d\\/\\d\\d")) {
            int[] yearsInt = splitYearsInt(years);
            if (yearsInt[1] == yearsInt[2] - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Receives years in the format of "yyyy/yy" and splits it into an array of Strings.
     * @param years String - In the format of "yyyy/yy"
     * @return - String[] - century(2-digits), firstYear(2-digits), secondYear(2-digits)
     */
    private static String[] splitYearsString(String years) {
        String century = years.substring(0, 2);
        String firstYear = years.substring(2, 4);
        String secondYear = years.substring(5);
        return new String[]{century, firstYear, secondYear};
    }

    /**
     * Receives years in the format of "yyyy/yy" and splits it into an array of int.
     * @param years String - In the format of "yyyy/yy"
     * @return - int[] - century(2-digits), firstYear(2-digits), secondYear(2-digits)
     */
    private static int[] splitYearsInt(String years) {
        String[] splitYearsSt = splitYearsString(years);
        int centuryInt = Integer.parseInt(splitYearsSt[0]);
        int firstYearInt = Integer.parseInt(splitYearsSt[1]);
        int secondYearInt = Integer.parseInt(splitYearsSt[2]);
        return new int[]{centuryInt, firstYearInt, secondYearInt};
    }


    private boolean checkTeamsEquals(List<Team> teams1,List<Team> teams2) {
        for (Team team : teams1){
            if(!teams2.contains(team)){
                return false;
            }
        }
        return  true;
    }
}