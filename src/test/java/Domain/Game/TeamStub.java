package Domain.Game;

import Domain.Users.Coach;
import Domain.Users.Player;
import Domain.Users.TeamManager;
import Domain.Users.TeamOwner;

import java.util.ArrayList;
import java.util.List;

public class TeamStub extends Team{

    private List<TeamOwner> teamOwners;
    private List<Player> players;
    private List<TeamManager> teamManagers;
    private List<Coach> coaches;
    private List<Stadium> stadiums;
    private int selector;

    public TeamStub(int selector) {
        this.selector = selector;
        this.teamOwners = new ArrayList<>();
        this.players = new ArrayList<>();
        this.teamManagers = new ArrayList<>();
        this.coaches = new ArrayList<>();
        this.stadiums = new ArrayList<>();
        this.setTeamName( "stubTeam"+selector);
        if(selector == 66143 || selector == 66163)
            setStatus(TeamStatus.CLOSED);
    }

    @Override
    public List<TeamOwner> getTeamOwners() {
        return this.teamOwners;
    }

    @Override
    public List<Player> getTeamPlayers() {
        return this.players;
    }

    @Override
    public List<Coach> getTeamCoaches() {
        return this.coaches;
    }

    @Override
    public List<TeamManager> getTeamManagers() {
        return this.teamManagers;
    }

    @Override
    public List<Stadium> getStadiums() {
        return this.stadiums;
    }


    @Override
    public boolean removeTeamOwner(TeamOwner teamOwner) {
        return this.teamOwners.remove(teamOwner);
    }

    @Override
    public boolean removeStadium(Stadium stadium) {
        return this.stadiums.remove(stadium);
    }

    @Override
    public boolean removeTeamPlayer(Player player) {
        return this.players.remove(player);
    }

    @Override
    public boolean removeTeamCoach(Coach coach) {
        return this.coaches.remove(coach);
    }

    @Override
    public boolean removeTeamManager(TeamManager teamManager) {
        return this.teamManagers.remove(teamManager);
    }

    public boolean addTeamOwner(TeamOwner townr) {
        return this.teamOwners.add(townr);
    }

    public boolean addPlayer(Player player) {
        return this.players.add(player);
    }

    public boolean addTeamManager(TeamManager teamManager) {
        return this.teamManagers.add(teamManager);
    }

    public boolean addCoach(Coach coach) {
        return this.coaches.add(coach);
    }

    @Override
    public boolean addStadium(Stadium stadium) {
        return this.stadiums.add(stadium);
    }


    @Override
    public String getTeamName() {
     /*   if(this.selector ==0){
            return "TeamStub 1";
        } else {
            return null;
        }*/
     return "stubTeam"+selector;
    }

    @Override
    public void setTeamName(String testName) {
        super.setTeamName(testName);
    }
}
