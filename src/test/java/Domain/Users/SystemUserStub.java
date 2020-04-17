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
        if(selector == 0 || selector == 4 || selector == 66141 || selector == 66161)
        {
            return false;
        }
        return true;
    }

    public Role getRole(RoleTypes roleType)
    {
        if(selector == 1 || selector == 66142 || selector == 66162)
        {
            return null;
        } else if( selector == 2 || selector == 66143 || selector == 66144 || selector == 66151 || selector == 66163
                || selector == 66251 || selector == 66171 || selector == 661724 || selector == 662724){
            return new TeamOwnerStub(this);
        } else if( selector == 3){
            return null;
        }
        else if( selector == 5){
            return new AssociationRepresentativeStub(this);
        }
        else if (selector == 661721 ||selector == 662721)
            return new PlayerStub(this);
        else if (selector == 661722 || selector == 662722)
            return new TeamManagerStub(this);
        else if (selector == 661723 || selector == 662723)
            return new CoachStub(this);

        return null;
    }
}
