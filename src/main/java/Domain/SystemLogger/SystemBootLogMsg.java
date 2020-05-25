package Domain.SystemLogger;

public class SystemBootLogMsg extends LoggerInfoMessage {
    public SystemBootLogMsg(String usernamePerformed){
        super(usernamePerformed, "System Boot",
                "System booted");
    }
}
