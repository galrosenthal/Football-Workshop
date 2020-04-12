package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Stadium;
import Domain.Game.Team;
import Domain.Game.TeamAsset;
import Domain.Users.*;
import Domain.Exceptions.*;
import Service.UIController;
import com.sun.corba.se.spi.ior.ObjectKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TeamController {


    public static boolean addTeamOwner(String username, Team teamToOwn, TeamOwner owner)
    throws Exception{

        List<TeamOwner> teamOwners = teamToOwn.getTeamOwners();

        SystemUser newTeamOwnerUser = EntityManager.getInstance().getUser(username);

        //TODO: check already owned another team in the league
        if(newTeamOwnerUser == null)
        {
            throw new UserNotFoundException("Could not find a user by the given username" + username);
        }

        Role newTeamOwnerRole = newTeamOwnerUser.getRole(RoleTypes.TEAM_OWNER);
        TeamOwner teamOwner;
        if(newTeamOwnerRole == null)
        {
            teamOwner = new TeamOwner(newTeamOwnerUser);
        }
        else
        {

            teamOwner = (TeamOwner) newTeamOwnerRole;
            if(teamOwners.contains(teamOwner))
            {
                throw new alreadyTeamOwnerException("This User is already a team owner");
            }
        }
        teamOwner.addTeamToOwn(teamToOwn);
        teamToOwn.addTeamOwner(owner,teamOwner);



        return true;
    }


    /**
     * Add a player with playerUsername to the teamToAddPlayer by the teamOwner
     * @param playerUsername is the Username of the player to add
     * @param teamToAddPlayer is the Team to add the player to
     * @param teamOwner is the Owner who asks to add the player
     * @return true if the player was added successfully
     * @throws UserNotFoundException
     * @throws PlayerIsAlreadyInThisTeamException
     */
    public static boolean addPlayer(String playerUsername, Team teamToAddPlayer, TeamOwner teamOwner) throws Exception
    {
        SystemUser playerUser = EntityManager.getInstance().getUser(playerUsername);
        if(playerUser == null)
        {
            throw new UserNotFoundException("Could not find a user by the given username" + playerUsername);
        }

        Role getRoleForPlayer = playerUser.getRole(RoleTypes.PLAYER);
        Player playerRole;
        if(getRoleForPlayer == null)
        {
            PlayerFieldJobs filedJob = getPlayerFieldJob();
            Date bday = getPlayerBirthDate();
            playerRole = new Player(playerUser,filedJob,bday);
        }
        else {
            playerRole = (Player)getRoleForPlayer;
            if(teamToAddPlayer.getTeamPlayers().contains(playerRole))
            {
                throw new PlayerIsAlreadyInThisTeamException("Player is already part of this team");
            }
        }

        return teamToAddPlayer.addTeamPlayer(teamOwner,playerRole);
    }

    /**
     * Get the player date of birth from the user
     * @return the birth date of the player as java.util.Date
     */
    private static Date getPlayerBirthDate() {
        UIController.printMessage("Please insert Player Birth Date in format dd/MM/yyyy");
        String bDate;
        do {
            bDate = UIController.receiveString();
        }while (!bDate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$"));

        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(bDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Gets the player field job chosen by the user from the list of filed jobs
     * @return the field job chosen for the suer
     * @see PlayerFieldJobs
     */
    private static PlayerFieldJobs getPlayerFieldJob() {
        UIController.printMessage("Please Choose FiledJob for player");
        for (int i = 0; i < PlayerFieldJobs.values().length; i++) {
            UIController.printMessage(i + ". " + PlayerFieldJobs.values()[i]);
        }
        int jobIndex;

        do{
            jobIndex = UIController.receiveInt();
        }while (!(jobIndex >= 0 && jobIndex < PlayerFieldJobs.values().length));

        return PlayerFieldJobs.values()[jobIndex];
    }

    public static boolean addCoach(String coachName, Team teamToAddCoach, TeamOwner teamOwner) throws Exception {
        return false;
    }

    public static boolean addTeamManager(String managerName, Team teamToAddManager, TeamOwner teamOwner) throws Exception {
        return false;
    }

    public static boolean addStadium(String stadiumName, Team teamToAddStadium, TeamOwner teamOwner) throws Exception {
        return false;
    }

/*
    //FIXME!!!!!!!!!!!!!!!!!!!!!!

    public static void chooseAssetToModify(Team chosenTeam) {

        List<TeamAsset> assetsCanBeModify = TeamController.getAssetTypeFromUser(chosenTeam); //Get which asset to display
        TeamAsset assetTypeToModify = assetTypeToModify(assetsCanBeModify);
        String[] properties = null;
        switch (assetTypeToModify){
            case PLAYER :
                try{
                    Player playerToModify = getPlayerByChoice(chosenTeam);
                    UIController.printMessage(playerToModify.toString());
                    properties = Player.getProperties();
                    int propertyIndexToModify = choosePropertiesToModify(properties);
                    UIController.printMessage("Enter new property value "+properties[propertyIndexToModify]);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case COACH:
                try {
                    Coach coachToModify = getCoachByChoice(chosenTeam);
                    UIController.printMessage(coachToModify.toString());
                    properties = Coach.getProperties();
                    int propertyIndexToModify = choosePropertiesToModify(properties);
                    UIController.printMessage("Enter new property value "+properties[propertyIndexToModify]);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case TEAM_MANAGER:
                try {
                    TeamManager teamManagerToModify= getTeamManagerByChoice(chosenTeam);
                    UIController.printMessage(teamManagerToModify.toString());
                    properties = TeamManager.getProperties();
                    int propertyIndexToModify = choosePropertiesToModify(properties);
                    UIController.printMessage("Enter new property value "+properties[propertyIndexToModify]);


                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case STADIUM:
                try {
                    Stadium stadium = getStadiumByChoice(chosenTeam);
                    UIController.printMessage(stadium.toString());
                    properties = Stadium.getProperties();
                    int propertyIndexToModify = choosePropertiesToModify(properties);
                    UIController.printMessage("Enter new property value "+properties[propertyIndexToModify]);


                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

 */

    public static void chooseAssetToModify(Team chosenTeam) {

        List<TeamAsset> assetsCanBeModify = TeamController.getAssetTypeFromUserG(chosenTeam); //Get which asset to display
        TeamAsset assetTypeToModify = assetTypeToModify(assetsCanBeModify);
        String[] properties = null;
        //todo: check
        if(assetTypeToModify == TeamAsset.STADIUM)
        {
            Stadium stadium = getStadiumByChoice(chosenTeam);
            UIController.printMessage(stadium.toString());
            properties = Stadium.getProperties();
            int propertyIndexToModify = choosePropertiesToModify(properties);
            UIController.printMessage("Enter new property value "+properties[propertyIndexToModify]);
        }
        else
        {
            Role roleToModify= getRoleByChoice(chosenTeam);
            UIController.printMessage(roleToModify.toString());
            /*Properties!!!!!!!!!*/
        }
    }


    /**
     *
     * @param chosenTeam
     * @return List<TeamAsset> - all the assets that the team has.
     */

    private static List<TeamAsset> getAssetTypeFromUserG(Team chosenTeam) {
        List<TeamAsset> teamAssets = new ArrayList<>();

        if(chosenTeam.getStadiums().size()>0)
        {
            teamAssets.add(TeamAsset.STADIUM);
        }
        if(chosenTeam.getAllRoles().size()>0)
        {
            teamAssets.add(TeamAsset.TEAM_MANAGER);
        }
        return teamAssets;

    }


    /**
     *
     * @param chosenTeam
     * @return List<TeamAsset> - all the assets that the team has.
     */

    private static List<TeamAsset> getAssetTypeFromUser(Team chosenTeam) {
        List<TeamAsset> teamAssets = new ArrayList<>();

        if(chosenTeam.getStadiums().size()>0)
        {
            teamAssets.add(TeamAsset.STADIUM);
        }
        if(chosenTeam.getTeamCoaches().size()>0)
        {
            teamAssets.add(TeamAsset.COACH);
        }
        if(chosenTeam.getTeamPlayers().size()>0)
        {
            teamAssets.add(TeamAsset.PLAYER);
        }
        if(chosenTeam.getTeamManagers().size()>0)
        {
            teamAssets.add(TeamAsset.TEAM_MANAGER);
        }
        return teamAssets;

    }

    /**
     *
     * @param assetsCanBeModify
     * @return TeamAsset - asset Type that user system want to modify
     */
    //todo: check TeamAsset.values()[i]);
    private static TeamAsset assetTypeToModifyG(List<TeamAsset> assetsCanBeModify)
    {
        UIController.printMessage("Choose Asset Type: ");
        for (int i = 0; i < assetsCanBeModify.size(); i++) {
            UIController.printMessage(i + ". " + TeamAsset.values()[i]);
        }
        int assetIndex;
        do{
            assetIndex = UIController.receiveInt();
        }while (!(assetIndex >= 0 && assetIndex < TeamAsset.values().length));

        return TeamAsset.values()[assetIndex];
    }


    /**
     *
     * @param assetsCanBeModify
     * @return TeamAsset - asset Type that user system want to modify
     */
    //todo: check TeamAsset.values()[i]);
    private static TeamAsset assetTypeToModify(List<TeamAsset> assetsCanBeModify)
    {
        UIController.printMessage("Choose Asset Type: ");
        for (int i = 0; i < assetsCanBeModify.size(); i++) {
            UIController.printMessage(i + ". " + TeamAsset.values()[i]);
        }
        int assetIndex;
        do{
            assetIndex = UIController.receiveInt();
        }while (!(assetIndex >= 0 && assetIndex < TeamAsset.values().length));

        return TeamAsset.values()[assetIndex];
    }


    /**
     *
     * @param chosenTeam
     * @return role - from chosenTeam who had been chosen by system user
     */
    private static Role getRoleByChoice(Team chosenTeam) {
        List<Role> myRoles = chosenTeam.getAllRoles();
        if(myRoles == null)
        {
            return null;
        }
        UIController.printMessage("Choose an Asset Role Number");
        for (int i = 0; i < myRoles.size() ; i++) {
            UIController.printMessage(i + ". " + myRoles.get(i).getSystemUser().getName());
        }
        int roleIndex;

        do{
            roleIndex = UIController.receiveInt();
        }while (!(roleIndex >= 0 && roleIndex < myRoles.size()));

        return myRoles.get(roleIndex);
    }

    //todo: all the next function maybe should be in Team class - public.


    /**
     *
     * @param chosenTeam
     * @return Player - from chosenTeam who had been chosen by system user
     */
    private static Player getPlayerByChoice(Team chosenTeam) {
        List<Player> myPlayers = chosenTeam.getTeamPlayers();
        if(myPlayers == null)
        {
            return null;
        }
        UIController.printMessage("Choose a Player Number");
        for (int i = 0; i < myPlayers.size() ; i++) {
            UIController.printMessage(i + ". " + myPlayers.get(i).getSystemUser().getName());
        }
        int playerIndex;

        do{
            playerIndex = UIController.receiveInt();
        }while (!(playerIndex >= 0 && playerIndex < myPlayers.size()));

        return myPlayers.get(playerIndex);
    }





    /**
     *
     * @param chosenTeam
     * @return Coach - from chosenTeam who had been chosen by system user
     */
    private static Coach getCoachByChoice(Team chosenTeam) {
        List<Coach> myCoaches = chosenTeam.getTeamCoaches();
        if(myCoaches == null)
        {
            return null;
        }
        UIController.printMessage("Choose a Coach Number");
        for (int i = 0; i < myCoaches.size() ; i++) {
            UIController.printMessage(i + ". " + myCoaches.get(i).getSystemUser().getName());
        }
        int coachIndex;

        do{
            coachIndex = UIController.receiveInt();
        }while (!(coachIndex >= 0 && coachIndex < myCoaches.size()));

        return myCoaches.get(coachIndex);
    }

    /**
     *
     * @param chosenTeam
     * @return TeamManager - from chosenTeam who had been chosen by system user
     */
    private static TeamManager getTeamManagerByChoice(Team chosenTeam) {
        List<TeamManager> myTeamManagers = chosenTeam.getTeamManagers();
        if(myTeamManagers == null)
        {
            return null;
        }
        UIController.printMessage("Choose a Team Manager Number");
        for (int i = 0; i < myTeamManagers.size() ; i++) {
            UIController.printMessage(i + ". " + myTeamManagers.get(i).getSystemUser().getName());
        }
        int teamManagerIndex;

        do{
            teamManagerIndex = UIController.receiveInt();
        }while (!(teamManagerIndex >= 0 && teamManagerIndex < myTeamManagers.size()));

        return myTeamManagers.get(teamManagerIndex);
    }

    /**
     *
     * @param chosenTeam
     * @return stadium - from chosenTeam who had been chosen by system user
     */
    private static Stadium getStadiumByChoice(Team chosenTeam) {
        List<Stadium> myStadiums = chosenTeam.getStadiums();
        if(myStadiums == null)
        {
            return null;
        }
        UIController.printMessage("Choose Stadium Number");
        for (int i = 0; i < myStadiums.size() ; i++) {
            UIController.printMessage(i + ". " + myStadiums.get(i).getStadName());
        }
        int stadiumIndex;

        do{
            stadiumIndex = UIController.receiveInt();
        }while (!(stadiumIndex >= 0 && stadiumIndex < myStadiums.size()));

        return myStadiums.get(stadiumIndex);
    }

    private static int choosePropertiesToModify(String [] properties)
    {
        UIController.printMessage("Choose Property Number to modify");
        for (int i = 0; i < properties.length; i++) {
            UIController.printMessage(i+". "+properties[i]);
        }
        int propertyIndex;
        do{
            propertyIndex = UIController.receiveInt();
        }while (!(propertyIndex >= 0 && propertyIndex < properties.length));

        return propertyIndex;
    }




}
