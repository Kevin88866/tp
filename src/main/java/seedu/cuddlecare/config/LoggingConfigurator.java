package seedu.cuddlecare.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Configures the logging system for the CuddleCare application.
 * <p>
 * This class sets up a file-based logger that writes log messages to
 * {@code logs/cuddlecare_app.log}. Existing handlers on the root logger
 * are removed, and the log level is set to {@link Level#INFO}.
 * <p>
 * If the "logs" directory does not exist, it will be created.
 */
public class LoggingConfigurator {

    /** Path to the directory where log files will be stored. */
    private static final Path LOG_DIR_PATH = Paths.get("logs");

    /** Path to the main log file for the application. */
    private static final String LOG_FILE_PATH = "logs/cuddlecare_app.log";

    /**
     * Sets up logging for the application.
     * <p>
     * Steps performed by this method:
     * <ol>
     *     <li>Removes all existing handlers from the root logger.</li>
     *     <li>Sets the root logger level to {@link Level#INFO}.</li>
     *     <li>Creates the "logs" directory if it does not exist.</li>
     *     <li>Initializes a {@link FileHandler} with a {@link SimpleFormatter}
     *     that writes log messages to {@link #LOG_FILE_PATH}.</li>
     * </ol>
     * <p>
     * If any {@link IOException} occurs during setup, an error message is printed
     * to {@link System#err} and the stack trace is printed.
     */
    public static void setup() {
        Logger rootLogger = Logger.getLogger("");

        for (Handler handler: rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        rootLogger.setLevel(Level.INFO);

        try {

            if (Files.notExists(LOG_DIR_PATH)) {
                Files.createDirectories(LOG_DIR_PATH);
            }

            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, 0, 1, true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);

        } catch (IOException e) {
            System.err.println("FATAL: Could not set up logging");
            e.printStackTrace();
        }
    }
}
