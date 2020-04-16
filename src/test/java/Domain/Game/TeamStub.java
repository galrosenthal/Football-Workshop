package Domain.Game;

import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;


public class TeamStub extends Team{

    private int selector;

    /**
     * Selector latest Number: 2
     */
    public TeamStub(int selector) {
        super();
        this.selector = selector;
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }




    public boolean addTeamOwner(TeamOwner townr) {
        return super.getTeamOwners().add(townr);
    }

    @Override
    public String getTeamName() {
        if(this.selector >= 0){
            return "TeamStub 1";
        } else {
            return null;
        }
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
    public boolean addTeamOwner(TeamOwner townr, TeamOwner teamOwner) {
        return super.addTeamOwner(townr, teamOwner);
    }

    @Override
    public List<Player> getTeamPlayers() {
        return super.getTeamPlayers();
    }

    @Override
    public List<Coach> getTeamCoaches() {
        return super.getTeamCoaches();
    }

    @Override
    public List<TeamManager> getTeamManagers() {
        return super.getTeamManagers();
    }
}
