package Domain.SystemLogger;

public abstract class LoggerInfoMessage {
    /**
     * Represents an Info message in the logger.
     */
    private String usernamePerformedAction;
    private String actionName;
    private String message;

    /**
     * Constructor.
     * @param user username of the user who performed the action. Can also be "guest".
     * @param actionName The name of the action performed
     * @param message Details about the action performed
     */
    public LoggerInfoMessage(String user, String actionName, String message){
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
