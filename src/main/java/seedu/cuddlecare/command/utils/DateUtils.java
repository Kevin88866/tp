package seedu.cuddlecare.command.utils;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtils {

    private static final Logger LOGGER = Logger.getLogger(DateUtils.class.getName());

    /**
     * Parses the user input string to extract from and to date parameters.
     * <p>
     * The expected input format is from/YYYY-MM-DD to/YYYY-MM-DD.
     * If either date is missing or invalid, an error message will
     * be printed and the method returns null.
     *
     * @param args the date range in format: from/DATE to/DATE
     * @return a {@link Map} contianing two entries: {@code "from"} and {@code "to"}
     *     mapped to their respective {@link LocalDate} values, or {@code null} if parsing fails
     */
    public static Map<String, LocalDate> parseDateRange(String args) {
        LocalDate fromDate = null;
        LocalDate toDate = null;
        Map<String, LocalDate> dateRange = new HashMap<>();

        String[] parts = args.split("(?=from/|to/)");
        for (String part : parts) {
            if (part.startsWith("from/")) {
                String dateString = part.substring(5).trim();
                if (dateString.isEmpty()) {
                    System.out.println("Error: Start date cannot be empty.");
                    return null;
                }
                try {
                    fromDate = LocalDate.parse(dateString);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid start date format. Please use yyyy-MM-dd format.");
                    return null;
                }
            } else if (part.startsWith("to/")) {
                String dateString = part.substring(3).trim();
                if (dateString.isEmpty()) {
                    System.out.println("Error: End date cannot be empty.");
                    return null;
                }
                try {
                    toDate = LocalDate.parse(dateString);
                } catch (DateTimeParseException e) {
                    LOGGER.log(Level.WARNING, "Invalid to date format: {0}", dateString);
                    System.out.println("Invalid end date format. Please use yyyy-MM-dd format.");
                    return null;
                }
            }
        }

        dateRange.put("from", fromDate);
        dateRange.put("to", toDate);

        return dateRange;
    }

    /**
     * Validates that both dates are present and that the start date is not after the end date.
     * <p>
     * If the date range is invalid, this method logs a message and prints error message to the console.
     *
     * @param fromDate the start date provided by the user
     * @param toDate   the end date provided by the user
     * @return {@code true} if both dates are valid, else {@code false}
     */
    public static boolean isDateValid(LocalDate fromDate, LocalDate toDate, String syntax) {
        if (fromDate == null || toDate == null) {
            LOGGER.log(Level.INFO, "Missing date range parameters - from: {0}, to: {1}",
                    new Object[]{fromDate, toDate});
            System.out.println("Invalid input. Usage: " + syntax);
            return false;
        }

        if (fromDate.isAfter(toDate)) {
            LOGGER.log(Level.INFO, "Invalid date range - from {0} is after to {1}",
                    new Object[]{fromDate, toDate});
            System.out.println("Error: Start date cannot be after end date.");
            return false;
        }

        return true;
    }
}
