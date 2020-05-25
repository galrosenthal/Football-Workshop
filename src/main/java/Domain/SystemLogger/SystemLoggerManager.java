package Domain.SystemLogger;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import java.util.Collections;
import java.util.List;

public class SystemLoggerManager {
    /**
     * Responsible for logging system actions.
     */
    private static final Logger logger = Logger.getLogger(SystemLoggerManager.class);

    /**
     * Logs new Info record to the logger.
     * @param classCalling The class who asked to log the message.
     * @param loggerMessage
     */
    public static void logInfo(Class classCalling, LoggerInfoMessage loggerMessage){
        MDC.put("username", loggerMessage.getUsernamePerformedAction());
        MDC.put("className", classCalling.getName());
        MDC.put("actionName", loggerMessage.getActionName());
        logger.info(loggerMessage.getMessage());
    }

    /**
     * Logs new Error record to the logger.
     * Errors are events that might still allow the application to continue running.
     * @param classCalling The class who asked to log the message.
     * @param message
     */
    public static void logError(Class classCalling, String message){
        MDC.put("className", classCalling.getName());
        logger.error(message);
    }

    /**
     * Logs new Fatal record to the logger.
     * Fatals are very severe error events that will presumably lead the application to abort.
     * @param classCalling
     * @param message
     */
    public static void logFatal(Class classCalling, String message){
        MDC.put("className", classCalling.getName());
        logger.fatal(message);
    }

    /**
     * Disable logging system events and errors to logger file.
     * Usually used in tests.
     */
    public static void disableLoggers(){
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for ( Logger logger : loggers ) {
            logger.setLevel(Level.OFF); //Disable logging
        }
    }

    /**
     * Enable logging system events and errors to logger file.
     * Usually used when finished tests.
     */
    public static void enableLoggers(){
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for ( Logger logger : loggers ) {
            logger.setLevel(Level.ALL);//Enable logging
        }
    }
}
