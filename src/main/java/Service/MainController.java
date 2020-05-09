package Service;

import Domain.EntityManager;
import Domain.Exceptions.*;
import Domain.Users.*;
import GUI.FootballMain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainController {


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
            return false;
        }
    }


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

    public static boolean isUserExists(String username) {
        return EntityManager.getInstance().getUser(username) != null;
    }

    public static List<String> getAllUsersByUsername() {
        List<String> allRegisteredUsers = new ArrayList<>();
        for (SystemUser su :
                EntityManager.getInstance().getAllUsers()) {
            allRegisteredUsers.add(su.getUsername());
        }
        return allRegisteredUsers;
    }

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
            FootballMain.showNotification("Something Went wrong please try again");
        }
    }


}
