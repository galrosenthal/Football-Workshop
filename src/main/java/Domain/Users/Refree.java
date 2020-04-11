package Domain.Users;

public class Refree extends Role {
    public Refree(String username, SystemUser systemUser) {
        super(RoleTypes.REFEREE, systemUser);
    }
}
