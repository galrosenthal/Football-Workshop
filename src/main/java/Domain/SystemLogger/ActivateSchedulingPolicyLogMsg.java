package Domain.SystemLogger;

public class ActivateSchedulingPolicyLogMsg extends LoggerInfoMessage {
    public ActivateSchedulingPolicyLogMsg(String usernamePerformed, String season, String league, String numGamesCreated) {
        super(usernamePerformed, "Activate Scheduling Policy",
                "Scheduling policy activated on season \""+season+"\"" +
                " in league \""+ league+"\". "+numGamesCreated+" games were created");
    }
}
