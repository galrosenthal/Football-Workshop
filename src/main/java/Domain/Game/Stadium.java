package Domain.Game;

import java.util.List;

public class Stadium {
    private List<Team> teams;


    public boolean addTeam(Team team){
        if(!this.teams.contains(team)) {
            this.teams.add(team);
            return true;
        }
        return false;
    }

    public boolean removeTeam(Team team){
        return this.teams.remove(team);
    }
}
