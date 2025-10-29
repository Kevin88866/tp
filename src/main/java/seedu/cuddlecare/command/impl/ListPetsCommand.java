package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.util.List;
import java.util.logging.Logger;

/**
 * Lists all pets currently tracked by the application.
 * <p>
 * Usage: {@code list pets}
 * <br>Prints pets in 1-based indexing order.
 */
public class ListPetsCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ListPetsCommand.class.getName());

    // @@author HarshitSrivastavaHS
    private static final String SYNTAX = "list-pets";
    private static final String SHORT_DESCRIPTION = "Lists all pets in the application.";
    private static final String LONG_DESCRIPTION = "Displays all pets currently tracked by the application in numbered order. "
            + "Useful for viewing an overview of all registered pets at once.";
    private static final List<String> CATEGORIES = List.of("Pet");
    // @@author

    /** Repository of pets. */
    private final PetList pets;

    /**
     * @param pets repository of pets
     */
    public ListPetsCommand(PetList pets) {
        this.pets = pets;
    }

    /**
     * Executes the list operation. Ignores any arguments.
     *
     * @param args ignored
     */
    @Override
    public void exec(String args) {
        assert pets != null : "Pet list must not be null";
        int size = pets.size();
        if (size == 0) {
            System.out.println("No pets found.");
            LOGGER.fine("ListPetsCommand: empty list");
            return;
        }
        System.out.println("Here are your pets:");
        for (int i = 0; i < size; i++) {
            Pet p = pets.get(i);
            System.out.println((i + 1) + ". " + p.toString());
        }
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
