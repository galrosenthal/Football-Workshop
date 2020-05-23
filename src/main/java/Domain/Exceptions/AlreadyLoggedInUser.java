package Domain.Exceptions;

public class AlreadyLoggedInUser extends Exception{

    public AlreadyLoggedInUser(String message) {
        super(message);
    }
}
