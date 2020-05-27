package Domain.SystemLogger;

public class AddSchedulingPolicyLogMsg extends LoggerInfoMessage {
    public AddSchedulingPolicyLogMsg(String usernamePerformed){
        super(usernamePerformed, "Add Scheduling Policy",
                "New scheduling policy added to the system");
    }
}
