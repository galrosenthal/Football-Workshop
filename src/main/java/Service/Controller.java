package Service;

import Domain.Controllers.TeamController;
import Domain.Game.Team;
import Domain.Users.*;

import java.util.List;

public class Controller {

    public static boolean systemBoot(){
        //Establishing connections to external DBMS
        //access DB
        //extract system admins

        UIController.printMessage("Please enter a system administrator username: ");
        String username = UIController.receiveString();
        UIController.printMessage("Please enter your password: ");
        String password = UIController.receiveString();

        //Retrieve system user
        SystemUser admin = null;
        try {
            admin =  new Unregistered().login(username, password);
        } catch (Exception e) {
            UIController.printMessage("Username or Password was incorrect!!!!!");
            e.printStackTrace();
        }
        UIController.printMessage("Successful login. Welcome back, "+ admin.getName());
        //system boot choice
        boolean choice = UIController.receiveChoice("Would you like to boot the system? y/n");
        if (!choice){
            return false;
        }

        //Establishing connections to external financial system

        //Establishing connections to external tax system
        UIController.printMessage("The system was booted successfully");
        return true;
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
