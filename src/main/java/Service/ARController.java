package Service;

import Domain.EntityManager;
import Domain.Game.League;
import Domain.Game.Season;
import Domain.Users.AssociationRepresentative;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;

import java.util.List;

/**
 * Association Representative services controller
 */
public class ARController {

    /**
     * Controls the flow of Creating a new League.
     *
     * @param systemUser - SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return - boolean - True if a new league was created successfully, else false
     */
    public static boolean addLeague(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);

        UIController.printMessage("Enter new league name:");
        String leagueName = UIController.receiveString();

        //delegate the operation responsibility to AssociationRepresentative
        try {
            ARRole.addLeague(leagueName);
        } catch (Exception e) {
            UIController.printMessage(e.getMessage());
            return false;
        }
        UIController.printMessage("The league was created successfully");
        return true;
    }

    public static boolean addSeasonToLeague(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);

        League chosenLeague = getLeagueByChoice();

        String seasonYears = "";
        boolean seasonExists = true;
        while (seasonExists) {
            UIController.printMessage("Enter season years (yyyy/yy) ex. 2020/21:");
            seasonYears = UIController.receiveString();
            if (!Season.isGoodYearsFormat(seasonYears)) {
                do {
                    UIController.printMessage("wrong years format, please enter the season years (yyyy/yy) ex. 2020/21:");
                    seasonYears = UIController.receiveString();
                } while (!Season.isGoodYearsFormat(seasonYears));
            }

            if (!chosenLeague.doesSeasonExists(seasonYears)) {
                seasonExists = false;
            } else {
                UIController.printMessage("This season already exists in the chosen league. Please enter different years");
            }
        }

        if (chosenLeague.addSeason(seasonYears)) {
            return true;
        }
        UIController.printMessage("The season creation failed");
        return false;
    }

    private static League getLeagueByChoice() {
        List<League> leagues = EntityManager.getInstance().getLeagues();
        UIController.printMessage("Choose a League Number");
        for (int i = 0; i < leagues.size(); i++) {
            UIController.printMessage(i + ". " + leagues.get(i).getName());
        }
        int Index;
        do {
            Index = UIController.receiveInt();
        } while (!(Index >= 0 && Index < leagues.size()));

        return leagues.get(Index);
    }
}
