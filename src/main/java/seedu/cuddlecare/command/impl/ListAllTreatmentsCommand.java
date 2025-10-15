package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class ListAllTreatmentsCommand implements Command {

    private static final Logger logger = Logger.getLogger(ListAllTreatmentsCommand.class.getName());

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
        logger.setLevel(Level.OFF);
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
            logger.log(Level.WARNING, "Invalid format.");
            System.out.println("Invalid format. There should be no extra details after the command.");
            return;
        }
        ArrayList<String> sortedTreatments = (ArrayList<String>) pets.stream()
                .flatMap(pet -> pet.getTreatments().stream()
                        .map(treatment -> pet.getName() + ": " + treatment))
                .sorted(Comparator.comparing(s -> LocalDate.parse(s.split(" on ")[1])))
                .collect(toList());

        if (sortedTreatments.isEmpty()) {
            logger.log(Level.WARNING, "No treatments logged.");
            System.out.println("No treatments logged.");
            return;
        }

        sortedTreatments.stream()
                .forEach(System.out::println);
    }
}
