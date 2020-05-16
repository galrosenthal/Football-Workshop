package Domain.Game;

import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;


public class TeamStub extends Team {

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
        this.setTeamName("stubTeam" + selector);
        if (selector == 66143 || selector == 66163)
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
        if(selector >= 1 && selector <= 6) {
            for(int i = 1 ; i <= 11; i++){
                this.players.add(new PlayerStub(new SystemUserStub("a","a",1)));
            }
        }
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
        return "stubTeam" + selector;
    }

    @Override
    public void setTeamName(String testName) {
        super.setTeamName(testName);
    }

    @Override
    public boolean addTeamPlayer(TeamOwner townr, Role teamPlayer) {
        if (selector == 1) {
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
        if (this.selector == 6131) {
            return true;
        }
        return super.addTeamManager(townr, teamManager);
    }

    @Override
    public boolean addTeamOwner(Role teamOwner) {
        return true;
    }


    @Override
    public boolean isTeamOwner(TeamOwner teamOwner) {
        if (selector == 6110) {
            return true;
        } else if (selector == 6111) {
            return true;
        } else if (selector == 6112) {
            return true;
        } else if (selector == 6113) {
            return true;
        } else if (selector == 6131) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addAsset(String assetName, TeamOwner teamOwner, TeamAsset assetType) {
        if (selector == 6110) {
            return true;
        } else if (selector == 6111) {
            return false;
        } else if (selector == 6112) {
            return true;
        } else if (selector == 6113) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * Each team has a lot of Assets {@link TeamAsset},
     *
     * @return a list of all the team assets
     */
    public List<Asset> getAllAssets() {

        if (this.selector == 6132) {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6132));
            return allTeamAssets;

        } else if (this.selector == 6133) {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6133));
            return allTeamAssets;

        } else if (this.selector == 6134) {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6134));
            return allTeamAssets;

        } else if (this.selector == 6135) {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6135));
            return allTeamAssets;

        } else if (this.selector == 6136) {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6136));
            return allTeamAssets;

        } else if (this.selector == 6137) {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6137));
            return allTeamAssets;

        } else if (this.selector == 6138) {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6138));
            return allTeamAssets;

        }
        List<Asset> allTeamAssets = new ArrayList<>();
        return allTeamAssets;

    }

    @Override
    public boolean equals(Object o) {
        return (super.equals(o) && (this.selector==((TeamStub)o).selector));
    }

    @Override
    public List<Enum> getAllProperty(Asset asset, String propertyName) {
        List<Enum> enumList = asset.getAllPropertyList(this, propertyName);
        return enumList;
    }
}
