package Domain.Exceptions;

public class GameNotFoundException extends Exception {
    public GameNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
