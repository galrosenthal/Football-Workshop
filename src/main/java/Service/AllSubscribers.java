package Service;


import Domain.Users.SystemUser;

import java.util.ArrayList;
import java.util.List;

public class AllSubscribers implements Observer {



    private static AllSubscribers allSubscribersInstance = null;

    //all online users! todo: get all users!!
    private List<SystemUser> systemUsers;


    /**
     * Private constructor. part of the Singleton design
     */
    private AllSubscribers() {
        this.systemUsers = new ArrayList<>();
    }

    public List<SystemUser> getSystemUsers() {
        return systemUsers;
    }

    /**
     * Returns an instance of AllSubscribers. part of the Singleton design
     *
     * @return - AllSubscribers - an instance of AllSubscribers
     */
    public static AllSubscribers getInstance() {
        if (allSubscribersInstance == null) {
            allSubscribersInstance = new AllSubscribers();
        }
        return allSubscribersInstance;
    }


    //add user after login
    //todo: check if after signUp - user is login????
    public void login(SystemUser systemUser)
    {
        systemUsers.add(systemUser);
    }
    //remove user after logout
    //fixme! need to add logout function - UC
    public void logout(SystemUser systemUser)
    {
        systemUsers.remove(systemUser);
    }

    @Override
    public void update(List<SystemUser> systemUsers)
    {
        for (int i = 0; i < systemUsers.size(); i++) {
            //todo: alert!
        }
    }

}
