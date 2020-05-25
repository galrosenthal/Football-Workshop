package Domain.SystemLogger;

public class CreateNewLeagueLogMsg extends LoggerMessage {
    public CreateNewLeagueLogMsg(String usernamePerformed, String leagueName){
        super(usernamePerformed, "Create League", "Created new league: \""+leagueName+"\"");
    }

}
