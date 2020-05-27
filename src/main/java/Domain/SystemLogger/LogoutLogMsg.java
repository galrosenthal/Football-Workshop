package Domain.SystemLogger;

public class LogoutLogMsg extends LoggerInfoMessage {
    public LogoutLogMsg(String usernamePerformed){
        super(usernamePerformed, "Logout", "User logged out");
    }
}
