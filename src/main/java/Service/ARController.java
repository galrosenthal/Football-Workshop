package Service;

import Domain.EntityManager;
import Domain.Users.AssociationRepresentative;
import Domain.Users.Role;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;

/**
 * Association Representative services controller
 */
public class ARController {

    /**
     * Controls the flow of Creating a new League.
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
        return true;
    }
}
