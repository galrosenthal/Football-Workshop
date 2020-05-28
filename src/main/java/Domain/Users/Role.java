package Domain.Users;

import Domain.EntityManager;

public abstract class Role {
    protected RoleTypes type;

    protected SystemUser systemUser;

    /**
     * Constructor
     *
     * @param systemUser - SystemUser - The system user to add the new role to
     * @param addToDB    - boolean - Whether to add the new role to the database
     * @param type       - RoleTypes - the role type
     */
    public Role(RoleTypes type, SystemUser systemUser, boolean addToDB) {
        this.type = type;
        this.systemUser = systemUser;
        systemUser.addNewRole(this);
        if (addToDB) {
            EntityManager.getInstance().addRole(this);
        }
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
