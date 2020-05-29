package Domain.SystemLogger;

public class RegisterNewTeamLogMsg extends LoggerInfoMessage {

    public RegisterNewTeamLogMsg(String usernamePerformed, String teamName){
        super(usernamePerformed, "Register New Team","Registered new team: \""+teamName+"\"");
    }
}
