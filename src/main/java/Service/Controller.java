package Service;

import Domain.Controllers.TeamController;
import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;

import java.util.List;
import java.util.Scanner;

public class Controller {
    private static UIController currentUICtrlr = new UIController();

    private static TeamController tc = new TeamController();

    public static void setTc(TeamController newTC) {
        tc = newTC;
    }

    public static void setCurrentUICtrlr(UIController anotherUICtrlr) {
        currentUICtrlr = anotherUICtrlr;
    }

    public static boolean addTeamOwner(SystemUser systemUser)
    {
        if(!systemUser.isType(RoleTypes.TEAM_OWNER))
        {
            return false;
        }
        Role myTeamOwnerRole = systemUser.getRole(RoleTypes.TEAM_OWNER);
        if(!(myTeamOwnerRole instanceof TeamOwner))
        {
            return false;
        }
        TeamOwner myTeamOwner = (TeamOwner)myTeamOwnerRole;
        Team chosenTeam = getTeamByChoice(myTeamOwner);

        String newTeamOwnerUsername = getUsernameFromUser("Team Owner");

        try{
            tc.addTeamOwner(newTeamOwnerUsername,chosenTeam,myTeamOwner);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private static String getUsernameFromUser(String msg) {
        currentUICtrlr.printMessage("Enter new "+ msg +" Username:");

        String username = currentUICtrlr.receiveString();
        return username;

    }

    private static Team getTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        currentUICtrlr.printMessage("Choose a Team Number");
        for (int i = 0; i < myTeams.size() ; i++) {
            currentUICtrlr.printMessage(i + ". " + myTeams.get(i).getTeamName());
        }
        int teamIndex;

        do{
            teamIndex = currentUICtrlr.receiveInt();
        }while (!(teamIndex >= 0 && teamIndex < myTeams.size()));

        return myTeams.get(teamIndex);
    }
}
