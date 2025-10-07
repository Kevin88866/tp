package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

/**
 * Lists all pets currently tracked by the application.
 * <p>
 * Usage: {@code list pets}
 * <br>Prints pets in 1-based indexing order.
 */
public class ListPetsCommand implements Command {

    private final PetList pets;

    /**
     * Creates a command that lists all pets.
     *
     * @param pets the {@link PetList} to read from
     */
    public ListPetsCommand(PetList pets) {
        this.pets = pets;
    }

    /**
     * Executes the list operation. Arguments are ignored.
     *
     * @param args unused
     */
    @Override
    public void exec(String args) {
        int size = pets.size();
        if (size == 0) {
            System.out.println("No pets found.");
            return;
        }
        System.out.println("Here are your pets:");
        for (int i = 0; i < size; i++) {
            Pet p = pets.get(i);
            // rely on Pet#toString() formatting
            System.out.println((i + 1) + ". " + p.toString());
        }
    }
}
