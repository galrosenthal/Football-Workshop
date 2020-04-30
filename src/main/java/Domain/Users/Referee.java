package Domain.Users;

public class Referee extends Role {

    private String training;

    public Referee(SystemUser systemUser, String training) {
        super(RoleTypes.REFEREE, systemUser);
        this.training = training;
    }
}
