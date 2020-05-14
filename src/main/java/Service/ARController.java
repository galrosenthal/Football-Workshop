package Service;

import Domain.EntityManager;
import Domain.Exceptions.RoleExistsAlreadyException;
import Domain.Exceptions.TeamAlreadyExistsException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Game.League;
import Domain.Game.Season;
import Domain.Game.Team;
import Domain.Users.AssociationRepresentative;
import Domain.Users.Referee;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import com.vaadin.flow.component.Component;

import java.util.ArrayList;
import java.util.List;

import static Service.UIController.*;

/**
 * Association Representative services controller
 */
public class ARController {

    /**
     * Controls the flow of Creating a new League.
     *
     * @param systemUser - SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return - boolean - True if a new league was created successfully, else false
     */
    public static boolean addLeague(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);

        String leagueName = UIController.receiveString("Enter new league name:");

        //delegate the operation responsibility to AssociationRepresentative
        try {
            ARRole.addLeague(leagueName);
        } catch (Exception e) {
            UIController.showNotification(e.getMessage());
            return false;
        }
        UIController.showNotification("The league was created successfully");
        return true;
    }

    /**
     * Controls the flow of Adding a new Season to a chosen league.
     *
     * @param systemUser - SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return - True if a new season was created and added to a league successfully, else false
     */
    public static boolean addSeasonToLeague(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }

        League chosenLeague = null;
        try {
            chosenLeague = getLeagueByChoice();
        } catch (Exception e) {
            UIController.showNotification(e.getMessage() + "\nPlease add a league before adding a season");
            return false;
        }

        String seasonYears = "";
        boolean seasonExists = true;

        while (seasonExists) {
            seasonYears = UIController.receiveString("Enter season years (yyyy/yy) ex. 2020/21:");

            if (!Season.isGoodYearsFormat(seasonYears)) {
                do {
                    seasonYears = UIController.receiveString("wrong years format, please enter the season years (yyyy/yy) ex. 2020/21:");
                } while (!Season.isGoodYearsFormat(seasonYears));
            }

            if (!chosenLeague.doesSeasonExists(seasonYears)) {
                seasonExists = false;
            } else {
                UIController.showNotification("This season already exists in the chosen league. Please enter different years");
            }
        }

        chosenLeague.addSeason(seasonYears);
        UIController.showNotification("The season was created successfully");
        return true;
//        }
//        UIController.printMessage("The season creation failed");
//        return false;
    }

    private static League getLeagueByChoice() throws Exception {
        List<League> leagues = EntityManager.getInstance().getLeagues();
        if (leagues == null || leagues.isEmpty()) {
            throw new Exception("There are no leagues");
        }
        List<String> leaguesList = new ArrayList<>();
        for (int i = 0; i < leagues.size(); i++) {
            leaguesList.add(leagues.get(i).getName());
        }
        int Index;
        do {
            Index = UIController.receiveInt("Choose a League Number",leaguesList);
        } while (!(Index >= 0 && Index < leagues.size()));

        return leagues.get(Index);
    }

    /**
     * Receives the user's selection of a season from a given league.
     * @param league - League - a league with the desired season
     * @return - Season - the selected season
     * @throws Exception - throws if there are no seasons in the league
     */
    private static Season getSeasonByChoice(League league) throws Exception {
        List<Season> seasons = league.getSeasons();
        if (seasons == null || seasons.isEmpty()) {
            throw new Exception("There are no seasons in the league");
        }
        List<String> seasonsList = new ArrayList<>();
        for (int i = 0; i < seasons.size(); i++) {
            seasonsList.add(seasons.get(i).getYears());
        }
        int Index;
        do {
            Index = UIController.receiveInt("Choose a Season Number",seasonsList);
        } while (!(Index >= 0 && Index < seasons.size()));

        return seasons.get(Index);
    }

    /**
     * Controls the flow of Adding a new Referee role to an existing user.
     *
     * @param systemUser - SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return - boolean - True if a new referee was added successfully, else false
     */
    public static boolean addReferee(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);

        //receive the future referee username from ar
        String newRefereeUsername = getUsernameFromUser("referee");
        SystemUser refereeUser = null;
        do {
            refereeUser = EntityManager.getInstance().getUser(newRefereeUsername);
            if (refereeUser == null) {
                UIController.showNotification("Could not find a user by the given username\nPlease try again");
                newRefereeUsername = getUsernameFromUser("referee");
            }
        } while (refereeUser == null);
        //A user has been identified.

        String training = UIController.receiveString("Enter the new referee's training:");

        boolean succeeded = false;
        try {
            succeeded = ARRole.addReferee(refereeUser, training);
        } catch (RoleExistsAlreadyException e) {
            UIController.showNotification("The user chosen is already a referee");
            return false;
        }

        if (succeeded) {
            //TODO: Send notification to newRefereeUser
            UIController.showNotification("The referee has been added successfully");
        }
        return true;
    }

    /**
     * Controls the flow of Adding a new Referee role to an existing user.
     *
     * @param systemUser - SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return - boolean - True if a new referee was added successfully, else false
     */
    public static boolean removeReferee(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);

        SystemUser chosenUser = null;
        try {
            chosenUser = getRefereeByChoice();
        } catch (Exception e) {
            UIController.showNotification(e.getMessage());
            return false;
        }

        if(ARRole.removeReferee(chosenUser)) {
            UIController.showNotification("The referee has been removed successfully");
            return true;
        }
        return false;
    }

    /**
     * receives a system user selection from the user.
     * @return - SystemUser - a referee chosen by the user
     * @throws Exception - "There are no referees"
     */
    private static SystemUser getRefereeByChoice() throws Exception {
        List<SystemUser> referees = EntityManager.getInstance().getReferees();
        if (referees.isEmpty()) {
            throw new Exception("There are no referees");
        }

        List<String> refereesList = new ArrayList<>();
        for (int i = 0; i < referees.size(); i++) {
            refereesList.add(referees.get(i).getName());
        }
        int index;
        do {
            index = UIController.receiveInt("Choose a referee number from the list:",refereesList);
        } while (!(index >= 0 && index < referees.size()));

        return referees.get(index);
    }

    /**
     * Controls the flow of assigning a referee to an existing season.
     *
     * @param systemUser - SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return - boolean - True if a referee was assigned successfully, else false
     */
    public static boolean assignReferee(SystemUser systemUser) {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);
        //League selection
        League chosenLeague = null;
        try {
            chosenLeague = getLeagueByChoice();
        } catch (Exception e) {
            UIController.showNotification(e.getMessage() + "\nPlease add a league before assigning a referee");
            return false;
        }

        Season chosenSeason = null;
        try {
            chosenSeason = getSeasonByChoice(chosenLeague);
        } catch (Exception e) {
            UIController.showNotification(e.getMessage() + "\nPlease add a season before assigning a referee");
            return false;
        }

        SystemUser chosenRefereeUser = null;
        try {
            chosenRefereeUser = getRefereeByChoice();
        } catch (Exception e) {
            UIController.showNotification(e.getMessage());
            return false;
        }
        Referee refereeRole = (Referee)chosenRefereeUser.getRole(RoleTypes.REFEREE);


        try {
            ARRole.assignRefereeToSeason(chosenSeason,refereeRole);
        } catch (Exception e) {
            UIController.showNotification(e.getMessage());
            return false;
        }

        UIController.showNotification("The referee has been assigned to the season successfully");
        return true;
    }


    /**
     * Controls the flow of Creating a new team.
     * @param systemUser - SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return - boolean - True if a new team was created successfully, else false
     * @throws TeamAlreadyExistsException
     * @throws UserNotFoundException
     */
    public static boolean registerNewTeam(SystemUser systemUser) throws TeamAlreadyExistsException,UserNotFoundException  {
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);

        String teamName = UIController.receiveString("Enter the new team's name:");

        boolean teamExists = EntityManager.getInstance().doesTeamExists(teamName);
        if (teamExists) {
            throw new TeamAlreadyExistsException("The team \"" + teamName + "\" already exists in the system");
        }

        String newTeamOwnerUsername = UIController.getUsernameFromUser("Team Owner");
        SystemUser newTeamOwnerUser = EntityManager.getInstance().getUser(newTeamOwnerUsername);
        if (newTeamOwnerUser == null) {
            throw new UserNotFoundException("Could not find a user by the given username");
        }

        //delegate the operation responsibility to AssociationRepresentative
        boolean succeeded = ARRole.addTeam(teamName, newTeamOwnerUser);
        if (succeeded) {
            UIController.showNotification("The team " + teamName + " has been created successfully");
        }
        return succeeded;
    }

    /**
     * Controls the flow of adding teams to season.
     * @param systemUser SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return boolean - True if new teams were assigned successfully to the chosen league's latest season, else false
     */
    public static boolean addTeamsToSeason(SystemUser systemUser){
        return addRemoveTeamsToSeason(systemUser, "add");
    }

    /**
     * Controls the flow of removing teams from season.
     * @param systemUser SystemUser - the user who initiated the procedure, needs to be an association representative
     * @return boolean - True if teams were removed successfully from the chosen league's latest season, else false
     */
    public static boolean removeTeamsFromSeason(SystemUser systemUser){
        return addRemoveTeamsToSeason(systemUser, "remove");
    }


    private static boolean addRemoveTeamsToSeason(SystemUser systemUser, String action){
        if (!systemUser.isType(RoleTypes.ASSOCIATION_REPRESENTATIVE)) {
            return false;
        }
        AssociationRepresentative ARRole = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);

        League chosenLeague = null;
        try {
            chosenLeague = getLeagueThatHasntStartedByChoice();
        } catch (Exception e) {
            UIController.printMessage(e.getMessage());
            return false;
        }

        Season currLeagueSeason = chosenLeague.getLatestSeason();
        List<Team> chosenTeams = null;
        try {
            if(action.equals("add"))
                chosenTeams = getTeamsBySeasonByChoice(currLeagueSeason, "not in season");
            else // "remove"
                chosenTeams = getTeamsBySeasonByChoice(currLeagueSeason, "in season");
        } catch (Exception e) {
            UIController.printMessage(e.getMessage());
            return false;
        }

        //delegate the operation responsibility to AssociationRepresentative
        boolean succeeded;
        if(action.equals("add"))
             succeeded = ARRole.assignTeamsToSeason(chosenTeams, currLeagueSeason);
        else  // "remove"
            succeeded = ARRole.removeTeamsFromSeason(chosenTeams, currLeagueSeason);
        if (succeeded) {
            if(action.equals("add"))
                UIController.printMessage("The teams have been successfully assigned to the league's latest season");
            else  // "remove"
                UIController.printMessage("The teams have been successfully removed from the league's latest season");
        }
        return succeeded;
    }

    private static List<Team> getTeamsBySeasonByChoice(Season season, String action) throws Exception {
        List<Team> teams = EntityManager.getInstance().getTeams();
        if (teams == null || teams.isEmpty()) {
            throw new Exception("There are no teams");
        }
        List<Team> teamsInSeason = season.getTeams();
        List<Team> teamChoices = new ArrayList<>();
        for(Team teamInSys : teams){
            if(action.equals("not in season")) {
                if (!teamsInSeason.contains(teamInSys))
                    teamChoices.add(teamInSys);
            }
            else { //"in season"
                if (teamsInSeason.contains(teamInSys))
                    teamChoices.add(teamInSys);
            }
        }
        if(teamChoices.isEmpty()){
            if(action.equals("not in season"))
                 throw new Exception("There are no teams that do not belong already to the chosen league's latest season");
            else //"in season"
                throw new Exception("There are no teams that belong to the chosen league's latest season");
        }

        if(action.equals("not in season"))
        UIController.printMessage("Choose Team numbers from the list of " +
                "teams that do not belong to the chosen league's latest season." +
                "\nWhen you are done, type -1");
        else //"in season"
            UIController.printMessage("Choose Team numbers from the list of " +
                    "teams that belong to the chosen league's latest season." +
                    "\nWhen you are done, type -1");
        List<Team> chosenTeams = new ArrayList<>();
        for (int i = 0; i < teamChoices.size(); i++) {
            UIController.printMessage(i + ". " + teamChoices.get(i).getTeamName());
        }
        int index = UIController.receiveInt();
        while(index != -1){
            if(index >= 0 && index < teamChoices.size()){
                chosenTeams.add(teamChoices.get(index));
            }
            index = UIController.receiveInt();
        }
        return chosenTeams;
    }

    private static League getLeagueThatHasntStartedByChoice() throws Exception {
        List<League> leagues = EntityManager.getInstance().getLeagues();
        if (leagues == null || leagues.isEmpty()) {
            throw new Exception("There are no leagues");
        }
        List<League> leaguesChoices = new ArrayList<>();
        for (int i = 0; i < leagues.size(); i++) {
            if (leagues.get(i).getLatestSeason() != null && !leagues.get(i).getLatestSeason().getIsUnderway())
                leaguesChoices.add(leagues.get(i));
        }
        if(leaguesChoices.isEmpty()){
            throw new Exception("There are no leagues that their latest season hasn't started");
        }

        UIController.printMessage("Choose a League Number from the list of " +
                "leagues that their latest season hasn't started");
        for (int i = 0; i < leaguesChoices.size(); i++) {
            UIController.printMessage(i + ". " + leaguesChoices.get(i).getName());
        }
        int Index;
        do {
            Index = UIController.receiveInt();
        } while (!(Index >= 0 && Index < leaguesChoices.size()));

        return leaguesChoices.get(Index);
    }
}
