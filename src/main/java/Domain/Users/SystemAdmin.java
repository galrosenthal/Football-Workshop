package Domain.Users;

import Domain.EntityManager;
import Domain.Game.Team;
import Domain.UserComplaints;

import java.util.ArrayList;
import java.util.List;

public class SystemAdmin extends Role  {
    private List<UserComplaints> complaintsToReview;
    public SystemAdmin(SystemUser systemUser) {
        super(RoleTypes.SYSTEM_ADMIN,systemUser);
    }
    

    /*notify teamOwners  and TeamMangers - close team Permanently*/
    public void closeTeamPermanently(Team team)
    {
        EntityManager entityManager = EntityManager.getInstance();
        List<TeamOwner> teamOwners = new ArrayList<>();
        List<TeamManager> teamManagers = new ArrayList<>();
        List<SystemUser> systemUsers = new ArrayList<>();
        teamOwners.addAll(team.getTeamOwners());
        teamManagers.addAll(team.getTeamManagers());
        for (int i = 0; i <teamOwners.size() ; i++) {
            systemUsers.add(teamOwners.get(i).getSystemUser());
        }
        for (int i = 0; i <teamManagers.size() ; i++) {
            systemUsers.add(teamManagers.get(i).getSystemUser());
        }
        entityManager.notifyObserver(systemUsers);
    }
}
