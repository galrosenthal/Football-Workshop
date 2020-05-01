package Domain.Users;

public class Referee extends Role {

    private String training;

    public Referee(SystemUser systemUser, String training) {
        super(RoleTypes.REFEREE, systemUser);
        this.training = training;
    }

    public String getTraining() {
        return training;
    }

    /**
     * Checks if the referee has future games to judge.
     * @return - true if the referee is connected to future games.
     */
    public boolean hasFutureGames() {
        //TODO:Checks if the referee has future games to judge.
        return true;
    }
}
