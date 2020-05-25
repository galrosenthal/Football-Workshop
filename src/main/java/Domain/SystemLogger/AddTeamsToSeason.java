package Domain.SystemLogger;

public class AddTeamsToSeason extends LoggerMessage {
    public AddTeamsToSeason(String usernamePerformed, String amountAdded, String season, String league){
        super(usernamePerformed, "Add Teams to Season",
                amountAdded+" teams were assigned to season \""+season+"\"" +
                " in league \""+ league+"\"");
    }
}
