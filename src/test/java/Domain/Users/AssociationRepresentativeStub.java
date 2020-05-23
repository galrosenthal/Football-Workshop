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
    public boolean addReferee(SystemUser newRefereeUser, RefereeQualification training) throws RoleExistsAlreadyException {
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
    public void addPointsPolicy(int victoryPoints, int lossPoints, int tiePoints) throws Exception {
        if(selector == 9511){
            throw new IllegalArgumentException("The victory points most be positive");
        }else if(selector == 9512){
            throw new IllegalArgumentException("The loss points most be negative or zero");
        }else if(selector == 9513){
            throw new IllegalArgumentException("This Points policy already exists");
        }
    }

    @Override
    public boolean addTeam(String teamName, SystemUser newTeamOwnerUser) {
        return true;
    }
}
