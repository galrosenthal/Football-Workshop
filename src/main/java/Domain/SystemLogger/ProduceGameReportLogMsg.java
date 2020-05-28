package Domain.SystemLogger;

public class ProduceGameReportLogMsg extends LoggerInfoMessage {
    public ProduceGameReportLogMsg(String usernamePerformed, String gameTitle){
        super(usernamePerformed, "Produce Game Report",
                "Game report produced for game \"" + gameTitle + "\"");
    }
}
