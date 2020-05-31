package Domain.Users;


public abstract class Role {
    protected RoleTypes type;

    protected SystemUser systemUser;

    /**
     * Constructor
     *
     * @param type       - RoleTypes - the role type
     * @param systemUser - SystemUser - The system user to add the new role to
     */
    public Role(RoleTypes type, SystemUser systemUser) {
        this.type = type;
        this.systemUser = systemUser;
        systemUser.addNewRole(this);
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


}
