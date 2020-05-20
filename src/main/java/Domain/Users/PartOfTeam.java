package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public abstract class PartOfTeam extends Role implements Asset {

    public PartOfTeam(RoleTypes type, SystemUser systemUser) {
        super(type, systemUser);
        allTeamsConnections = new HashSet<>();
    }

    HashSet<BelongToTeam> allTeamsConnections;

    public boolean addTeamConnection(BelongToTeam teamConnection){
        if(!allTeamsConnections.contains(teamConnection)){
            return allTeamsConnections.add(teamConnection);
        }
        return false;
    }

}
