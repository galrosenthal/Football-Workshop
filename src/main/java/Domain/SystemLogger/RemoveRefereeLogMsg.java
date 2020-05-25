package Domain.SystemLogger;

public class RemoveRefereeLogMsg extends LoggerMessage {
    public RemoveRefereeLogMsg(String usernamePerformed, String refUsername){
        super(usernamePerformed, "Remove Referee", "User: "+refUsername+" is no longer Referee");
    }
}
