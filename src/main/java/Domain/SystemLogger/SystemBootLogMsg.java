package Domain.SystemLogger;

public class SystemBootLogMsg extends LoggerMessage {
    public SystemBootLogMsg(String usernamePerformed){
        super(usernamePerformed, "System Boot",
                "System booted");
    }
}
