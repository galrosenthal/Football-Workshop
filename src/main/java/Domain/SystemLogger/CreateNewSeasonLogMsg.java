package Domain.SystemLogger;

public class CreateNewSeasonLogMsg  extends LoggerMessage {
    public CreateNewSeasonLogMsg(String usernamePerformed, String seasonName, String leagueName){
        super(usernamePerformed, "Create Season","Created new season \""+seasonName+"\" for league \""+leagueName+"\"");
    }
}
