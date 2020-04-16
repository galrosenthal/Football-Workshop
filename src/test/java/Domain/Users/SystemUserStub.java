package Domain.Users;

import Domain.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class SystemUserStub extends SystemUser{

    private int selector;


    public SystemUserStub(String username,String name,int selector)
    {
        super(username,name);
        this.selector = selector;
        EntityManager.getInstance().addUser(this);
    }

    public SystemUserStub(String username,String name,String password, int selector)
    {
        super(username,name,password);
        this.selector = selector;
        EntityManager.getInstance().addUser(this);
    }

//

    public String getName() {
        return "Stub";
    }

    public String getUsername() {
        return username;
    }

    public void addNewRole(Role role)
    {
        getRoles().add(role);
    }

    public List<Role> getRoles() {
        return getRoles();
    }

    public boolean isType(RoleTypes roleType) {
        if(selector == 0 || selector == 4)
        {
            return false;
        }
        return true;
    }

    public Role getRole(RoleTypes roleType)
    {
        if(selector == 1)
        {
            return null;
        } else if( selector == 2){
            return new TeamOwnerStub(this);
        } else if( selector == 3){
            return null;
        }
        else if( selector == 5){
            return new AssociationRepresentativeStub(this);
        }
        return null;
    }
}
