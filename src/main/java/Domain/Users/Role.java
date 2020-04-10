package Domain.Users;

public abstract class Role {
    protected RoleTypes type;

    protected SystemUser systemUser;

    public Role(RoleTypes type, SystemUser systemUser) {
        this.type = type;
        this.systemUser = systemUser;
        systemUser.addNewRole(this);
    }

    public RoleTypes getType() {
        return type;
    }

    public void setType(RoleTypes type) {
        this.type = type;
    }


}
