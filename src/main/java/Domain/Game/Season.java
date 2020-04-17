package Domain.Game;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Season {

    private League league;
    private String years; //name
    List<Team> teams;

    public Season(League league, String years) {
        this.league = league;
        this.teams = new ArrayList<>();
        this.years = years;
    }

    public Year getYear() {
        int[] yearsInt = splitYearsInt(years);
        return Year.parse(""+yearsInt[0]+yearsInt[2]);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public boolean addTeam(Team team){
        if(!teams.contains(team)){
            teams.add(team);
            return true;
        }
        return false;
    }

    private static String[] splitYearsString(String years){
        String century = years.substring(0,2);
        String firstYear = years.substring(2,4);
        String secondYear = years.substring(5);
        return new String[]{century,firstYear,secondYear};
    }

    private static int[] splitYearsInt(String years){
        String[] splitYearsSt = splitYearsString(years);
        int centuryInt = Integer.parseInt(splitYearsSt[0]);
        int firstYearInt = Integer.parseInt(splitYearsSt[1]);
        int secondYearInt = Integer.parseInt(splitYearsSt[2]);
        return new int[]{centuryInt,firstYearInt,secondYearInt};
    }
}
