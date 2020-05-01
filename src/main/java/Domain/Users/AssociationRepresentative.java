package Domain.Users;

import Domain.EntityManager;
import Domain.Exceptions.RoleExistsAlreadyException;
import Domain.Financials.AssociationFinancialRecordLog;

import java.util.List;

public class AssociationRepresentative extends Role {
    List<AssociationFinancialRecordLog> logger;

    public AssociationRepresentative(SystemUser systemUser) {
        super(RoleTypes.ASSOCIATION_REPRESENTATIVE, systemUser);
    }

    /**
     * Creates a new League.
     *
     * @param leagueName - String - A unique league name
     * @return - boolean - True if a new league was created successfully, else false
     * @throws Exception - throws if a league already exists with the given leagueName
     */
    public boolean addLeague(String leagueName) throws Exception {
        //verifies if a league in the chosen name already exists
        boolean doesLeagueExist = EntityManager.getInstance().doesLeagueExists(leagueName);
        if (doesLeagueExist) {
            throw new Exception("League with the same name already exists");
        }
        //Adding a new league
        EntityManager.getInstance().addLeague(leagueName);

        return true;
    }

    /**
     * Adds a new Referee role to a given user with the given training.
     * If the user is already a referee then throw exception.
     *
     * @param newRefereeUser - SystemUser - a user to add a referee role to.
     * @param training       - String - the training of the referee
     * @return - boolean - true if the referee role was added successfully.
     * @throws RoleExistsAlreadyException - if the user is already a referee.
     */
    public boolean addReferee(SystemUser newRefereeUser, String training) throws RoleExistsAlreadyException {
        if (newRefereeUser.getRole(RoleTypes.REFEREE) != null) {
            throw new RoleExistsAlreadyException("Already a referee");
        }
        Referee refereeRole = new Referee(newRefereeUser, training);

        return true;
    }

    /**
     * Removes the referee role from a given user.
     * @param chosenUser - SystemUser - a user with a Referee role to be removed.
     * @return - boolean - true if the Referee role was removed successfully, else false
     */
    public boolean removeReferee(SystemUser chosenUser) {
        Referee refereeRole = (Referee)chosenUser.getRole(RoleTypes.REFEREE);
        if (refereeRole!= null) {
            if(!refereeRole.hasFutureGames()) {
                chosenUser.removeRole(refereeRole);
                return true;
            }
        }
        return false;
    }
}
