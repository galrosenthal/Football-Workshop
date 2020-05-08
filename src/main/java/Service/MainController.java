package Service;

import Domain.EntityManager;
import Domain.Exceptions.UsernameOrPasswordIncorrectException;
import Domain.Users.Role;
import Domain.Users.SystemUser;

import java.util.ArrayList;
import java.util.List;

public class MainController {


    public static boolean login(String username, String password)
    {

        try {
            SystemUser currentUser = Controller.login(username,password);
            return true;
        }
        catch (UsernameOrPasswordIncorrectException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean isLogedIn(SystemUser su)
    {
        if(su == null)
        {
            return false;
        }

        return false;
    }

    public static List<String> getRoles(String username)
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

}
