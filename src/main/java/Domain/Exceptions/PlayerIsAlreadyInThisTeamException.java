package Domain.Exceptions;

public class PlayerIsAlreadyInThisTeamException extends Exception {
    public PlayerIsAlreadyInThisTeamException(String s) {
        super(s);
    }
}
