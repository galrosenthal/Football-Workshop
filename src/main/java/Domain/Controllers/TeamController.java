package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Stadium;
import Domain.Game.Asset;
import Domain.Game.Season;
import Domain.Game.Team;
import Domain.Game.TeamStatus;
import Domain.Game.TeamAsset;
import Domain.Users.*;
import com.sun.xml.internal.bind.v2.TODO;
import Service.UIController;
import Domain.Exceptions.*;

import java.util.ArrayList;
import java.util.List;


public class TeamController {


    /**
     * Adding new team owner to the relevant team. The method checks that the current team owner - owner is
     * a owner in the team, the new owner has system user and he isn't a team owner of any the in the same season.
     * @param username - the user we want to add as team owner to the team
     * @param teamToOwn - the team we want to own her a new team owner
     * @param owner - the current owner of the team
     * @return true if the the operation succeed
     * @throws Exception
     */
    public static boolean addTeamOwner(String username, Team teamToOwn, TeamOwner owner)
    throws Exception{

        List<TeamOwner> teamOwners = teamToOwn.getTeamOwners();

        if (!teamOwners.contains(owner)) {
            throw new Exception("Only the owner of this team can add a new owner");
        }
        SystemUser newTeamOwnerUser = EntityManager.getInstance().getUser(username);

        if (newTeamOwnerUser == null) {
            throw new UserNotFoundException("Could not find a user by the given username");
        }

        Role newTeamOwnerRole = newTeamOwnerUser.getRole(RoleTypes.TEAM_OWNER);
        TeamOwner teamOwner;
        if (newTeamOwnerRole == null) {
            teamOwner = new TeamOwner(newTeamOwnerUser);
            newTeamOwnerUser.addNewRole(teamOwner);
        }
        else
        {

            teamOwner = (TeamOwner) newTeamOwnerRole;
            if (teamOwners.contains(teamOwner)) {
                throw new RoleExistsAlreadyException("This User is already a team owner of this team");
            }

            if (isAlreadyOwnedAnotherTeamInSeason(teamToOwn, teamOwner)) {
                throw new Exception("This User is already a team owner of a different team in same league");
            }

        }


        teamOwner.addTeamToOwn(teamToOwn);

        if(teamToOwn.addTeamOwner(teamOwner)){
            teamOwner.setAppointedOwner(owner.getSystemUser());
        }


        return true;
    }

    /**
     * Adds asset with assetName and assetType to the teamToAddAsset by the teamOwner
     * asset can be one of the following:
     * <p>{@link Player}</p>
     * <p>{@link Coach}</p>
     * <p>{@link TeamManager}</p>
     * <p>{@link Stadium}</p>
     *
     * @param assetName      is the Name or Username of the asset to add
     * @param teamToAddAsset is the Team to add the asset to
     * @param teamOwner      is the Owner who asks to add the asset
     * @param assetType      is the type of the asset to add {@link TeamAsset}
     * @return true if the player was added successfully
     * @throws NoTeamExistsException
     * @throws NotATeamOwner
     * @throws NullPointerException
     * @throws UserNotFoundException
     * @throws StadiumNotFoundException
     */
    public static boolean addAssetToTeam(String assetName, Team teamToAddAsset, TeamOwner teamOwner, TeamAsset assetType)
            throws NoTeamExistsException, NotATeamOwner, NullPointerException, UserNotFoundException
            , StadiumNotFoundException {
        if (teamToAddAsset == null) {
            throw new NoTeamExistsException("No Team was given");
        }
        if (assetType == null) {
            throw new NullPointerException("No Asset Type was given");
        }
        if (!teamToAddAsset.isTeamOwner(teamOwner)) {
            throw new NotATeamOwner("Not One of the Team Owners");
        }

        return teamToAddAsset.addAsset(assetName, teamOwner, assetType);
    }

    /**
     * Modify team asset property by the team owner - to chosen team
     * asset property can be - list property , enum property , string property.
     * all assets are implements asset interface!
     * @param chosenTeam
     * @return true if the assets has been modified
     * @throws AssetsNotExistsException
     * @throws AssetCantBeModifiedException
     */

    public static boolean editAssets(Team chosenTeam) throws AssetsNotExistsException, AssetCantBeModifiedException {
        List<Asset> allAssetsTeam = chosenTeam.getAllAssets();
        if (allAssetsTeam.size() == 0) {

            throw new AssetsNotExistsException("There is not assets to team");

        }

        List<Object> objects = new ArrayList<>();
        objects.addAll(allAssetsTeam);
        //User choose which asset he wants to modify
        UIController.printMessage("Choose asset to Modify: ");
        int assetIndex = TeamController.choosePropertiesUI(objects);
        List<String> properties = allAssetsTeam.get(assetIndex).getProperties();
        if (properties.size() == 0) {

            throw new AssetCantBeModifiedException("Nothing can be modify in this asset");

        }
        objects = new ArrayList<>();
        objects.addAll(properties);
        //choosePropertiesToModify
        UIController.printMessage("Choose Property Number to modify");
        int propertyIndexToModify = TeamController.choosePropertiesUI(objects);
        if (allAssetsTeam.get(assetIndex).isListProperty(properties.get(propertyIndexToModify))) {
            int action = actionToDo();
            if (action == 0) {
                boolean isAdded = addProperty(allAssetsTeam.get(assetIndex), properties.get(propertyIndexToModify), chosenTeam);
                if (!isAdded) {

                    throw new AssetCantBeModifiedException("Can not modify asset");

                } else {
                    return true;
                }
            } else {
                boolean isRemoved = removeProperty(allAssetsTeam.get(assetIndex), properties.get(propertyIndexToModify), chosenTeam);
                if (!isRemoved) {

                    throw new AssetCantBeModifiedException("Can not modify asset");

                } else {
                    return true;
                }
            }
           // In case the user wants to modify EnumProperty - choose which new property he wants to change.
        } else if (allAssetsTeam.get(assetIndex).isEnumProperty(properties.get(propertyIndexToModify))) {
            List<Enum> allEnumValues = allAssetsTeam.get(assetIndex).getAllValues(properties.get(propertyIndexToModify));
            UIController.printMessage("Choose Property new value ");
            objects = new ArrayList<>();
            objects.addAll(allEnumValues);
            int propertyNewValueIndex = choosePropertiesUI(objects);
            return allAssetsTeam.get(assetIndex).changeProperty(properties.get(propertyIndexToModify), allEnumValues.get(propertyNewValueIndex).toString());
        } else if (allAssetsTeam.get(assetIndex).isStringProperty(properties.get(propertyIndexToModify))) {
            UIController.printMessage("Enter new value: ");
            String newValue = UIController.receiveString();
            return allAssetsTeam.get(assetIndex).changeProperty(properties.get(propertyIndexToModify), newValue);
        }
        return false;
    }


    /**
     * add to asset property - type list
     * @param asset - asset to modify
     * @param propertyName - which property
     * @param team - chosen team
     * @return true if successfully added
     */
    private static boolean addProperty(Asset asset, String propertyName, Team team) {
        List<Enum> enumList = team.getAllProperty(asset, propertyName);
        if (enumList == null) {
            return false;
        }
        List<Enum> allValue = asset.getAllValues(propertyName);
        allValue.removeAll(enumList);
        if (allValue.size() == 0) {
            return false;
        }
        //add attribute from property list
        UIController.printMessage("Choose Property to add ");
        List<Object> objects = new ArrayList<>();
        objects.addAll(allValue);
        int indexToAdd = choosePropertiesUI(objects);
        return asset.addProperty(propertyName, allValue.get(indexToAdd), team);
    }


    /**
     * remove to asset property - type list
     * @param asset - asset to modify
     * @param propertyName - which property
     * @param team - chosen team
     * @return true if successfully removed
     */
    private static boolean removeProperty(Asset asset, String propertyName, Team team) {
        List<Enum> enumList = team.getAllProperty(asset, propertyName);
        if (enumList == null || enumList.size() == 0) {
            return false;
        }
        //remove attribute from property list
        UIController.printMessage("Choose Property to remove ");
        List<Object> objects = new ArrayList<>();
        objects.addAll(enumList);
        int indexToRemove = choosePropertiesUI(objects);
        return asset.removeProperty(propertyName, enumList.get(indexToRemove), team);
    }




    /**
     * @param properties - list of all properties
     * @return propertyIndex -int- the user wants to modify
     */
    private static int choosePropertiesUI(List<Object> properties) {
        for (int i = 0; i < properties.size(); i++) {
            UIController.printMessage(i + 1 + ". " + properties.get(i));
        }
        int propertyIndex;
        do {
            propertyIndex = UIController.receiveInt() - 1;
        } while (!(propertyIndex >= 0 && propertyIndex < properties.size()));

        return propertyIndex;
    }



    /**
     * In case the property ia a list - let the user to choose if he want to Add or Delete value to the list
     *
     * @return 0 - if to add value , 1 - if to remove value
     */
    private static int actionToDo() {
        UIController.printMessage("Choose which action: ");
        UIController.printMessage("1. Add");
        UIController.printMessage("2. Remove");
        int propertyIndex;
        do {
            propertyIndex = UIController.receiveInt() - 1;
        } while (!(propertyIndex >= 0 && propertyIndex <= 1));

        return propertyIndex;
    }


    /**
     * Check if the new team owner already owned a team in the same season (which is the same league)
     *
     * @param teamToOwn    the team he should be owned to
     * @param ownerToCheck the new team owner
     * @return true if the new team owner already owned a team in the same season
     */
    private static boolean isAlreadyOwnedAnotherTeamInSeason(Team teamToOwn, TeamOwner ownerToCheck) {

        Season currentSeason = teamToOwn.getCurrentSeason();
        //If the team not assigned yet to a season
        if(currentSeason == null){
            return false;
        }

        List<Team> teamsInSeason = currentSeason.getTeams();
        if (teamsInSeason.size() == 0) {
            return false;
        }
        for (Team team : teamsInSeason) {
            List<TeamOwner> teamOwners = team.getTeamOwners();

            if (teamOwners.contains(ownerToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Closing the team, removing the team itself from its assets,
     * BUT not the assets from the team. After the team is closed we cannot perform actions on it.
     * @param teamToClose Team to close.
     * @return True if succeeded.
     */
    public static boolean closeTeam(Team teamToClose) {
        //Removing the team from team managers, coaches, players, stadiums
        for(Stadium st : teamToClose.getStadiums()){
            st.removeTeam(teamToClose);
        }
        for(TeamManager tm : teamToClose.getTeamManagers()){
            tm.removeTeam(teamToClose);
        }
        for(Player p : teamToClose.getTeamPlayers()){
            p.removeTeam(teamToClose);
        }
        for(Coach coach : teamToClose.getTeamCoaches()){
            coach.removeTeamToCoach(teamToClose);
        }

        teamToClose.setStatus(TeamStatus.CLOSED);
        return true;
    }

    /**
     * Re-open a closed team. Trying to add back the team's managers, coaches, players, stadiums.
     * Check that the team's assets still exists, if so, adds back the team to the assets.
     * If an asset is no longer in the system or its permissions gor revoked, we should
     * remove the asset from the team.
     * @param teamToReOpen The team to reopen.
     * @return True if succeeded.
     */
    public static boolean reopenTeam(Team teamToReOpen, TeamOwner teamOwner) {
        teamToReOpen.setStatus(TeamStatus.OPEN);

        for(int i = 0 ; i < teamToReOpen.getStadiums().size(); i++){
            Stadium st = teamToReOpen.getStadiums().get(i);
            if(st != null && EntityManager.getInstance().isStadiumExists(st)) //Check in the db that the stadium still exists
                st.addTeam(teamToReOpen, teamOwner);
            else{
                //Removes stadium from the team because he is no longer exists.
                teamToReOpen.removeStadium(st);
                i--;
            }
        }

        for(int i =0 ; i < teamToReOpen.getTeamPlayers().size(); i++){
            Player playerRole = teamToReOpen.getTeamPlayers().get(i);
            if(roleStillExists(playerRole) &&
                    playerRole.getSystemUser().getRole(RoleTypes.PLAYER) instanceof Player) //Check in the db that the player still exists
            {
                playerRole.addTeam(teamToReOpen);
            }
            else{
                //Removes player from the team because he is no longer exists.
                teamToReOpen.removeTeamPlayer(playerRole);
                i--;
            }
        }

        for(int i = 0 ; i < teamToReOpen.getTeamCoaches().size(); i++){
            Coach coachRole = teamToReOpen.getTeamCoaches().get(i);
            if(roleStillExists(coachRole) &&
                    coachRole.getSystemUser().getRole(RoleTypes.COACH) instanceof Coach)  //Check in the db that the coach still exists
            {
                coachRole.addTeamToCoach(teamToReOpen);
            }
            else{
                //Removes Team Coach from the team because he is no longer exists.
                teamToReOpen.removeTeamCoach(coachRole);
                i--;
            }
        }

        for(int i =0 ; i < teamToReOpen.getTeamManagers().size(); i++){
            TeamManager tmRole = teamToReOpen.getTeamManagers().get(i);
            if(roleStillExists(tmRole) &&
                    tmRole.getSystemUser().getRole(RoleTypes.TEAM_MANAGER) instanceof TeamManager)//Check in the db that the tm still exists
            {
                tmRole.addTeam(teamToReOpen);
            }
            else {
                //Removes Team Manager from the team because he is no longer exists.
                teamToReOpen.removeTeamManager(tmRole);
                i--;
                if(tmRole == null || tmRole.getSystemUser() == null
                        || EntityManager.getInstance().getUser(tmRole.getSystemUser().getUsername()) == null) {
                    //the user deleted entirely from the system
                    UIController.printMessage("Could not restore permissions of lost team manager");
                }
                else {//The user exists but no longer have the role team-manager
                    UIController.printMessage("Could not restore permissions of the team manager: " +
                            tmRole.getSystemUser().getName());
                }
            }
        }

        return true;
    }

    /**
     * Checks if a role still exists on the system.
     * @param role Role to check.
     * @return True if the role still exists.
     */
    private static boolean roleStillExists(Role role){
        if(role != null &&
                role.getSystemUser() != null &&
                EntityManager.getInstance().getUser(role.getSystemUser().getUsername())!= null)
            return true;
        return false;
    }

    /**
     *
     * @param
     * @param
     * @param
     */
    public static boolean removeTeamOwner(String username, Team team, TeamOwner owner) throws Exception {
        List<TeamOwner> teamOwners = team.getTeamOwners();

        if (!teamOwners.contains(owner)) {
            throw new Exception("Only the owner of this team can remove owner");
        }
        SystemUser newTeamOwnerUser = EntityManager.getInstance().getUser(username);

        if (newTeamOwnerUser == null) {
            throw new Exception("Could not find a user by the given username");
        }

        Role TeamOwnerRole = newTeamOwnerUser.getRole(RoleTypes.TEAM_OWNER);
        TeamOwner teamOwner = (TeamOwner)TeamOwnerRole;

        if(!teamOwners.contains(teamOwner)){
            throw new Exception("The user is not a owner of this team");
        }

        List<TeamOwner> teamOwnersToRemove= allTeamOwnersToRemove(teamOwner,teamOwners);

        //Remove the teamOwner and all the team owners his appointed
        for (TeamOwner toRemove:
                teamOwnersToRemove) {
            team.removeTeamOwner(toRemove);
        }

        return true;
    }

    /**
     * The method returns all the team owners we owned by the team owner the user want to remove
     * @param teamOwner - the appointed owner
     * @return list of all the owners the teamOwner appointed
     */
    private static List<TeamOwner> allTeamOwnersToRemove(TeamOwner teamOwner,List<TeamOwner> teamOwners) {
        List<TeamOwner> teamOwnersToCheck = new ArrayList<>();
        List<TeamOwner> teamOwnersToRemove = new ArrayList<>();

        teamOwnersToCheck.add(teamOwner);
        teamOwnersToRemove.add(teamOwner);

        //Remove the desired owner


        while(teamOwnersToCheck.size() != 0){
            TeamOwner teamOwnerToCheck = teamOwnersToCheck.remove(0);

            for (TeamOwner ownerOfTeam: teamOwners){

                // Change the if to representative once it will complete
                if(ownerOfTeam.getAppointedOwner()!= null && ownerOfTeam.getAppointedOwner().equals(teamOwnerToCheck.getSystemUser())){
                    teamOwnersToCheck.add(ownerOfTeam);
                    teamOwnersToRemove.add(ownerOfTeam);
                }
            }
        }

        return teamOwnersToRemove;
    }
}
