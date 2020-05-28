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

    private Map<String , List<VaadinSession>> systemUsers;



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
    public void login(String systemUser, VaadinSession sessionUI)
    {
        if(!systemUsers.containsKey(systemUser))
        {
            List<VaadinSession> newLoginUser = new ArrayList<>();
            systemUsers.put(systemUser, newLoginUser);
        }

        systemUsers.get(systemUser).add(sessionUI);
    }
    //remove user after logout
    public void logout(String systemUser, VaadinSession currUIloggedOut)
    {
        systemUsers.get(systemUser).remove(currUIloggedOut);
    }

    @Override
    public void update(List<SystemUser> systemUsers , String alert)
    {
        for (int i = 0; i < systemUsers.size(); i++) {
            //todo: alert!
            List<VaadinSession> allSessionsUI = this.systemUsers.get(systemUsers.get(i).getUsername());
            //session
            for (VaadinSession userSession :
                    allSessionsUI) {
//                userSession.getUIs();
                UIController.showAlert(userSession , alert);
            }
        }
    }

//    /**
//     * Checks whether or not the size of the logged in sessions
//     * of a specific username
//     * is 1
//     * @param username the specific user thats want to logout
//     * @return true if there is only 1 active session
//     */
//    public boolean isLastConnection(String username) {
//        return systemUsers.get(username).size() == 1;
//    }
}
