package Domain.SystemLogger;

public class SignUpLogMsg extends LoggerInfoMessage {
    public SignUpLogMsg(String usernamePerformed){
        super(usernamePerformed, "Sign Up", "User signed up");
    }
}
