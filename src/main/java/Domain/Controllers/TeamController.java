package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Asset;
import Domain.Game.Team;
import Domain.Users.*;
import Domain.Exceptions.*;
import Service.UIController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TeamController {


    public static boolean addTeamOwner(String username, Team teamToOwn, TeamOwner owner)
            throws Exception {

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
            int fieldJobIndex = getEnumByRoleType("FiledJob for player", RoleTypes.PLAYER);
            PlayerFieldJobs filedJob = PlayerFieldJobs.values()[fieldJobIndex];
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
    private static int getEnumByRoleType(String msg, RoleTypes rt) {
        UIController.printMessage("Please Choose" + msg);
        List<Object> values = new ArrayList<>();
        if(rt == RoleTypes.PLAYER)
        {

            for (int i = 0; i < PlayerFieldJobs.values().length; i++) {
                values.add(PlayerFieldJobs.values()[i]);
            }

        }
        else if(rt == RoleTypes.COACH)
        {
            for (int i = 0; i < CoachQualification.values().length; i++) {
                values.add(CoachQualification.values()[i]);
            }
        }
        else
        {
            return -1;
        }

        for (int i = 0; i < values.size(); i++) {
            UIController.printMessage(i + ". " + values.get(i));
        }

        int index;

        do{
            index = UIController.receiveInt();
        }while (!(index >= 0 && index < PlayerFieldJobs.values().length));

        return index;
    }

    public static boolean addCoach(String coachUsername, Team teamToAddCoach, TeamOwner teamOwner) throws Exception {
        SystemUser coachUser = EntityManager.getInstance().getUser(coachUsername);
        if(coachUser == null)
        {
            throw new UserNotFoundException("Could not find a user by the given username" + coachUsername);
        }

        Role getRoleForUser = coachUser.getRole(RoleTypes.COACH);
        Coach coachRole;
        if(getRoleForUser == null)
        {
            int index = getEnumByRoleType("qualification for coach", RoleTypes.COACH);
            CoachQualification qlf = CoachQualification.values()[index];

            UIController.printMessage("what is the Coach JobTitle?");
            String jobTitle = UIController.receiveString();

            coachRole = new Coach(coachUser, qlf, teamToAddCoach, jobTitle);

        }
        else
        {
            coachRole = (Coach) getRoleForUser;
            if(teamToAddCoach.getTeamCoaches().contains(coachRole))
            {
                throw new CoachIsAlreadyInThisTeamException("Coach is already in this team");
            }
        }

        return teamToAddCoach.addTeamCoach(teamOwner,coachRole);
    }

    public static boolean addTeamManager(String managerUsername, Team teamToAddManager, TeamOwner teamOwner) throws Exception {
        SystemUser managerUser = EntityManager.getInstance().getUser(managerUsername);
        if(managerUser == null)
        {
            throw new UserNotFoundException("Could not find a user by the given username" + managerUsername);
        }

        Role getRoleForUser = managerUser.getRole(RoleTypes.COACH);
        TeamManager managerRole;
        if(getRoleForUser == null)
        {


            managerRole = new TeamManager(managerUser);
        }
        return false;
    }

    public static boolean addStadium(String stadiumName, Team teamToAddStadium, TeamOwner teamOwner) throws Exception {
        return false;
    }


    public static boolean editAssets(Team chosenTeam)
    {
        List<Asset> allAssetsTeam = chosenTeam.getAllAssets();
        int assetIndex =TeamController.chooseAssetToModify(allAssetsTeam);
        List<String> properties = allAssetsTeam.get(assetIndex).getProperties();
        int propertyIndexToModify = choosePropertiesToModify(properties);
        allAssetsTeam.get(assetIndex).changeProperty(properties.get(propertyIndexToModify), properties.get(propertyIndexToModify));
        if( allAssetsTeam.get(assetIndex).isListProperty(properties.get(propertyIndexToModify)))
        {
            int action = actionToDo();
            if(action == 0)
            {
                allAssetsTeam.get(assetIndex).addProperty();
            }
            else
            {
                allAssetsTeam.get(assetIndex).removeProperty();
            }
        }
        else if(allAssetsTeam.get(assetIndex).isEnumProperty(properties.get(propertyIndexToModify)))
        {
            List<Enum> allEnumValues = allAssetsTeam.get(assetIndex).getAllValues(properties.get(propertyIndexToModify));
            int propertyNewValueIndex = changePropertyValue(allEnumValues);
            allAssetsTeam.get(assetIndex).changeProperty(properties.get(propertyIndexToModify) ,  allEnumValues.get(propertyNewValueIndex).toString());
        }
        else if(allAssetsTeam.get(assetIndex).isStringProperty(properties.get(propertyIndexToModify)))
        {
            UIController.printMessage("Enter new value: ");
            String newValue = UIController.receiveString();
            allAssetsTeam.get(assetIndex).changeProperty(properties.get(propertyIndexToModify) , newValue);
        }
        return true;
    }
    /**
     *
     * @param allAssetsTeam - List<Asset>
     */
    private static int chooseAssetToModify(List<Asset> allAssetsTeam) {

        UIController.printMessage("Choose Asset to modify: ");
        for (int i = 0; i < allAssetsTeam.size(); i++) {
            UIController.printMessage(i+1 + ". " + allAssetsTeam.get(i));
        }
        int assetIndex;
        do {
            assetIndex = UIController.receiveInt();
        } while (!(assetIndex >= 1 && assetIndex < allAssetsTeam.size()));
        return assetIndex;


    }




    /**
     * In case the user wants to modify EnumProperty - choose which new property he wants to change.
     * @param allEnumValues
     * @return
     */
    private static int changePropertyValue( List<Enum> allEnumValues) {
        UIController.printMessage("Choose Property new value ");
        for (int i = 0; i < allEnumValues.size(); i++) {
            UIController.printMessage(i + ". " + allEnumValues.get(i).toString());

        }
        int propertyNewValueIndex;
        do {
            propertyNewValueIndex = UIController.receiveInt();
        } while (!(propertyNewValueIndex >= 0 && propertyNewValueIndex < allEnumValues.size()));
        return propertyNewValueIndex;

    }

    /**
     *
     * @param properties - list of all properties
     * @return propertyIndex -int- the user wants to modify
     */
    private static int choosePropertiesToModify( List<String>properties) {
        UIController.printMessage("Choose Property Number to modify");
        for (int i = 0; i < properties.size(); i++) {
            UIController.printMessage(i + ". " + properties.get(i));
        }
        int propertyIndex;
        do {
            propertyIndex = UIController.receiveInt();
        } while (!(propertyIndex >= 0 && propertyIndex < properties.size()));

        return propertyIndex;
    }

    /**
     * In case the property ia a list - let the user to choose if he want to Add or Delete value to the list
     * @return 0 - if to add value , 1 - if to remove value
     */
    private static int actionToDo()
    {
        UIController.printMessage("Choose which action: ");
        UIController.printMessage("1. Add");
        UIController.printMessage("2. Remove");
        int propertyIndex;
        do {
            propertyIndex = UIController.receiveInt()-1;
        } while (!(propertyIndex >= 0 && propertyIndex <=1));

        return propertyIndex;
    }



}
