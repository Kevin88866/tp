package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.util.logging.Logger;
import seedu.cuddlecare.ui.Ui;

/**
 * Lists all pets currently tracked by the application.
 * <p>
 * Usage: {@code list pets}
 * <br>Prints pets in 1-based indexing order.
 */
public class ListPetsCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ListPetsCommand.class.getName());

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
            Ui.println("No pets found.");
            LOGGER.fine("ListPetsCommand: empty list");
            return;
        }
        Ui.println("Here are your pets:");
        for (int i = 0; i < size; i++) {
            Pet p = pets.get(i);
            Ui.println((i + 1) + ". " + p.toString());
        }
    }
}
