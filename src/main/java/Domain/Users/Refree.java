package Domain.Users;

public class Refree extends Registered {
    public Refree(String username, String pass, String name) {
        super(RoleTypes.REFEREE, username, pass, name);
    }
}
