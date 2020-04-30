package Domain.Users;

import DB.DBManager;
import Domain.EntityManager;
import Service.UIController;

import java.util.ArrayList;
import java.util.List;

public class Unregistered extends User{
    private SystemUser systemUser = null;



    /**
     * Returns the System User that the unregistered user has become to - by signing up or logging in.
     * @return the SystemUser that the unregistered user has become to.
     */
    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}
