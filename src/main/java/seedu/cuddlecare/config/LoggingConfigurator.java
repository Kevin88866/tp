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

public class LoggingConfigurator {

    private static final Path LOG_DIR_PATH = Paths.get("logs");
    private static final String LOG_FILE_PATH = "logs/cuddlecare_app.log";

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
