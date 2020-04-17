package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Asset;
import Domain.Game.Season;
import Domain.Game.Stadium;
import Domain.Game.Team;
import Domain.Game.TeamAsset;
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
        } else {

            teamOwner = (TeamOwner) newTeamOwnerRole;
            if (teamOwners.contains(teamOwner)) {
                throw new alreadyTeamOwnerException("This User is already a team owner of this team");
            }

            if (isAlreadyOwnedAnotherTeamInSeason(teamToOwn, teamOwner)) {
                throw new Exception("This User is already a team owner of a different team in same league");
            }

        }

        teamOwner.addTeamToOwn(teamToOwn);
        teamToOwn.addTeamOwner(teamOwner);


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

    public static boolean editAssets(Team chosenTeam) throws Exception {
        List<Asset> allAssetsTeam = chosenTeam.getAllAssets();
        if (allAssetsTeam.size() == 0) {

            throw new AssetsNotExistsException("There is not assets to team");

        }
        int assetIndex = TeamController.chooseAssetToModify(allAssetsTeam);
        List<String> properties = allAssetsTeam.get(assetIndex).getProperties();
        if (properties.size() == 0) {

            throw new AssetCantBeModifiedException("Nothing can be modify in this asset");

        }
        int propertyIndexToModify = choosePropertiesToModify(properties);
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
        } else if (allAssetsTeam.get(assetIndex).isEnumProperty(properties.get(propertyIndexToModify))) {
            List<Enum> allEnumValues = allAssetsTeam.get(assetIndex).getAllValues(properties.get(propertyIndexToModify));
            int propertyNewValueIndex = changePropertyValue(allEnumValues);
            return allAssetsTeam.get(assetIndex).changeProperty(properties.get(propertyIndexToModify), allEnumValues.get(propertyNewValueIndex).toString());
        } else if (allAssetsTeam.get(assetIndex).isStringProperty(properties.get(propertyIndexToModify))) {
            UIController.printMessage("Enter new value: ");
            String newValue = UIController.receiveString();
            //todo: check string?
            return allAssetsTeam.get(assetIndex).changeProperty(properties.get(propertyIndexToModify), newValue);
        }
        return false;
    }

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
        int indexToAdd = addPropertyToListProperty(allValue);
        return asset.addProperty(propertyName, allValue.get(indexToAdd), team);
    }

    /**
     * In case the user wants to add Property to property list- choose which new property he wants to change.
     * - assumption- list of Enums property
     *
     * @param allEnumValues
     * @return propertyNewValueIndex -int-
     */
    private static int addPropertyToListProperty(List<Enum> allEnumValues) {
        UIController.printMessage("Choose Property to add ");
        for (int i = 0; i < allEnumValues.size(); i++) {
            UIController.printMessage(i + 1 + ". " + allEnumValues.get(i).toString());

        }
        int propertyValueIndex;
        do {
            propertyValueIndex = UIController.receiveInt() - 1;
        } while (!(propertyValueIndex >= 0 && propertyValueIndex < allEnumValues.size()));
        return propertyValueIndex;

    }

    private static boolean removeProperty(Asset asset, String propertyName, Team team) {
        List<Enum> enumList = team.getAllProperty(asset, propertyName);
        if (enumList == null || enumList.size() == 0) {
            return false;
        }
        int indexToAdd = removePropertyToListProperty(enumList);
        return asset.removeProperty(propertyName, enumList.get(indexToAdd), team);
    }

    /**
     * In case the user wants to remove Property to property list- choose which new property he wants to change.
     * - assumption- list of Enums property
     *
     * @param allEnumValues
     * @return propertyNewValueIndex -int-
     */
    private static int removePropertyToListProperty(List<Enum> allEnumValues) {
        UIController.printMessage("Choose Property to remove ");
        for (int i = 0; i < allEnumValues.size(); i++) {
            UIController.printMessage(i + 1 + ". " + allEnumValues.get(i).toString());

        }
        int propertyValueIndex;
        do {
            propertyValueIndex = UIController.receiveInt() - 1;
        } while (!(propertyValueIndex >= 0 && propertyValueIndex < allEnumValues.size()));
        return propertyValueIndex;

    }

    /**
     * User choose which asset he wants to modify
     *
     * @param allAssetsTeam - List<Asset>
     * @return assetIndex -int-
     */
    private static int chooseAssetToModify(List<Asset> allAssetsTeam) {

        UIController.printMessage("Choose Asset to modify: ");
        for (int i = 0; i < allAssetsTeam.size(); i++) {
            UIController.printMessage(i + 1 + ". " + allAssetsTeam.get(i));
        }
        int assetIndex;
        do {
            assetIndex = UIController.receiveInt();
        } while (!(assetIndex >= 1 && assetIndex <= allAssetsTeam.size()));
        return assetIndex - 1;
    }


    /**
     * In case the user wants to modify EnumProperty - choose which new property he wants to change.
     *
     * @param allEnumValues
     * @return propertyNewValueIndex -int-
     */
    private static int changePropertyValue(List<Enum> allEnumValues) {
        UIController.printMessage("Choose Property new value ");
        for (int i = 0; i < allEnumValues.size(); i++) {
            UIController.printMessage(i + 1 + ". " + allEnumValues.get(i).toString());

        }
        int propertyNewValueIndex;
        do {
            propertyNewValueIndex = UIController.receiveInt() - 1;
        } while (!(propertyNewValueIndex >= 0 && propertyNewValueIndex < allEnumValues.size()));
        return propertyNewValueIndex;

    }

    /**
     * @param properties - list of all properties
     * @return propertyIndex -int- the user wants to modify
     */
    private static int choosePropertiesToModify(List<String> properties) {
        UIController.printMessage("Choose Property Number to modify");
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
        if (currentSeason == null) {
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

}
