package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A command that filters treatments by date range across all pets.
 * <p>
 * When executed, this command searches through all treatments of all pets
 * and returns those that fall within the specified date range (inclusive).
 */
public class FilterTreatmentByDateCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(FilterTreatmentByDateCommand.class.getName());

    /**
     * A list of all pets.
     */
    protected final PetList pets;

    /**
     * Initializes the FilterTreatmentByDateCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public FilterTreatmentByDateCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the filter treatment by date command.
     * <p>
     * Filters treatments across all pets and displays those that fall
     * within the specified date range (inclusive of start and end dates).
     *
     * @param args the date range in format: from/DATE to/DATE
     */
    @Override
    public void exec(String args) {
        assert args != null : "Command arguments cannot be null";
        LOGGER.log(Level.INFO, "Starting filter treatment by date command execution");

        try {
            Map<String, LocalDate> dateRange = parseDateRange(args);
            if (dateRange == null) {
                return;
            }

            LocalDate fromDate = dateRange.get("from");
            LocalDate toDate = dateRange.get("to");

            if (!isDateValid(fromDate, toDate)) {
                return;
            }

            ArrayList<String> filteredTreatments = filterTreatments(fromDate, toDate);

            if (filteredTreatments.isEmpty()) {
                LOGGER.log(Level.INFO, "No treatments found in date range {0} to {1}",
                        new Object[]{fromDate, toDate});
                System.out.println("No treatments found from " + fromDate + " to " + toDate + ".");
                return;
            }

            printFilteredList(filteredTreatments, fromDate, toDate);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unexpected error during filter treatment by date execution", e);
            System.out.println("Unable to filter treatments by date. Please try again.");
        }
    }

    protected void printFilteredList(ArrayList<String> filteredTreatments, LocalDate fromDate, LocalDate toDate) {
        System.out.println("Found " + filteredTreatments.size() +
                " treatment(s) from " + fromDate + " to " + toDate + ":");
        filteredTreatments.forEach(System.out::println);
    }

    /**
     * Filters treatments across all pets within the specified date range.
     * <p>
     * This method iterates through all pets in the pet list and collects
     * treatments whose dates fall within the given start and end dates.
     *
     * @param fromDate the start date of the filter range
     * @param toDate   the end date of the filter range
     * @return an ArrayList of formatted treatment strings within the date range
     */
    protected ArrayList<String> filterTreatments(LocalDate fromDate, LocalDate toDate) {
        ArrayList<String> filteredList = new ArrayList<>();

        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            for (Treatment treatment : pet.getTreatments()) {
                LocalDate treatmentDate = treatment.getDate();
                if (!treatmentDate.isBefore(fromDate) && !treatmentDate.isAfter(toDate)) {
                    filteredList.add(pet.getName() + ": " + treatment);
                }
            }
        }
        return filteredList;
    }

    /**
     * Parses the user input string to extract from and to date parameters.
     * <p>
     * The expected input format is from/YYYY-MM-DD to/YYYY-MM-DD.
     * If either date is missing or invalid, an error message will
     * be printed and the method returns null.
     *
     * @param args the date range in format: from/DATE to/DATE
     * @return a {@link Map} contianing two entries: {@code "from"} and {@code "to"}
     * mapped to their respective {@link LocalDate} values, or {@code null} if parsing fails
     */
    protected Map<String, LocalDate> parseDateRange(String args) {
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
    protected boolean isDateValid(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            LOGGER.log(Level.INFO, "Missing date range parameters - from: {0}, to: {1}",
                    new Object[]{fromDate, toDate});
            System.out.println("Invalid input. Usage: " + getSyntax());
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

    protected String getSyntax() {
        return "treatment-date from/DATE to/DATE";
    }
}
