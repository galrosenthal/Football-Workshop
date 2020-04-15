package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Season;
import Domain.Game.Team;
import Domain.Users.*;

import java.util.List;


public class TeamController {


    public static boolean addTeamOwner(String username, Team teamToOwn, TeamOwner owner)
    throws Exception{

        List<TeamOwner> teamOwners = teamToOwn.getTeamOwners();

        SystemUser newTeamOwnerUser = EntityManager.getInstance().getUser(username);

        if(newTeamOwnerUser == null)
        {
            throw new Exception("Could not find a user by the given username");
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

            if(isAlreadyOwnedAnotherTeamInLeague(teamToOwn,teamOwner)){
                throw new Exception("This User is already a team owner of a different team in same league");
            }
            if(teamOwners.contains(teamOwner))
            {
                throw new Exception("This User is already a team owner");
            }
        }
        teamOwner.addTeamToOwn(teamToOwn);
        teamToOwn.addTeamOwner(owner,teamOwner);


        return true;
    }

    /**
     *Check if the new team owner already owned a team in the same league the team he should be owned exist
     * @param teamToOwn the team he should be owned to
     * @param ownerToCheck the new team owner
     * @return true if the new team owner already owned a team in the same league
     */
    private static boolean isAlreadyOwnedAnotherTeamInLeague(Team teamToOwn,TeamOwner ownerToCheck){

        Season currentSeason = teamToOwn.getCurrentSeason();
        List<Team> teamsInSeason = currentSeason.getTeams();

        for (Team team: teamsInSeason){
            List<TeamOwner> teamOwners = team.getTeamOwners();

            if(teamOwners.contains(ownerToCheck)){
                return true;
            }
        }
        return false;
    }

}
