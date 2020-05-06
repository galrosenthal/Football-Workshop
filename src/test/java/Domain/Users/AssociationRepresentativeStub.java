package Domain.Users;

import Domain.Exceptions.RoleExistsAlreadyException;
import Domain.Game.Season;

public class AssociationRepresentativeStub extends AssociationRepresentative{

    private static int selector;

    public AssociationRepresentativeStub(SystemUser systemUser) {
        super(systemUser);
    }

    public static void setSelector(int selector) {
        AssociationRepresentativeStub.selector = selector;
    }

    @Override
    public boolean addLeague(String leagueName) throws Exception {
        if (selector ==0) {
            throw new Exception("stub exception");
        }
        return true;
    }
    @Override
    public boolean addReferee(SystemUser newRefereeUser, String training) throws RoleExistsAlreadyException {
        if (selector ==0) {
            throw new RoleExistsAlreadyException("Already a referee");
        }
        return true;
    }

    @Override
    public boolean removeReferee(SystemUser chosenUser) {
        if (selector ==0) {
            return false;
        }
        return  true;
    }

    @Override
    public void assignRefereeToSeason(Season chosenSeason, Referee refereeRole) throws Exception {
        if (selector ==0) {
            throw new Exception("This referee is already assigned to the chosen season");
        }
    }


    @Override
    public boolean addTeam(String teamName, SystemUser newTeamOwnerUser) {
        return true;
    }
}
