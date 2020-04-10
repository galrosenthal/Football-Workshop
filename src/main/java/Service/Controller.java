package Service;

import Domain.Controllers.TeamController;
import Domain.Controllers.TopDomainController;
import Domain.Game.Team;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;

import java.util.List;

public class Controller {

    TopServiceController currentServiceController = TopServiceController.getInstance();

    TopDomainController currentDomainController = TopDomainController.getInstance();


    private static volatile Controller mInstance;

    private Controller() {
    }

    public boolean addTeamOwner(SystemUser systemUser)
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
            currentDomainController.getTeamController().addTeamOwner(newTeamOwnerUsername,chosenTeam,myTeamOwner);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private String getUsernameFromUser(String msg) {
        currentServiceController.getUiController().printMessage("Enter new "+ msg +" Username:");

        String username = currentServiceController.getUiController().receiveString();
        return username;

    }

    private Team getTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        currentServiceController.getUiController().printMessage("Choose a Team Number");
        for (int i = 0; i < myTeams.size() ; i++) {
            currentServiceController.getUiController().printMessage(i + ". " + myTeams.get(i).getTeamName());
        }
        int teamIndex;

        do{
            teamIndex = currentServiceController.getUiController().receiveInt();
        }while (!(teamIndex >= 0 && teamIndex < myTeams.size()));

        return myTeams.get(teamIndex);
    }


    public static Controller getInstance() {
        if (mInstance == null) {
            synchronized (Controller.class) {
                if (mInstance == null) {
                    mInstance = new Controller();
                }
            }
        }
        return mInstance;
    }
}
