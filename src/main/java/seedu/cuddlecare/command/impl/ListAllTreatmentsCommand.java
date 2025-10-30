package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class ListAllTreatmentsCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ListAllTreatmentsCommand.class.getName());

    // @@author HarshitSrivastavaHS
    private static final String SYNTAX = "list-all-treatments";
    private static final String SHORT_DESCRIPTION = "Lists all treatments across all pets";
    private static final String LONG_DESCRIPTION = "Displays every treatment recorded for " +
            "all pets in the application, sorted by date in ascending order.";
    private static final List<String> CATEGORIES = List.of("Treatment");
    // @@author

    /**
     * A list of all pets.
     */
    private final PetList pets;

    /**
     * Initializes the ListAllTreatmentsCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public ListAllTreatmentsCommand(PetList pets) {
        this.pets = pets;
        assert pets != null : "pets cannot be null.";
    }

    /**
     * Executes the List All Treatments command.
     * <p>
     * Retrieves all treatments from every pet in the list, sorts them by date
     * in ascending order, and prints each treatment prefixed by the corresponding
     * pet's name. If no treatments are logged, a message is printed.
     *
     * @param args ignored, not used in this method.
     */
    @Override
    public void exec(String args) {
        if (!args.isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid format.");
            System.out.println("Invalid format. There should be no extra details after the command.");
            return;
        }
        ArrayList<String> sortedTreatments = (ArrayList<String>) pets.stream()
                .flatMap(pet -> pet.getTreatments().stream()
                        .map(treatment -> pet.getName() + ": " + treatment))
                .sorted(Comparator.comparing(s -> LocalDate.parse(s.split(" on ")[1]
                        .split("\n")[0])))
                .collect(toList());

        if (sortedTreatments.isEmpty()) {
            LOGGER.log(Level.WARNING, "No treatments logged.");
            System.out.println("No treatments logged.");
            return;
        }

        Ui.printList(sortedTreatments);
    }

    // @@author HarshitSrivastavaHS
    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public String getLongDescription() {
        return LONG_DESCRIPTION;
    }

    @Override
    public String getShortDescription() {
        return SHORT_DESCRIPTION;
    }

    @Override
    public List<String> getCategory() {
        return CATEGORIES;
    }
    // @@author
}
