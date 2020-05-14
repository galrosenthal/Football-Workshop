package Domain.Game;

import Domain.Users.Referee;

import java.time.Year;
import java.util.*;

public class Season {

    private League league;
    private String years; //name
    private List<Team> teams;
    private List<Referee> referees;
    private List<Game> games;

    private SchedulingPolicy schedulingPolicy;
    private PointsPolicy pointsPolicy;

    /**
     * Constructor
     *
     * @param league - League - the league that the season belongs to
     * @param years  - String - the season's years in the format of "yyyy/yy"
     */
    public Season(League league, String years) {
        this.league = league;
        this.teams = new ArrayList<>();
        this.years = years;
        this.referees = new ArrayList<>();
        this.games = new ArrayList<>();
        this.pointsPolicy = PointsPolicy.getDefaultPointsPolicy();
        this.schedulingPolicy = SchedulingPolicy.getDefaultSchedulingPolicy();
    }

    /**
     * Getter for the main year of the season - the later year.
     *
     * @return - Year - the later year of the season
     */
    public Year getYear() {
        int[] yearsInt = splitYearsInt(years);
        return Year.parse("" + yearsInt[0] + yearsInt[2]);
    }

    /**
     * Getter for the years field.
     *
     * @return - String - the season's years in the format of "yyyy/yy"
     */
    public String getYears() {
        return years;
    }

    /**
     * Getter for the referees
     *
     * @return - List<Referee>
     */
    public List<Referee> getReferees() {
        return new ArrayList<>(referees);
    }

    public boolean addTeam(Team team) {
        if (!teams.contains(team)) {
            teams.add(team);
            return true;
        }

        return false;
    }

    public boolean hasStarted() {
        //TODO: Check if the season has started
        return false;
    }

    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Returns the number of referees assigned to this season
     *
     * @return - int - the number of referees assigned to this season
     */
    public int refereesSize() {
        return this.referees.size();
    }

    /**
     * Verifies that a given string is a correct season format.
     * yyyy/yy where the first year is one year prior to the second.
     * ex.: 2020/21 represents the season of 2020-2021.
     *
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
     *
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
     *
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


    private boolean checkTeamsEquals(List<Team> teams1, List<Team> teams2) {
        for (Team team : teams1) {
            if (!teams2.contains(team)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a given referee role is assigned to this season
     *
     * @param refereeRole - Referee - the referee to verify if he is assigned to this season
     * @return - boolean - true if th referee is assigned to this season.
     */
    public boolean doesContainsReferee(Referee refereeRole) {
        if (refereeRole != null) {
            if (this.referees.contains(refereeRole)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Assigns a referee to this season
     *
     * @param refereeRole - Referee - the referee to be assigned to the season
     */
    public void assignReferee(Referee refereeRole) {
        if (refereeRole != null) {
            this.referees.add(refereeRole);
        }
    }

    /**
     * Un-assigns a referee from this season
     *
     * @param referee - Referee - a referee role to be removed
     */
    public void unAssignReferee(Referee referee) {
        if (referee != null) {
            this.referees.remove(referee);
        }
    }

    /**
     * Returns a map of the ranking of the teams
     *
     * @return - Map<Integer, Team>  - A map of the ranking of the teams
     */
    public Map<Integer, Team> getRanking() {
        Map<Team, Integer> teamsPoints = getTeamsPoints();
        // Create a list from elements of HashMap
        List<Map.Entry<Team, Integer>> list = new ArrayList<>(teamsPoints.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        //replacing the score with the rank
        for (int rank = list.size() - 1; rank <= 0; rank++) { //TODO: Test boundaries
            Map.Entry<Team, Integer> entry = list.get(rank);
            list.remove(entry);
            entry.setValue(rank);
            list.add(entry);
        }
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        // put data from sorted list to hashmap
        HashMap<Integer, Team> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Team, Integer> entry : list) {
            sortedMap.put(entry.getValue(), entry.getKey());
        }
        return sortedMap;
    }

    /**
     * Returns a map of all the teams in the season and their points
     *
     * @return - Map<Team, Integer> - team -> team's points
     */
    public Map<Team, Integer> getTeamsPoints() {
        //init
        Map<Team, Integer> teamsPoints = new HashMap<>();
        for (Team team : this.teams) {
            teamsPoints.put(team, 0);
        }

        for (Game game : this.games) {
            if (game.hasFinished()) {
                Points gamePoints = pointsPolicy.getPoints(game);
                int homePoints = teamsPoints.get(gamePoints.getHomeTeam());
                int awayPoints = teamsPoints.get(gamePoints.getAwayTeam());
                int newHomePoints = homePoints + gamePoints.getHomeTeamPoints();
                int newAwayPoints = awayPoints + gamePoints.getAwayTeamPoints();
                //Inserting the updated points
                teamsPoints.put(gamePoints.getHomeTeam(), newHomePoints);
                teamsPoints.put(gamePoints.getAwayTeam(), newAwayPoints);
            }
        }
        return teamsPoints;
    }

    public void setPointsPolicy(PointsPolicy pointsPolicy) {
        this.pointsPolicy = pointsPolicy;
    }

    public PointsPolicy getPointsPolicy() {
        return pointsPolicy;
    }
}