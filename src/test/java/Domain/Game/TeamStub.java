package Domain.Game;

import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;


public class TeamStub extends Team{

    private List<TeamOwner> teamOwners;
    private int selector;

    /**
     * Selector latest Number: 2
     */
    public TeamStub(int selector) {
        super();
        this.selector = selector;
        this.teamOwners = new ArrayList<>();
    }

    @Override
    public List<TeamOwner> getTeamOwners() {
        return super.getTeamOwners();
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
        if(this.selector == 6131)
        {
            return true;
        }
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
        else if(selector == 6112)
        {
            return true;
        }else if(selector == 6113)
        {
            return true;
        }
        else if(selector == 6131)
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

    /**
     * Each team has a lot of Assets {@link TeamAsset},
     * @return a list of all the team assets
     */
    public List<Asset> getAllAssets() {

        if(this.selector == 6132)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6132));
            return allTeamAssets;

        }
        else if(this.selector == 6133)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6133));
            return allTeamAssets;

        }
        else if(this.selector == 6134)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6134));
            return allTeamAssets;

        }
        else if(this.selector == 6135)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6135));
            return allTeamAssets;

        }
        else if(this.selector == 6136)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6136));
            return allTeamAssets;

        }
        else if(this.selector == 6137)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6137));
            return allTeamAssets;

        }
        else if(this.selector == 6138)
        {
            List<Asset> allTeamAssets = new ArrayList<>();
            allTeamAssets.add(new AssetStub(6138));
            return allTeamAssets;

        }
        List<Asset> allTeamAssets = new ArrayList<>();
        return allTeamAssets;

    }

    @Override
    public List<Enum> getAllProperty(Asset asset, String propertyName) {
        List<Enum> enumList = asset.getAllPropertyList(this , propertyName);
        return enumList;
    }
}
