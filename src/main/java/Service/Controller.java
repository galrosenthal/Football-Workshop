package Service;

import Domain.Controllers.TeamController;
import Domain.Exceptions.NoTeamExistsException;
import Domain.Game.Team;
import Domain.Game.TeamAsset;
import Domain.Game.TeamStatus;
import Domain.Users.*;

import java.util.List;

public class Controller {

    public static boolean systemBoot() {
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
            admin = new Unregistered().login(username, password);
        } catch (Exception e) {
            UIController.printMessage("Username or Password was incorrect!!!!!");
            e.printStackTrace();
        }
        UIController.printMessage("Successful login. Welcome back, " + admin.getName());
        //system boot choice
        boolean choice = UIController.receiveChoice("Would you like to boot the system? y/n");
        if (!choice) {
            return false;
        }

        //Establishing connections to external financial system

        //Establishing connections to external tax system
        UIController.printMessage("The system was booted successfully");
        return true;
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
            UIController.printMessage("Cannot perform action on closed team.");
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
        List<TeamOwner> teamOwners = chosenTeam.getTeamOwners();
        int i=0;

        UIController.printMessage("Choose a Team Owner Number:");
        for (TeamOwner teamOwner : teamOwners){
            UIController.printMessage(i+". " + teamOwner.getSystemUser().getName());
        }

        String username = UIController.receiveString();
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

    private static String getUsernameFromUser(String msg) {
        UIController.printMessage("Enter "+ msg +" Username:");

        String username = UIController.receiveString();
        return username;

    }

    public static Team getTeamByChoice(TeamOwner myTeamOwner) {
        List<Team> myTeams = myTeamOwner.getOwnedTeams();
        if(myTeams == null|| myTeams.size() == 0)
        {
            return null;
        }
        UIController.printMessage("Choose a Team Number");
        for (int i = 0; i < myTeams.size() ; i++) {
            if(myTeams.get(i).getStatus() == TeamStatus.CLOSED)
                 UIController.printMessage(i + ". " + myTeams.get(i).getTeamName()+" (closed)");
            else if(myTeams.get(i).getStatus() == TeamStatus.PERMENENTLY_CLOSED)
                UIController.printMessage(i + ". " + myTeams.get(i).getTeamName()+" (closed forever)");
            else //open
                UIController.printMessage(i + ". " + myTeams.get(i).getTeamName());
        }
        int teamIndex;

        do {
            teamIndex = UIController.receiveInt();
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
        UIController.printMessage(msg);
        String username = UIController.receiveString();
        return username;
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
            UIController.printMessage("The action completed successfully");

        return isSuccess;
    }







}
