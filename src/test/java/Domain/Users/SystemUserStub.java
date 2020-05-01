package Domain.Users;

import Domain.EntityManager;

import java.text.ParseException;
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

    public List<Role> getRoles() {
        return super.getRoles();
    }

    public boolean isType(RoleTypes roleType) {
        if(selector == 0 || selector == 4 || selector == 66141 || selector == 66161 || selector == 9101)
        {
            return false;
        }
        return true;
    }

    public Role getRole(RoleTypes roleType) {
        if(selector == 1 || selector == 66142 || selector == 66162)
        {
            return null;
        } else if( selector == 2 || selector == 66143 || selector == 66144 || selector == 66151 || selector == 66163
                || selector == 66251 || selector == 66171 || selector == 661724 || selector == 662724){
            return new TeamOwnerStub(this);
        } else if( selector == 3){
            return null;
        }
        else if( selector == 5 || selector == 9102 || selector == 9103 || selector == 9104){
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
        else if (selector == 661721 ||selector == 662721)
            return new PlayerStub(this);
        else if (selector == 661722 || selector == 662722)
            return new TeamManagerStub(this);
        else if (selector == 661723 || selector == 662723)
            return new CoachStub(this);

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

        else if( selector == 6131){

            TeamOwnerStub teamOwner =  new TeamOwnerStub(this);
            teamOwner.setSelector(6131);
            return teamOwner;
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
