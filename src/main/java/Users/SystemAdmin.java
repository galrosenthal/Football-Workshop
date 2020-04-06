package Users;

public class SystemAdmin extends Registered {
    public SystemAdmin(String username, String pass, String name) {
        super(RegisteredTypes.SYSTEM_ADMIN, username, pass, name);
    }
}
