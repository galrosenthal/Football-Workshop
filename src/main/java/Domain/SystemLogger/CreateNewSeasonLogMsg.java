package Domain.SystemLogger;

public class CreateNewSeasonLogMsg  extends LoggerInfoMessage {
    public CreateNewSeasonLogMsg(String usernamePerformed, String seasonName, String leagueName){
        super(usernamePerformed, "Create Season","Created new season \""+seasonName+"\" for league \""+leagueName+"\"");
    }
}
