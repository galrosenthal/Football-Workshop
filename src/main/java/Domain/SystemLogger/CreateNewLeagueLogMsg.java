package Domain.SystemLogger;

public class CreateNewLeagueLogMsg extends LoggerInfoMessage {
    public CreateNewLeagueLogMsg(String usernamePerformed, String leagueName){
        super(usernamePerformed, "Create League", "Created new league: \""+leagueName+"\"");
    }

}
