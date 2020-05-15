package Service;

import Domain.Controllers.TeamController;
import Domain.EntityManager;
import Domain.Exceptions.NoTeamExistsException;
import Domain.Exceptions.UsernameAlreadyExistsException;
import Domain.Exceptions.UsernameOrPasswordIncorrectException;
import Domain.Exceptions.WeakPasswordException;
import Domain.Game.Team;
import Domain.Game.TeamAsset;
import Domain.Game.TeamStatus;
import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;
import static Service.UIController.getUsernameFromUser;

public class Controller {

    public static boolean systemBoot() {
        //Establishing connections to external DBMS
        //access DB
        //extract system admins

        String username = UIController.receiveString("Please enter a system administrator username: ",null);
        String password = UIController.receiveString("Please enter your password: ", null);

        //Retrieve system user
        SystemUser admin = null;
        try {
            admin = Controller.login(username, password);
        } catch (Exception e) {
            UIController.showNotification("Username or Password was incorrect!!!!!");
            e.printStackTrace();
        }
        UIController.showNotification("Successful login. Welcome back, " + admin.getName());
        //system boot choice
        boolean choice = UIController.receiveChoice("Would you like to boot the system? y/n");
        if (!choice) {
            return false;
        }

        //Establishing connections to external financial system

        //Establishing connections to external tax system
        UIController.showNotification("The system was booted successfully");
        return true;
    }

    public static boolean addTeamOwner(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.TEAM_OWNER)) {
            return false;
        }
        Role myTeamOwnerRole = systemUser.getRole(RoleTypes.TEAM_OWNER);
        if (!(myTeamOwnerRole instanceof TeamOwner)) {
            return false;
        }
        TeamOwner myTeamOwner = (TeamOwner) myTeamOwnerRole;
        Team chosenTeam = getTeamByChoice(myTeamOwner);

        if(chosenTeam.getStatus() != TeamStatus.OPEN) {
            UIController.showNotification("Error: Cannot perform action on closed team.");
            return false; //cannot perform action on closed team.
        }

        String newTeamOwnerUsername = getUsernameFromUser("Team Owner");

        try {
            TeamController.addTeamOwner(newTeamOwnerUsername, chosenTeam, myTeamOwner);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static TeamOwner getUserIfIsTeamOwner(SystemUser systemUser) {
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



    public static Team getTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        if(myTeams == null|| myTeams.size() == 0)
        {
            return null;
        }
        List<String> teamsToShow = new ArrayList<>();
        for (int i = 0; i < myTeams.size() ; i++) {
            if(myTeams.get(i).getStatus() == TeamStatus.CLOSED)
                 teamsToShow.add(myTeams.get(i).getTeamName()+" (closed)");
            else if(myTeams.get(i).getStatus() == TeamStatus.PERMENENTLY_CLOSED)
                teamsToShow.add(myTeams.get(i).getTeamName()+" (closed forever)");
            else //open
                teamsToShow.add(myTeams.get(i).getTeamName());
        }
        int teamIndex;

        do {
            teamIndex = UIController.receiveInt("Choose a Team ", teamsToShow);
        } while (!(teamIndex >= 0 && teamIndex < myTeams.size()));

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
        String name = getNameFromUser("What is the asset name/username?");

        return TeamController.addAssetToTeam(name,chosenTeam,myTeamOwner,ass);
    }

    private static String getNameFromUser(String msg) {
        String username = UIController.receiveString(msg, null);
        return username;
    }


    private static TeamAsset getAssetTypeFromUser() {
        List<String> assetTypes = new ArrayList<>();
        for (int i = 0; i < TeamAsset.values().length; i++) {
            assetTypes.add(TeamAsset.values()[i].name());
        }

        int assetIndex;

        do{
            assetIndex = UIController.receiveInt("Choose Asset Type: ");
        }while (!(assetIndex >= 0 && assetIndex < TeamAsset.values().length));

        return TeamAsset.values()[assetIndex];
    }

    /**
     * Team Owner Asks to edit an asset to the Team
     * @param systemUser - the System User of the Team Owner
     * @return true if the asset was edit successfully, false otherwise.
     */
    public static boolean modifyTeamAssetDetails(SystemUser systemUser) throws Exception
    {
        TeamOwner myTeamOwner = getUserIfIsTeamOwner(systemUser);
        if (myTeamOwner == null) {
            return false;
        }
        Team chosenTeam = getTeamByChoice(myTeamOwner);

        if (chosenTeam == null) {
            throw new NoTeamExistsException("There was no Team found");
        }

        boolean isSuccess = TeamController.editAssets(chosenTeam);

        if(isSuccess)
            UIController.showNotification("The action completed successfully");

        return isSuccess;
    }

    /**
     * Receives user name and password from the unregistered user who wants to log in to the system,
     * Delegates the responsibility to EntityManger.
     * @param usrNm User name
     * @param pswrd Password
     * @return The user in the system with those credentials.
     * @throws UsernameOrPasswordIncorrectException If user name or password are incorrect.
     */
    public static SystemUser login(String usrNm, String pswrd) throws UsernameOrPasswordIncorrectException {
        return EntityManager.getInstance().login(usrNm, pswrd);
    }


    /**
     * Receives name, user name and password from the unregistered user who wants to sign up to the system,
     * Delegates the responsibility to EntityManger.
     * @param name Name.
     * @param usrNm User name.
     * @param pswrd Password.
     * @return New user with those credentials.
     * @throws Exception If user name is already belongs to a user in the system, or
     * the password does not meet the security requirements.
     */
    public static SystemUser signUp(String name, String usrNm, String pswrd)
            throws UsernameAlreadyExistsException, WeakPasswordException {

        return EntityManager.getInstance().signUp(name, usrNm, pswrd);


    }


}
