package Service;

import Domain.Controllers.TeamController;
import Domain.EntityManager;
import Domain.Exceptions.*;
import Domain.Game.Team;
import Domain.Game.TeamAsset;
import Domain.Game.TeamStatus;
import Domain.SystemLogger.*;
import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import static Service.UIController.getUsernameFromUser;

public class Controller {

    public static boolean systemBoot() {
        //Establishing connections to external DBMS
        //access DB
        //extract system admins
        try {
            String usernameAndPassword = UIController.receiveUserLoginInfo("Please enter a system administrator username:;Please enter the password");
            String username = usernameAndPassword.split(UIController.STRING_DELIMETER)[0];
            String password = usernameAndPassword.split(UIController.STRING_DELIMETER)[1];

            //Retrieve system user
            SystemUser admin = null;

            admin = Controller.login(username, password);
            if(!MainController.getUserRoles(username).contains(RoleTypes.SYSTEM_ADMIN.name()))
            {
                UIController.showNotification("You are not a System Admin please try different user");
//                MainController.performLogout(username, admin, UI.getCurrent());
                return false;
            }

            UIController.showNotification("Successful login. Welcome back, " + admin.getName());
            //system boot choice
            boolean choice = UIController.receiveChoice("Would you like to boot the system? y/n");
            if (!choice) {
//                MainController.performLogout(username, admin, UI.getCurrent());
                return false;
            }

            //Establishing connections to external financial system

            //Establishing connections to external tax system
            UIController.showNotification("The system was booted successfully");
//            MainController.performLogout(username, admin, UI.getCurrent());
            //Log the action
            SystemLoggerManager.logInfo(Controller.class, new SystemBootLogMsg(admin.getUsername()));
            return true;
        } catch (CancellationException ce)
        {
            ce.printStackTrace();
            return false;
        }
        catch (Exception e) {
            UIController.showNotification("Username or Password was incorrect!!!!!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * The method gets a user who want to add a new owner to his team. the method checks the user
     * is a owner of a team, ask him for team selection and user selection for add.
     * @param systemUser - a Team owner
     * @return true if the method succeed adding a new team owner
     */
    public static boolean addTeamOwner(SystemUser systemUser)
    {
        TeamOwner myTeamOwner = getUserIfIsTeamOwner(systemUser);

        if(myTeamOwner == null){
            return false;
        }
        Team chosenTeam = getTeamByChoice(myTeamOwner);

        if(chosenTeam.getStatus() != TeamStatus.OPEN) {
            UIController.showNotification("Error: Cannot perform action on closed team.");
            return false; //cannot perform action on closed team.
        }

        String newTeamOwnerUsername = getUsernameFromUser("new Team Owner");

        try {
            TeamController.addTeamOwner(newTeamOwnerUsername, chosenTeam, myTeamOwner);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * The method gets a user who want to remove a team owner. The method checks the user is a team owner and
     * ask him for team selection and user selection he want to remove from the team he owned.
     * @param user - team owner who want to remove another team owner from the team he owned
     * @return true if the method succeed to remove the team owner
     */
    public static boolean removeTeamOwner(SystemUser user){

        TeamOwner myTeamOwner = getUserIfIsTeamOwner(user);

        if(myTeamOwner == null){
            return false;
        }

        Team chosenTeam = getTeamByChoice(myTeamOwner);

        if(chosenTeam == null){
            return false;
        }
        String newTeamOwnerUsername = getUserOwnerSelection(chosenTeam);

        try{
            TeamController.removeTeamOwner(newTeamOwnerUsername,chosenTeam,myTeamOwner);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * The method represent the owners of the team and ask for user selection
     * @param chosenTeam - The Owner's team
     * @return the chosen user
     */
    private static String getUserOwnerSelection(Team chosenTeam) {
        List<String> teamOwners = chosenTeam.getTeamOwnersString();
        int i=0;

        String username = UIController.receiveString("Choose a Team Owner Number:",teamOwners);
        return username;
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
            String msg = "There was no Team found";
            SystemLoggerManager.logError(Controller.class, msg);
            throw new NoTeamExistsException(msg);
        }

        TeamAsset ass = getAssetTypeFromUser();
        String name = getNameFromUser("What is the asset name/username?");

        return TeamController.addAssetToTeam(name,chosenTeam,myTeamOwner,ass);
    }

    private static String getNameFromUser(String msg) {
        String username = UIController.receiveString(msg);
        return username;
    }


    private static TeamAsset getAssetTypeFromUser() {
        List<String> assetTypes = new ArrayList<>();
        for (int i = 0; i < TeamAsset.values().length; i++) {
            assetTypes.add(TeamAsset.values()[i].name());
        }

        int assetIndex;

        do{
            assetIndex = UIController.receiveInt("Choose Asset Type: ", assetTypes);
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
            String msg = "There was no Team found";
            SystemLoggerManager.logError(Controller.class, msg);
            throw new NoTeamExistsException(msg);
        }

        boolean isSuccess = TeamController.editAssets(chosenTeam);

        if(isSuccess)
            UIController.showNotification("The action completed successfully");

        return isSuccess;
    }

    /**
     * Receives user name and password from the unregistered user who wants to log in to the system,
     * Delegates the responsibility to EntityManger.
     * Add system user to all AllSubscribers - userSystem list  - online
     * @param usrNm User name
     * @param pswrd Password
     * @return The user in the system with those credentials.
     * @throws UsernameOrPasswordIncorrectException If user name or password are incorrect.
     */
    public static SystemUser login(String usrNm, String pswrd) throws UsernameOrPasswordIncorrectException, AlreadyLoggedInUser {
        SystemUser SystemUser =  EntityManager.getInstance().login(usrNm, pswrd);
        return SystemUser;
    }


    /**
     * Receives name, user name and password from the unregistered user who wants to sign up to the system,
     * Delegates the responsibility to EntityManger.
     * @param name Name.
     * @param usrNm User name.
     * @param pswrd Password.
     * @param email  email address
     * @param emailAlert - boolean  - if send via email - true, otherwise false
     * @return New user with those credentials.
     * @throws Exception If user name is already belongs to a user in the system, or
     * the password does not meet the security requirements.
     */
    public static SystemUser signUp(String name, String usrNm, String pswrd, String email, boolean emailAlert)
            throws UsernameAlreadyExistsException, WeakPasswordException, InvalidEmailException {

        return EntityManager.getInstance().signUp(name, usrNm, pswrd, email, emailAlert);


    }


}
