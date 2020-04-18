package Domain.Exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String s) {
        super(s);
    }
}
