package Domain.SystemLogger;

public class CloseTeamLogMsg extends LoggerInfoMessage {

    public CloseTeamLogMsg(String usernamePerformed, String teamName){
        super(usernamePerformed, "Close Team","Team: \""+teamName+"\" is now closed");
    }
}
