package Domain.SystemLogger;

public class AddTeamOwnerLogMsg extends LoggerInfoMessage {
    public AddTeamOwnerLogMsg(String usernamePerformed, String teamOwnerUsername, String teamName){
        super(usernamePerformed, "Add Team Owner",
                "User: "+teamOwnerUsername+" is now Team Owner of team: \""+teamName+"\"");
    }
}
