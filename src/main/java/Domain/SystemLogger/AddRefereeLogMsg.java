package Domain.SystemLogger;

public class AddRefereeLogMsg extends LoggerMessage {
    public AddRefereeLogMsg(String usernamePerformed, String refUsername){
        super(usernamePerformed, "Add Referee", "User: "+refUsername+" is now Referee");
    }
}
