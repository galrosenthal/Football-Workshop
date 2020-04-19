package Domain.Users;

import Domain.EntityManager;
import Domain.Financials.AssociationFinancialRecordLog;

import java.util.List;

public class AssociationRepresentative extends Role {
    List<AssociationFinancialRecordLog> logger;
    public AssociationRepresentative(SystemUser systemUser) {
        super(RoleTypes.ASSOCIATION_REPRESENTATIVE, systemUser);
    }

    /**
     * Creates a new League.
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
}
