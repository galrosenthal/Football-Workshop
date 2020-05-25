package Domain.SystemLogger;

public class SetPointsPolicyLogMsg extends LoggerInfoMessage {
    public SetPointsPolicyLogMsg(String usernamePerformed, String season, String league) {
        super(usernamePerformed, "Set Points Policy",
                "New points policy was set to season \""+season+"\"" +
                " in league \""+ league+"\"");
    }
}