package Domain.Game;

import java.util.ArrayList;
import java.util.List;

public class Season {
    String name;
    int year;
    List<Team> teams;

    public Season(){
        teams = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
