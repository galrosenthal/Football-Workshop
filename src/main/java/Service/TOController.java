package Service;


import Domain.Controllers.TeamController;
import Domain.Game.Team;
import Domain.Game.TeamStatus;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;

import java.util.List;

/**
 * Team Owner services controller
 */
public class TOController {

    public static boolean closeTeam(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.TEAM_OWNER)) {
            return false;
        }

        Role myTeamOwnerRole = systemUser.getRole(RoleTypes.TEAM_OWNER);
        if(!(myTeamOwnerRole instanceof TeamOwner))
        {
            return false;
        }

        TeamOwner myTeamOwner = (TeamOwner)myTeamOwnerRole;
        Team chosenTeam = Controller.getTeamByChoice(myTeamOwner);

        if(chosenTeam.getStatus() != TeamStatus.OPEN)
            return false; //cannot perform action on closed team.

        boolean choice = UIController.receiveChoice("Are you sure you want to close the team \""+
                chosenTeam.getTeamName()+"\"? y/n");
        if (!choice){
            return false;
        }

        TeamController.closeTeam(chosenTeam);
        return true;
    }


    public static boolean reopenTeam(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.TEAM_OWNER)) {
            return false;
        }

        Role myTeamOwnerRole = systemUser.getRole(RoleTypes.TEAM_OWNER);
        if(!(myTeamOwnerRole instanceof TeamOwner))
        {
            return false;
        }

        TeamOwner myTeamOwner = (TeamOwner)myTeamOwnerRole;
        Team chosenTeam = getClosedTeamByChoice(myTeamOwner);

        boolean choice = UIController.receiveChoice("Are you sure you want to re-open the team \""+
                chosenTeam.getTeamName()+"\"? y/n");
        if (!choice){
            return false;
        }

        TeamController.reopenTeam(chosenTeam);
        return true;
    }


    private static Team getClosedTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        UIController.printMessage("Choose a Team Number");
        for (int i = 0; i < myTeams.size() ; i++) {
            if(myTeams.get(i).getStatus() == TeamStatus.CLOSED)
                UIController.printMessage(i + ". " + myTeams.get(i).getTeamName()+" (closed)");
        }
        int teamIndex;

        do{
            teamIndex = UIController.receiveInt();
        }while (!(teamIndex >= 0 && teamIndex < myTeams.size()));

        return myTeams.get(teamIndex);
    }

}
