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

    /**
     * Called when a system user wants to close a team (should bb team owner for that).
     * Performs validations and calls the TeamController for further treatment.
     * @param systemUser The system user who wants to close a team.
     * @return True if successfully closed a team.
     */
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

        if(chosenTeam.getStatus() != TeamStatus.OPEN) {
            UIController.printMessage("Cannot perform action on closed team.");
            return false; //cannot perform action on closed team.
        }

        boolean choice = UIController.receiveChoice("Are you sure you want to close the team \""+
                chosenTeam.getTeamName()+"\"? y/n");
        if (!choice){
            UIController.printMessage("No teams were closed");
            return false;
        }

        TeamController.closeTeam(chosenTeam);
        UIController.printMessage("Team \""+chosenTeam.getTeamName()+"\" closed successfully");
        return true;
    }

    /**
     * Called when a system user wants to re-open a closed team (should bb team owner for that).
     * Performs validations and calls the TeamController for further treatment.
     * @param systemUser The system user who wants to re-open a closed  team.
     * @return True if successfully re-opened a team.
     */
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
            UIController.printMessage("No teams were re-opened");
            return false;
        }

        TeamController.reopenTeam(chosenTeam);
        UIController.printMessage("Team \""+chosenTeam.getTeamName()+"\" re-opened successfully");
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
