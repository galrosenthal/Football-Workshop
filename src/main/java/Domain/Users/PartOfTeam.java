package Domain.Users;

import Domain.EntityManager;
import Domain.Game.Asset;
import Domain.Game.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class PartOfTeam extends Role implements Asset {

    private HashSet<BelongToTeam> allTeamsConnections;


    public PartOfTeam(RoleTypes type, SystemUser systemUser, boolean addToDB) {
        super(type, systemUser, addToDB);
        allTeamsConnections = new HashSet<>();
    }


    @Override
    public boolean addTeam(Team team, TeamOwner teamOwner) {
        if(team != null)
        {
            if(this.getType() == RoleTypes.PLAYER)
            {
                return team.addTeamPlayer(teamOwner,this);
            }
            else if(this.getType() == RoleTypes.COACH)
            {
                return team.addTeamCoach(teamOwner,this);
            }

        }
        return false;
    }

    public boolean addTeamConnection(BelongToTeam teamConnection){
        if(!allTeamsConnections.contains(teamConnection)){
            if(EntityManager.getInstance().addConnection(teamConnection.getTeamBelongsTo(),this)){
                return allTeamsConnections.add(teamConnection);
            }
        }
        return false;
    }

    @Override
    public String getAssetName()
    {
        return this.systemUser.getUsername();
    }

    public List<Team> getTeams() {
        List<Team> allTeams = new ArrayList<>();
        for (BelongToTeam bg :
                allTeamsConnections) {
            allTeams.add(bg.getTeamBelongsTo());
        }

        return allTeams;
    }

    public boolean removeTeam(Team teamToRemove)
    {
        for (BelongToTeam bg :
                allTeamsConnections) {
            if(bg.getTeamBelongsTo().equals(teamToRemove))
            {
                allTeamsConnections.remove(bg);
                if(this.getType() == RoleTypes.PLAYER)
                {
                    teamToRemove.removeTeamPlayer((Player)this);
                }
                else if(this.getType() == RoleTypes.COACH)
                {
                    teamToRemove.removeTeamCoach((Coach)this);
                }
                return true;
            }
        }
        return false;
    }


    public BelongToTeam getBelongTeamByTeam(Team teamToSearch)
    {
        for (BelongToTeam bg :
                allTeamsConnections) {
            if(bg.getTeamBelongsTo().equals(teamToSearch))
            {
                return bg;
            }
        }

        return null;
    }

    /**
     * Get the value of all the property of this asset in the teamOfAsset
     * @param teamOfAsset the team of the Asset to get the property value
     * @return the value of the property
     */
    public String getTeamJob(Team teamOfAsset) {
        BelongToTeam assetBelongsTo = getBelongTeamByTeam(teamOfAsset);
        if(assetBelongsTo == null)
        {
            return null;
        }
        return assetBelongsTo.getTeamJob();
    }


    //    public boolean addAllProperties(Team teamOfAsset) {
//        if(this.getType() == RoleTypes.PLAYER)
//        {
//
//        }
//        else if(this.getType() == RoleTypes.COACH)
//        {
//
//        }
//    }
}
