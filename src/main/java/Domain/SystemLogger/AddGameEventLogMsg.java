package Domain.SystemLogger;

public class AddGameEventLogMsg extends LoggerInfoMessage {
    public AddGameEventLogMsg(String usernamePerformed, String eventName, String gameTitle){
        super(usernamePerformed, "Add Game Event",
                eventName+ " event added to game \"" + gameTitle + "\"");
    }
}
