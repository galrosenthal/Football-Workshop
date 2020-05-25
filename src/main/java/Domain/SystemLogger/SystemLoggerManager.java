package Domain.SystemLogger;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import java.util.Collections;
import java.util.List;

public class SystemLoggerManager {
    static final Logger logger = Logger.getLogger(SystemLoggerManager.class);

    public static void logInfo(Class classCalling, LoggerMessage loggerMessage){
        MDC.put("username", loggerMessage.getUsernamePerformedAction());
        MDC.put("className", classCalling.getName());
        MDC.put("actionName", loggerMessage.getActionName());
        logger.info(loggerMessage.getMessage());
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
