package Domain.Users;


import java.util.ArrayList;
import java.util.List;

public class SystemUser {

    List<Roles> roles;

    public SystemUser() {
        this.roles = new ArrayList<>();
        roles.add(new Unregistered());
    }

    public boolean addNewRole(Roles role){
        return roles.add(role);
    }
}
