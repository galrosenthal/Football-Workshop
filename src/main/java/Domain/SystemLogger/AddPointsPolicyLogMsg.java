package Domain.SystemLogger;

public class AddPointsPolicyLogMsg extends LoggerInfoMessage {
    public AddPointsPolicyLogMsg(String usernamePerformed){
        super(usernamePerformed, "Add Points Policy","New points policy added to the system");
    }
}
