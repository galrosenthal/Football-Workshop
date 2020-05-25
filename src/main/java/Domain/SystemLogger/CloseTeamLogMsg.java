package Domain.SystemLogger;

public class CloseTeamLogMsg extends LoggerMessage {

    public CloseTeamLogMsg(String usernamePerformed, String teamName){
        super(usernamePerformed, "Close Team","Team: \""+teamName+"\" is now closed");
    }
}
