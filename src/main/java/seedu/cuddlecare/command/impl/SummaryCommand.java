package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.utils.DateUtils;
import seedu.cuddlecare.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

import static java.util.stream.Collectors.toList;

public class SummaryCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SummaryCommand.class.getName());

    /**
     * A list of all pets.
     */
    private final PetList pets;

    /**
     * Initializes the SummaryCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public SummaryCommand(PetList pets) {
        this.pets = pets;
        assert pets != null : "pets cannot be null.";
    }

    /**
     * Executes the summary command.
     * <p>
     * Prints a list of treatments completed within the period specified.
     *
     * @param args user input containing the period for the summary
     */
    @Override
    public void exec(String args) {
        assert args != null : "Command arguments cannot be null";

        Map<String, LocalDate> dateRange = DateUtils.parseDateRange(args);
        if (dateRange == null) {
            return;
        }

        LocalDate fromDate = dateRange.get("from");
        LocalDate toDate = dateRange.get("to");

        if (!DateUtils.isDateValid(fromDate, toDate, getSyntax())) {
            return;
        }

        ArrayList<String> filteredList = filterTreatments(fromDate, toDate);

        if (filteredList.isEmpty()) {
            LOGGER.log(Level.INFO, "No treatments found in date range {0} to {1}",
                    new Object[]{fromDate, toDate});
            System.out.println("No treatments found from " + fromDate + " to " + toDate + ".");
            return;
        }

        Ui.printHeader("Treatment Summary from: " + fromDate + " to: " + toDate);
        Ui.printList(filteredList);
    }

    /**
     * Filters treatments across all pets that have been completed within the specified date range.
     * <p>
     * This method uses the Stream API to process the list of pets and their treatments.
     * It selects only treatments that meet both conditions:
     * <ul>
     *   <li>The treatment date falls between {@code fromDate} and {@code toDate} (inclusive).</li>
     *   <li>The treatment is marked as completed.</li>
     * </ul>
     * The filtered treatments are formatted as strings in the form {@code PetName: Treatment}
     * and returned in a list.</p>
     *
     * @param fromDate the start date of the filter range
     * @param toDate   the end date of the filter range
     * @return an {@link ArrayList} of formatted strings representing completed treatments
     *     within the specified date range
     */
    protected ArrayList<String> filterTreatments(LocalDate fromDate, LocalDate toDate) {
        ArrayList<String> filteredList = (ArrayList<String>) pets.stream()
                .flatMap(pet -> pet.getTreatments().stream()
                        .filter(treatment -> {
                            LocalDate date = treatment.getDate();
                            boolean isCompleted = treatment.isCompleted();
                            boolean isDateValid = !date.isBefore(fromDate) && !date.isAfter(toDate);
                            return isDateValid && isCompleted;
                        })
                        .map(treatment -> pet.getName() + ": " + treatment))
                .collect(toList());

        return filteredList;
    }

    protected String getSyntax() {
        return "summary from/DATE to/DATE";
    }
}
