package Domain.Users;

public class SystemAdmin extends Role {
    public SystemAdmin(SystemUser systemUser) {
        super(RoleTypes.SYSTEM_ADMIN,systemUser);
    }
}
