package Users;

public class TeamManager extends Registered {
    public TeamManager(String username, String pass, String name) {
        super(RegisteredTypes.TEAM_MANAGER, username, pass, name);
    }
}
