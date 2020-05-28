package Domain;

import Domain.Users.SystemUser;

import java.util.List;

public interface Subject {
//    void register(Observer o);
//    void unregister(Observer o);
    void notifyObserver(List<SystemUser> systemUsers, String alert);
}
