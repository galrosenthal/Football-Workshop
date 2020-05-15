package Domain.Users;

import Domain.Alert;
import Domain.Game.Team;
import Domain.Subject;
import Domain.UserComplaints;

import java.util.ArrayList;
import java.util.List;

public class SystemAdmin extends Role implements Subject {
    private List<UserComplaints> complaintsToReview;
    public SystemAdmin(SystemUser systemUser) {
        super(RoleTypes.SYSTEM_ADMIN,systemUser);
    }
    

    /*notify teamOwners  and TeamMangers - close team Permanently*/
    public void closeTeamPermanently(Team team)
    {
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
        String alert = team.getTeamName()+" close permanently";
        notifyObserver(systemUsers, alert);
    }

    @Override
    public void notifyObserver(List<SystemUser> systemUsers, String alert) {
        Alert alertInstance = Alert.getInstance();
        alertInstance.update(systemUsers, alert);

    }
}
