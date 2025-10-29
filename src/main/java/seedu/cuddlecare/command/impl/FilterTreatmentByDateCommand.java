package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.utils.DateUtils;
import seedu.cuddlecare.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

//@@author Pavithra6-Srinivasan

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
            Map<String, LocalDate> dateRange = DateUtils.parseDateRange(args);
            if (dateRange == null) {
                return;
            }

            LocalDate fromDate = dateRange.get("from");
            LocalDate toDate = dateRange.get("to");

            if (!DateUtils.isDateValid(fromDate, toDate, getSyntax())) {
                return;
            }

            ArrayList<String> filteredTreatments = filterTreatments(fromDate, toDate);

            if (filteredTreatments.isEmpty()) {
                LOGGER.log(Level.INFO, "No treatments found in date range {0} to {1}",
                        new Object[]{fromDate, toDate});
                System.out.println("No treatments found from " + fromDate + " to " + toDate + ".");
                return;
            }

            Ui.printHeader("Found " + filteredTreatments.size() +
                    " treatment(s) from " + fromDate + " to " + toDate + ":");
            Ui.printList(filteredTreatments);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unexpected error during filter treatment by date execution", e);
            System.out.println("Unable to filter treatments by date. Please try again.");
        }
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
    private ArrayList<String> filterTreatments(LocalDate fromDate, LocalDate toDate) {
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

    private String getSyntax() {
        return "treatment-date from/DATE to/DATE";
    }
}
