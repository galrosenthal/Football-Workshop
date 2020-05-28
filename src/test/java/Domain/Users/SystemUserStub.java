package Domain.Users;

import Domain.EntityManager;

import java.util.List;

public class SystemUserStub extends SystemUser{

    private int selector;
    private Role role;

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
        super(username,name,password,"test@gmail.com", false, true);
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
        if(selector == 0 || selector == 4 || selector == 66141 || selector == 66161
                || selector == 9101 || selector == 91121 || selector == 91221)
        {
            return false;
        }
        else if(selector == 9321 && roleType.equals(RoleTypes.ASSOCIATION_REPRESENTATIVE)){
            return true;
        }
        else if(selector == 9321 && roleType.equals(RoleTypes.REFEREE)){
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
        else if( selector == 5 || selector == 9102 || selector == 9103 || selector == 9104 || selector == 91122
                || selector == 91222){
            return new AssociationRepresentativeStub(this);
        }else if(selector == 6111)
        {
            return getRoles().get(0);
        }else if(selector == 6112)
        {
            return new Player(this,null, true);
        }
        else if(selector == 6116)
        {
            PlayerStub p = new PlayerStub(this,null);
            p.setSelector(0);
            return p;
        }
        else if (selector == 661721 ||selector == 662721 || selector == 10312)
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
        }else if( selector == 9311){
            RefereeStub refereeStub =  new RefereeStub(this ,RefereeQualification.VAR_REFEREE);
            return refereeStub;
        }else if( selector == 9312){
            selector = 93121;
            return null;
        }else if( selector == 93121){
            RefereeStub refereeStub =  new RefereeStub(this ,RefereeQualification.VAR_REFEREE);
            return refereeStub;
        }else if( selector == 93131){
            selector = 93132;
            return null;
        }else if( selector == 93132){
            Referee referee =  new Referee(this ,RefereeQualification.VAR_REFEREE, true);
            return referee;
        }else if( selector == 9321){
            return new AssociationRepresentativeStub(this);
        }else if( selector == 1031){
            if(role == null){
                role =  new RefereeStub(this ,RefereeQualification.VAR_REFEREE);
            }
            return role;
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
