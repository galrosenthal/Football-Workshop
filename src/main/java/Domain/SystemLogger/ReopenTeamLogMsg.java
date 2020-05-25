package Domain.SystemLogger;

public class ReopenTeamLogMsg extends LoggerInfoMessage {

    public ReopenTeamLogMsg(String usernamePerformed, String teamName){
        super(usernamePerformed, "Reopen Team","Team: \""+teamName+"\" is reopend");
    }
}
