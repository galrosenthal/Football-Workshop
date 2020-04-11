package Service;

import Domain.Controllers.TeamController;
import Domain.Game.Team;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;

import java.util.List;

public class Controller {


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
            TeamController.addTeamOwner(newTeamOwnerUsername,chosenTeam,myTeamOwner);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private static String getUsernameFromUser(String msg) {
        UIController.printMessage("Enter new "+ msg +" Username:");

        String username = UIController.receiveString();
        return username;

    }

    private static Team getTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        UIController.printMessage("Choose a Team Number");
        for (int i = 0; i < myTeams.size() ; i++) {
            UIController.printMessage(i + ". " + myTeams.get(i).getTeamName());
        }
        int teamIndex;

        do{
            teamIndex = UIController.receiveInt();
        }while (!(teamIndex >= 0 && teamIndex < myTeams.size()));

        return myTeams.get(teamIndex);
    }


}
