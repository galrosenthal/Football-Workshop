package Domain.Users;

public abstract class Role {
    protected RoleTypes type;

    protected SystemUser systemUser;

    public Role(RoleTypes type, SystemUser systemUser) {
        this.type = type;
        this.systemUser = systemUser;
        //systemUser.addNewRole(this);
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public RoleTypes getType() {
        return type;
    }

    public void setType(RoleTypes type) {
        this.type = type;
    }


    public String[] getProperties() {
        return null;
    }

    public boolean changeProperty(String property)
    {
        return false;
    }


}
