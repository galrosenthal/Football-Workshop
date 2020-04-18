package Domain.Game;

import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;

public class TeamStub extends Team{

    private List<TeamOwner> teamOwners;
    private List<Player> players;
    private List<TeamManager> teamManagers;
    private List<Coach> coaches;
    private List<Stadium> stadiums;
    private int selector;

    /**
     * Selector latest Number: 2
     */
    public TeamStub(int selector) {
        super();
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

    public void setSelector(int selector) {
        this.selector = selector;
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

    @Override
    public boolean addTeamPlayer(TeamOwner townr, Role teamPlayer) {
        if(selector == 1)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean addTeamCoach(TeamOwner townr, Role coach) {
        return super.addTeamCoach(townr, coach);
    }

    @Override
    public boolean addTeamManager(TeamOwner townr, Role teamManager) {
        return super.addTeamManager(townr, teamManager);
    }

    @Override
    public boolean addTeamOwner(Role teamOwner) {
        return true;
    }


    @Override
    public boolean isTeamOwner(TeamOwner teamOwner) {
        if(selector == 6110)
        {
            return true;
        }
        else if(selector == 6111)
        {
            return true;
        }
        else if(selector == 6112)
        {
            return true;
        }else if(selector == 6113)
        {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean addAsset(String assetName, TeamOwner teamOwner, TeamAsset assetType){
        if(selector == 6110)
        {
            return true;
        }
        else if(selector == 6111)
        {
            return false;
        }else if(selector == 6112)
        {
            return true;
        }
        else if(selector == 6113)
        {
            return false;
        }
        else{
            return false;
        }
    }
}
