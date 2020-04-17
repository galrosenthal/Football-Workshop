package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Stadium;
import Domain.Game.Team;
import Domain.Game.TeamStatus;
import Domain.Users.*;
import Service.UIController;

import java.util.List;


public class TeamController {


    public static boolean addTeamOwner(String username, Team teamToOwn, TeamOwner owner)
    throws Exception{

        List<TeamOwner> teamOwners = teamToOwn.getTeamOwners();

        SystemUser newTeamOwnerUser = EntityManager.getInstance().getUser(username);

        //TODO: check already owned another team in the league
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
                throw new Exception("This User is already a team owner");
            }
        }
        teamOwner.addTeamToOwn(teamToOwn);
        teamToOwn.addTeamOwner(owner,teamOwner);



        return true;
    }


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

    public static boolean reopenTeam(Team teamToReOpen) {
        //Trying to add back the team's managers, coaches, players, stadiums
        //Check that the team's assets still exists

        for(Stadium st : teamToReOpen.getStadiums()){
            if(st != null) //Check in the db that the stadium still exists
                st.addTeam(teamToReOpen);
        }

        for(Role playerRole : teamToReOpen.getTeamPlayers()){
            if(playerRole != null &&
                    playerRole.getSystemUser() != null &&
                    playerRole.getSystemUser().getRole(RoleTypes.PLAYER) instanceof Player) { //Check in the db that the player still exists

                Player p = (Player)playerRole;
                p.addTeam(teamToReOpen);
            }
        }

        for(Role coachRole : teamToReOpen.getTeamCoaches()){
            if(coachRole != null &&
                    coachRole.getSystemUser() != null &&
                    coachRole.getSystemUser().getRole(RoleTypes.COACH) instanceof Coach) { //Check in the db that the coach still exists

                Coach c = (Coach) coachRole;
                c.addTeamToCoach(teamToReOpen);
            }
        }

        for(Role tmRole : teamToReOpen.getTeamManagers()){
            if(tmRole != null &&
                    tmRole.getSystemUser() != null &&
                    tmRole.getSystemUser().getRole(RoleTypes.TEAM_MANAGER) instanceof TeamManager){//Check in the db that the tm still exists

                TeamManager tm = (TeamManager)tmRole;
                tm.addTeam(teamToReOpen);
            }
            else {
                if(tmRole == null || tmRole.getSystemUser() == null)
                     UIController.printMessage("Could not restore permissions of lost team manager");
                else
                    UIController.printMessage("Could not restore permissions of the team manager: "+
                            tmRole.getSystemUser().getName());
            }
        }

        teamToReOpen.setStatus(TeamStatus.OPEN);
        return true;
    }
}
