package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Stadium;
import Domain.Game.Season;
import Domain.Game.Team;
import Domain.Game.TeamStatus;
import Domain.Users.*;
import Service.UIController;

import java.util.List;


public class TeamController {


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
        teamToOwn.addTeamOwner(teamOwner);


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
    public static boolean reopenTeam(Team teamToReOpen) {
        teamToReOpen.setStatus(TeamStatus.OPEN);

        for(Stadium st : teamToReOpen.getStadiums()){
            if(st != null) //Check in the db that the stadium still exists
                st.addTeam(teamToReOpen);
            else{ //TODO: Remove this asset from the team because he is not in the system anymore!

            }
        }

        for(Role playerRole : teamToReOpen.getTeamPlayers()){
            if(roleStillExists(playerRole) &&
                    playerRole.getSystemUser().getRole(RoleTypes.PLAYER) instanceof Player) //Check in the db that the player still exists
            {
                Player p = (Player)playerRole;
                p.addTeam(teamToReOpen);
            }
            else{ //TODO: Remove this asset from the team because he is not in the system anymore!

            }
        }

        for(Role coachRole : teamToReOpen.getTeamCoaches()){
            if(roleStillExists(coachRole) &&
                    coachRole.getSystemUser().getRole(RoleTypes.COACH) instanceof Coach)  //Check in the db that the coach still exists
            {
                Coach c = (Coach) coachRole;
                c.addTeamToCoach(teamToReOpen);
            }
            else{ //TODO: Remove this asset from the team because he is not in the system anymore!

            }
        }

        for(Role tmRole : teamToReOpen.getTeamManagers()){
            if(roleStillExists(tmRole) &&
                    tmRole.getSystemUser().getRole(RoleTypes.TEAM_MANAGER) instanceof TeamManager)//Check in the db that the tm still exists
            {
                TeamManager tm = (TeamManager)tmRole;
                tm.addTeam(teamToReOpen);
            }
            else {
                //TODO: Remove this asset from the team because he is not in the system anymore!
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
}
