package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Season;
import Domain.Game.Team;
import Domain.Users.*;
import com.sun.xml.internal.bind.v2.TODO;

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

        if(!teamOwners.contains(owner)){
            throw new Exception("Only the owner of this team can add a new owner");
        }
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
            //teamOwner.addTeamToOwn(teamToOwn);
            newTeamOwnerUser.addNewRole(teamOwner);

        }
        else
        {

            teamOwner = (TeamOwner) newTeamOwnerRole;

            if(teamOwners.contains(teamOwner))
            {
                throw new Exception("This User is already a team owner of this team");
            }

            if(isAlreadyOwnedAnotherTeamInSeason(teamToOwn,teamOwner)){
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
     *Check if the new team owner already owned a team in the same season (which is the same league)
     * @param teamToOwn the team he should be owned to
     * @param ownerToCheck the new team owner
     * @return true if the new team owner already owned a team in the same season
     */
    private static boolean isAlreadyOwnedAnotherTeamInSeason(Team teamToOwn, TeamOwner ownerToCheck){

        Season currentSeason = teamToOwn.getCurrentSeason();

        //If the team not assigned yet to a season
        if(currentSeason == null){
            return false;
        }

        List<Team> teamsInSeason = currentSeason.getTeams();

        for (Team team: teamsInSeason){
            List<TeamOwner> teamOwners = team.getTeamOwners();

            if(teamOwners.contains(ownerToCheck)){
                return true;
            }
        }
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
