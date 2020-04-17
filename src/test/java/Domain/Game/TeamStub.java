package Domain.Game;

import Domain.Exceptions.StadiumNotFoundException;
import Domain.Exceptions.UserNotFoundException;
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
        return true;
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
    public boolean addTeamOwner(Role teamOwner) {
        return true;
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
        }
        else{
            return false;
        }
    }
}
