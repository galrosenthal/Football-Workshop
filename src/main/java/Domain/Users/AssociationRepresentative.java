package Domain.Users;

import Domain.EntityManager;
import Domain.Financials.AssociationFinancialRecordLog;
import Domain.Game.Team;

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

    /**
     * Creates a new team.
     * @param teamName - String - A unique team name
     * @param newTeamOwnerUser - SystemUser - The user who is chosen to be the team owner of the new team.
     * @return - boolean - True if a new team was created successfully, else false
     */
    public boolean addTeam(String teamName, SystemUser newTeamOwnerUser) {
        Role newTeamOwnerRole = newTeamOwnerUser.getRole(RoleTypes.TEAM_OWNER);
        TeamOwner teamOwner;
        if (newTeamOwnerRole == null) {
            teamOwner = new TeamOwner(newTeamOwnerUser);
        }
        else{
            teamOwner = (TeamOwner) newTeamOwnerRole;
        }
        EntityManager.getInstance().addTeam(teamName, teamOwner);
        teamOwner.setAppointedOwner(this.getSystemUser());
        Team newTeam = EntityManager.getInstance().getTeam(teamName);
        teamOwner.addTeamToOwn(newTeam);
        newTeam.addTeamOwner(teamOwner);
        return true;
    }
}
