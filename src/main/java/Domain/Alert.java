package Domain;

import Domain.Users.AssociationRepresentative;
import Domain.Users.SystemAdmin;
import Domain.Users.SystemUser;
import Service.AllSubscribers;
import Service.Observer;

import java.util.ArrayList;
import java.util.List;

public class Alert implements Subject, Observer{
    private static Alert alertInstance = null;

    /**
     * Returns an instance of Alert. part of the Singleton design
     *
     * @return - Alert - an instance of Alert
     */
    public static Alert getInstance() {
        if (alertInstance == null) {
            alertInstance = new Alert();
            SystemUser a = new SystemUser("Administrator","Aa123456","admin");
            a.addNewRole(new SystemAdmin(a));
            a.addNewRole(new AssociationRepresentative(a));
        }

        return alertInstance;
    }

    @Override
    public void notifyObserver(List<SystemUser> systemUsers, String alert) {
        AllSubscribers allSubscribers = AllSubscribers.getInstance();
        List<String> onlineSystemUsers = allSubscribers.getSystemUsers();
        List<SystemUser> updateSystemUsers = new ArrayList<>();
        for (int i = 0; i < systemUsers.size() ; i++) {
            /*In case system user online*/
            if(onlineSystemUsers.contains(systemUsers.get(i).getUsername()))
            {
                updateSystemUsers.add(systemUsers.get(i));
            }
            //todo: save alert on db
        }
        allSubscribers.update(updateSystemUsers, alert);
    }

    @Override
    public void update(List<SystemUser> systemUsers, String alert) {
        notifyObserver(systemUsers,alert);
    }
}
