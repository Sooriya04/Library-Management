package library;

import java.util.logging.*;

public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());

    static {
        try {
            // Configure the logger
            LogManager.getLogManager().reset();
            logger.setLevel(Level.ALL);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            logger.addHandler(consoleHandler);
            
            // Create a FileHandler to log to a file
            FileHandler fileHandler = new FileHandler("library_management.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new CustomLogFormatter()); // Set custom formatter
            logger.addHandler(fileHandler);
            
            logger.setUseParentHandlers(false); // Disable the default console handler
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warning(message);
    }

    public static void logSevere(String message) {
        logger.severe(message);
    }
}