package Domain.SystemLogger;

public class AssignRefereeLogMsg extends LoggerMessage {
    public AssignRefereeLogMsg(String usernamePerformed, String refUsername, String season, String league){
        super(usernamePerformed, "Assign Referee",
                "Referee: "+refUsername+" is assigned to season \""+season+"\"" +
                " in league \""+ league+"\"");
    }
}
