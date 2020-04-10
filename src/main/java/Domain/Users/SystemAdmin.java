package Domain.Users;

public class SystemAdmin extends Registered {
    public SystemAdmin(String username, String pass, String name) {
        super(RoleTypes.SYSTEM_ADMIN, username, pass, name);
    }
}
