package Domain.SystemLogger;

public class RemoveTeamOwnerLogMsg  extends LoggerMessage {
    public RemoveTeamOwnerLogMsg(String usernamePerformed, String teamOwnerUsername, String teamName){
        super(usernamePerformed, "Remove Team Owner", "User: "+teamOwnerUsername+" is no longer Team Owner of team: \""+teamName+"\"");
    }
}
