package Service;


import Domain.Controllers.TeamController;
import Domain.Game.Team;
import Domain.Game.TeamStatus;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;

import java.util.ArrayList;
import java.util.List;

/**
 * Team Owner services controller
 */
public class TOController {

    private static boolean closeOrOpenTeam(SystemUser systemUser, String closeOrReopen) throws Exception {
        TeamOwner myTeamOwner = Controller.getUserIfIsTeamOwner(systemUser);
        if(myTeamOwner == null)
        {
            return false;
        }
        Team chosenTeam;
        if(closeOrReopen.equals("reopen")){
            chosenTeam = getClosedTeamByChoice(myTeamOwner);
        }
        else {
            if (closeOrReopen.equals("close")) {
                chosenTeam = Controller.getTeamByChoice(myTeamOwner);
                if (chosenTeam.getStatus() != TeamStatus.OPEN) {
                    UIController.showNotification("Cannot perform action on closed team.");
                    return false; //cannot perform action on closed team.
                }
            }
            else{
                throw new Exception("Expected \"close\" or \"reopen\" command");
            }
        }

        boolean choice = UIController.receiveChoice("Are you sure you want to "+closeOrReopen+" the team \""+
                chosenTeam.getTeamName()+"\"? y/n");
        if (!choice){
            UIController.showNotification("No teams were "+closeOrReopen+"ed");
            return false;
        }

        if(closeOrReopen.equals("reopen")){
            TeamController.reopenTeam(chosenTeam, myTeamOwner);
        }
        else if(closeOrReopen.equals("close")){
            TeamController.closeTeam(chosenTeam);
        }

        UIController.showNotification("Team \""+chosenTeam.getTeamName()+"\" " + closeOrReopen+"ed successfully");
        myTeamOwner.closeReopenTeam(chosenTeam);
        return true;
    }

    /**
     * Called when a system user wants to close a team (should bb team owner for that).
     * Performs validations and calls the TeamController for further treatment.
     * @param systemUser The system user who wants to close a team.
     * @return True if successfully closed a team.
     */
    public static boolean closeTeam(SystemUser systemUser) throws Exception {
        return closeOrOpenTeam(systemUser, "close");
    }

    /**
     * Called when a system user wants to re-open a closed team (should bb team owner for that).
     * Performs validations and calls the TeamController for further treatment.
     * @param systemUser The system user who wants to re-open a closed  team.
     * @return True if successfully re-opened a team.
     */
    public static boolean reopenTeam(SystemUser systemUser) throws Exception {
        return closeOrOpenTeam(systemUser, "reopen");
    }


    private static Team getClosedTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        List<String> closedTeamsList = new ArrayList<>();
        for (int i = 0; i < myTeams.size() ; i++) {
            if(myTeams.get(i).getStatus() == TeamStatus.CLOSED)
                closedTeamsList.add(myTeams.get(i).getTeamName()+" (closed)");
        }
        int teamIndex;

        do{
            teamIndex = UIController.receiveInt("Choose a Team Number",closedTeamsList);
        }while (!(teamIndex >= 0 && teamIndex < myTeams.size()));

        return myTeams.get(teamIndex);
    }

}
