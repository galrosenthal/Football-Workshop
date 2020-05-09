package Service;

import Domain.Users.SystemUser;

import java.util.List;

public interface Observer {

     void update(List<SystemUser> systemUsers);
}
