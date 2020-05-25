package Domain.SystemLogger;

public class RegisterNewTeamLogMsg extends LoggerMessage {

    public RegisterNewTeamLogMsg(String usernamePerformed, String teamName){
        super(usernamePerformed, "Register New Team","Registered new team: \""+teamName+"\"");
    }
}
