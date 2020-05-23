package Service;


import Domain.Users.SystemUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllSubscribers implements Observer {



    private static AllSubscribers allSubscribersInstance = null;

    private Map<String , UI> systemUsers;



    /**
     * Private constructor. part of the Singleton design
     */
    private AllSubscribers() {
        this.systemUsers = new HashMap<>();
    }

    public List<String> getSystemUsers() {
        List<String> systemUsers = new ArrayList<>();
        for(String systemUserKey: this.systemUsers.keySet())
        {
            systemUsers.add(systemUserKey);
        }
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
    public void login(String systemUser, UI sessionUI)
    {
        systemUsers.put(systemUser, sessionUI);
    }
    //remove user after logout
    //fixme! need to add logout function - UC
    public void logout(String systemUser)
    {
        systemUsers.remove(systemUser);
    }

    @Override
    public void update(List<SystemUser> systemUsers , String alert)
    {
        for (int i = 0; i < systemUsers.size(); i++) {
            //todo: alert!
            UI sessionUI = this.systemUsers.get(systemUsers.get(i).getUsername());
            //session
            UIController.showAlert(sessionUI , alert);
        }
    }
}
