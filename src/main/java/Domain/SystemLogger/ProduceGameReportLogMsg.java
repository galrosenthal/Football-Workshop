package Domain.SystemLogger;

public class ProduceGameReportLogMsg extends LoggerMessage {
    public ProduceGameReportLogMsg(String usernamePerformed, String gameTitle){
        super(usernamePerformed, "Produce Game Report",
                "Game report produced for game \"" + gameTitle + "\"");
    }
}
