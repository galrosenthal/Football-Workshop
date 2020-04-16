package Domain.Game;

import java.util.ArrayList;
import java.util.List;

public class Season {
    String name;
    int year;
    List<Team> teams;
    League league;

    public Season(){
        teams = new ArrayList<>();
    }

    public Season(String name){
        this();
        this.name = name;
    }
    public int getYear() {
        return year;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Season)) return false;
        Season season = (Season) o;
        return this.name.equals(season.name) &&
                this.year == season.year &&
                this.teams.size() == season.teams.size() &&
                this.league.getName() == season.getLeague().getName() &&
                checkTeamsEquals(season.getTeams(),this.teams);
    }

    private boolean checkTeamsEquals(List<Team> teams1,List<Team> teams2) {
        for (Team team : teams1){
            if(!teams2.contains(team)){
                return false;
            }
        }

        return  true;
    }

    public League getLeague() {
        return league;
    }
}
