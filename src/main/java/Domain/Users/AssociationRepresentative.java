package Domain.Users;

import Domain.EntityManager;
import Domain.Exceptions.RoleExistsAlreadyException;
import Domain.Financials.AssociationFinancialRecordLog;
import Domain.Game.Team;
import Domain.Game.Season;

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
        teamOwner.setAppointedOwner(this.getSystemUser());
        Team newTeam = createNewTeam(teamName, teamOwner);
        teamOwner.addTeamToOwn(newTeam);
        newTeam.addTeamOwner(teamOwner);
        return true;
    }

    /**
     * Creates a new team. Responsible only for creating and adding a new team, doesn't do any farther checks.
     * @param teamName - String - the team's name.
     * @param to -TeamOwner - The team's owner.
     * @return   The new Team that was created.
     */
    private Team createNewTeam(String teamName, TeamOwner to) {
        Team team = new Team(teamName, to);
        EntityManager.getInstance().addTeam(team);
        return team;
    }

    /**
     * Removes the referee role from a given user.
     * @param chosenUser - SystemUser - a user with a Referee role to be removed.
     * @return - boolean - true if the Referee role was removed successfully, else false
     */
    public boolean removeReferee(SystemUser chosenUser) {
        Referee refereeRole = (Referee) chosenUser.getRole(RoleTypes.REFEREE);
        if (refereeRole != null) {
            if (!refereeRole.hasFutureGames()) {
                refereeRole.unAssignFromAllSeasons();
                chosenUser.removeRole(refereeRole);
                return true;
            }
        }
        return false;
    }

    /**
     * Assigns a given referee to a given season if the referee has not been previously assigned to the season.
     *
     * @param chosenSeason - Season - the season to assign the referee to
     * @param refereeRole  - Referee - the referee to be assigned
     * @throws Exception - throws if the referee has been previously assigned to the season.
     */
    public void assignRefereeToSeason(Season chosenSeason, Referee refereeRole) throws Exception {
        if (chosenSeason.doesContainsReferee(refereeRole)) {
            throw new Exception("This referee is already assigned to the chosen season");
        } else {
            chosenSeason.assignReferee(refereeRole);
            refereeRole.assignToSeason(chosenSeason);
        }
    }

    /**
     * Assign teams to season
     * @param chosenTeams The teams to assign.
     * @param season The season to assign the teams to.
     * @return false if no teams were assigned to the season, else true.
     */
    public boolean assignTeamsToSeason(List<Team> chosenTeams, Season season){
        if(chosenTeams == null || chosenTeams.isEmpty())
            return false;
        for(Team team : chosenTeams) {
            season.addTeam(team);
            team.addSeason(season);
        }
        return true;
    }

    /**
     * Remove teams from season
     * @param chosenTeams The teams to remove.
     * @param season The season to remove the teams from.
     * @return false if no teams were removed from the season, else true.
     */
    public boolean removeTeamsFromSeason(List<Team> chosenTeams, Season season){
        if(chosenTeams == null || chosenTeams.isEmpty())
            return false;
        for(Team team : chosenTeams) {
            season.removeTeam(team);
            team.removeSeason(season);
        }
        return true;
    }

}
