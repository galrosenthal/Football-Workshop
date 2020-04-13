package Service;

import Domain.Controllers.TeamController;
import Domain.Exceptions.NoTeamExistsException;
import Domain.Exceptions.alreadyTeamOwnerException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Game.Team;
import Domain.Game.TeamAsset;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;

import java.util.List;

public class Controller {


    public static boolean addTeamOwner(SystemUser systemUser) throws Exception {
        TeamOwner myTeamOwner = getUserIfIsTeamOwner(systemUser);
        if(myTeamOwner == null)
        {
            return false;
        }

        Team chosenTeam = getTeamByChoice(myTeamOwner);
        if(chosenTeam == null)
        {
            throw new NoTeamExistsException("There was no Team found");
        }

        String newTeamOwnerUsername = getUsernameFromUser("Team Owner");

        try{
            TeamController.addTeamOwner(newTeamOwnerUsername,chosenTeam,myTeamOwner);
        }
        catch (alreadyTeamOwnerException | UserNotFoundException e)
        {
//            e.printStackTrace();
            throw e;
        }
        return true;

    }

    private static TeamOwner getUserIfIsTeamOwner(SystemUser systemUser) {
        if(!systemUser.isType(RoleTypes.TEAM_OWNER))
        {
            return null;
        }
        Role myTeamOwnerRole = systemUser.getRole(RoleTypes.TEAM_OWNER);
        if(!(myTeamOwnerRole instanceof TeamOwner))
        {
            return null;
        }
        return (TeamOwner)myTeamOwnerRole;
    }

    private static String getUsernameFromUser(String msg) {
        UIController.printMessage("Enter new "+ msg +" Username:");

        String username = UIController.receiveString();
        return username;

    }

    private static Team getTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        if(myTeams == null)
        {
            return null;
        }
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



    /**
     * Team Owner Asks to add a new asset to the Team
     * @param systemUser - the System User of the Team Owner
     * @return true if the asset added successfully
     */
    public static boolean addAsset(SystemUser systemUser) throws Exception
    {
        TeamOwner myTeamOwner = getUserIfIsTeamOwner(systemUser);
        if(myTeamOwner == null)
        {
            return false;
        }

        Team chosenTeam = getTeamByChoice(myTeamOwner);

        if(chosenTeam == null)
        {
            throw new NoTeamExistsException("There was no Team found");
        }

        TeamAsset ass = getAssetTypeFromUser();

        String name = "";
        switch (ass){
            case PLAYER :
                name = getUsernameFromUser("Player");
                try{
                    return TeamController.addPlayer(name,chosenTeam,myTeamOwner);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            case COACH:
                name = getUsernameFromUser("Coach");
                try {
                    return TeamController.addCoach(name,chosenTeam,myTeamOwner);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            case TEAM_MANAGER:
                name = getUsernameFromUser("Team Manager");
                try {
                    return TeamController.addTeamManager(name,chosenTeam,myTeamOwner);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            case STADIUM:
                UIController.printMessage("Enter Stadium Username:");
                name = UIController.receiveString();
                try {
                    return TeamController.addStadium(name,chosenTeam,myTeamOwner);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            default:
                return false;
        }
    }



    private static TeamAsset getAssetTypeFromUser() {
        UIController.printMessage("Choose Asset Type: ");
        for (int i = 0; i < TeamAsset.values().length; i++) {
            UIController.printMessage(i + ". " + TeamAsset.values()[i]);
        }

        int assetIndex;

        do{
            assetIndex = UIController.receiveInt();
        }while (!(assetIndex >= 0 && assetIndex < TeamAsset.values().length));

        return TeamAsset.values()[assetIndex];
    }

}
