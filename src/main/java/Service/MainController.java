package Service;

import Domain.EntityManager;
import Domain.Exceptions.*;
import Domain.Game.League;
import Domain.Game.Season;
import Domain.Game.Team;
import Domain.Users.*;
import com.vaadin.flow.component.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MainController {


    /**
     * execute the login flow in the system
     * @param username
     * @param password
     * @return true if the user successfully loginned to the system
     */
    public static boolean login(String username, String password)
    {

        try {
            SystemUser currentUser = Controller.login(username,password);
            UIController.showNotification("Login Successfully");
            return true;
        }
        catch (UsernameOrPasswordIncorrectException e)
        {
            e.printStackTrace();
            UIController.showNotification(e.getMessage());
            return false;
        }
    }


    /**
     *
     * @param username the username to get all its roles
     * @return list of all the roles of the user by name
     */
    public static List<String> getUserRoles(String username)
    {
        SystemUser currentUser = EntityManager.getInstance().getUser(username);

        if(currentUser == null)
        {
            return null;
        }
        List<String> currentRoles = new ArrayList<>();
        for (Role r :
                currentUser.getRoles()) {
            currentRoles.add(r.getType().toString());
        }

        return currentRoles;

    }

    /**
     * Register the user with details:
     * @param userDetailsToRegister a list of details about the user as follows:
     *                              [0] = username
     *                              [1] = First name
     *                              [2] = Last name
     *                              [3] = Password
     * @return true if the user was signed up successfully
     */
    public static boolean signup(List<String> userDetailsToRegister)
    {
        if(userDetailsToRegister == null)
        {
            return false;
        }

        try {
            String name = userDetailsToRegister.get(1) + " " + userDetailsToRegister.get(2);
            return Controller.signUp(name, userDetailsToRegister.get(0), userDetailsToRegister.get(3)) != null;
        }
        catch (UsernameAlreadyExistsException | WeakPasswordException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * verifies if a user exists by its username
     * @param username the username to validate
     * @return true if the user exists in the system
     */
    public static boolean isUserExists(String username) {
        return EntityManager.getInstance().getUser(username) != null;
    }

    /**
     * @return list of all the users in the system by their name
     */
    public static List<String> getAllUsersByUsername() {
        List<String> allRegisteredUsers = new ArrayList<>();
        for (SystemUser su :
                EntityManager.getInstance().getAllUsers()) {
            allRegisteredUsers.add(su.getUsername());
        }
        return allRegisteredUsers;
    }

    /**
     * @return list of all the roletypes available in the system
     */
    public static List<String> getRoleTypes() {
        List<String> allRoles = new ArrayList<>();
        for (RoleTypes rt :
                RoleTypes.values()) {
            if(!rt.toString().equals("FAN"))
            {
                allRoles.add(rt.toString());
            }
        }

        return allRoles;
    }

    /**
     * This function gets a String of Role type and a String array of all the details relevant for this role.
     * The username should be the first value of the String array allRelevantDetails[0]
     * @param roleType the type of the new role
     * @param allRelevantDetails the Array of Strings of details relevant for the role
     * @return true if the role was successfuly added to the user
     */
    public static boolean addRoleToUser(String roleType, String... allRelevantDetails)
    {
        if(allRelevantDetails.length == 0)
        {
            return false;
        }
        SystemUser user = EntityManager.getInstance().getUser(allRelevantDetails[0]);
        if(user == null)
        {
            return false;
        }

        switch (roleType.toLowerCase())
        {
            case "player":
                if(allRelevantDetails.length < 2)
                {
                    return false;
                }
                try
                {
                    Date dateFromString = new SimpleDateFormat("dd-MM-yyyy").parse(allRelevantDetails[1]);
                    new Player(user,dateFromString);
                    break;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            case "coach":
                new Coach(user);
                break;
            case "team_manager":
                new TeamManager(user);
                break;
            case "team_owner":
                new TeamOwner(user);
                break;
            case "referee":
                if(allRelevantDetails.length < 2)
                {
                    return false;
                }
                new Referee(user,allRelevantDetails[1]);
                break;
            case "system_admin":
                new SystemAdmin(user);
                break;
            case "association_representative":
                new AssociationRepresentative(user);
                break;
        }


        return true;

    }


    /**
     * This function gets the username of the user asking to register a new Team
     * and starts the flow of registering a new User
     * @param username the username of the user who asked to register a new Team
     */
    public static void registerNewTeam(String username)
    {
        SystemUser associationUser = EntityManager.getInstance().getUser(username);
        if(associationUser == null)
        {
            return;
        }
        try
        {
            ARController.registerNewTeam(associationUser);
        }
        catch (TeamAlreadyExistsException | UserNotFoundException e )
        {
            e.printStackTrace();
            UIController.showNotification(e.getMessage());
        }
    }


    /**
     * Get a list of all Teams by thier name
     * @return List<String of all the teams names
     */
    public static List<String> getAllTeamsByName() {
        List<Team> allTeams = EntityManager.getInstance().getAllTeams();
        List<String> teamsByName = new ArrayList<>();
        for (Team t :
                allTeams) {
            teamsByName.add(t.getTeamName());
        }

        return teamsByName;
    }

    public static void addNewLeague(String username) {
        SystemUser associationUser = EntityManager.getInstance().getUser(username);
        if(associationUser == null)
        {
            return;
        }
        try
        {
            ARController.addLeague(associationUser);
        }
        catch (Exception e )
        {
            e.printStackTrace();
            UIController.showNotification(e.getMessage());
        }
    }

    public static void addSeasonToLeague(String username) {
        SystemUser associationUser = EntityManager.getInstance().getUser(username);
        if(associationUser == null)
        {
            return;
        }
        try
        {
            ARController.addSeasonToLeague(associationUser);
        }
        catch (Exception e )
        {
            e.printStackTrace();
            UIController.showNotification(e.getMessage());
        }
    }

    public static List<String> getAllLeaguesByName() {
        List<String> allLeagues = new ArrayList<>();
        for (League lg :
                EntityManager.getInstance().getLeagues()) {
            allLeagues.add(lg.getName());
        }
        return allLeagues;
    }

    public static List<String> getSeasonFromLeague(String leagueName)
    {
        League lg = EntityManager.getInstance().getLeagueByName(leagueName);
        List<String> leagueSeasons = new ArrayList<>();
        if(lg != null)
        {
            for (Season s :
                    lg.getSeasons()) {
                leagueSeasons.add(s.getYears());
            }
        }
        return leagueSeasons;
    }

    public static void addReferee(String username) {
        SystemUser associationUser = EntityManager.getInstance().getUser(username);
        if(associationUser == null)
        {
            return;
        }
        try
        {
            ARController.addReferee(associationUser);
        }
        catch (Exception e )
        {
            e.printStackTrace();
            UIController.showNotification(e.getMessage());
        }
    }

    public static void removeReferee(String username) {
        SystemUser associationUser = EntityManager.getInstance().getUser(username);
        if(associationUser == null)
        {
            return;
        }
        try
        {
            ARController.removeReferee(associationUser);
        }
        catch (Exception e )
        {
            e.printStackTrace();
            UIController.showNotification(e.getMessage());
        }
    }

    public static void assignReferee(String username) {
        SystemUser associationUser = EntityManager.getInstance().getUser(username);
        if(associationUser == null)
        {
            return;
        }
        try
        {
            ARController.assignReferee(associationUser);
        }
        catch (Exception e )
        {
            e.printStackTrace();
            UIController.showNotification(e.getMessage());
        }
    }
}
