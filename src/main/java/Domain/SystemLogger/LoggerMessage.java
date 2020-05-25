package Domain.SystemLogger;

public abstract class LoggerMessage {
    private String usernamePerformedAction;
    private String actionName;
    private String message;

    public LoggerMessage(String user, String actionName, String message){
        this.usernamePerformedAction = user;
        this.actionName = actionName;
        this.message = message;
    }

    public String getUsernamePerformedAction() {
        return usernamePerformedAction;
    }

    public String getMessage() {
        return message;
    }

    public String getActionName() {
        return actionName;
    }
}
