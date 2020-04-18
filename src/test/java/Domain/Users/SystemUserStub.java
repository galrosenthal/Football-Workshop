package Domain.Users;

import Domain.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class SystemUserStub extends SystemUser{

    private int selector;


    /**
     * Selector latest number: 9
     */
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

    public boolean addNewRole(Role role)
    {
        if(role != null)
        {
            getRoles().add(role);
            return true;
        }
        return false;
    }

    public List<Role> getRoles() {
        return super.getRoles();
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
        }else if(selector == 6111)
        {
            return getRoles().get(0);
        }else if(selector == 6112)
        {
            return new Player(this,null);
        }
        else if(selector == 6116)
        {
            PlayerStub p = new PlayerStub(this,null);
            p.setSelector(0);
            return p;
        }
        else if(selector == 6117)
        {
            CoachStub c = new CoachStub(this);
            c.setSelector(0);
            return c;
        }
        else if(selector == 6118)
        {
            TeamManagerStub tm = new TeamManagerStub(this);
            tm.setSelector(0);
            return tm;
        }
        else if(selector == 6119)
        {
            return null;
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if(selector == 0 || selector == 1)
        {
            return false;
        }
        return true;
    }

    public void setSelector(int i) {
        selector = i;
    }
}
