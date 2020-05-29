package Domain.SystemLogger;

public class LoginLogMsg  extends LoggerInfoMessage {
    public LoginLogMsg(String usernamePerformed){
        super(usernamePerformed, "Login", "User logged in");
    }
}
