package Domain.SystemLogger;

public class RemoveTeamsFromSeason extends LoggerInfoMessage {
    public RemoveTeamsFromSeason(String usernamePerformed, String amountRemoved, String season, String league){
        super(usernamePerformed, "Remove Teams from Season" ,
                amountRemoved+" teams were removed from season \""+season+"\"" +
                " in league \""+ league+"\"");
    }
}
