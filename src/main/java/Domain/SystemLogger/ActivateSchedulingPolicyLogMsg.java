package Domain.SystemLogger;

public class ActivateSchedulingPolicyLogMsg extends LoggerMessage {
    public ActivateSchedulingPolicyLogMsg(String usernamePerformed, String season, String league, String numGamesCreated) {
        super(usernamePerformed, "Activate Scheduling Policy",
                "Scheduling policy activated on season \""+season+"\"" +
                " in league \""+ league+"\". "+numGamesCreated+" games were created");
    }
}
